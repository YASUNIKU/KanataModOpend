package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.MegaFloorPatternRegistry;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.MegaRoomAPattern;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.MegaRoomBPattern;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.DefaultStairPattern;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.ElevatorPattern;
import com.github.yasuniku.kanata.dimension.generator.town.cellpatterns.megabuilding.megastairpatterns.StairPatternRegistry;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.FloorPatternRegistry;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.RoomAPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.RoomBPattern;
import com.github.yasuniku.kanata.dimension.generator.town.floorpatterns.RoomPattern;
import com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns.ChippedPillarPattern;
import com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns.FullPillarPattern;
import com.github.yasuniku.kanata.dimension.generator.town.pillarpatterns.PillarPatternRegistry;
import com.github.yasuniku.kanata.dimension.generator.town.wallpatterns.*;

public class KanataPatterns {
    public static void register() {
        FloorPatternRegistry.register(new RoomPattern());
        FloorPatternRegistry.register(new RoomAPattern());
        FloorPatternRegistry.register(new RoomBPattern());

        MegaFloorPatternRegistry.register(new MegaRoomAPattern());
        MegaFloorPatternRegistry.register(new MegaRoomBPattern());

        WallPatternRegistry.register(new WindowAWallPattern());
        WallPatternRegistry.register(new WindowBWallPattern());
        WallPatternRegistry.register(new PillarAWallPattern());
        WallPatternRegistry.register(new PillarBWallPattern());

        PillarPatternRegistry.register(new FullPillarPattern());
        PillarPatternRegistry.register(new ChippedPillarPattern());

        StairPatternRegistry.register(new DefaultStairPattern());
        StairPatternRegistry.register(new ElevatorPattern());
    }
}
