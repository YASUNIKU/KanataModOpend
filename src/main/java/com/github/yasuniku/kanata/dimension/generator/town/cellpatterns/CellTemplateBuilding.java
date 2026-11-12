package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.IsRoad;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.utils.BlockPlaceHelper;
import com.github.yasuniku.kanata.utils.LocalXZPos;
import com.github.yasuniku.kanata.utils.MyChunkPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.Random;

public class CellTemplateBuilding implements Cell {
    long seed;
    Direction direction;
    int baseFloors;

    public CellTemplateBuilding(int baseFloors, Random r, IsRoad isRoad) {
        this.baseFloors = baseFloors;
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

        Rotation rotation = Rotation.NONE;
        rotation = switch (direction) {
            case NORTH -> Rotation.CLOCKWISE_180;
            case WEST -> Rotation.CLOCKWISE_90;
            case EAST -> Rotation.COUNTERCLOCKWISE_90;
            default -> rotation;
        };

        var randSrc = RandomSource.create(seed);
        var structureSettings = new StructurePlaceSettings().setRotation(rotation).setKnownShape(true);

        var origin = BlockPlaceHelper
            .RotateChunk(rotation, new LocalXZPos(randSrc.nextInt(2), randSrc.nextInt(2)))
            .toBlockPos(info.surfaceBy(), chunkPos);

        StructureTemplate template;
        for (int floor = 0; floor < baseFloors; floor++) {
            switch (floor) {
                case 0 -> template = BlockPlaceHelper.getStructureTemplate(
                    Objects.requireNonNull(level.getServer()),
                    "building_1f_cafe"
                );
                default -> template = BlockPlaceHelper.getStructureTemplate(
                    Objects.requireNonNull(level.getServer()),
                    "building_2f_office"
                );
            }
            template.placeInWorld(level, origin, origin, structureSettings, randSrc, Block.UPDATE_KNOWN_SHAPE);
            origin = origin.above(template.getSize().getY());
        }
    }
}
