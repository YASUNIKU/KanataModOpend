package com.github.yasuniku.kanata.block.officedesk;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class OfficeDeskScreen extends BaseFurnitureContainerScreen<OfficeDeskMenu> {
    public OfficeDeskScreen(OfficeDeskMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
