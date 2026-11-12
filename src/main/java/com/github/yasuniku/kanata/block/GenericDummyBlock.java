package com.github.yasuniku.kanata.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GenericDummyBlock extends Block {
    public static final DirectionProperty MASTER_DIR = DirectionProperty.create("master_dir", Direction.values());

    public GenericDummyBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MASTER_DIR, Direction.DOWN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MASTER_DIR);
    }

    /**
     * 親ブロックの座標を取得
     */
    public BlockPos getMasterPos(BlockPos dummyPos, BlockState state) {
        return dummyPos.relative(state.getValue(MASTER_DIR));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(MASTER_DIR, rotation.rotate(state.getValue(MASTER_DIR)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(MASTER_DIR, mirror.mirror(state.getValue(MASTER_DIR)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hit
    ) {
        BlockPos masterPos = getMasterPos(pos, state);
        BlockState masterState = level.getBlockState(masterPos);

        if (masterState.getBlock() instanceof IMasterBlock) {
            return masterState.use(level, player, hand, hit.withPosition(masterPos));
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) {
            return;
        }

        if (!level.isClientSide) {
            BlockPos masterPos = getMasterPos(pos, state);
            BlockState masterState = level.getBlockState(masterPos);

            if (masterState.getBlock() instanceof IMasterBlock) {
                level.destroyBlock(masterPos, true);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    // 中クリック（ピックブロック）で親ブロックのアイテムを取得
    @SuppressWarnings("deprecation")
    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        BlockPos masterPos = getMasterPos(pos, state);
        BlockState masterState = level.getBlockState(masterPos);
        return masterState.getBlock().getCloneItemStack(level, masterPos, masterState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        var masterState = level.getBlockState(getMasterPos(pos, state));

        var block = masterState.getBlock();

        if (block instanceof IMasterBlock masterBlock) {
            return masterBlock.getSlaveShape(level, pos);
        }
        return super.getShape(state, level, pos, ctx);
    }
}
