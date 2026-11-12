package com.github.yasuniku.kanata.dimension.generator.town.areapatterns;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.Cell;
import com.github.yasuniku.kanata.utils.CellPos;

import java.util.Random;

public interface AreaPattern {
    Cell getCell(CellPos cellPos, Random r);
}
