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

public class MegaRoomAPattern implements MegaFloorPattern {
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
        if (mblPos.x() == 15) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        } else if (mblPos.x() == 20) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        }

        //部屋の壁
        if (mblPos.y() == 8 && 20 < mblPos.x()) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        } else if (mblPos.y() == 15 && (mblPos.x() < 15 || 20 < mblPos.x())) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        } else if (mblPos.y() == 23 && (mblPos.x() < 15 || 20 < mblPos.x())) {
            for (int y = 0; y < buildingCommonInfo.floorHeight() - 1; y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        }

        //ルートボックスなど
        boolean boxPosEast = (1 < mblPos.x() && mblPos.x() < 15);
        boolean boxPosWest = (20 < mblPos.x() && mblPos.x() < 30);

        if (mblPos.y() == 9 && boxPosWest) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.SOUTH, r);
        } else if ((mblPos.y() == 7 || mblPos.y() == 14) && boxPosWest) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.NORTH, r);
        } else if ((mblPos.y() == 2 || mblPos.y() == 16 || mblPos.y() == 24) && (boxPosEast || boxPosWest)) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.SOUTH, r);
        } else if ((mblPos.y() == 22 || mblPos.y() == 29) && (boxPosEast || boxPosWest)) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.NORTH, r);
        }

        //ドア
        if (mblPos.x() == 15 && (mblPos.y() == 5 || mblPos.y() == 19 || mblPos.y() == 27)) {
            placeDoor(level, blockPos.above(), Direction.EAST, false, DoorHingeSide.RIGHT);
        } else if (mblPos.x() == 20 && (mblPos.y() == 5 || mblPos.y() == 12 || mblPos.y() == 19 || mblPos.y() == 27)) {
            placeDoor(level, blockPos.above(), Direction.WEST, false, DoorHingeSide.RIGHT);
        }
    }
}
