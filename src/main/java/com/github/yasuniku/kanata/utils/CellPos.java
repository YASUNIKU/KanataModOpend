package com.github.yasuniku.kanata.utils;

public record CellPos(int x, int z) {

    public static CellPos fromVector2DInt(Vector2DInt vector2DInt) {
        return new CellPos(vector2DInt.x(), vector2DInt.y());
    }

    public CellPos add(int x, int z) {
        return new CellPos(x() + x, z() + z);
    }
}
