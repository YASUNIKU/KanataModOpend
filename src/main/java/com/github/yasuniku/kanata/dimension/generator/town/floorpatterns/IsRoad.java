package com.github.yasuniku.kanata.dimension.generator.town.floorpatterns;

import net.minecraft.core.Direction;

import java.util.Random;

public record IsRoad(boolean north, boolean south, boolean west, boolean east) {
    public Direction getEntranceDirection(Random r) {
        Direction[] buf = new Direction[4];
        int count = 0;

        if (north) {
            buf[count++] = Direction.NORTH;
        }
        if (east) {
            buf[count++] = Direction.EAST;
        }
        if (west) {
            buf[count++] = Direction.WEST;
        }
        if (south) {
            buf[count++] = Direction.SOUTH;
        }

        if (count == 0) {
            return Direction.NORTH;
        }

        return buf[r.nextInt(count)];
    }
}
