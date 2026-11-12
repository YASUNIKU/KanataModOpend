package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;

import java.util.Random;

public class EntranceHallFloorPattern implements FloorPattern {
    @Override
    public void generate(
        LevelAccessor level,
        BlockPos origin,
        Random r,
        BuildingCommonInfo info,
        WallPattern wallPatttern
    ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockPos pos = origin.offset(x, 0, z);
                //天井
                generateCeiling(level, pos, r, info, x, z);
                // 床
                generateFloor(level, pos, r, info, x, z);
                //柱
                generatePillar(level, pos, r, info, x, z);
                // 壁
                generateWall(level, pos, r, info, x, z, wallPatttern);
                //階段
                generateStair(level, pos, r, info, x, z);
            }
        }
    }

    @Override
    public void generateWall(
        LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        WallPattern wallPatttern
    ) {
        FloorPattern.super.generateWall(level, blockPos, r, buildingCommonInfo, posX, posZ, wallPatttern);
        generateDoor(level, blockPos, buildingCommonInfo, posX, posZ);
    }

    @Override
    public void generateStair(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        if (4 <= posX && posX <= 10 && 2 <= posZ && posZ <= 3) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                //階段を設置する空間を一旦空気で満たす
                if (0 < y) {
                    level.setBlock(blockPos.above(y), Blocks.AIR.defaultBlockState(), 2);
                }
                if (y == posX - 3) {
                    //階段本体部分。下付き階段ブロック
                    level.setBlock(
                        blockPos.above(y),
                        buildingCommonInfo
                            .floorBlock()
                            .stair()
                            .defaultBlockState()
                            .setValue(StairBlock.FACING, Direction.EAST)
                            .setValue(StairBlock.HALF, Half.BOTTOM),
                        2
                    );
                    //階段の支えに上付き階段ブロック。
                    level.setBlock(
                        blockPos.above(y - 1),
                        buildingCommonInfo
                            .floorBlock()
                            .stair()
                            .defaultBlockState()
                            .setValue(StairBlock.FACING, Direction.WEST)
                            .setValue(StairBlock.HALF, Half.TOP),
                        2
                    );
                }
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
}
