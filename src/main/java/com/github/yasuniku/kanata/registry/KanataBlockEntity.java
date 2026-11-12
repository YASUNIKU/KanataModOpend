package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.block.locker.LockerBlockEntity;
import com.github.yasuniku.kanata.block.moduleinstaller.ModuleInstallerBlockEntity;
import com.github.yasuniku.kanata.block.moduleslotadder.ModuleSlotAdderBlockEntity;
import com.github.yasuniku.kanata.block.moduleupdatemachine.ModuleUpdateMachineBlockEntity;
import com.github.yasuniku.kanata.block.office_shelf.OfficeShelfBlockEntity;
import com.github.yasuniku.kanata.block.officecabinet.OfficeCabinetBlockEntity;
import com.github.yasuniku.kanata.block.officedesk.OfficeDeskBlockEntity;
import com.github.yasuniku.kanata.block.portal.PortalBlockEntity;
import com.github.yasuniku.kanata.block.returnportal.ReturnPortalBlockEntity;
import com.github.yasuniku.kanata.block.shopshelf.ShopShelfBlockEntity;
import com.github.yasuniku.kanata.block.spawnbox.SpawnBoxBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class KanataBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<PortalBlockEntity>> T1_PORTAL = BLOCK_ENTITIES.register(
        "t1_portal",
        () -> BlockEntityType.Builder.of(PortalBlockEntity::new, KanataBlock.T1_PORTAL_BLOCK.get()).build(null)
    );

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<ReturnPortalBlockEntity>> RETURN_PORTAL =
        BLOCK_ENTITIES.register(
            "return_portal",
            () -> BlockEntityType.Builder
                .of(ReturnPortalBlockEntity::new, KanataBlock.RETURN_PORTAL_BLOCK.get())
                .build(null)
        );

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<SpawnBoxBlockEntity>> SPAWN_BOX = BLOCK_ENTITIES.register(
        "spawn_box",
        () -> BlockEntityType.Builder.of(SpawnBoxBlockEntity::new, KanataBlock.SPAWN_BOX_BLOCK.get()).build(null)
    );
    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<ModuleInstallerBlockEntity>> MODULE_INSTALLER =
        BLOCK_ENTITIES.register(
            "module_installer",
            () -> BlockEntityType.Builder
                .of(ModuleInstallerBlockEntity::new, KanataBlock.MODULE_INSTALLER.get())
                .build(null)
        );

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<ModuleSlotAdderBlockEntity>> MODULE_SLOT_ADDER =
        BLOCK_ENTITIES.register(
            "module_slot_adder",
            () -> BlockEntityType.Builder
                .of(ModuleSlotAdderBlockEntity::new, KanataBlock.MODULE_SLOT_ADDER.get())
                .build(null)
        );

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<ModuleUpdateMachineBlockEntity>> MODULE_UPDATE_MACHINE =
        BLOCK_ENTITIES.register(
            "module_update_machine",
            () -> BlockEntityType.Builder
                .of(ModuleUpdateMachineBlockEntity::new, KanataBlock.MODULE_UPDATE_MACHINE.get())
                .build(null)
        );
    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<ShopShelfBlockEntity>> SHOP_SHELF = BLOCK_ENTITIES.register(
        "shop_shelf",
        () -> BlockEntityType.Builder.of(ShopShelfBlockEntity::new, KanataBlock.SHOP_SHELF.get()).build(null)
    );
    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<OfficeDeskBlockEntity>> OFFICE_DESK = BLOCK_ENTITIES.register(
        "office_desk",
        () -> BlockEntityType.Builder.of(OfficeDeskBlockEntity::new, KanataBlock.OFFICE_DESK.get()).build(null)
    );
    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<OfficeCabinetBlockEntity>> OFFICE_CABINET =
        BLOCK_ENTITIES.register(
            "office_cabinet_block.json",
            () -> BlockEntityType.Builder
                .of(OfficeCabinetBlockEntity::new, KanataBlock.OFFICE_CABINET.get())
                .build(null)
        );
    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<OfficeShelfBlockEntity>> OFFICE_SHELF = BLOCK_ENTITIES.register(
        "office_shelf_block.json",
        () -> BlockEntityType.Builder.of(OfficeShelfBlockEntity::new, KanataBlock.OFFICE_SHELF.get()).build(null)
    );

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<LockerBlockEntity>> LOCKER = BLOCK_ENTITIES.register(
        "locker",
        () -> BlockEntityType.Builder.of(LockerBlockEntity::new, KanataBlock.LOCKER.get()).build(null)
    );

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
