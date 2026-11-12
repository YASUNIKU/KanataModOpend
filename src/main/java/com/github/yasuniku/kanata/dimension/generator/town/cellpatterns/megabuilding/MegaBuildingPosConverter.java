package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding;

import com.github.yasuniku.kanata.utils.Vector2DInt;

public class MegaBuildingPosConverter {
    @SuppressWarnings("unused")
    public static Vector2DInt ConvertToBuildingPosFromMegaBuildingLocalBlockPos(Vector2DInt pos) {
        return new Vector2DInt(pos.x() < 16 ? 0 : 1, pos.y() < 16 ? 0 : 1);
    }

    @SuppressWarnings("unused")
    public static Vector2DInt ConvertToLocalBlockPosFromMegaBuildingLocalBlockPos(Vector2DInt pos32x) {
        return new Vector2DInt(
            pos32x.x() < 16 ? pos32x.x() : pos32x.x() - 16,
            pos32x.y() < 16 ? pos32x.y() : pos32x.y() - 16
        );
    }

    public static Vector2DInt ConvertToMegaBuildingLocalBlockPos(Vector2DInt buildingPos, Vector2DInt pos16x) {
        return new Vector2DInt(buildingPos.x() * 16 + pos16x.x(), buildingPos.y() * 16 + pos16x.y());
    }
}
