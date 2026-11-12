package com.github.yasuniku.kanata.block.officedesk;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerMenu;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class OfficeDeskMenu extends BaseFurnitureContainerMenu {
    public OfficeDeskMenu(int containerId, Inventory inv) {
        this(containerId, inv, new ItemStackHandler(4), ContainerLevelAccess.NULL);
    }

    public OfficeDeskMenu(int id, Inventory inventory, IItemHandler dataInventory, ContainerLevelAccess access) {
        super(KanataMenus.OFFICE_DESK.get(), id, inventory, dataInventory, access, KanataBlock.OFFICE_DESK.get(), 2, 2);
    }
}
