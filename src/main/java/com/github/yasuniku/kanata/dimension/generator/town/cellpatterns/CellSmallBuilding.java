package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.IsRoad;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.utils.*;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CellSmallBuilding implements Cell {
    long seed;
    Direction direction;

    List<String> structures = List.of("store_small", "store_small_2");

    public CellSmallBuilding(Random r, IsRoad isRoad) {
        seed = r.nextLong();
        direction = isRoad.getEntranceDirection(r);
    }

    @Override
    public void generate(
        MyChunkPos chunkPos,
        @UnknownNullability @NonNull ServerLevelAccessor level,
        CellCommonInfo info
    ) {
        BlockPlaceHelper.fillCuboid(
            level,
            chunkPos,
            info.surfaceBy(),
            info.surfaceBy(),
            KanataBlock.REINFORCED_CONCRETE_FLOOR_BLOCK.get().defaultBlockState()
        );

        Vector2DInt buildingSize = new Vector2DInt(8, 15);

        Rotation rotation = Rotation.NONE;
        rotation = switch (direction) {
            case NORTH -> Rotation.CLOCKWISE_180;
            case WEST -> Rotation.CLOCKWISE_90;
            case EAST -> Rotation.COUNTERCLOCKWISE_90;
            default -> rotation;
        };

        var randSrc = RandomSource.create(seed);
        var structureSettings = new StructurePlaceSettings().setRotation(rotation).setKnownShape(true);

        var localPos = new LocalXZPos(0, randSrc.nextInt(2));
        var localPos2 = new LocalXZPos(buildingSize.x() + localPos.x(), randSrc.nextInt(2));

        BlockPlaceHelper.placeStructureTemplate(
            BlockPlaceHelper.RotateChunk(rotation, localPos).toBlockPos(info.surfaceBy(), chunkPos),
            level,
            Objects.requireNonNull(Utils.getRandomElement(structures, randSrc)),
            randSrc,
            structureSettings
        );
        BlockPlaceHelper.placeStructureTemplate(
            BlockPlaceHelper.RotateChunk(rotation, localPos2).toBlockPos(info.surfaceBy(), chunkPos),
            level,
            Objects.requireNonNull(Utils.getRandomElement(structures, randSrc)),
            randSrc,
            structureSettings
        );
    }
}
