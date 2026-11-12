package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StairPatternRegistry {
    private static final List<MegaStairPattern> PATTERNS = new ArrayList<>();

    public static void register(MegaStairPattern pattern) {
        PATTERNS.add(pattern);
    }

    public static MegaStairPattern pickRandom(Random r) {
        return PATTERNS.get(r.nextInt(PATTERNS.size()));
    }
}
