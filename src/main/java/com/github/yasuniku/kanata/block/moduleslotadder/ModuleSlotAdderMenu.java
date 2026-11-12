package com.github.yasuniku.kanata.block.moduleslotadder;

import com.github.yasuniku.kanata.item.module.ModuleHelper;
import com.github.yasuniku.kanata.registry.KanataItem;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ModuleSlotAdderMenu extends ItemCombinerMenu {

    public ModuleSlotAdderMenu(int containerId, Inventory inv, ContainerLevelAccess access) {
        super(KanataMenus.MODULE_SLOT_ADDER.get(), containerId, inv, access);
    }

    // クライアント側用
    public ModuleSlotAdderMenu(int containerId, Inventory inv) {
        this(containerId, inv, ContainerLevelAccess.NULL);
    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        //noinspection unused
        return ItemCombinerMenuSlotDefinition
            .create()
            .withSlot(0, 30, 30, (_unused) -> true)
            .withSlot(1, 80, 30, (_unused) -> true)
            .withResultSlot(2, 130, 30)
            .build();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            // 0: ツール, 1: ModuleItem, 2: 出力
            if (index == 2) {
                // 出力スロット → プレイヤーインベントリへ
                if (!this.moveItemStackTo(original, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(original, newStack);
            } else if (index < 2) {
                // 入力スロット → プレイヤーインベントリへ
                if (!this.moveItemStackTo(original, 3, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // プレイヤーインベントリ → 入力スロットへ
                if (!this.moveItemStackTo(original, 0, 2, false)) {
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

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return hasStack;
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        int required = requiredMaterial(this.inputSlots.getItem(0));
        stack.onCraftedBy(player.level(), player, stack.getCount());
        this.shrinkStackInSlot(0, 1);
        this.shrinkStackInSlot(1, required);
    }

    @Override
    protected boolean isValidBlock(BlockState pState) {
        return true;
    }

    @Override
    public void createResult() {
        ItemStack target = this.inputSlots.getItem(0);
        ItemStack material = this.inputSlots.getItem(1);

        int currentSlots = ModuleHelper.getMaxInstallableModuleCount(target);
        if (currentSlots < 5 && material.is(KanataItem.MODULE_SLOT_PARTS.get()) && requiredMaterial(target) <= material.getCount()) {
            ItemStack result = target.copy();
            ModuleHelper.addMaxInstallableModuleCount(result, 1);
            this.resultSlots.setItem(0, result);
            return;
        }
        this.resultSlots.setItem(0, ItemStack.EMPTY);
    }

    private void shrinkStackInSlot(int pIndex, int decrement) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        if (!itemstack.isEmpty()) {
            itemstack.shrink(decrement);
            this.inputSlots.setItem(pIndex, itemstack);
        }
    }

    private static int requiredMaterial(ItemStack target) {
        return 1 << ModuleHelper.getMaxInstallableModuleCount(target);
    }
}
