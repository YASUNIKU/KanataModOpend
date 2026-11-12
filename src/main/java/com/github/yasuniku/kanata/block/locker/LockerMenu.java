package com.github.yasuniku.kanata.block.locker;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerMenu;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.registry.KanataMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LockerMenu extends BaseFurnitureContainerMenu {
    public LockerMenu(int containerId, Inventory inv) {
        this(containerId, inv, new ItemStackHandler(12), ContainerLevelAccess.NULL);
    }

    public LockerMenu(int id, Inventory inventory, IItemHandler dataInventory, ContainerLevelAccess access) {
        super(KanataMenus.LOCKER.get(), id, inventory, dataInventory, access, KanataBlock.LOCKER.get(), 4, 3);
    }
}
