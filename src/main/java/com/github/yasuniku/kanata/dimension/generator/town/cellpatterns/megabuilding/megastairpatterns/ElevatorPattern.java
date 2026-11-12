package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class ElevatorPattern implements MegaStairPattern {
    @Override
    public void generate(
        @UnknownNullability @NonNull LevelAccessor level,
        BlockPos blockPos,
        Random r,
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
            if (posX == 15 && (11 <= posZ && posZ <= 13)) {
                //出入り用に壁に穴をあける形で扉を作る。
                for (int y = 0; y < 4; y++) {
                    level.setBlock(blockPos.above(y + 1), Blocks.AIR.defaultBlockState(), 2);
                }
            }
            if (posX == 15 && (posZ == 11 || posZ == 13)) {
                BlockState state = posZ == 11 ? Blocks.IRON_BARS
                    .defaultBlockState()
                    .setValue(IronBarsBlock.NORTH, true)
                    .setValue(IronBarsBlock.SOUTH, false) : Blocks.IRON_BARS
                    .defaultBlockState()
                    .setValue(IronBarsBlock.NORTH, false)
                    .setValue(IronBarsBlock.SOUTH, true);
                for (int y = 0; y < 4; y++) {
                    level.setBlock(blockPos.above(y + 1), state, 3);
                }
            }
        }
        if (posX == 9 && (posZ == 11 || posZ == 13)) {
            for (int y = 0; y < buildingCommonInfo.floorHeight(); y++) {

                if (r.nextBoolean()) {
                    level.setBlock(
                        blockPos.above(y),
                        Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.EAST),
                        2
                    );
                }
            }
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
        if (!((8 <= posX && posX <= 15) && (9 <= posZ && posZ <= 15))) {
            return;
        }
        generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ, buildingPos);
        level.setBlock(blockPos, buildingCommonInfo.floorBlock().floor().defaultBlockState(), 2);
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
        }
        generate(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ, buildingPos);
        level.setBlock(
            blockPos.above(buildingCommonInfo.floorHeight()),
            buildingCommonInfo.floorBlock().floor().defaultBlockState(),
            2
        );
    }
}
