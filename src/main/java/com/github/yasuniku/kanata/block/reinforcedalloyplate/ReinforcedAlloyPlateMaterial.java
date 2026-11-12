package com.github.yasuniku.kanata.block.reinforcedalloyplate;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public interface ReinforcedAlloyPlateMaterial {
    static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties
            .of()
            .strength(500.0F, 1200.0F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK)
            .pushReaction(PushReaction.BLOCK);
    }
}
