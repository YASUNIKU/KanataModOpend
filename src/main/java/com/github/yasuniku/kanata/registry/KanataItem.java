package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.block.spawnbox.SpawnBoxBlockItem;
import com.github.yasuniku.kanata.item.DebugItem;
import com.github.yasuniku.kanata.item.ReinforcedDoorCardKeyItem;
import com.github.yasuniku.kanata.item.ScrapArmorItem;
import com.github.yasuniku.kanata.item.ScrapArmorMaterials;
import com.github.yasuniku.kanata.item.module.ModuleItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class KanataItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KanataMod.MOD_ID);

    //region ポータル系
    public static final RegistryObject<Item> T1_PORTAL_BLOCK = ITEMS.register(
        "t1_portal_block",
        () -> new BlockItem(KanataBlock.T1_PORTAL_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> T1_PORTAL_FRAME_BLOCK = ITEMS.register(
        "t1_portal_frame_block",
        () -> new BlockItem(KanataBlock.T1_PORTAL_FRAME_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> RETURN_PORTAL_BLOCK = ITEMS.register(
        "return_portal_block",
        () -> new BlockItem(KanataBlock.RETURN_PORTAL_BLOCK.get(), new Item.Properties())
    );
    //endregion

    //region 強化コンクリート
    public static final RegistryObject<Item> REINFORCED_CONCRETE_BLOCK = ITEMS.register(
        "reinforced_concrete_block",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_STAIRS = ITEMS.register(
        "reinforced_concrete_stairs",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_STAIRS.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_SLAB = ITEMS.register(
        "reinforced_concrete_slab",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_SLAB.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_WALL = ITEMS.register(
        "reinforced_concrete_wall",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_WALL.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_PANEL = ITEMS.register(
        "reinforced_concrete_panel",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_PANEL.get(), new Item.Properties())
    );
    //endregion

    //region 強化コンクリート床
    public static final RegistryObject<Item> REINFORCED_CONCRETE_FLOOR_BLOCK = ITEMS.register(
        "reinforced_concrete_floor_block",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_FLOOR_STAIRS = ITEMS.register(
        "reinforced_concrete_floor_stairs",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_FLOOR_STAIRS.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_FLOOR_SLAB = ITEMS.register(
        "reinforced_concrete_floor_slab",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_FLOOR_SLAB.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_FLOOR_WALL = ITEMS.register(
        "reinforced_concrete_floor_wall",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_FLOOR_WALL.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_CONCRETE_FLOOR_PANEL = ITEMS.register(
        "reinforced_concrete_floor_panel",
        () -> new BlockItem(KanataBlock.REINFORCED_CONCRETE_FLOOR_PANEL.get(), new Item.Properties())
    );
    //endregion

    //region 壊れた強化コンクリート
    public static final RegistryObject<Item> CRUMBLING_REINFORCED_CONCRETE_BLOCK = ITEMS.register(
        "crumbling_reinforced_concrete_block",
        () -> new BlockItem(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> CRUMBLING_REINFORCED_CONCRETE_STAIRS = ITEMS.register(
        "crumbling_reinforced_concrete_stairs",
        () -> new BlockItem(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_STAIRS.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> CRUMBLING_REINFORCED_CONCRETE_SLAB = ITEMS.register(
        "crumbling_reinforced_concrete_slab",
        () -> new BlockItem(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_SLAB.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> CRUMBLING_REINFORCED_CONCRETE_WALL = ITEMS.register(
        "crumbling_reinforced_concrete_wall",
        () -> new BlockItem(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_WALL.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> CRUMBLING_REINFORCED_CONCRETE_PANEL = ITEMS.register(
        "crumbling_reinforced_concrete_panel",
        () -> new BlockItem(KanataBlock.CRUMBLING_REINFORCED_CONCRETE_PANEL.get(), new Item.Properties())
    );
    //endregion

    //region 強化合金板
    public static final RegistryObject<Item> REINFORCED_ALLOY_PLATE_BLOCK = ITEMS.register(
        "reinforced_alloy_plate_block",
        () -> new BlockItem(KanataBlock.REINFORCED_ALLOY_PLATE_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_ALLOY_PLATE_STAIRS = ITEMS.register(
        "reinforced_alloy_plate_stairs",
        () -> new BlockItem(KanataBlock.REINFORCED_ALLOY_PLATE_STAIRS.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_ALLOY_PLATE_SLAB = ITEMS.register(
        "reinforced_alloy_plate_slab",
        () -> new BlockItem(KanataBlock.REINFORCED_ALLOY_PLATE_SLAB.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_ALLOY_PLATE_WALL = ITEMS.register(
        "reinforced_alloy_plate_wall",
        () -> new BlockItem(KanataBlock.REINFORCED_ALLOY_PLATE_WALL.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_ALLOY_PLATE_PANEL = ITEMS.register(
        "reinforced_alloy_plate_panel",
        () -> new BlockItem(KanataBlock.REINFORCED_ALLOY_PLATE_PANEL.get(), new Item.Properties())
    );
    //endregion

    //region 強化レンガ
    public static final RegistryObject<Item> REINFORCED_BRICK_BLOCK = ITEMS.register(
        "reinforced_brick_block",
        () -> new BlockItem(KanataBlock.REINFORCED_BRICK_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_BRICK_STAIRS = ITEMS.register(
        "reinforced_brick_stairs",
        () -> new BlockItem(KanataBlock.REINFORCED_BRICK_STAIRS.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_BRICK_SLAB = ITEMS.register(
        "reinforced_brick_slab",
        () -> new BlockItem(KanataBlock.REINFORCED_BRICK_SLAB.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_BRICK_WALL = ITEMS.register(
        "reinforced_brick_wall",
        () -> new BlockItem(KanataBlock.REINFORCED_BRICK_WALL.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_BRICK_PANEL = ITEMS.register(
        "reinforced_brick_panel",
        () -> new BlockItem(KanataBlock.REINFORCED_BRICK_PANEL.get(), new Item.Properties())
    );
    //endregion

    //region 家具ブロック
    public static final RegistryObject<Item> CAFE_TABLE_BLOCK = ITEMS.register(
        "cafe_table_block",
        () -> new BlockItem(KanataBlock.CAFE_TABLE_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> PARTITION_BOARD_BLOCK = ITEMS.register(
        "partition_board_block",
        () -> new BlockItem(KanataBlock.PARTITION_BOARD_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> CAFE_CHAIR_BLOCK = ITEMS.register(
        "cafe_chair_block",
        () -> new BlockItem(KanataBlock.CAFE_CHAIR_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> WIRE_BLOCK =
        ITEMS.register("wire_block", () -> new BlockItem(KanataBlock.WIRE_BLOCK.get(), new Item.Properties()));

    //endregion

    //region 加工テーブル系

    public static final RegistryObject<Item> MODULE_INSTALLER = ITEMS.register(
        "module_installer",
        () -> new BlockItem(KanataBlock.MODULE_INSTALLER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> MODULE_SLOT_ADDER = ITEMS.register(
        "module_slot_adder",
        () -> new BlockItem(KanataBlock.MODULE_SLOT_ADDER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> MODULE_UPDATE_MACHINE = ITEMS.register(
        "module_update_machine",
        () -> new BlockItem(KanataBlock.MODULE_UPDATE_MACHINE.get(), new Item.Properties())
    );

    //endregion

    //region インベントリを持つ家具
    public static final RegistryObject<Item> SPAWN_BOX_BLOCK = ITEMS.register(
        "spawn_box_block",
        () -> new SpawnBoxBlockItem(KanataBlock.SPAWN_BOX_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> SHOP_SHELF =
        ITEMS.register("shop_shelf", () -> new BlockItem(KanataBlock.SHOP_SHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> OFFICE_DESK =
        ITEMS.register("office_desk", () -> new BlockItem(KanataBlock.OFFICE_DESK.get(), new Item.Properties()));
    public static final RegistryObject<Item> OFFICE_CABINET =
        ITEMS.register("office_cabinet", () -> new BlockItem(KanataBlock.OFFICE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> OFFICE_SHELF =
        ITEMS.register("office_shelf", () -> new BlockItem(KanataBlock.OFFICE_SHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> LOCKER =
        ITEMS.register("locker", () -> new BlockItem(KanataBlock.LOCKER.get(), new Item.Properties()));
    public static final RegistryObject<Item> CARDBOARD_BOX =
        ITEMS.register("cardboard_box", () -> new BlockItem(KanataBlock.CARDBOARD_BOX.get(), new Item.Properties()));

    //endregion

    public static final RegistryObject<Item> DEBUG_ITEM =
        ITEMS.register("debug_item", () -> new DebugItem(new Item.Properties()));

    public static final RegistryObject<Item> ROBOT_SPAWN_EGG = ITEMS.register(
        "robot_spawn_egg", () -> new ForgeSpawnEggItem(
            KanataEntity.ROBOT, // エンティティタイプ
            0x808080,           // ベースカラー
            0x1B2A41,           // スポットカラー
            new Item.Properties()
        )
    );

    public static final RegistryObject<Item> REINFORCED_DOOR = ITEMS.register(
        "reinforced_door",
        () -> new BlockItem(KanataBlock.REINFORCED_DOOR.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> REINFORCED_DOOR_CARD_KEY = ITEMS.register(
        "reinforced_door_card_key",
        () -> new ReinforcedDoorCardKeyItem(new Item.Properties().durability(10)      //耐久値10
            .setNoRepair()       //修理不可
        )
    );
    public static final RegistryObject<Item> SCRAP = ITEMS.register("scrap", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SCRAP_HELMET = ITEMS.register(
        "scrap_helmet",
        () -> new ScrapArmorItem(ScrapArmorMaterials.SCRAP, ArmorItem.Type.HELMET, new Item.Properties())
    );

    public static final RegistryObject<Item> SCRAP_CHESTPLATE = ITEMS.register(
        "scrap_chestplate",
        () -> new ScrapArmorItem(ScrapArmorMaterials.SCRAP, ArmorItem.Type.CHESTPLATE, new Item.Properties())
    );

    public static final RegistryObject<Item> SCRAP_LEGGINGS = ITEMS.register(
        "scrap_leggings",
        () -> new ScrapArmorItem(ScrapArmorMaterials.SCRAP, ArmorItem.Type.LEGGINGS, new Item.Properties())
    );

    public static final RegistryObject<Item> SCRAP_BOOTS = ITEMS.register(
        "scrap_boots",
        () -> new ScrapArmorItem(ScrapArmorMaterials.SCRAP, ArmorItem.Type.BOOTS, new Item.Properties())
    );
    public static final RegistryObject<Item> MODULE_ITEM =
        ITEMS.register("module_item", () -> new ModuleItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MODULE_SLOT_PARTS =
        ITEMS.register("module_slot_parts", () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
