package com.github.yasuniku.kanata.block.officecabinet;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerMenu;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class OfficeCabinetMenu extends BaseFurnitureContainerMenu {
    public OfficeCabinetMenu(int id, Inventory inventory) {
        this(id, inventory, new ItemStackHandler(9), ContainerLevelAccess.NULL);
    }

    public OfficeCabinetMenu(int id, Inventory inventory, IItemHandler dataInventory, ContainerLevelAccess access) {
        super(
            KanataMenus.OFFICE_CABINET.get(),
            id,
            inventory,
            dataInventory,
            access,
            KanataBlock.OFFICE_CABINET.get(),
            3,
            3
        );
    }
}
