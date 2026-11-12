package com.github.yasuniku.kanata.block.moduleinstaller;

import com.github.yasuniku.kanata.item.module.ModuleHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class ModuleInstallerScreen extends AbstractContainerScreen<ModuleInstallerMenu> {
    private static final ResourceLocation GUI_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/module_installer_bg.png");

    private static final ResourceLocation SLOT_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/module_installer_itemslot.png");

    public ModuleInstallerScreen(ModuleInstallerMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 222;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        // 背景
        gfx.blit(
            GUI_TEXTURE,
            this.leftPos,
            this.topPos,
            0,
            0,
            this.imageWidth,
            this.imageHeight,
            this.imageWidth,
            this.imageHeight
        );

        // スロット枠の描画（ここで描く）
        for (Slot slot : this.menu.slots) {
            int index = slot.index;

            int x = this.leftPos + slot.x;
            int y = this.topPos + slot.y;

            // スロット枠
            gfx.blit(SLOT_TEXTURE, x - 1, y - 1, 0, 0, 18, 18, 18, 18);

            // モジュールスロットの有効/無効化
            if (index >= 1 && index <= 5) {
                ItemStack tool = this.menu.getSlot(0).getItem();
                int maxModules = ModuleHelper.getMaxInstallableModuleCount(tool);

                if (index > maxModules) {
                    // 無効スロット → 暗くする
                    gfx.fill(x, y, x + 16, y + 16, 0x88000000);
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTicks);
        this.renderTooltip(gfx, mouseX, mouseY);
    }
}
