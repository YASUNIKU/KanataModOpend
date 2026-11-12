package com.github.yasuniku.kanata.utils;

import com.github.yasuniku.kanata.dimension.generator.town.AreaGrid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public record MyChunkPos(int x, int z) {

    public static MyChunkPos fromChunkPos(ChunkPos chunkPos) {
        return new MyChunkPos(chunkPos.x, chunkPos.z);
    }

    public BlockPos toBlockPos(int localX, int y, int localZ) {
        return new BlockPos(toBlockX() + localX, y, toBlockZ() + localZ);
    }

    public BlockPos toBlockPos(int y, LocalXZPos localXZPos) {
        return toBlockPos(localXZPos.x(), y, localXZPos.z());
    }

    public CellPos toCellPos() {
        return new CellPos(Math.floorMod(x(), AreaGrid.areaSize), Math.floorMod(z(), AreaGrid.areaSize));
    }

    public AreaPos toAreaPos() {
        return new AreaPos(Math.floorDiv(x(), AreaGrid.areaSize), Math.floorDiv(z(), AreaGrid.areaSize));
    }

    public int toBlockX() {
        return x() * 16;
    }

    public int toBlockZ() {
        return z() * 16;
    }
}
