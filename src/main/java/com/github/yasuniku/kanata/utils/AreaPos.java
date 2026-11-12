package com.github.yasuniku.kanata.utils;

public record AreaPos(int x, int z) {
    public AreaPos add(int x, int z) {
        return new AreaPos(x() + x, z() + z);
    }
}
