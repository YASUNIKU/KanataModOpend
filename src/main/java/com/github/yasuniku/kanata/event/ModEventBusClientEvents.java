package com.github.yasuniku.kanata.event;

import com.github.yasuniku.kanata.block.locker.LockerScreen;
import com.github.yasuniku.kanata.block.moduleinstaller.ModuleInstallerScreen;
import com.github.yasuniku.kanata.block.moduleslotadder.ModuleSlotAdderScreen;
import com.github.yasuniku.kanata.block.moduleupdatemachine.ModuleUpdateMachineModel;
import com.github.yasuniku.kanata.block.moduleupdatemachine.ModuleUpdateMachineScreen;
import com.github.yasuniku.kanata.block.office_shelf.OfficeShelfScreen;
import com.github.yasuniku.kanata.block.officecabinet.OfficeCabinetScreen;
import com.github.yasuniku.kanata.block.officedesk.OfficeDeskScreen;
import com.github.yasuniku.kanata.block.shopshelf.ShopShelfScreen;
import com.github.yasuniku.kanata.entity.client.RobotModel;
import com.github.yasuniku.kanata.entity.client.RobotRenderer;
import com.github.yasuniku.kanata.entity.client.SeatRenderer;
import com.github.yasuniku.kanata.item.module.ModuleModel;
import com.github.yasuniku.kanata.registry.KanataEntity;
import com.github.yasuniku.kanata.registry.KanataItem;
import com.github.yasuniku.kanata.registry.KanataMenus;
import com.github.yasuniku.kanata.registry.KanataModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Set;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KanataModelLayers.ROBOT_LAYER, RobotModel::createBodyLayer);
        event.registerLayerDefinition(
            KanataModelLayers.MODULE_UPDATE_MACHINE,
            ModuleUpdateMachineModel::createBodyLayer
        );
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(KanataEntity.ROBOT.get(), RobotRenderer::new);
        EntityRenderers.register(KanataEntity.SEAT.get(), SeatRenderer::new);

        event.enqueueWork(
            // Assume RegistryObject<MenuType<MyMenu>> MY_MENU
            // Assume MyContainerScreen<MyMenu> which takes in three parameters
            () -> MenuScreens.register(KanataMenus.MODULE_INSTALLER.get(), ModuleInstallerScreen::new));
        event.enqueueWork(() -> MenuScreens.register(KanataMenus.MODULE_SLOT_ADDER.get(), ModuleSlotAdderScreen::new));
        event.enqueueWork(() -> MenuScreens.register(
            KanataMenus.MODULE_UPDATE_MACHINE.get(),
            ModuleUpdateMachineScreen::new
        ));
        event.enqueueWork(() -> MenuScreens.register(KanataMenus.SHOP_SHELF.get(), ShopShelfScreen::new));
        event.enqueueWork(() -> MenuScreens.register(KanataMenus.OFFICE_DESK.get(), OfficeDeskScreen::new));
        event.enqueueWork(() -> MenuScreens.register(KanataMenus.OFFICE_CABINET.get(), OfficeCabinetScreen::new));
        event.enqueueWork(() -> MenuScreens.register(KanataMenus.OFFICE_SHELF.get(), OfficeShelfScreen::new));
        event.enqueueWork(() -> MenuScreens.register(KanataMenus.LOCKER.get(), LockerScreen::new));
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
            (stack, tintIndex) -> ((ForgeSpawnEggItem) stack.getItem()).getColor(tintIndex),
            KanataItem.ROBOT_SPAWN_EGG.get()
        );
    }

    @SubscribeEvent
    public static void replaceModuleModel(ModelEvent.ModifyBakingResult e) {
        ModelResourceLocation key =
            new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "module_item"), "inventory");
        BakedModel oldModel = e.getModels().get(key);
        if (oldModel != null) {
            e.getModels().put(key, new ModuleModel(oldModel));
        }
    }

    @SubscribeEvent
    public static void addModuleModels(ModelEvent.RegisterAdditional e) {
        Set<ResourceLocation> locs = Minecraft.getInstance().getResourceManager().listResources(
            "models",
            loc -> MOD_ID.equals(loc.getNamespace()) && loc.getPath().contains("/modules/") && loc
                .getPath()
                .endsWith(".json")
        ).keySet();
        for (ResourceLocation s : locs) {
            String path = s.getPath().substring("models/".length(), s.getPath().length() - ".json".length());
            e.register(ResourceLocation.fromNamespaceAndPath(MOD_ID, path));
        }
    }
}
