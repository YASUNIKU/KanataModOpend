package com.github.yasuniku.kanata.block.reinforcedbrick;

import net.minecraft.world.level.block.IronBarsBlock;

public class ReinforcedBrickPanel extends IronBarsBlock implements ReinforcedBrickMaterial {
    public ReinforcedBrickPanel() {
        super(ReinforcedBrickMaterial.properties());
    }
}
