package com.github.yasuniku.kanata.dimension.generator.town;

import com.github.yasuniku.kanata.dimension.generator.town.areapatterns.Area;
import com.github.yasuniku.kanata.utils.AreaPos;

public class AreaGrid {

    private final long worldSeed;
    @SuppressWarnings("FieldCanBeLocal")
    public static final int areaSize = 16; // 例: 16チャンク

    public AreaGrid(long worldSeed) {
        this.worldSeed = worldSeed;
    }

    public Area getArea(AreaPos areaPos) {
        return new Area(areaPos, worldSeed);
    }
}
