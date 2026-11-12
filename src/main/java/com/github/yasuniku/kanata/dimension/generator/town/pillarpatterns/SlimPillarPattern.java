package com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Random;

@SuppressWarnings("unused")
public class SlimPillarPattern implements PillarPattern {
    @Override
    public void generate(
        @UnknownNullability @NonNull LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        if (!(1 < posX && posX < 14 || 1 < posZ && posZ < 14)) {
            if (!(posX == 0 || posX == 15 || posZ == 0 || posZ == 15)) {
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

    @Override
    public void generateRooftop(
        @UnknownNullability @NonNull LevelAccessor level,
        BlockPos blockPos,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ
    ) {
        if (!(1 < posX && posX < 14 || 1 < posZ && posZ < 14)) {
            if (!(posX == 0 || posX == 15 || posZ == 0 || posZ == 15)) {
                for (int y = 0; y < 4; y++) {
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
