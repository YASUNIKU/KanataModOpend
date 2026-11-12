package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.block.CafeTableBlock;
import com.github.yasuniku.kanata.block.GenericDummyBlock;
import com.github.yasuniku.kanata.block.PartitionBoardBlock;
import com.github.yasuniku.kanata.block.WireBlock;
import com.github.yasuniku.kanata.block.cafechair.CafeChairBlock;
import com.github.yasuniku.kanata.block.cardboardbox.CardboardBoxBlock;
import com.github.yasuniku.kanata.block.locker.LockerBlock;
import com.github.yasuniku.kanata.block.moduleinstaller.ModuleInstallerBlock;
import com.github.yasuniku.kanata.block.moduleslotadder.ModuleSlotAdderBlock;
import com.github.yasuniku.kanata.block.moduleupdatemachine.ModuleUpdateMachineBlock;
import com.github.yasuniku.kanata.block.office_shelf.OfficeShelfBlock;
import com.github.yasuniku.kanata.block.officecabinet.OfficeCabinetBlock;
import com.github.yasuniku.kanata.block.officedesk.OfficeDeskBlock;
import com.github.yasuniku.kanata.block.portal.T1PortalBlock;
import com.github.yasuniku.kanata.block.portalframe.T1PortalFrameBlock;
import com.github.yasuniku.kanata.block.reinforcedalloyplate.*;
import com.github.yasuniku.kanata.block.reinforcedbrick.*;
import com.github.yasuniku.kanata.block.reinforcedconcrete.*;
import com.github.yasuniku.kanata.block.reinforceddoor.ReinforcedDoorBlock;
import com.github.yasuniku.kanata.block.returnportal.ReturnPortalBlock;
import com.github.yasuniku.kanata.block.shopshelf.ShopShelfBlock;
import com.github.yasuniku.kanata.block.spawnbox.SpawnBoxBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class KanataBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    //region ポータル関連
    @SuppressWarnings("unused")
    public static final RegistryObject<Block> T1_PORTAL_BLOCK = BLOCKS.register(
        "t1_portal_block",
        () -> new T1PortalBlock(Block.Properties.of().noOcclusion().strength(1.0f).lightLevel(s -> 10))
    );
    @SuppressWarnings("unused")
    public static final RegistryObject<Block> T1_PORTAL_FRAME_BLOCK = BLOCKS.register(
        "t1_portal_frame_block",
        () -> new T1PortalFrameBlock(BlockBehaviour.Properties
            .of()
            .mapColor(MapColor.COLOR_PURPLE)
            .strength(50.0F, 1200.0F)
            .lightLevel(s -> 7) // うっすら光る感じ
            .noOcclusion())
    );
    public static final RegistryObject<Block> RETURN_PORTAL_BLOCK = BLOCKS.register(
        "return_portal_block",
        () -> new ReturnPortalBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );
    //endregion

    //region 強化コンクリートブロック
    public static final RegistryObject<ReinforcedConcreteBlock> REINFORCED_CONCRETE_BLOCK =
        BLOCKS.register("reinforced_concrete_block", ReinforcedConcreteBlock::new);
    public static final RegistryObject<ReinforcedConcreteStair> REINFORCED_CONCRETE_STAIRS =
        BLOCKS.register("reinforced_concrete_stairs", ReinforcedConcreteStair::new);
    public static final RegistryObject<ReinforcedConcreteSlab> REINFORCED_CONCRETE_SLAB =
        BLOCKS.register("reinforced_concrete_slab", ReinforcedConcreteSlab::new);
    public static final RegistryObject<ReinforcedConcreteWall> REINFORCED_CONCRETE_WALL =
        BLOCKS.register("reinforced_concrete_wall", ReinforcedConcreteWall::new);
    public static final RegistryObject<ReinforcedConcretePanel> REINFORCED_CONCRETE_PANEL =
        BLOCKS.register("reinforced_concrete_panel", ReinforcedConcretePanel::new);
    //endregion

    //region 強化コンクリート床
    public static final RegistryObject<ReinforcedConcreteBlock> REINFORCED_CONCRETE_FLOOR_BLOCK =
        BLOCKS.register("reinforced_concrete_floor_block", ReinforcedConcreteBlock::new);
    public static final RegistryObject<ReinforcedConcreteStair> REINFORCED_CONCRETE_FLOOR_STAIRS =
        BLOCKS.register("reinforced_concrete_floor_stairs", ReinforcedConcreteStair::new);
    public static final RegistryObject<ReinforcedConcreteSlab> REINFORCED_CONCRETE_FLOOR_SLAB =
        BLOCKS.register("reinforced_concrete_floor_slab", ReinforcedConcreteSlab::new);
    public static final RegistryObject<ReinforcedConcreteWall> REINFORCED_CONCRETE_FLOOR_WALL =
        BLOCKS.register("reinforced_concrete_floor_wall", ReinforcedConcreteWall::new);
    public static final RegistryObject<ReinforcedConcretePanel> REINFORCED_CONCRETE_FLOOR_PANEL =
        BLOCKS.register("reinforced_concrete_floor_panel", ReinforcedConcretePanel::new);
    //endregion

    //region 壊れた強化コンクリートブロック
    public static final RegistryObject<Block> CRUMBLING_REINFORCED_CONCRETE_BLOCK = BLOCKS.register(
        "crumbling_reinforced_concrete_block",
        () -> new Block(BlockBehaviour.Properties
            .of()
            .mapColor(MapColor.COLOR_GRAY)
            .strength(50.0F, 6.0F)
            .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<StairBlock> CRUMBLING_REINFORCED_CONCRETE_STAIRS = BLOCKS.register(
        "crumbling_reinforced_concrete_stairs",
        () -> new StairBlock(
            () -> REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(),
            BlockBehaviour.Properties.of().strength(50.0F, 6.0F).requiresCorrectToolForDrops()
        )
    );
    public static final RegistryObject<SlabBlock> CRUMBLING_REINFORCED_CONCRETE_SLAB = BLOCKS.register(
        "crumbling_reinforced_concrete_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.of().strength(50.0F, 6.0F).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<WallBlock> CRUMBLING_REINFORCED_CONCRETE_WALL = BLOCKS.register(
        "crumbling_reinforced_concrete_wall",
        () -> new WallBlock(BlockBehaviour.Properties.of().strength(50.0F, 6.0F).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<IronBarsBlock> CRUMBLING_REINFORCED_CONCRETE_PANEL = BLOCKS.register(
        "crumbling_reinforced_concrete_panel",
        () -> new IronBarsBlock(BlockBehaviour.Properties
            .copy(Blocks.GLASS_PANE)
            .strength(50.0F, 6.0F)
            .requiresCorrectToolForDrops())
    );
    //endregion

    //region 強化合金板ブロック
    public static final RegistryObject<ReinforcedAlloyPlateBlock> REINFORCED_ALLOY_PLATE_BLOCK =
        BLOCKS.register("reinforced_alloy_plate_block", ReinforcedAlloyPlateBlock::new);
    public static final RegistryObject<ReinforcedAlloyPlateStair> REINFORCED_ALLOY_PLATE_STAIRS =
        BLOCKS.register("reinforced_alloy_plate_stairs", ReinforcedAlloyPlateStair::new);
    public static final RegistryObject<ReinforcedAlloyPlateSlab> REINFORCED_ALLOY_PLATE_SLAB =
        BLOCKS.register("reinforced_alloy_plate_slab", ReinforcedAlloyPlateSlab::new);
    public static final RegistryObject<ReinforcedAlloyPlateWall> REINFORCED_ALLOY_PLATE_WALL =
        BLOCKS.register("reinforced_alloy_plate_wall", ReinforcedAlloyPlateWall::new);
    public static final RegistryObject<ReinforcedAlloyPlatePanel> REINFORCED_ALLOY_PLATE_PANEL =
        BLOCKS.register("reinforced_alloy_plate_panel", ReinforcedAlloyPlatePanel::new);
    //endregion

    //region 強化レンガブロック
    public static final RegistryObject<ReinforcedBrickBlock> REINFORCED_BRICK_BLOCK =
        BLOCKS.register("reinforced_brick_block", ReinforcedBrickBlock::new);
    public static final RegistryObject<ReinforcedBrickStair> REINFORCED_BRICK_STAIRS =
        BLOCKS.register("reinforced_brick_stairs", ReinforcedBrickStair::new);
    public static final RegistryObject<ReinforcedBrickSlab> REINFORCED_BRICK_SLAB =
        BLOCKS.register("reinforced_brick_slab", ReinforcedBrickSlab::new);
    public static final RegistryObject<ReinforcedBrickWall> REINFORCED_BRICK_WALL =
        BLOCKS.register("reinforced_brick_wall", ReinforcedBrickWall::new);
    public static final RegistryObject<ReinforcedBrickPanel> REINFORCED_BRICK_PANEL =
        BLOCKS.register("reinforced_brick_panel", ReinforcedBrickPanel::new);
    //endregion

    //region 家具ブロック
    public static final RegistryObject<Block> CAFE_TABLE_BLOCK = BLOCKS.register(
        "cafe_table_block",
        () -> new CafeTableBlock(BlockBehaviour.Properties.of().strength(2.5f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> PARTITION_BOARD_BLOCK = BLOCKS.register(
        "partition_board_block",
        () -> new PartitionBoardBlock(BlockBehaviour.Properties.of().strength(2.5f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> CAFE_CHAIR_BLOCK = BLOCKS.register(
        "cafe_chair_block",
        () -> new CafeChairBlock(BlockBehaviour.Properties.of().strength(2.5f).requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> WIRE_BLOCK = BLOCKS.register("wire_block", WireBlock::new);
    //endregion

    //region クラフトテーブル系
    public static final RegistryObject<Block> GENERIC_DUMMY_BLOCK = BLOCKS.register(
        "generic_dummy_block",
        () -> new GenericDummyBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );

    public static final RegistryObject<ReinforcedDoorBlock> REINFORCED_DOOR = BLOCKS.register(
        "reinforced_door",
        () -> new ReinforcedDoorBlock(BlockBehaviour.Properties
            .of()
            .mapColor(MapColor.METAL)
            .strength(100.0f, 6.0f)
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK))
    );
    public static final RegistryObject<Block> MODULE_INSTALLER = BLOCKS.register(
        "module_installer",
        () -> new ModuleInstallerBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );

    public static final RegistryObject<Block> MODULE_SLOT_ADDER = BLOCKS.register(
        "module_slot_adder",
        () -> new ModuleSlotAdderBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );
    public static final RegistryObject<Block> MODULE_UPDATE_MACHINE = BLOCKS.register(
        "module_update_machine",
        () -> new ModuleUpdateMachineBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );
    //endregion

    //region 機能を持つ家具系
    public static final RegistryObject<Block> SPAWN_BOX_BLOCK = BLOCKS.register(
        "spawn_box_block",
        () -> new SpawnBoxBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );
    public static final RegistryObject<Block> SHOP_SHELF = BLOCKS.register(
        "shop_shelf",
        () -> new ShopShelfBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );
    public static final RegistryObject<Block> OFFICE_DESK = BLOCKS.register(
        "office_desk",
        () -> new OfficeDeskBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );

    public static final RegistryObject<OfficeCabinetBlock> OFFICE_CABINET = BLOCKS.register(
        "office_cabinet_block",
        () -> new OfficeCabinetBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD))
    );

    public static final RegistryObject<OfficeShelfBlock> OFFICE_SHELF = BLOCKS.register(
        "office_shelf_block",
        () -> new OfficeShelfBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD))
    );

    public static final RegistryObject<Block> LOCKER = BLOCKS.register(
        "locker",
        () -> new LockerBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD))
    );

    public static final RegistryObject<CardboardBoxBlock> CARDBOARD_BOX = BLOCKS.register(
        "cardboard_box",
        () -> new CardboardBoxBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD))
    );

    //endregion

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
