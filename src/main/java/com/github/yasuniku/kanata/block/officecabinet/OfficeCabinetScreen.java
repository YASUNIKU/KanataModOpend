package com.github.yasuniku.kanata.block.officecabinet;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class OfficeCabinetScreen extends BaseFurnitureContainerScreen<OfficeCabinetMenu> {
    public OfficeCabinetScreen(OfficeCabinetMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
