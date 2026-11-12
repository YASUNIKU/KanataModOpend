package com.github.yasuniku.kanata.block.moduleupdatemachine;

public enum ProgressDataSlot {
    PROGRESS, MAX_PROGRESS;
    public static final int COUNT = values().length;

    public static ProgressDataSlot fromIndex(int pIndex) {
        if (COUNT <= pIndex || pIndex < 0) {
            throw new IllegalArgumentException("無効なindexが参照されたよ");
        }
        return values()[pIndex];
    }
}
