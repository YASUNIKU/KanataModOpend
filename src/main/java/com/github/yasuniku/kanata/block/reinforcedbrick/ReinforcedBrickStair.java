package com.github.yasuniku.kanata.block.reinforcedbrick;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.world.level.block.StairBlock;

public class ReinforcedBrickStair extends StairBlock implements ReinforcedBrickMaterial {
    public ReinforcedBrickStair() {
        //noinspection deprecation
        super(KanataBlock.REINFORCED_BRICK_BLOCK.get().defaultBlockState(), ReinforcedBrickMaterial.properties());
    }
}
