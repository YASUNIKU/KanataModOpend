package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

import static com.github.yasuniku.kanata.utils.BlockPlaceHelper.placeChestOrSpawnBox;

public class RoomBPattern implements FloorPattern {
    @Override
    public void generate(
        LevelAccessor level,
        BlockPos origin,
        Random r,
        BuildingCommonInfo info,
        WallPattern wallPatttern
    ) {
        int wallX = 5 + r.nextInt(6);
        int wallZ = 8 + r.nextInt(3);
        int door1X = 2 + r.nextInt(wallX - 2);
        int door2X = wallX + 1 + r.nextInt(13 - wallX);
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
                generateRoomWall(level, pos, wallX, wallZ, door1X, door2X, r, info, x, z);
                // 階段
                generateStair(level, pos, r, info, x, z);
            }
        }
    }

    private void generateRoomWall(
        LevelAccessor level,
        BlockPos blockPos,
        int wallX,
        int wallZ,
        int door1X,
        int door2X,
        Random r,
        BuildingCommonInfo info,
        int posX,
        int posZ
    ) {
        if (posZ == wallZ && (1 <= posX && posX <= 14)) {
            for (int y = 0; y < info.floorHeight(); y++) {
                level.setBlock(blockPos.above(y), info.wallBlock().fullBlock().defaultBlockState(), 2);
                if ((posX == door1X || posX == door2X) && (y == 1 || y == 2)) {
                    level.setBlock(blockPos.above(y), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
        if (posX == wallX && (wallZ <= posZ && posZ <= 14)) {
            for (int y = 0; y < info.floorHeight(); y++) {
                level.setBlock(blockPos.above(y), info.wallBlock().fullBlock().defaultBlockState(), 2);
            }
        }
        if (3 <= posX && posX <= 13 && posZ == 13 && posX != wallX) {
            placeChestOrSpawnBox(level, blockPos.above(), Direction.NORTH, r);
        }
    }
}
