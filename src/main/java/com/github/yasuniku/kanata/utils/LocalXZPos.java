package com.github.yasuniku.kanata.utils;

import net.minecraft.core.BlockPos;

public record LocalXZPos(int x, int z) {
    public LocalXZPos add(int x, int z) {
        return new LocalXZPos(x() + x, z() + z);
    }

    public BlockPos toBlockPos(int y, MyChunkPos chunkPos) {
        return new BlockPos(chunkPos.toBlockX() + x(), y, chunkPos.toBlockZ() + z());
    }
}
