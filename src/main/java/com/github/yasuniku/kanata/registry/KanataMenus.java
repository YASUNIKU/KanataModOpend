package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.block.locker.LockerMenu;
import com.github.yasuniku.kanata.block.moduleinstaller.ModuleInstallerMenu;
import com.github.yasuniku.kanata.block.moduleslotadder.ModuleSlotAdderMenu;
import com.github.yasuniku.kanata.block.moduleupdatemachine.ModuleUpdateMachineMenu;
import com.github.yasuniku.kanata.block.office_shelf.OfficeShelfMenu;
import com.github.yasuniku.kanata.block.officecabinet.OfficeCabinetMenu;
import com.github.yasuniku.kanata.block.officedesk.OfficeDeskMenu;
import com.github.yasuniku.kanata.block.shopshelf.ShopShelfMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KanataMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
        DeferredRegister.create(ForgeRegistries.MENU_TYPES, KanataMod.MOD_ID);

    public static final RegistryObject<MenuType<ModuleInstallerMenu>> MODULE_INSTALLER =
        MENUS.register("module_installer", () -> new MenuType<>(ModuleInstallerMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final RegistryObject<MenuType<ModuleSlotAdderMenu>> MODULE_SLOT_ADDER =
        MENUS.register("module_slot_adder", () -> new MenuType<>(ModuleSlotAdderMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<ModuleUpdateMachineMenu>> MODULE_UPDATE_MACHINE = MENUS.register(
        "module_update_machine",
        () -> new MenuType<>(ModuleUpdateMachineMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );
    public static final RegistryObject<MenuType<ShopShelfMenu>> SHOP_SHELF =
        MENUS.register("shop_shelf", () -> new MenuType<>(ShopShelfMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<OfficeDeskMenu>> OFFICE_DESK =
        MENUS.register("office_desk", () -> new MenuType<>(OfficeDeskMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<OfficeCabinetMenu>> OFFICE_CABINET = MENUS.register(
        "office_cabinet_block",
        () -> new MenuType<>(OfficeCabinetMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );
    public static final RegistryObject<MenuType<OfficeShelfMenu>> OFFICE_SHELF =
        MENUS.register("office_shelf_block", () -> new MenuType<>(OfficeShelfMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<LockerMenu>> LOCKER =
        MENUS.register("locker", () -> new MenuType<>(LockerMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
