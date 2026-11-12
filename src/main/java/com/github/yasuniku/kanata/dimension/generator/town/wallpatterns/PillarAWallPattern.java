package com.github.yasuniku.kanata.dimension.generator.town.wallpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Random;

public class PillarAWallPattern implements WallPattern {
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
        if (posX == 1 || posX == 14) {
            if (!(posZ < 2 || posZ == 5 || posZ == 10 || 13 < posZ)) {
                for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                    if (2 <= y && y <= 5) {
                        level.setBlock(blockPos.above(y), Blocks.GLASS.defaultBlockState(), 2);
                    }
                }
            }
        }
        if (posZ == 1 || posZ == 14) {
            if (!(posX < 2 || posX == 5 || posX == 10 || 13 < posX)) {
                for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                    if (2 <= y && y <= 5) {
                        level.setBlock(blockPos.above(y), Blocks.GLASS.defaultBlockState(), 2);
                    }
                }
            }
        }
        if (posX == 0 || posX == 15 || posZ == 0 || posZ == 15) {
            level.setBlock(
                blockPos.above(buildingCommonInfo.floorHeight() - 1),
                buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                2
            );
            level.setBlock(blockPos, buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
        }
    }

    @Override
    public boolean[][] getBaseWallShape() {
        return new boolean[][]{{false, false, false, true, false, false, false, false, true, false, false, false}, {true, true, true, true, true, true, true, true, true, true, true, true}};
    }
}
