package com.github.yasuniku.kanata.block.reinforcedbrick;

import net.minecraft.world.level.block.Block;

public class ReinforcedBrickBlock extends Block implements ReinforcedBrickMaterial {
    public ReinforcedBrickBlock() {
        super(ReinforcedBrickMaterial.properties());
    }
}
