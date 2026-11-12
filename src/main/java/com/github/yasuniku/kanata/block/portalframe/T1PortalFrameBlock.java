package com.github.yasuniku.kanata.block.portalframe;

import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class T1PortalFrameBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public T1PortalFrameBlock(Properties props) {
        super(props);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);

        if (level.isClientSide()) {
            return;
        }

        // ブロックが別のブロックに置き換わった場合のみ実行
        if (state.getBlock() != newState.getBlock()) {
            destroyAdjacentPortalBlocks(level, pos);
        }
    }

    private void destroyAdjacentPortalBlocks(Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos adjacent = pos.relative(dir);

            if (level.getBlockState(adjacent).is(KanataBlock.T1_PORTAL_BLOCK.get())) {
                level.destroyBlock(adjacent, false); // ドロップなしで破壊
            }
        }
    }
}
