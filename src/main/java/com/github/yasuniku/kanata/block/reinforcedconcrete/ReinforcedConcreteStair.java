package com.github.yasuniku.kanata.block.reinforcedconcrete;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedConcreteStair extends StairBlock implements ReinforcedConcreteMaterial {
    public ReinforcedConcreteStair() {
        //noinspection deprecation
        super(KanataBlock.REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), ReinforcedConcreteMaterial.properties());
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        level.setBlock(pos, KanataBlock.CRUMBLING_REINFORCED_CONCRETE_STAIRS.get().defaultBlockState(), 3);
    }
}
