package com.github.yasuniku.kanata.dimension.generator.town.areapatterns;

import com.github.yasuniku.kanata.dimension.generator.town.areapatterns.district.District;
import com.github.yasuniku.kanata.dimension.generator.town.areapatterns.district.DistrictLayoutGenerator;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.*;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.*;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.MegaStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.StairPatternRegistry;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.BuildingCommonInfo;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.IsRoad;
import com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns.PillarPattern;
import com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns.PillarPatternRegistry;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.RooftopWallPattern;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPattern;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.WallPatternRegistry;
import com.github.yasuniku.kanata.utils.CellPos;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.github.yasuniku.kanata.dimension.generator.town.BlockVariantProperty.BUILDING_MATERIALS_VARIANT;

public class CentralPattern implements AreaPattern {

    private final List<District> districts;
    private final int baseFloors;

    public CentralPattern(long areaSeed) {
        this.districts = DistrictLayoutGenerator.generate(areaSeed);
        this.baseFloors = 2 + new Random(areaSeed).nextInt(10);
    }

    @Override
    public Cell getCell(CellPos cellPos, Random r) {
        int ex = cellPos.x();
        int ez = cellPos.z();

        var isRoad = createIsRoad(cellPos, 1);

        if (ex == 0 && ez == 0) {
            return new CellWideRoad(r, Blocks.REDSTONE_BLOCK.defaultBlockState(), isRoad);
        }
        // 外周道路
        if (ex == 0 || ez == 0 || ex == 15 || ez == 15) {
            return new CellWideRoad(r, Blocks.GRAY_CONCRETE.defaultBlockState(), isRoad);
        }

        for (District d : districts) {
            if (d.contains(cellPos)) {
                var megaPos = d.getMegaBuildingPos(cellPos);
                if (megaPos.isPresent()) {
                    return new CellMegaBuilding(generateMegaBuilding(d), megaPos.get());
                }

                if (r.nextBoolean()) {
                    return new CellTemplateBuilding(baseFloors, r, isRoad);
                } else {
                    return new CellSmallBuilding(r, isRoad);
                }
            }
        }
        return new CellRoad(r, Blocks.BLACK_CONCRETE.defaultBlockState(), isRoad);
    }

    private MegaBuildingInfo generateMegaBuilding(District d) {
        Random rand = new Random(d.getDistSeed());
        int floors = rand.nextInt(3) + baseFloors + 2;

        List<MegaFloorPattern> patterns = generateMegaFloorPatterns(rand, floors);
        List<WallPattern> wallPatterns = generateWallPatterns(rand, floors);
        PillarPattern pillarPattern = PillarPatternRegistry.pickRandom(rand);
        MegaStairPattern megaStairPattern = StairPatternRegistry.pickRandom(rand);

        BuildingCommonInfo buildingCommonInfo = new BuildingCommonInfo(
            Objects.requireNonNull(Utils.getRandomElement(BUILDING_MATERIALS_VARIANT, rand)),
            Objects.requireNonNull(Utils.getRandomElement(BUILDING_MATERIALS_VARIANT, rand)),
            8,
            pillarPattern,
            createIsRoad(d.getMegaBuildingStartCellPos().orElseThrow(), 2)
        );

        return new MegaBuildingInfo(
            d.getMegaBuildingStart().orElseThrow().x(),
            d.getMegaBuildingStart().orElseThrow().y(),
            d.getWidth(),
            d.getHeight(),
            buildingCommonInfo,
            patterns,
            wallPatterns,
            megaStairPattern
        );
    }

    private List<MegaFloorPattern> generateMegaFloorPatterns(Random r, int floors) {
        List<MegaFloorPattern> list = new ArrayList<>();
        for (int i = 0; i < floors; i++) {
            if (i == 0) {
                list.add(new MegaEntranceHallFloor());
            } else if (i == floors - 1) {
                list.add(new MegaReturnPortalFloor());
                list.add(new MegaRooftopPattern());
            } else {
                list.add(MegaFloorPatternRegistry.pickRandom(r));
            }
        }
        return list;
    }

    private List<WallPattern> generateWallPatterns(Random r, int floors) {
        List<WallPattern> list = new ArrayList<>();
        for (int i = 0; i < floors; i++) {
            list.add(WallPatternRegistry.pickRandom(r));
        }
        list.add(new RooftopWallPattern());
        return list;
    }

    private boolean isRoad(CellPos cellPos) {
        for (District d : districts) {
            if (d.contains(cellPos)) {
                return false;
            }
        }
        return true;
    }

    private IsRoad createIsRoad(CellPos cellPos, int width) {
        return new IsRoad(
            isRoad(cellPos.add(0, -1)),
            isRoad(cellPos.add(0, width)),
            isRoad(cellPos.add(-1, 0)),
            isRoad(cellPos.add(width, 0))
        );
    }
}
