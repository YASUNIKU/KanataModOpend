package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

import static com.github.yasuniku.kanata.utils.BlockPlaceHelper.placeChestOrSpawnBox;

public class RoomAPattern implements FloorPattern {
    @Override
    public void generate(
        LevelAccessor level,
        BlockPos origin,
        Random r,
        BuildingCommonInfo info,
        WallPattern wallPatttern
    ) {
        int wallX = 5 + r.nextInt(6);
        int doorZ = 5 + r.nextInt(8);
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
                generateRoomWall(level, pos, wallX, doorZ, r, info, x, z);
                // 階段
                generateStair(level, pos, r, info, x, z);
            }
        }
    }

    private void generateRoomWall(
        LevelAccessor level,
        BlockPos blockPos,
        int wallX,
        int doorZ,
        Random r,
        BuildingCommonInfo info,
        int posX,
        int posZ
    ) {
        if (!(posX == wallX && (4 < posZ && posZ <= 14))) {
            return;
        }

        for (int y = 0; y < info.floorHeight(); y++) {
            level.setBlock(blockPos.above(y), info.wallBlock().fullBlock().defaultBlockState(), 2);
            if ((posZ == doorZ || posZ == doorZ + 1)) {
                if (1 <= y && y <= 3) {
                    level.setBlock(blockPos.above(y), Blocks.AIR.defaultBlockState(), 2);
                }
            } else if (y == 1 && posZ <= 13) {
                if (r.nextBoolean()) {
                    placeChestOrSpawnBox(level, blockPos.above().east(1), Direction.EAST, r);
                } else {
                    placeChestOrSpawnBox(level, blockPos.above().west(1), Direction.WEST, r);
                }
            }
        }
    }
}
