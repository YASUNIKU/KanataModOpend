package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.IsRoad;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.utils.BlockPlaceHelper;
import com.github.yasuniku.kanata.utils.MyChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class CellRoad implements Cell {
    private final BlockState baseBlock;
    private final IsRoad isRoad;

    public CellRoad(Random r, BlockState baseBlock, IsRoad isRoad) {
        this.baseBlock = baseBlock;
        this.isRoad = isRoad;
    }

    @Override
    public void generate(
        MyChunkPos chunkPos,
        @UnknownNullability @NonNull ServerLevelAccessor level,
        CellCommonInfo info
    ) {
        BlockPlaceHelper.fillCuboid(
            level,
            chunkPos,
            info.surfaceBy(),
            info.surfaceBy(),
            KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get().defaultBlockState()
        );
        BlockPlaceHelper.fillCuboid(
            level,
            chunkPos.toBlockPos(3, info.surfaceBy(), 3),
            chunkPos.toBlockPos(12, info.surfaceBy(), 12),
            baseBlock
        );
        if (isRoad.north()) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(3, info.surfaceBy(), 0),
                chunkPos.toBlockPos(12, info.surfaceBy(), 3),
                baseBlock
            );
        }
        if (isRoad.south()) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(3, info.surfaceBy(), 12),
                chunkPos.toBlockPos(12, info.surfaceBy(), 15),
                baseBlock
            );
        }
        if (isRoad.west()) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(0, info.surfaceBy(), 3),
                chunkPos.toBlockPos(3, info.surfaceBy(), 12),
                baseBlock
            );
        }
        if (isRoad.east()) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(12, info.surfaceBy(), 3),
                chunkPos.toBlockPos(15, info.surfaceBy(), 12),
                baseBlock
            );
        }
    }
}
