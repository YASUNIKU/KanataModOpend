package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.MegaStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public interface MegaFloorPattern {
    default void generate(
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
                // 階段
                generateStair(levelAccessor, pos, r, buildingCommonInfo, megaStairPattern, x, z, buildingPos);
            }
        }
    }

    default void generateWall(
        LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        int posX,
        int posZ,
        WallPattern wallPatttern
    ) {
        if (buildingPos.y() == 0) {
            if (posZ == 0 || posZ == 1) {
                wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
            }
        } else {
            if (posZ == 14 || posZ == 15) {
                wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
            }
        }
        if (buildingPos.x() == 0) {
            if (posX == 0 || posX == 1) {
                wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
            }
        } else {
            if (posX == 14 || posX == 15) {
                wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
            }
        }
    }

    default void generateFloor(
        LevelAccessor level,
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
            level.setBlock(blockPos, buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
        }
    }

    default void generateCeiling(
        LevelAccessor level,
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
            level.setBlock(pos.below(1), buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
        }
    }

    default void generatePillar(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        int posX,
        int posZ
    ) {
        if (buildingPos.y() == 0) {
            if (buildingPos.x() == 0) {
                if (!(14 <= posX && 14 <= posZ)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            } else {
                if (!(posX <= 1 && 14 <= posZ)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            }
        } else {
            if (buildingPos.x() == 0) {
                if (!(14 <= posX && posZ <= 1)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            } else {
                if (!(posX <= 1 && posZ <= 1)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            }
        }
    }

    default void generateStair(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        MegaStairPattern megaStairPattern,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    ) {
        megaStairPattern.generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ, buildingPos);
    }
}
