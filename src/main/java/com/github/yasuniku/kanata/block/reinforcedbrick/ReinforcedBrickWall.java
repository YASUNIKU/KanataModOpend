package com.github.yasuniku.kanata.block.reinforcedbrick;

import net.minecraft.world.level.block.WallBlock;

public class ReinforcedBrickWall extends WallBlock implements ReinforcedBrickMaterial {
    public ReinforcedBrickWall() {
        super(ReinforcedBrickMaterial.properties());
    }
}
