package com.github.yasuniku.kanata.dimension.generator.town.wallpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public interface WallPattern {
    default void generate(
        LevelAccessor level,
        BlockPos origin,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        Direction direction = null;
        // 壁だけ
        if ((1 < posX && posX < 14) ^ (1 < posZ && posZ < 14)) {
            if ((1 < posX && posX < 14)) {
                if (posZ == 0 || posZ == 1) {
                    direction = Direction.NORTH;
                } else if (posZ == 14 || posZ == 15) {
                    direction = Direction.SOUTH;
                }
            } else {
                if (posX == 0 || posX == 1) {
                    direction = Direction.WEST;
                } else if (posX == 14 || posX == 15) {
                    direction = Direction.EAST;
                }
            }
        } else {
            return;
        }
        if (direction != null) {
            generateBaseWall(level, origin, r, buildingCommonInfo, posX, posZ, direction);
            generateDecorationWall(level, origin, r, buildingCommonInfo, posX, posZ, direction);
        }
    }

    default void generateBaseWall(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Direction direction
    ) {
        boolean[][] BaseWall = getBaseWallShape();

        switch (direction) {
            case NORTH: {
                if (BaseWall[posZ][posX - 2]) {
                    for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {

                        level.setBlock(
                            blockPos.above(y),
                            buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                            2
                        );
                    }
                }
                return;
            }
            case SOUTH: {
                if (BaseWall[1 - (posZ - 14)][13 - posX]) {
                    for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                        level.setBlock(
                            blockPos.above(y),
                            buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                            2
                        );
                    }
                }
                return;
            }
            case WEST: {
                if (BaseWall[posX][13 - posZ]) {
                    for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                        level.setBlock(
                            blockPos.above(y),
                            buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                            2
                        );
                    }
                }

                return;
            }
            case EAST: {
                if (BaseWall[1 - (posX - 14)][posZ - 2]) {
                    for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                        level.setBlock(
                            blockPos.above(y),
                            buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                            2
                        );
                    }
                }
            }
        }
    }

    default void generateDecorationWall(
        LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Direction direction
    ) {
        if (posX == 0 || posX == 15 || posZ == 0 || posZ == 15) {
            level.setBlock(
                blockPos.above(buildingCommonInfo.floorHeight() - 1),
                buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                2
            );
            level.setBlock(blockPos, buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
        }
    }

    default boolean[][] getBaseWallShape() {
        return new boolean[][]{{true, true, true, true, false, false, false, false, true, true, true, true}, {false, false, false, true, true, true, true, true, true, false, false, false,}};
    }
}
