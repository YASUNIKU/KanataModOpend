package com.github.yasuniku.kanata.utils;

public record Vector2DInt(int x, int y) {
    public Vector2DInt withX(int newX) {
        return new Vector2DInt(newX, this.y);
    }

    public Vector2DInt withY(int newY) {
        return new Vector2DInt(this.x, newY);
    }

    public Vector2DInt add(int dx, int dy) {
        return new Vector2DInt(this.x + dx, this.y + dy);
    }
}
