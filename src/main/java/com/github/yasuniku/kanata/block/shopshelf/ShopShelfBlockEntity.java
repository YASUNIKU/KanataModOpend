package com.github.yasuniku.kanata.block.shopshelf;

import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jspecify.annotations.Nullable;

public class ShopShelfBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler items = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged(); // NBT保存 & クライアントへ同期
            // クライアント側へモデル更新通知
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public ShopShelfBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.SHOP_SHELF.get(), pos, state);
    }

    public ItemStackHandler getItems() {
        return items;
    }

    // GUIタイトル
    @Override
    public Component getDisplayName() {
        return Component.literal("Shelf");
    }

    // GUIメニューを開く
    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        var level = getLevel();
        if (level == null) {
            return null;
        }
        return new ShopShelfMenu(
            pContainerId,
            pPlayerInventory,
            this.items,
            ContainerLevelAccess.create(level, getBlockPos())
        );
    }

    // NBT保存
    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("Items", items.serializeNBT());
        super.saveAdditional(tag);
    }

    // NBT読み込み
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.deserializeNBT(tag.getCompound("Items"));
    }

    // クライアント同期（BERがアイテムを描画するために必要）
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
