package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.MegaStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;

import java.util.List;

public record MegaBuildingInfo(int startX, int startZ, int width, int height, BuildingCommonInfo commonInfo,
                               List<MegaFloorPattern> floors, List<WallPattern> walls,
                               MegaStairPattern megaStairPattern) {
}

