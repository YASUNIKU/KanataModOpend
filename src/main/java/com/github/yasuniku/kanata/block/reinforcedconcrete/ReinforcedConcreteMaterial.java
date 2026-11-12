package com.github.yasuniku.kanata.block.reinforcedconcrete;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface ReinforcedConcreteMaterial {
    static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties
            .of()
            .strength(100.0F, 6.0F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK);
    }
}
