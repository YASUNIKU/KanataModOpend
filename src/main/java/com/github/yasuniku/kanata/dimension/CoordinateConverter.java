package com.github.yasuniku.kanata.dimension;

import com.github.yasuniku.kanata.dimension.generator.town.AreaGrid;
import com.github.yasuniku.kanata.utils.Vector2DInt;

public class CoordinateConverter {
    public static Vector2DInt cPositionToEPosition(Vector2DInt cPos) {
        return new Vector2DInt(Math.floorMod(cPos.x(), AreaGrid.areaSize), Math.floorMod(cPos.y(), AreaGrid.areaSize));
    }

    public static Vector2DInt cPositionToAPosition(Vector2DInt cPos) {
        return new Vector2DInt(Math.floorDiv(cPos.x(), AreaGrid.areaSize), Math.floorDiv(cPos.y(), AreaGrid.areaSize));
    }
}
