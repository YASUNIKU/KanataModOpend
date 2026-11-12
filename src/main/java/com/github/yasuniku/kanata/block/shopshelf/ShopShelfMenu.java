package com.github.yasuniku.kanata.block.shopshelf;

import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ShopShelfMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;

    public ShopShelfMenu(int containerId, Inventory inv) {
        this(containerId, inv, new ItemStackHandler(6), ContainerLevelAccess.NULL);
    }

    public ShopShelfMenu(int id, Inventory playerInv, IItemHandler dataInventory, ContainerLevelAccess access) {
        super(KanataMenus.SHOP_SHELF.get(), id);
        this.access = access;

        // 棚の6スロットを追加
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                int index = row * 3 + col;
                int x = 44 + col * 18;
                int y = 20 + row * 18;
                this.addSlot(new SlotItemHandler(dataInventory, index, x, y));
            }
        }

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            // container slots: 0〜4
            if (pIndex < 6) {
                // プレイヤーインベントリへ移動
                if (!this.moveItemStackTo(original, 6, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // input スロットへ移動
                if (!this.moveItemStackTo(original, 0, 6, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (original.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    // プレイヤーインベントリ（9×3）
    private void addPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    // ホットバー（9）
    private void addPlayerHotbar(Inventory playerInv) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, KanataBlock.SHOP_SHELF.get());
    }
}
