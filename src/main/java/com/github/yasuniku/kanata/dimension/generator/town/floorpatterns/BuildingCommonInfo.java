package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.BlockVariantProperty;
import com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns.PillarPattern;

public record BuildingCommonInfo(BlockVariantProperty wallBlock, BlockVariantProperty floorBlock, int floorHeight,
                                 PillarPattern pillarPattern, IsRoad isRoad) {
}

