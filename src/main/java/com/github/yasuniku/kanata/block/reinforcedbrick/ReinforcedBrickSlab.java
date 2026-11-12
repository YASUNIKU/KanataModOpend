package com.github.yasuniku.kanata.block.reinforcedbrick;

import net.minecraft.world.level.block.SlabBlock;

public class ReinforcedBrickSlab extends SlabBlock implements ReinforcedBrickMaterial {
    public ReinforcedBrickSlab() {
        super(ReinforcedBrickMaterial.properties());
    }
}
