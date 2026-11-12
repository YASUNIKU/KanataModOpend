package com.github.yasuniku.kanata.block.moduleinstaller;

import com.github.yasuniku.kanata.item.module.ModuleHelper;
import com.github.yasuniku.kanata.item.module.ModuleInstance;
import com.github.yasuniku.kanata.registry.KanataItem;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModuleInstallerMenu extends AbstractContainerMenu {
    private boolean isSettingItem = false;
    private final ContainerLevelAccess access;
    private final Container toolContainer = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            isSettingItem = true;
            shrinkModules();
            if (!toolContainer.getItem(0).isEmpty()) {
                List<ItemStack> moduleItems = ModuleHelper.generateModuleItemsFromTool(toolContainer.getItem(0));

                for (int i = 0; i < moduleItems.size(); i++) {
                    moduleContainer.setItem(i, moduleItems.get(i));
                }
            }
            isSettingItem = false;
        }
    };
    private final Container moduleContainer = new SimpleContainer(5) {
        public void setChanged() {
            if (isSettingItem) {
                return;
            }
            applyModules();
        }
    };

    public ModuleInstallerMenu(int id, Inventory playerInv, ContainerLevelAccess access) {
        super(KanataMenus.MODULE_INSTALLER.get(), id);
        this.access = access;

        // Tool Slot
        this.addSlot(new Slot(toolContainer, 0, 44, 11));

        // Module Slots
        for (int i = 0; i < 5; i++) {
            int slotIndex = i;
            this.addSlot(new Slot(moduleContainer, slotIndex, 100, 11 + i * 23) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    ItemStack tool = toolContainer.getItem(0);
                    int max = ModuleHelper.getMaxInstallableModuleCount(tool);
                    return slotIndex < max && stack.is(KanataItem.MODULE_ITEM.get());
                }
            });
        }

        addPlayerInventory(playerInv);
    }

    public ModuleInstallerMenu(int id, Inventory playerInv) {
        this(id, playerInv, ContainerLevelAccess.NULL);
    }

    private void addPlayerInventory(Inventory playerInv) {
        // メイン 3×9
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }

        // ホットバー
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 198));
        }
    }

    public void applyModules() {
        ItemStack tool = toolContainer.getItem(0);
        if (tool.isEmpty()) {
            return;
        }

        ItemStack result = tool.copy();
        List<ModuleInstance> moduleItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var inst = ModuleInstance.fromItemStack(moduleContainer.getItem(i));
            if (inst.isEmpty()) {
                continue;
            }
            moduleItems.add(inst.get());
        }
        ModuleHelper.setModules(result, moduleItems);
        toolContainer.setItem(0, result);
    }

    // --- ツール取り出し時にモジュールを消費 ---
    private void shrinkModules() {
        for (int i = 0; i < 5; i++) {
            ItemStack moduleItem = moduleContainer.getItem(i);
            if (!moduleItem.isEmpty()) {
                moduleItem.shrink(1);
                moduleContainer.setItem(i, moduleItem);
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack original = slot.getItem();
        newStack = original.copy();

        int inputStart = 0;
        int inputEnd = 6; // 0〜5
        int playerStart = 6;
        int playerEnd = this.slots.size();

        if (index < inputEnd) {
            // 入力 → プレイヤー
            if (!this.moveItemStackTo(original, playerStart, playerEnd, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            // プレイヤー → 入力
            if (!this.moveItemStackTo(original, inputStart, inputEnd, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (original.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, blockPos) -> this.clearContainer(pPlayer, this.toolContainer));
    }
}
