package com.github.yasuniku.kanata.block.reinforcedconcrete;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedConcreteWall extends WallBlock implements ReinforcedConcreteMaterial {
    public ReinforcedConcreteWall() {
        super(ReinforcedConcreteMaterial.properties());
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        level.setBlock(pos, KanataBlock.CRUMBLING_REINFORCED_CONCRETE_WALL.get().defaultBlockState(), 3);
    }
}
