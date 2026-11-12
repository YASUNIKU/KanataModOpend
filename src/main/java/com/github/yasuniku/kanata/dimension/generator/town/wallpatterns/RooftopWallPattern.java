package com.github.yasuniku.kanata.dimension.generator.town.wallpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Random;

public class RooftopWallPattern implements WallPattern {

    @Override
    public boolean[][] getBaseWallShape() {
        boolean[][] BaseWall = new boolean[2][16];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 16; j++) {
                BaseWall[i][j] = false;
            }
        }
        return BaseWall;
    }

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
        BlockState iron_fence_state = null;
        if (posX == 0 || posX == 15) {
            iron_fence_state = Blocks.IRON_BARS
                .defaultBlockState()
                .setValue(IronBarsBlock.NORTH, true)
                .setValue(IronBarsBlock.SOUTH, true);
        }
        if (posZ == 0 || posZ == 15) {
            iron_fence_state = Blocks.IRON_BARS
                .defaultBlockState()
                .setValue(IronBarsBlock.WEST, true)
                .setValue(IronBarsBlock.EAST, true);
        }
        if (!(iron_fence_state == null)) {
            for (int y = 0; y < 4; y++) {
                if (1 <= y) {
                    level.setBlock(blockPos.above(y), iron_fence_state, 2);
                }
            }
        }
        level.setBlock(blockPos, buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
    }
}
