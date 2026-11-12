package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.MegaStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.IsRoad;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Random;

public class MegaEntranceHallFloor implements MegaFloorPattern {
    @Override
    public void generateWall(
        @UnknownNullability LevelAccessor level,
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
                generateDoor(level, blockPos, buildingCommonInfo, posX, posZ);
            }
        } else {
            if (posZ == 14 || posZ == 15) {
                wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
                generateDoor(level, blockPos, buildingCommonInfo, posX, posZ);
            }
        }
        if (buildingPos.x() == 0) {
            if (posX == 0 || posX == 1) {
                wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
                generateDoor(level, blockPos, buildingCommonInfo, posX, posZ);
            }
        } else {
            if (posX == 14 || posX == 15) {
                wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
                generateDoor(level, blockPos, buildingCommonInfo, posX, posZ);
            }
        }
    }

    private void generateDoor(
        LevelAccessor level,
        BlockPos blockPos,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        IsRoad isRoad = buildingCommonInfo.isRoad();
        if (isRoad.north()) {
            if ((posX == 7 || posX == 8) && (posZ == 0 || posZ == 1)) {
                for (int y = 0; y < 3; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
        if (isRoad.south()) {
            if ((posX == 7 || posX == 8) && (posZ == 14 || posZ == 15)) {
                for (int y = 0; y < 3; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
        if (isRoad.west()) {
            if ((posX == 0 || posX == 1) && (posZ == 7 || posZ == 8)) {
                for (int y = 0; y < 3; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
        if (isRoad.east()) {
            if ((posX == 14 || posX == 15) && (posZ == 7 || posZ == 8)) {
                for (int y = 0; y < 3; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
    }

    @Override
    public void generateStair(
        @UnknownNullability LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        MegaStairPattern megaStairPattern,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    ) {
        megaStairPattern.generateEntranceHallStair(
            level,
            blockPos,
            ignoredR,
            buildingCommonInfo,
            posX,
            posZ,
            buildingPos
        );
    }
}
