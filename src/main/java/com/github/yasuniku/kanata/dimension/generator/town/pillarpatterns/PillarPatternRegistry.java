package com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PillarPatternRegistry {
    private static final List<PillarPattern> PATTERNS = new ArrayList<>();

    public static void register(PillarPattern pattern) {
        PATTERNS.add(pattern);
    }

    public static PillarPattern pickRandom(Random r) {
        return PATTERNS.get(r.nextInt(PATTERNS.size()));
    }
}
