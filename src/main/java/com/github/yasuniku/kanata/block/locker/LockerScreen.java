package com.github.yasuniku.kanata.block.locker;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LockerScreen extends BaseFurnitureContainerScreen<LockerMenu> {
    public LockerScreen(LockerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
