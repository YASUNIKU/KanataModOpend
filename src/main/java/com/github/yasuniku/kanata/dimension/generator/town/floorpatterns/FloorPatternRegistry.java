package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloorPatternRegistry {
    private static final List<FloorPattern> PATTERNS = new ArrayList<>();

    public static void register(FloorPattern pattern) {
        PATTERNS.add(pattern);
    }

    public static FloorPattern pickRandom(Random r) {
        return PATTERNS.get(r.nextInt(PATTERNS.size()));
    }
}
