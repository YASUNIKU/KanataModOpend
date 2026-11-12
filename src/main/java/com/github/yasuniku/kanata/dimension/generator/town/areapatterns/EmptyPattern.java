package com.github.yasuniku.kanata.dimension.generator.town.areapatterns;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.Cell;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.CellRoad;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.IsRoad;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.utils.CellPos;

import java.util.Random;

public class EmptyPattern implements AreaPattern {
    @Override
    public Cell getCell(CellPos cellPos, Random r) {
        return new CellRoad(
            r,
            KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get().defaultBlockState(),
            new IsRoad(true, true, true, true)
        );
    }
}
