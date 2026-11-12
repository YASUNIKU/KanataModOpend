package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MegaFloorPatternRegistry {
    private static final List<MegaFloorPattern> PATTERNS = new ArrayList<>();

    public static void register(MegaFloorPattern pattern) {
        PATTERNS.add(pattern);
    }

    public static MegaFloorPattern pickRandom(Random r) {
        return PATTERNS.get(r.nextInt(PATTERNS.size()));
    }
}
