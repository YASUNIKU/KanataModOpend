package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;

import java.util.Random;

public interface FloorPattern {
    void generate(
        LevelAccessor level,
        BlockPos origin,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        WallPattern wallPatttern
    );

    default void generateWall(
        LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        WallPattern wallPatttern
    ) {
        wallPatttern.generate(level, blockPos, r, buildingCommonInfo, posX, posZ);
    }

    default void generateFloor(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        if (0 < posX && posX < 15 && 0 < posZ && posZ < 15) {
            level.setBlock(blockPos, buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
        }
    }

    default void generateCeiling(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        if (0 < posX && posX < 15 && 0 < posZ && posZ < 15) {
            BlockPos pos = blockPos.offset(0, buildingCommonInfo.floorHeight(), 0);
            level.setBlock(pos.below(1), buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
        }
    }

    default void generatePillar(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        buildingCommonInfo.pillarPattern().generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
    }

    default void generateStair(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        if (4 <= posX && posX <= 11 && 2 <= posZ && posZ <= 3) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                //階段を設置する空間を一旦空気で満たす
                level.setBlock(blockPos.above(y), Blocks.AIR.defaultBlockState(), 2);
                if (posX == 11) {
                    //床の高さの階段だけ下の階段との接続なので別枠で生成
                    level.setBlock(
                        blockPos,
                        buildingCommonInfo
                            .floorBlock()
                            .stair()
                            .defaultBlockState()
                            .setValue(StairBlock.FACING, Direction.EAST)
                            .setValue(StairBlock.HALF, Half.BOTTOM),
                        2
                    );
                    //上で無駄に空気ブロックにした分を補填。上に階が続くならここは上書きされる。
                    level.setBlock(
                        blockPos.above(buildingCommonInfo.floorHeight() - 1),
                        buildingCommonInfo.floorBlock().floor().defaultBlockState(),
                        2
                    );
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
        } else if (1 <= posX && posX <= 14 && posZ == 4) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                //階段が露出しているのがかっこ悪いので壁を付ける
                level.setBlock(
                    blockPos.above(y - 1),
                    buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                    2
                );
            }
            if (2 <= posX && posX <= 3 || 12 <= posX && posX <= 13) {
                //出入り用に壁に穴をあける形で扉を作る。
                for (int y = 0; y < 3; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
    }
}
