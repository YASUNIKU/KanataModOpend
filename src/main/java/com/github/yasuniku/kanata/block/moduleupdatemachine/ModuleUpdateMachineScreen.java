package com.github.yasuniku.kanata.block.moduleupdatemachine;

import com.github.yasuniku.kanata.KanataMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class ModuleUpdateMachineScreen extends AbstractContainerScreen<ModuleUpdateMachineMenu> {

    private static final ResourceLocation BG_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "textures/gui/module_installer_bg.png");
    private static final ResourceLocation PROGRESS_BAR_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "textures/gui/progress_bar.png");
    private static final ResourceLocation SLOT_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/module_installer_itemslot.png");

    public ModuleUpdateMachineScreen(ModuleUpdateMachineMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {

        // 背景描画
        guiGraphics.blit(BG_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        guiGraphics.blit(
            PROGRESS_BAR_TEXTURE, leftPos + 80, topPos + 34,   // GUI 上の描画位置
            0, 0,                      // テクスチャの開始位置
            32, 16, 32, 32
        );
        // 加工進捗バー（かまど風）
        int progress = menu.getProgress();
        int max = menu.getMaxProgress();
        if (progress > 0) {
            int barWidth = (int) (32 * ((float) progress / max));
            guiGraphics.blit(
                PROGRESS_BAR_TEXTURE, leftPos + 80, topPos + 34,   // GUI 上の描画位置
                0, 16,                      // テクスチャの開始位置
                barWidth, 16, 32, 32
            );
        }
        // スロット枠の描画
        for (Slot slot : this.menu.slots) {
            int x = this.leftPos + slot.x;
            int y = this.topPos + slot.y;

            // スロット枠
            guiGraphics.blit(SLOT_TEXTURE, x - 1, y - 1, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
