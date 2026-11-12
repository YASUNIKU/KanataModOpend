package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.MegaStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Random;

public class MegaRooftopPattern implements MegaFloorPattern {
    @Override
    public void generate(
        LevelAccessor levelAccessor,
        BlockPos origin,
        Random r,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        WallPattern wallPatttern,
        MegaStairPattern megaStairPattern
    ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockPos pos = origin.offset(x, 0, z);

                // 床
                generateFloor(levelAccessor, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 壁
                generateWall(levelAccessor, pos, r, buildingCommonInfo, buildingPos, x, z, wallPatttern);
                // 柱
                generatePillar(levelAccessor, pos, r, buildingCommonInfo, buildingPos, x, z);
                // 階段
                generateStair(levelAccessor, pos, r, buildingCommonInfo, megaStairPattern, x, z, buildingPos);
            }
        }
    }

    @Override
    public void generatePillar(
        @UnknownNullability LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        Vector2DInt buildingPos,
        int posX,
        int posZ
    ) {
        if (buildingPos.y() == 0) {
            if (buildingPos.x() == 0) {
                if (!(14 <= posX && 14 <= posZ)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generateRooftop(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            } else {
                if (!(posX <= 1 && 14 <= posZ)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generateRooftop(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            }
        } else {
            if (buildingPos.x() == 0) {
                if (!(14 <= posX && posZ <= 1)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generateRooftop(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            } else {
                if (!(posX <= 1 && posZ <= 1)) {
                    buildingCommonInfo
                        .pillarPattern()
                        .generateRooftop(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ);
                }
            }
        }
    }

    @Override
    public void generateStair(
        @UnknownNullability LevelAccessor level,
        BlockPos blockPos,
        Random ignoredR,
        BuildingCommonInfo buildingCommonInfo,
        MegaStairPattern megaStairPattern,
        int posX,
        int posZ,
        Vector2DInt buildingPos
    ) {
        megaStairPattern.generateRootopStair(level, blockPos, ignoredR, buildingCommonInfo, posX, posZ, buildingPos);
    }
}
