package com.github.yasuniku.kanata.block.moduleupdatemachine;

import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModuleUpdateMachineMenu extends AbstractContainerMenu {

    private final ContainerData data;
    private final ContainerLevelAccess access;

    public ModuleUpdateMachineMenu(int containerId, Inventory inv) {
        this(
            containerId,
            inv,
            new ItemStackHandler(5),
            new SimpleContainerData(ProgressDataSlot.COUNT),
            ContainerLevelAccess.NULL
        );
    }

    public ModuleUpdateMachineMenu(
        int id,
        Inventory playerInv,
        IItemHandler dataInventory,
        ContainerData data,
        ContainerLevelAccess access
    ) {
        super(KanataMenus.MODULE_UPDATE_MACHINE.get(), id);

        this.access = access;
        // input slots (0〜3)
        this.addSlot(new SlotItemHandler(dataInventory, 0, 44, 20));
        this.addSlot(new SlotItemHandler(dataInventory, 1, 62, 20));
        this.addSlot(new SlotItemHandler(dataInventory, 2, 44, 38));
        this.addSlot(new SlotItemHandler(dataInventory, 3, 62, 38));

        // result slot (4)
        this.addSlot(new SlotItemHandler(dataInventory, 4, 120, 29) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false; // result はプレイヤーが置けない
            }
        });

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        // ---------------------------------------------------------
        // progress などの同期用データ
        // ---------------------------------------------------------
        this.data = data;
        this.addDataSlots(this.data);
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
        return AbstractContainerMenu.stillValid(this.access, pPlayer, KanataBlock.MODULE_UPDATE_MACHINE.get());
    }

    // ---------------------------------------------------------
    // shiftクリック処理
    // ---------------------------------------------------------
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            // container slots: 0〜4
            if (index < 5) {
                // プレイヤーインベントリへ移動
                if (!this.moveItemStackTo(original, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // input スロットへ移動
                if (!this.moveItemStackTo(original, 0, 4, false)) {
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

    // ---------------------------------------------------------
    // progress の同期（Screen側で使う）
    // ---------------------------------------------------------
    public int getProgress() {
        return data.get(ProgressDataSlot.PROGRESS.ordinal());
    }

    public int getMaxProgress() {
        return data.get(ProgressDataSlot.MAX_PROGRESS.ordinal());
    }
}
