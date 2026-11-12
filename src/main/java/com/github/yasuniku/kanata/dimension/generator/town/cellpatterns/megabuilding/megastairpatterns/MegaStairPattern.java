package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public interface MegaStairPattern {
    void generate(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    );

    void generateEntranceHallStair(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    );

    void generateRootopStair(
        LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    );
}
