package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns;

import com.github.yasuniku.kanata.utils.MyChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;

public interface Cell {
    void generate(MyChunkPos chunkPos, ServerLevelAccessor level, CellCommonInfo cellCommonInfo);
}
