package com.github.yasuniku.kanata.block.basefurniturecontainer;

import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public abstract class BaseFurnitureContainerBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler items;
    protected final int size;
    protected final String translationKey;

    protected BaseFurnitureContainerBlockEntity(
        BlockEntityType<?> type,
        BlockPos pos,
        BlockState state,
        int size,
        String translationKey
    ) {
        super(type, pos, state);

        this.size = size;
        this.translationKey = translationKey;
        this.items = new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    public ItemStackHandler getItems() {
        return items;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(translationKey);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("Items", items.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        items.deserializeNBT(tag.getCompound("Items"));
    }

    public void drops() {
        if (this.level != null && !this.level.isClientSide) {
            Utils.dropContents(this.level, this.getBlockPos(), Utils.toStream(this.items));
        }
    }
}
