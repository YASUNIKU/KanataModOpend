package com.github.yasuniku.kanata.dimension.generator.town.cellpatterns;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.MegaBuildingInfo;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.MegaFloorPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import com.github.yasuniku.kanata.utils.MyChunkPos;
import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class CellMegaBuilding implements Cell {
    private final MegaBuildingInfo info;
    private final Vector2DInt buildingPos;

    public CellMegaBuilding(MegaBuildingInfo info, Vector2DInt buildingPos) {
        this.info = info;
        this.buildingPos = buildingPos;
    }

    @Override
    public void generate(
        MyChunkPos chunkPos,
        @UnknownNullability @NonNull ServerLevelAccessor level,
        CellCommonInfo common
    ) {
        BuildingCommonInfo buildingCommonInfo = info.commonInfo();

        for (int floor = 0; floor < info.floors().size(); floor++) {

            int by = floor * buildingCommonInfo.floorHeight() + common.surfaceBy();

            MegaFloorPattern pattern = info.floors().get(floor);
            WallPattern wall = info.walls().get(floor);

            pattern.generate(
                level,
                chunkPos.toBlockPos(0, by, 0),
                new Random(),
                buildingCommonInfo,
                buildingPos,
                wall,
                info.megaStairPattern()
            );
        }
    }
}
