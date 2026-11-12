package com.github.yasuniku.kanata.dimension.generator.town.wallpatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WallPatternRegistry {
    private static final List<WallPattern> PATTERNS = new ArrayList<>();

    public static void register(WallPattern pattern) {
        PATTERNS.add(pattern);
    }

    public static WallPattern pickRandom(Random r) {
        return PATTERNS.get(r.nextInt(PATTERNS.size()));
    }
}
