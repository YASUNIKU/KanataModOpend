package com.github.yasuniku.kanata.block.moduleslotadder;

import com.github.yasuniku.kanata.KanataMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class ModuleSlotAdderScreen extends ItemCombinerScreen<ModuleSlotAdderMenu> {
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "textures/gui/module_installer_bg.png");
    private static final ResourceLocation SLOT_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/module_installer_itemslot.png");

    public ModuleSlotAdderScreen(ModuleSlotAdderMenu menu, Inventory inv, Component title) {
        super(menu, inv, title, TEXTURE);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partial, int mouseX, int mouseY) {
        gfx.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        // スロット枠の描画
        for (Slot slot : this.menu.slots) {
            int x = this.leftPos + slot.x;
            int y = this.topPos + slot.y;

            // スロット枠
            gfx.blit(SLOT_TEXTURE, x - 1, y - 1, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    protected void renderErrorIcon(GuiGraphics pGuiGraphics, int pX, int pY) {
    }
}
