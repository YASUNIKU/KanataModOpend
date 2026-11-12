package com.github.yasuniku.kanata.block.office_shelf;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class OfficeShelfScreen extends BaseFurnitureContainerScreen<OfficeShelfMenu> {
    public OfficeShelfScreen(OfficeShelfMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
