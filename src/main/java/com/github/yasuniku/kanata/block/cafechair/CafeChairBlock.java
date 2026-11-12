package com.github.yasuniku.kanata.block.cafechair;

import com.github.yasuniku.kanata.entity.custom.SeatEntity;
import com.github.yasuniku.kanata.registry.KanataEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.github.yasuniku.kanata.utils.Utils.rotateShape;

public class CafeChairBlock extends HorizontalDirectionalBlock {

    private static final VoxelShape SHAPE = Shapes.or(
        // 脚
        Block.box(12, 0, 11, 14, 8, 14),
        Block.box(2, 0, 11, 4, 8, 14),
        Block.box(2, 0, 2, 4, 8, 5),
        Block.box(12, 0, 2, 14, 8, 5),

        // 座面
        Block.box(2, 8, 2, 14, 10, 14),

        // 背もたれ
        Block.box(2, 10, 12, 14, 20, 14)
    );

    public CafeChairBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return rotateShape(facing, SHAPE);
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

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (player.isPassenger()) {
            return InteractionResult.CONSUME;
        }

        SeatEntity seat = KanataEntity.SEAT.get().create(level);

        if (seat == null) {
            return InteractionResult.FAIL;
        }

        seat.setPos(pos.getX() + 0.5, pos.getY() + 0.35, pos.getZ() + 0.5);

        level.addFreshEntity(seat);

        player.setYRot(state.getValue(FACING).toYRot());

        player.startRiding(seat, false);

        return InteractionResult.CONSUME;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING);
    }
}
