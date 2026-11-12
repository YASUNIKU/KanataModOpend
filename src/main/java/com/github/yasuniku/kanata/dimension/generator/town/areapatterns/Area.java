package com.github.yasuniku.kanata.dimension.generator.town.areapatterns;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.Cell;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.CellCommonInfo;
import com.github.yasuniku.kanata.utils.AreaPos;
import com.github.yasuniku.kanata.utils.CellPos;

import java.util.Random;

public class Area {
    @SuppressWarnings("unused")
    private final AreaPos areaPos;
    private final Random random;
    private final long areaSeed;
    private final AreaPattern areaPattern;
    private final CellCommonInfo cellCommonInfo = new CellCommonInfo(4);

    public Area(AreaPos areaPos, long worldSeed) {
        this.areaPos = areaPos;
        this.random = new Random(worldSeed + areaPos.x() * 341873128712L + areaPos.z() * 132897987541L);
        this.areaSeed = this.random.nextLong();
        this.areaPattern = random.nextInt(10) == 0 ? new EmptyPattern() : new CentralPattern(areaSeed);
    }

    public Cell getCell(CellPos cellPos) {
        Random r = new Random(areaSeed + cellPos.x() * 912367L + cellPos.z() * 192837L);

        return areaPattern.getCell(cellPos, r);
    }

    public CellCommonInfo getCellCommonInfo() {
        return cellCommonInfo;
    }
}
