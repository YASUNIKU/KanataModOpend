package com.github.yasuniku.kanata.block.basefurniturecontainer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class BaseFurnitureContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private static final ResourceLocation BG_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/module_installer_bg.png");
    private static final ResourceLocation SLOT_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/module_installer_itemslot.png");

    public BaseFurnitureContainerScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        RenderSystem.setShaderTexture(0, BG_TEXTURE);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // 背景画像を描画
        guiGraphics.blit(BG_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        // スロット枠の描画
        for (Slot slot : this.menu.slots) {
            int slotX = this.leftPos + slot.x;
            int slotY = this.topPos + slot.y;

            // スロット枠
            guiGraphics.blit(SLOT_TEXTURE, slotX - 1, slotY - 1, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
