package com.github.yasuniku.kanata.block.reinforcedbrick;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface ReinforcedBrickMaterial {
    static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties
            .of()
            .strength(200.0F, 1200.0F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHER_BRICKS);
    }
}
