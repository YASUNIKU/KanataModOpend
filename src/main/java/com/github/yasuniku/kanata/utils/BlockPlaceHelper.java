package com.github.yasuniku.kanata.utils;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;

public class BlockPlaceHelper {

    public static void placeRootChest(LevelAccessor level, BlockPos blockPos, Direction direction) {
        level.setBlock(blockPos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, direction), 3);
        BlockEntity be = level.getBlockEntity(blockPos);
        if (be instanceof ChestBlockEntity box) {
            box.setLootTable(setLootTableResourceLocation(level.getRandom().nextInt()), level.getRandom().nextLong());
        }
    }

    public static void placeLootChestInMegaBuildingTreasure(
        LevelAccessor level,
        BlockPos blockPos,
        Direction direction,
        Random random
    ) {
        //        if (!(random.nextInt(3) == 0)) {
        //            return;
        //        }
        //
        //        level.setBlock(blockPos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, direction), 3);
        //        BlockEntity be = level.getBlockEntity(blockPos);
        //        if (be instanceof ChestBlockEntity box) {
        //            box.setLootTable(
        //                ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "chests/building_loot"),
        //                level.getRandom().nextLong()
        //            );
        //        }
    }

    public static void placeSpawnBox(LevelAccessor level, BlockPos blockPos, Direction direction) {
        //        BlockState lower =
        //            KanataBlock.SPAWN_BOX_BLOCK.get().defaultBlockState().setValue(SpawnBoxBlock.FACING, direction);
        //
        //        BlockState upper = KanataBlock.GENERIC_DUMMY_BLOCK
        //            .get()
        //            .defaultBlockState()
        //            .setValue(GenericDummyBlock.MASTER_DIR, Direction.DOWN);
        //
        //        // 下半分
        //        level.setBlock(blockPos, lower, 3);
        //        // 上半分
        //        level.setBlock(blockPos.above(), upper, 3);
    }

    public static void placeChestOrSpawnBox(LevelAccessor level, BlockPos blockPos, Direction direction, Random r) {
        //        if (r.nextInt(5) == 0) {
        //            placeSpawnBox(level, blockPos, direction);
        //        } else if (r.nextInt(8) == 0) {
        //            placeRootChest(level, blockPos, direction);
        //        }
    }

    private static ResourceLocation setLootTableResourceLocation(int seed) {
        return switch (Math.floorMod(seed, 3)) {
            case 0 -> ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "chests/food_loot");
            case 1 -> ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "chests/tool_loot");
            case 2 -> ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "chests/materials_loot");
            default -> ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "chests/spawn_box_loot");
        };
    }

    public static void placeReinforcedDoor(
        LevelAccessor level,
        BlockPos blockPos,
        Direction direction,
        boolean isOpen,
        DoorHingeSide doorHingeSide
    ) {
        BlockState lower = KanataBlock.REINFORCED_DOOR
            .get()
            .defaultBlockState()
            .setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER)
            .setValue(DoorBlock.FACING, direction)
            .setValue(DoorBlock.OPEN, isOpen)
            .setValue(DoorBlock.HINGE, doorHingeSide);

        BlockState upper = KanataBlock.REINFORCED_DOOR
            .get()
            .defaultBlockState()
            .setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER)
            .setValue(DoorBlock.FACING, direction)
            .setValue(DoorBlock.OPEN, isOpen)
            .setValue(DoorBlock.HINGE, doorHingeSide);

        // 下半分
        level.setBlock(blockPos, lower, 3);
        // 上半分
        level.setBlock(blockPos.above(), upper, 3);
    }

    public static void placeDoor(
        LevelAccessor level,
        BlockPos blockPos,
        Direction direction,
        boolean isOpen,
        DoorHingeSide doorHingeSide
    ) {
        BlockState lower = Blocks.DARK_OAK_DOOR
            .defaultBlockState()
            .setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER)
            .setValue(DoorBlock.FACING, direction)
            .setValue(DoorBlock.OPEN, isOpen)
            .setValue(DoorBlock.HINGE, doorHingeSide);

        BlockState upper = Blocks.DARK_OAK_DOOR
            .defaultBlockState()
            .setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER)
            .setValue(DoorBlock.FACING, direction)
            .setValue(DoorBlock.OPEN, isOpen)
            .setValue(DoorBlock.HINGE, doorHingeSide);

        // 下半分
        level.setBlock(blockPos, lower, 3);
        // 上半分
        level.setBlock(blockPos.above(), upper, 3);
    }

    @SuppressWarnings("unused")
    public static void placeDoorOrReinforcedDoor(
        LevelAccessor level,
        BlockPos blockPos,
        Direction direction,
        boolean isOpen,
        DoorHingeSide doorHingeSide,
        Random random
    ) {
        if (random.nextBoolean()) {
            placeDoor(level, blockPos, direction, isOpen, doorHingeSide);
        } else {
            placeReinforcedDoor(level, blockPos, direction, isOpen, doorHingeSide);
        }
    }

    public static void placeStructureTemplate(
        BlockPos origin,
        ServerLevelAccessor level,
        String templateName,
        RandomSource random,
        StructurePlaceSettings structurePlaceSettings
    ) {
        var template = getStructureTemplate(level.getLevel().getServer(), templateName);
        template.placeInWorld(level, origin, origin, structurePlaceSettings, random, Block.UPDATE_KNOWN_SHAPE);
    }

    public static StructureTemplate getStructureTemplate(MinecraftServer server, String templateName) {
        return server
            .getStructureManager()
            .get(ResourceLocation.fromNamespaceAndPath(MOD_ID, templateName))
            .orElseThrow(() -> new RuntimeException(String.format("構造物の取得に失敗: %s", templateName)));
    }

    public static LocalXZPos RotateChunk(Rotation rotation, LocalXZPos buildingOrigin) {
        return switch (rotation) {
            case CLOCKWISE_90 -> RotateChunkClockWise90(buildingOrigin);
            case CLOCKWISE_180 -> RotateChunkClockWise180(buildingOrigin);
            case COUNTERCLOCKWISE_90 -> RotateChunkCounterClockWise90(buildingOrigin);
            default -> buildingOrigin;
        };
    }

    public static LocalXZPos RotateChunkClockWise90(LocalXZPos buildingOrigin) {
        return new LocalXZPos(15 - buildingOrigin.z(), buildingOrigin.x());
    }

    public static LocalXZPos RotateChunkClockWise180(LocalXZPos buildingOrigin) {
        return new LocalXZPos(15 - buildingOrigin.x(), 15 - buildingOrigin.z());
    }

    public static LocalXZPos RotateChunkCounterClockWise90(LocalXZPos buildingOrigin) {
        return new LocalXZPos(buildingOrigin.z(), 15 - buildingOrigin.x());
    }

    public static void fillCuboid(LevelWriter level, BlockPos min, BlockPos max, BlockState state) {
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            level.setBlock(pos, state, Block.UPDATE_KNOWN_SHAPE);
        }
    }

    public static void fillCuboid(LevelWriter level, MyChunkPos chunkPos, int minY, int maxY, BlockState state) {
        fillCuboid(level, chunkPos.toBlockPos(0, minY, 0), chunkPos.toBlockPos(15, maxY, 15), state);
    }
}
