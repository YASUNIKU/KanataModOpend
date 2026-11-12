package com.github.yasuniku.kanata.block.moduleslotadder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jspecify.annotations.Nullable;

public class ModuleSlotAdderBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    // 上側の形状（例：奥行き 2px の板）
    private static final VoxelShape UPPER_SHAPE = Block.box(0, 0, 14, 16, 16, 16);

    public ModuleSlotAdderBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(HALF, DoubleBlockHalf.LOWER));
    }

    // 設置時：下側だけ返す
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level level = ctx.getLevel();

        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(ctx)) {

            return this
                .defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(HALF, DoubleBlockHalf.LOWER);
        }

        return null;
    }

    // 上側ブロックを設置
    @Override
    public void setPlacedBy(
        Level level,
        BlockPos pos,
        BlockState state,
        @Nullable LivingEntity placer,
        ItemStack stack
    ) {
        BlockState upper = state.setValue(HALF, DoubleBlockHalf.UPPER);
        level.setBlock(pos.above(), upper, 3);
    }

    // 破壊時に上下をまとめて破壊
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockPos up = pos.above();
                if (level.getBlockState(up).is(this)) {
                    level.destroyBlock(up, false);
                }
            } else {
                BlockPos down = pos.below();
                if (level.getBlockState(down).is(this)) {
                    level.destroyBlock(down, false);
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    // 当たり判定
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return rotateShape(state.getValue(FACING));
        }

        // 下側はフルブロック
        return Shapes.block();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    // BlockEntity は下側のみ
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new ModuleSlotAdderBlockEntity(pos, state) : null;
    }

    // 形状回転
    private static VoxelShape rotateShape(Direction to) {
        VoxelShape[] buffer = new VoxelShape[]{ModuleSlotAdderBlock.UPPER_SHAPE, Shapes.empty()};
        int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;

        for (int i = 0; i < times; i++) {
            VoxelShape current = buffer[0];
            VoxelShape rotated = Shapes.empty();

            for (AABB box : current.toAabbs()) {
                rotated = Shapes.or(
                    rotated,
                    Shapes.create(1 - box.maxZ, box.minY, box.minX, 1 - box.minZ, box.maxY, box.maxX)
                );
            }

            buffer[0] = rotated;
        }

        return buffer[0];
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
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // どの位置をクリックしても LOWER の位置を逆算
        BlockPos bePos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below();

        BlockEntity be = level.getBlockEntity(bePos);

        if (be instanceof ModuleSlotAdderBlockEntity provider) {
            NetworkHooks.openScreen((ServerPlayer) player, provider, bePos);
        }

        return InteractionResult.CONSUME;
    }
}
