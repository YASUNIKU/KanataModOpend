package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.MegaStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Random;

import static com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.MegaBuildingPosConverter.ConvertToMegaBuildingLocalBlockPos;
import static com.github.yasuniku.kanata.utils.BlockPlaceHelper.placeLootChestInMegaBuildingTreasure;
import static com.github.yasuniku.kanata.utils.BlockPlaceHelper.placeReinforcedDoor;

public class MegaReturnPortalFloor implements MegaFloorPattern {
    @Override
    public void generate(
        LevelAccessor levelAccessor,
        BlockPos origin,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        WallPattern wallPatttern,
        MegaStairPattern megaStairPattern
    ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockPos pos = origin.offset(x, 0, z);

                // 天井
                generateCeiling(levelAccessor, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 床
                generateFloor(levelAccessor, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 柱
                generatePillar(levelAccessor, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 壁
                generateWall(levelAccessor, pos, r, buildingCommonInfo, buildingPos, x, z, wallPatttern);
                generateRoom(levelAccessor, pos, r, buildingCommonInfo, buildingPos, new Vector2DInt(x, z));
                // 階段
                generateStair(levelAccessor, pos, r, buildingCommonInfo, megaStairPattern, x, z, buildingPos);
            }
        }
    }

    @Override
    public void generateCeiling(
        @UnknownNullability LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        int posX,
        int posZ
    ) {
        Vector2DInt minPos = new Vector2DInt(2, 2);
        Vector2DInt maxPos = new Vector2DInt(13, 13);

        if (buildingPos.x() == 0) {
            maxPos = maxPos.withX(15);
        } else {
            minPos = minPos.withX(0);
        }
        if (buildingPos.y() == 0) {
            maxPos = maxPos.withY(15);
        } else {
            minPos = minPos.withY(0);
        }

        BlockPos pos = blockPos.offset(0, buildingCommonInfo.floorHeight(), 0);
        if (minPos.x() <= posX && posX <= maxPos.x() && minPos.y() <= posZ && posZ <= maxPos.y()) {
            level.setBlock(pos.below(1), KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get().defaultBlockState(), 2);
        }
    }

    @Override
    public void generateFloor(
        @UnknownNullability LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        int posX,
        int posZ
    ) {
        Vector2DInt minPos = new Vector2DInt(2, 2);
        Vector2DInt maxPos = new Vector2DInt(13, 13);

        if (buildingPos.x() == 0) {
            maxPos = maxPos.withX(15);
        } else {
            minPos = minPos.withX(0);
        }
        if (buildingPos.y() == 0) {
            maxPos = maxPos.withY(15);
        } else {
            minPos = minPos.withY(0);
        }

        if (minPos.x() <= posX && posX <= maxPos.x() && minPos.y() <= posZ && posZ <= maxPos.y()) {
            level.setBlock(blockPos, KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get().defaultBlockState(), 2);
        }
    }

    private void generateRoom(
        LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        Vector2DInt pos
    ) {
        //内壁の範囲外は生成しない
        if (buildingPos.x() == 0) {
            if (pos.x() < 2) {
                return;
            }
        } else {
            if (13 < pos.x()) {
                return;
            }
        }
        if (buildingPos.y() == 0) {
            if (pos.y() < 2) {
                return;
            }
        } else {
            if (13 < pos.y()) {
                return;
            }
        }

        Vector2DInt mblPos = ConvertToMegaBuildingLocalBlockPos(buildingPos, pos);

        if (mblPos.x() == 2 || mblPos.x() == 29 || mblPos.y() == 2 || mblPos.y() == 29) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 2);
            }
        }

        if (mblPos.x() == 15) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 2);
            }
        }

        if (mblPos.x() == 20 && 16 < mblPos.y()) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 2);
            }
        }

        if (mblPos.y() == 16) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 2);
            }
        }

        if (mblPos.y() == 22 && (mblPos.x() <= 15 || 20 <= mblPos.x())) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 2);
            }
        }

        if (mblPos.x() == 7 && 8 <= mblPos.y() && mblPos.y() <= 15) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 2);
            }
        }
        if (7 <= mblPos.x() && mblPos.x() <= 15 && mblPos.y() == 8) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 2);
            }
        }

        if (mblPos.x() == 22 && mblPos.y() == 9) {
            level.setBlock(blockPos.above(), KanataBlock.RETURN_PORTAL_BLOCK.get().defaultBlockState(), 2);
        }

        if ((mblPos.x() == 17 || mblPos.x() == 18) && mblPos.y() == 16) {
            for (int y = 0; y < 4; y++) {
                level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
            }
        }

        if (mblPos.x() == 15 && mblPos.y() == 5) {
            placeReinforcedDoor(level, blockPos.above(), Direction.EAST, false, DoorHingeSide.RIGHT);
        }
        if (mblPos.x() == 15 && mblPos.y() == 19) {
            placeReinforcedDoor(level, blockPos.above(), Direction.EAST, false, DoorHingeSide.RIGHT);
        }
        if (mblPos.x() == 15 && mblPos.y() == 26) {
            placeReinforcedDoor(level, blockPos.above(), Direction.EAST, false, DoorHingeSide.RIGHT);
        }
        if (mblPos.x() == 20 && mblPos.y() == 19) {
            placeReinforcedDoor(level, blockPos.above(), Direction.WEST, false, DoorHingeSide.RIGHT);
        }
        if (mblPos.x() == 20 && mblPos.y() == 26) {
            placeReinforcedDoor(level, blockPos.above(), Direction.WEST, false, DoorHingeSide.RIGHT);
        }
        if (3 < mblPos.x() && mblPos.x() < 15 && (mblPos.y() == 3 || mblPos.y() == 17 || mblPos.y() == 23)) {
            placeLootChestInMegaBuildingTreasure(level, blockPos.above(), Direction.SOUTH, r);
        }
        if (3 < mblPos.x() && mblPos.x() < 15 && (mblPos.y() == 21 || mblPos.y() == 28)) {
            placeLootChestInMegaBuildingTreasure(level, blockPos.above(), Direction.NORTH, r);
        }
        if (20 < mblPos.x() && mblPos.x() <= 28 && (mblPos.y() == 17 || mblPos.y() == 23)) {
            placeLootChestInMegaBuildingTreasure(level, blockPos.above(), Direction.SOUTH, r);
        }
        if (20 < mblPos.x() && mblPos.x() <= 28 && (mblPos.y() == 21 || mblPos.y() == 28)) {
            placeLootChestInMegaBuildingTreasure(level, blockPos.above(), Direction.NORTH, r);
        }
    }
}
