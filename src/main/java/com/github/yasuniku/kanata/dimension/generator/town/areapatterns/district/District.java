package com.github.yasuniku.kanata.dimension.generator.town.areapatterns.district;

import com.github.yasuniku.kanata.utils.CellPos;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class District {
    private CellPos start;
    private Vector2DInt size;
    private @Nullable Vector2DInt megaBuildingStart;
    private final long distSeed;

    public District(CellPos start, Vector2DInt size, Long seed) {
        this.start = start;
        this.size = size;
        this.distSeed = seed;
    }

    public void setStart(CellPos cellPos) {
        this.start = cellPos;
    }

    public void setSize(Vector2DInt size) {
        this.size = size;
    }

    public void setMegaBuildingStart(@Nullable Vector2DInt megaBuildingStart) {
        this.megaBuildingStart = megaBuildingStart;
    }

    public CellPos getStart() {
        return start;
    }

    public Vector2DInt getSize() {
        return size;
    }

    public int getWidth() {
        return size.x();
    }

    public int getHeight() {
        return size.y();
    }

    public Optional<Vector2DInt> getMegaBuildingStart() {
        return Optional.ofNullable(megaBuildingStart);
    }

    public long getDistSeed() {
        return distSeed;
    }

    public Optional<Vector2DInt> getMegaBuildingPos(CellPos cellPos) {
        if (megaBuildingStart == null) {
            return Optional.empty();
        }

        var x = cellPos.x() - (start.x() + megaBuildingStart.x());
        var z = cellPos.z() - (start.z() + megaBuildingStart.y());

        if (0 <= x && x < 2 && 0 <= z && z < 2) {
            return Optional.of(new Vector2DInt(x, z));
        }
        return Optional.empty();
    }

    public Optional<CellPos> getMegaBuildingStartCellPos() {
        return Optional.ofNullable(megaBuildingStart).map(s -> start.add(s.x(), s.y()));
    }

    public boolean contains(CellPos cellPos) {
        return start.x() <= cellPos.x() && cellPos.x() < start.x() + size.x() && start.z() <= cellPos.z() && cellPos.z() < start.z() + size.y();
    }
}
