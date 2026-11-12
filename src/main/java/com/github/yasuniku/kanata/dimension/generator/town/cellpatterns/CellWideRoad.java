package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.IsRoad;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.utils.BlockPlaceHelper;
import com.github.yasuniku.kanata.utils.CellPos;
import com.github.yasuniku.kanata.utils.MyChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class CellWideRoad implements Cell {
    private final BlockState baseBlock;
    private final IsRoad isRoad;

    private static final int WIDTHSIDEWALK = 2;

    @SuppressWarnings("unused")
    public CellWideRoad(Random r, BlockState baseBlock, IsRoad isRoad) {
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

        CellPos cellPos = chunkPos.toCellPos();

        if (cellPos.x() == 0) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(0, info.surfaceBy(), 0),
                chunkPos.toBlockPos(WIDTHSIDEWALK, info.surfaceBy(), 15),
                baseBlock
            );
        } else if (cellPos.x() == 15) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(15 - WIDTHSIDEWALK, info.surfaceBy(), 0),
                chunkPos.toBlockPos(15, info.surfaceBy(), 15),
                baseBlock
            );
        }
        if (cellPos.z() == 0) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(0, info.surfaceBy(), 0),
                chunkPos.toBlockPos(15, info.surfaceBy(), WIDTHSIDEWALK),
                baseBlock
            );
        } else if (cellPos.z() == 15) {
            BlockPlaceHelper.fillCuboid(
                level,
                chunkPos.toBlockPos(0, info.surfaceBy(), 15 - WIDTHSIDEWALK),
                chunkPos.toBlockPos(15, info.surfaceBy(), 15),
                baseBlock
            );
        }
    }
}
