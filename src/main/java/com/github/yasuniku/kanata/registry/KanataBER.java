package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.block.moduleinstaller.ModuleInstallerRenderer;
import com.github.yasuniku.kanata.block.moduleupdatemachine.ModuleUpdateMachineBlockEntityRenderer;
import com.github.yasuniku.kanata.block.portal.PortalRenderer;
import com.github.yasuniku.kanata.block.returnportal.ReturnPortalRenderer;
import com.github.yasuniku.kanata.block.shopshelf.ShopShelfBlockEntityRenderer;
import com.github.yasuniku.kanata.block.spawnbox.SpawnBoxBlockEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KanataBER {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(KanataBlockEntity.T1_PORTAL.get(), ctx -> new PortalRenderer());
        event.registerBlockEntityRenderer(KanataBlockEntity.RETURN_PORTAL.get(), ReturnPortalRenderer::new);
        event.registerBlockEntityRenderer(KanataBlockEntity.SPAWN_BOX.get(), ctx -> new SpawnBoxBlockEntityRenderer());
        event.registerBlockEntityRenderer(
            KanataBlockEntity.MODULE_INSTALLER.get(),
            ctx -> new ModuleInstallerRenderer()
        );
        event.registerBlockEntityRenderer(
            KanataBlockEntity.MODULE_UPDATE_MACHINE.get(),
            ModuleUpdateMachineBlockEntityRenderer::new
        );
        event.registerBlockEntityRenderer(KanataBlockEntity.SHOP_SHELF.get(), ShopShelfBlockEntityRenderer::new);
    }
}
