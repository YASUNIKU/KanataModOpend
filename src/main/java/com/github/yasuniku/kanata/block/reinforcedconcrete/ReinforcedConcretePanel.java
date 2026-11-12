package com.github.yasuniku.kanata.block.reinforcedconcrete;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedConcretePanel extends IronBarsBlock implements ReinforcedConcreteMaterial {
    public ReinforcedConcretePanel() {
        super(ReinforcedConcreteMaterial.properties());
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        level.setBlock(pos, KanataBlock.CRUMBLING_REINFORCED_CONCRETE_PANEL.get().defaultBlockState(), 3);
    }
}
