package com.github.yasuniku.kanata.dimension.generator.town;

import com.github.yasuniku.kanata.utils.MyChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;

public class ChunkBuildingRenderer {
    private final AreaGrid areaGrid;

    public ChunkBuildingRenderer(long worldSeed) {
        this.areaGrid = new AreaGrid(worldSeed);
    }

    public void generateChunk(ServerLevelAccessor level, MyChunkPos chunkPos) {
        var area = areaGrid.getArea(chunkPos.toAreaPos());
        area.getCell(chunkPos.toCellPos()).generate(chunkPos, level, area.getCellCommonInfo());
    }
}
