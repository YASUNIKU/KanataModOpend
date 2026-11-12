package com.github.yasuniku.kanata.block.reinforcedconcrete;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedConcreteSlab extends SlabBlock implements ReinforcedConcreteMaterial {
    public ReinforcedConcreteSlab() {
        super(ReinforcedConcreteMaterial.properties());
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        level.setBlock(pos, KanataBlock.CRUMBLING_REINFORCED_CONCRETE_SLAB.get().defaultBlockState(), 3);
    }
}
