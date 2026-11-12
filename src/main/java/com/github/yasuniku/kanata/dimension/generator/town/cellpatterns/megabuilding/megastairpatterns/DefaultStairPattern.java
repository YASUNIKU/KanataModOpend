package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class DefaultStairPattern implements MegaStairPattern {
    @Override
    public void generate(
        @UnknownNullability @NonNull LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    ) {
        if (!(buildingPos.x() == 0 && buildingPos.y() == 0)) {
            return;
        }
        if ((8 <= posX && posX <= 15) && (9 <= posZ && posZ <= 15)) {
            //一旦階段の範囲を空気で満たす
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                level.setBlock(blockPos.above(y), Blocks.AIR.defaultBlockState(), 2);
            }
        }
        //階段を囲う壁
        if ((8 <= posX && posX <= 15) && (posZ == 9 || posZ == 15) || (posX == 8 || posX == 15) && (9 <= posZ && posZ <= 15)) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {

                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
            if (posX == 15 && ((posZ == 10 || posZ == 11) || (posZ == 13 || posZ == 14))) {
                //出入り用に壁に穴をあける形で扉を作る。
                for (int y = 0; y < 4; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }

        if (11 <= posX && posX <= 14) {
            //階段下側
            int setY;
            if (posZ == 13 || posZ == 14) {
                //1段目が(14,1,14)、4段目が(11,4,14)
                setY = (14 - posX) + 1;
                level.setBlock(
                    blockPos.above(setY),
                    buildingCommonInfo
                        .floorBlock()
                        .stair()
                        .defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.WEST)
                        .setValue(StairBlock.HALF, Half.BOTTOM),
                    2
                );

                level.setBlock(
                    blockPos.above(setY - 1),
                    buildingCommonInfo
                        .floorBlock()
                        .stair()
                        .defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.EAST)
                        .setValue(StairBlock.HALF, Half.TOP),
                    2
                );
            }
            if (posZ == 12) {
                for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                    level.setBlock(
                        blockPos.above(y),
                        buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                        2
                    );
                }
            }
            //階段上側
            if (posZ == 10 || posZ == 11) {
                //1段目が(12,5,11)、4段目が(9,8,11)
                //1段目が(11,5,11)、4段目が(14,8,11)
                setY = posX - 6;
                level.setBlock(
                    blockPos.above(!(posX == 14) ? setY : 0),
                    buildingCommonInfo
                        .floorBlock()
                        .stair()
                        .defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.EAST)
                        .setValue(StairBlock.HALF, Half.BOTTOM),
                    2
                );
                if (posX == 14) {
                    level.setBlock(
                        blockPos.above(buildingCommonInfo.floorHeight() - 1),
                        buildingCommonInfo.floorBlock().floor().defaultBlockState(),
                        2
                    );
                } else {
                    level.setBlock(
                        blockPos.above(setY - 1),
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
        if ((posX == 9 || posX == 10) && (10 <= posZ && posZ <= 14)) {
            level.setBlock(blockPos.above(4), buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
        }
    }

    @Override
    public void generateEntranceHallStair(
        @UnknownNullability @NonNull LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    ) {
        if (!(buildingPos.x() == 0 && buildingPos.y() == 0)) {
            return;
        }
        if ((8 <= posX && posX <= 15) && (9 <= posZ && posZ <= 15)) {
            //一旦階段の範囲を空気で満たす
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                level.setBlock(blockPos.above(y), Blocks.AIR.defaultBlockState(), 2);
            }
        }
        //階段を囲う壁
        if ((8 <= posX && posX <= 15) && (posZ == 9 || posZ == 15) || (posX == 8 || posX == 15) && (9 <= posZ && posZ <= 15)) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {

                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
            if (posX == 15 && (posZ == 13 || posZ == 14)) {
                //出入り用に壁に穴をあける形で扉を作る。
                for (int y = 0; y < 4; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }

        if (11 <= posX && posX <= 14) {
            //階段下側
            int setY;
            if (posZ == 13 || posZ == 14) {
                //1段目が(14,1,14)、4段目が(11,4,14)
                setY = (14 - posX) + 1;
                level.setBlock(
                    blockPos.above(setY),
                    buildingCommonInfo
                        .floorBlock()
                        .stair()
                        .defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.WEST)
                        .setValue(StairBlock.HALF, Half.BOTTOM),
                    2
                );
                for (int i = 0; i < setY; i++) {
                    level.setBlock(blockPos.above(i), buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
                }
            }
            if (posZ == 12) {
                for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                    level.setBlock(
                        blockPos.above(y),
                        buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(),
                        2
                    );
                }
            }
            //階段上側
            if (posZ == 10 || posZ == 11) {
                //1段目が(12,5,11)、4段目が(9,8,11)
                //1段目が(11,5,11)、4段目が(14,8,11)
                setY = posX - 6;

                if (posX == 14) {
                    for (int i = 0; i < buildingCommonInfo.floorHeight(); i++) {
                        level.setBlock(
                            blockPos.above(i),
                            buildingCommonInfo.floorBlock().floor().defaultBlockState(),
                            2
                        );
                    }
                } else {
                    level.setBlock(
                        blockPos.above(setY),
                        buildingCommonInfo
                            .floorBlock()
                            .stair()
                            .defaultBlockState()
                            .setValue(StairBlock.FACING, Direction.EAST)
                            .setValue(StairBlock.HALF, Half.BOTTOM),
                        2
                    );
                    for (int i = 0; i < setY; i++) {
                        level.setBlock(
                            blockPos.above(i),
                            buildingCommonInfo.floorBlock().floor().defaultBlockState(),
                            2
                        );
                    }
                }
            }
        }
        if ((posX == 9 || posX == 10) && (10 <= posZ && posZ <= 14)) {
            for (int i = 0; i <= 4; i++) {
                level.setBlock(blockPos.above(i), buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
            }
        }
    }

    @Override
    public void generateRootopStair(
        @UnknownNullability @NonNull LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    ) {
        if (!(buildingPos.x() == 0 && buildingPos.y() == 0)) {
            return;
        }
        if (!((8 <= posX && posX <= 15) && (9 <= posZ && posZ <= 15))) {
            return;
        } else {
            //一旦階段の範囲を空気で満たす
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                level.setBlock(blockPos.above(y), Blocks.AIR.defaultBlockState(), 2);
            }
        }
        //階段を囲う壁
        if (posZ == 9 || posZ == 15 || posX == 8 || posX == 15) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {

                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
            if (posX == 15 && (posZ == 10 || posZ == 11)) {
                //出入り用に壁に穴をあける形で扉を作る。
                for (int y = 0; y < 4; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
        if ((11 <= posX && posX <= 14) && posZ == 12) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {
                level.setBlock(blockPos.above(y), buildingCommonInfo.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        }
        if (posX == 14) {
            if (posZ == 10 || posZ == 11) {
                level.setBlock(
                    blockPos.above(0),
                    buildingCommonInfo
                        .floorBlock()
                        .stair()
                        .defaultBlockState()
                        .setValue(StairBlock.FACING, Direction.EAST)
                        .setValue(StairBlock.HALF, Half.BOTTOM),
                    2
                );
            }
        }
        level.setBlock(
            blockPos.above(buildingCommonInfo.floorHeight()),
            buildingCommonInfo.floorBlock().floor().defaultBlockState(),
            2
        );
    }
}
