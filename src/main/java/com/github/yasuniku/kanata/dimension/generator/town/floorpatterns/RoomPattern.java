package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class RoomPattern implements FloorPattern {
    @Override
    public void generate(
        LevelAccessor level,
        BlockPos origin,
        Random r,
        BuildingCommonInfo info,
        WallPattern wallPatttern
    ) {
        // 16×16 チャンク内に部屋を作る
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockPos pos = origin.offset(x, 0, z);

                // 天井
                generateCeiling(level, pos, r, info, x, z);
                // 床
                generateFloor(level, pos, r, info, x, z);
                // 柱
                generatePillar(level, pos, r, info, x, z);
                // 壁だけ
                generateWall(level, pos, r, info, x, z, wallPatttern);
                // 階段
                generateStair(level, pos, r, info, x, z);
            }
        }
    }
}
