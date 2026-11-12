package com.github.yasuniku.kanata.block.reinforcedalloyplate;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.world.level.block.StairBlock;

public class ReinforcedAlloyPlateStair extends StairBlock implements ReinforcedAlloyPlateMaterial {
    public ReinforcedAlloyPlateStair() {
        //noinspection deprecation
        super(
            KanataBlock.REINFORCED_ALLOY_PLATE_BLOCK.get().defaultBlockState(),
            ReinforcedAlloyPlateMaterial.properties()
        );
    }
}
