package com.github.yasuniku.kanata.dimension.generator.town.wallpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Random;

public class WindowAWallPattern implements WallPattern {
    @Override
    public void generateDecorationWall(
        @UnknownNullability LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Direction direction
    ) {
        if (posX == 1 || posX == 14 || posZ == 1 || posZ == 14) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                if (2 <= y && y <= 5) {
                    level.setBlock(blockPos.above(y), Blocks.GLASS.defaultBlockState(), 2);
                }
            }
        }
        WallPattern.super.generateDecorationWall(level, blockPos, r, buildingCommonInfo, posX, posZ, direction);
    }

    @Override
    public boolean[][] getBaseWallShape() {
        return new boolean[][]{{false, false, false, false, false, false, false, false, false, false, false, false,}, {true, true, true, true, true, true, true, true, true, true, true, true}};
    }
}
