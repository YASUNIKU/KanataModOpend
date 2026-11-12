package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.MegaStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;

import java.util.Random;

import static com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.MegaBuildingPosConverter.ConvertToMegaBuildingLocalBlockPos;
import static com.github.yasuniku.kanata.utils.BlockPlaceHelper.placeChestOrSpawnBox;
import static com.github.yasuniku.kanata.utils.BlockPlaceHelper.placeDoor;

public class MegaRoomBPattern implements MegaFloorPattern {
    @Override
    public void generate(
        LevelAccessor level,
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
                generateCeiling(level, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 床
                generateFloor(level, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 柱
                generatePillar(level, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 壁
                generateWall(level, pos, r, buildingCommonInfo, buildingPos, x, z, wallPatttern);
                generateRoomWall(level, pos, r, buildingCommonInfo, buildingPos, new Vector2DInt(x, z));
                // 階段
                generateStair(level, pos, r, buildingCommonInfo, megaStairPattern, x, z, buildingPos);
            }
        }
    }

    public void generateRoomWall(
        LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        Vector2DInt pos
    ) {
        //内壁の範囲外は生成しない
        if (buildingPos.x() == 0) {
            if (pos.x() < 1) {
                return;
            }
        } else {
            if (14 < pos.x()) {
                return;
            }
        }
        if (buildingPos.y() == 0) {
            if (pos.y() < 1) {
                return;
            }
        } else {
            if (14 < pos.y()) {
                return;
            }
        }

        Vector2DInt mblPos = ConvertToMegaBuildingLocalBlockPos(buildingPos, pos);

        //廊下の壁
        if (mblPos.x() == 15 && mblPos.y() < 15) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        } else if (mblPos.x() == 20 && mblPos.y() < 15) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        } else if (mblPos.y() == 15 && (mblPos.x() <= 15 || 20 <= mblPos.x())) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        } else if (mblPos.y() == 20) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        }

        //部屋の壁
        if (mblPos.y() == 8 && (mblPos.x() < 15 || 20 < mblPos.x())) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        } else if ((mblPos.x() == 8 || mblPos.x() == 15 || mblPos.x() == 23) && 20 < mblPos.y()) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        }

        //ルートボックスなど
        boolean boxPosNorth = (1 < mblPos.x() && mblPos.x() < 15) || (20 < mblPos.x() && mblPos.x() < 30);

        if (mblPos.y() == 2 && boxPosNorth) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.SOUTH, r);
        } else if (mblPos.y() == 7 && boxPosNorth) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.NORTH, r);
        } else if (mblPos.y() == 14 && (20 < mblPos.x() && mblPos.x() < 30)) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.NORTH, r);
        }

        if (mblPos.x() == 2 && (8 < mblPos.y() && mblPos.y() < 15)) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.EAST, r);
        } else if (mblPos.x() == 7 && (8 < mblPos.y() && mblPos.y() < 15)) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.WEST, r);
        } else if ((mblPos.x() == 2 || mblPos.x() == 9 || mblPos.x() == 16 || mblPos.x() == 24) && (20 < mblPos.y() && mblPos.y() < 30)) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.EAST, r);
        } else if ((mblPos.x() == 7 || mblPos.x() == 14 || mblPos.x() == 22 || mblPos.x() == 29) && (20 < mblPos.y() && mblPos.y() < 30)) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.WEST, r);
        }

        //ドア

        if (mblPos.x() == 15 && mblPos.y() == 5) {
            placeDoor(level, blockPos.above(), Direction.SOUTH, false, DoorHingeSide.RIGHT);
        } else if (mblPos.x() == 20 && (mblPos.y() == 5 || mblPos.y() == 12)) {
            placeDoor(level, blockPos.above(), Direction.NORTH, false, DoorHingeSide.RIGHT);
        } else if (mblPos.y() == 15 && mblPos.x() == 5) {
            placeDoor(level, blockPos.above(), Direction.SOUTH, false, DoorHingeSide.RIGHT);
        } else if (mblPos.y() == 20 && (mblPos.x() == 5 || mblPos.x() == 12 || mblPos.x() == 19 || mblPos.x() == 26)) {
            placeDoor(level, blockPos.above(), Direction.NORTH, false, DoorHingeSide.RIGHT);
        }
    }
}
