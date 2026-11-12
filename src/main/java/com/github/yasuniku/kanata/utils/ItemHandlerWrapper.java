package com.github.yasuniku.kanata.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerWrapper implements IItemHandler {
    protected IItemHandler originalHandler;

    public ItemHandlerWrapper(IItemHandler originalHandler) {
        this.originalHandler = originalHandler;
    }

    @Override
    public int getSlots() {
        return originalHandler.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return originalHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return originalHandler.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return originalHandler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return originalHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return originalHandler.isItemValid(slot, stack);
    }
}
