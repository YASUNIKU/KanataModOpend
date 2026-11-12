package com.github.yasuniku.kanata.block.basefurniturecontainer;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BaseFurnitureContainerMenu extends AbstractContainerMenu {
    protected final Block targetBlock;
    protected final ContainerLevelAccess access;
    protected final int rows;
    protected final int cols;

    public BaseFurnitureContainerMenu(
        MenuType<?> menuType,
        int containerId,
        Inventory playerInv,
        IItemHandler dataInventory,
        ContainerLevelAccess access,
        Block targetBlock,
        int rows,
        int cols
    ) {
        super(menuType, containerId);
        this.targetBlock = targetBlock;
        this.access = access;
        this.rows = rows;
        this.cols = cols;

        var minSize = rows * cols;
        if (dataInventory.getSlots() < minSize) {
            throw new IllegalArgumentException("Container size " + dataInventory.getSlots() + " is smaller than expected " + minSize);
        }

        addFurnitureSlots(dataInventory);
        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);
    }

    protected void addFurnitureSlots(IItemHandler dataInventory) {
        int slotIndex = 0;

        int startX = 89 - (18 * cols / 2);
        int startY = 47 - (18 * rows / 2);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                addSlot(new SlotItemHandler(dataInventory, slotIndex++, startX + col * 18, startY + row * 18));
            }
        }
    }

    protected void addPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {

                addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    protected void addPlayerHotbar(Inventory playerInv) {
        for (int col = 0; col < 9; ++col) {
            addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        int containerSlots = rows * cols;

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();

            result = stack.copy();

            if (index < containerSlots) {
                if (!moveItemStackTo(stack, containerSlots, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(stack, 0, containerSlots, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, targetBlock);
    }
}
