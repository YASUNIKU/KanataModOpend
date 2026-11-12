package com.github.yasuniku.kanata.block.moduleupdatemachine;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.item.module.ModuleHelper;
import com.github.yasuniku.kanata.item.module.ModuleInstance;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import com.github.yasuniku.kanata.utils.ItemHandlerWrapper;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jspecify.annotations.Nullable;

import java.util.stream.Collectors;

public class ModuleUpdateMachineBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler items = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            if (0 <= slot && slot <= 3) {
                tryStartProcess();
            }
            syncToClient();
        }
    };

    private void syncToClient() {
        if (this.level != null && !this.level.isClientSide) {
            BlockState state = this.getBlockState();
            this.level.sendBlockUpdated(this.worldPosition, state, state, ModuleUpdateMachineBlock.UPDATE_CLIENTS);
        }
    }

    ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (ProgressDataSlot.fromIndex(pIndex)) {
                case PROGRESS -> progress;
                case MAX_PROGRESS -> maxProgress;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            throw new RuntimeException("使用しないsetterが呼び出されたよ");
        }

        @Override
        public int getCount() {
            return ProgressDataSlot.COUNT;
        }
    };
    //-1の時は処理をしてない
    private int progress = -1;
    private final int maxProgress = 200;

    private final boolean[] hasItems = new boolean[items.getSlots()];
    private final LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> new ItemHandlerWrapper(items) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot < 4) {
                return super.insertItem(slot, stack, simulate);
            }
            return stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == 4) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    });

    public ModuleUpdateMachineBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.MODULE_UPDATE_MACHINE.get(), pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(Level level, BlockPos pos, BlockState state, ModuleUpdateMachineBlockEntity be) {
        if (level.isClientSide) {
            return;
        }

        if (be.isProcessing()) {
            be.progress++;

            if (be.progress >= be.maxProgress) {
                be.finishProcess(level.random.nextInt());
            }

            be.setChanged();
        }
    }

    private boolean isProcessing() {
        return this.progress != -1;
    }

    private boolean isValidIngredients() {

        var rarity = ModuleInstance.fromItemStack(this.items.getStackInSlot(0)).map(ModuleInstance::getRarity);

        if (rarity.flatMap(ModuleRarity::nextRarity).isEmpty()) {
            return false;
        }
        return Utils
            .toStream(this.items)
            .limit(4)
            .map(ModuleInstance::fromItemStack)
            .allMatch(moduleStack -> moduleStack.map(ModuleInstance::getRarity).equals(rarity));
    }

    private void tryStartProcess() {
        if (isValidIngredients()) {
            startProcess();
        } else {
            stopProcess();
        }
    }

    private void startProcess() {
        progress = 0;
        setChanged();
    }

    private void stopProcess() {
        progress = -1;
        setChanged();
    }

    private void finishProcess(int seed) {
        var moduleItemMaterial = items.getStackInSlot(0);
        var inst = ModuleInstance.fromItemStack(moduleItemMaterial);
        if (inst.isEmpty()) {
            KanataMod.LOGGER.error("不正なmoduleインスタンスが生成されたよ");
            stopProcess();
            return;
        }
        var rarity = inst.get().getRarity();

        // input を消費
        for (int i = 0; i < 4; i++) {
            items.getStackInSlot(i).shrink(1);
        }

        var result = rarity
            .nextRarity()
            .map(x -> ModuleHelper.generateModuleItemByRarity(x, seed))
            .orElseGet(() -> Utils.createErrorItem("不正なレアリティが入ったよ"));

        items.setStackInSlot(4, result);

        stopProcess();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Module Update Machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        var level = getLevel();
        if (level == null) {
            return null;
        }
        return new ModuleUpdateMachineMenu(
            pContainerId,
            pPlayerInventory,
            this.items,
            this.data,
            ContainerLevelAccess.create(level, getBlockPos())
        );
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("Progress", progress);
        tag.put("Inventory", items.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.progress = tag.getInt("Progress");
        this.items.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        var hasItems = Utils
            .toStream(items)
            .map(x -> ByteTag.valueOf(!x.isEmpty()))
            .collect(Collectors.toCollection(ListTag::new));
        tag.put("HasItems", hasItems);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        var list = tag.getList("HasItems", CompoundTag.TAG_BYTE);
        for (int i = 0; i < list.size(); i++) {
            hasItems[i] = ((ByteTag) list.get(i)).getAsByte() != 0;
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        var tag = pkt.getTag();
        if (tag == null) {
            return;
        }
        handleUpdateTag(tag);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void drops() {
        if (this.level != null && !this.level.isClientSide) {
            Utils.dropContents(this.getLevel(), this.getBlockPos(), Utils.toStream(this.items));
        }
    }

    public boolean shouldRenderModule(int index) {
        return hasItems[index];
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventoryHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryHandler.invalidate();
    }
}
