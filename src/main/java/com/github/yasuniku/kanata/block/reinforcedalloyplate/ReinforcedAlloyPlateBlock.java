package com.github.yasuniku.kanata.block.reinforcedalloyplate;

import net.minecraft.world.level.block.Block;

public class ReinforcedAlloyPlateBlock extends Block implements ReinforcedAlloyPlateMaterial {
    public ReinforcedAlloyPlateBlock() {
        super(ReinforcedAlloyPlateMaterial.properties());
    }
}
