package com.github.yasuniku.kanata.utils;

import net.minecraft.util.StringRepresentable;

public enum TablePart implements StringRepresentable {
    LEFT,        // 親
    RIGHT;       // 右

    public String toString() {
        return this.getSerializedName();
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}

