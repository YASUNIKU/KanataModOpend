package com.github.yasuniku.kanata.block.office_shelf;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerMenu;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class OfficeShelfMenu extends BaseFurnitureContainerMenu {
    public OfficeShelfMenu(int id, Inventory inventory) {
        this(id, inventory, new ItemStackHandler(9), ContainerLevelAccess.NULL);
    }

    public OfficeShelfMenu(int id, Inventory inventory, IItemHandler dataInventory, ContainerLevelAccess access) {
        super(
            KanataMenus.OFFICE_SHELF.get(),
            id,
            inventory,
            dataInventory,
            access,
            KanataBlock.OFFICE_SHELF.get(),
            3,
            3
        );
    }
}
