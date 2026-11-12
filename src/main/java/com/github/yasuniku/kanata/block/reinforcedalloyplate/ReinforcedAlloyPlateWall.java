package com.github.yasuniku.kanata.block.reinforcedalloyplate;

import net.minecraft.world.level.block.WallBlock;

public class ReinforcedAlloyPlateWall extends WallBlock implements ReinforcedAlloyPlateMaterial {
    public ReinforcedAlloyPlateWall() {
        super(ReinforcedAlloyPlateMaterial.properties());
    }
}
