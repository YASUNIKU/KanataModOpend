package com.github.yasuniku.kanata.block.reinforcedconcrete;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedConcreteBlock extends Block implements ReinforcedConcreteMaterial {
    public ReinforcedConcreteBlock() {
        super(ReinforcedConcreteMaterial.properties());
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        // 爆発に当たった瞬間に crumbling_reinforced_concrete に置き換える
        level.setBlock(pos, KanataBlock.CRUMBLING_REINFORCED_CONCRETE_BLOCK.get().defaultBlockState(), 3);
    }
}
