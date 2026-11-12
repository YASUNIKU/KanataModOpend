package com.github.yasuniku.kanata.block.spawnbox;

import com.github.yasuniku.kanata.block.GenericDummyBlock;
import com.github.yasuniku.kanata.block.IMasterBlock;
import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import static com.github.yasuniku.kanata.utils.Utils.rotateShape;

public class SpawnBoxBlock extends Block implements EntityBlock, IMasterBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    // 壁の厚さ
    private static final int t = 1;

    private static final VoxelShape UPPER_CLOSE;
    private static final VoxelShape UPPER_OPEN;

    static {
        VoxelShape shape = Shapes.empty();

        shape = Shapes.or(shape, Block.box(0, 16 - t, 0, 16, 16, 16));//天井

        shape = Shapes.or(shape, Block.box(0, 0, 0, t, 16, 16));//正面からみて右

        shape = Shapes.or(shape, Block.box(16 - t, 0, 0, 16, 16, 16));//正面から見て左

        shape = Shapes.or(shape, Block.box(0, 0, 16 - t, 16, 16, 16));//手前側

        UPPER_OPEN = shape;

        shape = Shapes.or(shape, Block.box(0, 0, 0, 16, 16, t));//北向きのとき奥側
        UPPER_CLOSE = shape;
    }

    private static final VoxelShape LOWER_CLOSE;
    private static final VoxelShape LOWER_OPEN;

    static {
        VoxelShape shape = Shapes.empty();

        shape = Shapes.or(shape, Block.box(0, 0, 0, 16, t, 16));//底

        shape = Shapes.or(shape, Block.box(0, 0, 0, t, 16, 16));//正面からみて右

        shape = Shapes.or(shape, Block.box(16 - t, 0, 0, 16, 16, 16));//正面から見て左

        shape = Shapes.or(shape, Block.box(0, 0, 16 - t, 16, 16, 16));//手前側

        LOWER_OPEN = shape;

        shape = Shapes.or(shape, Block.box(0, 0, 0, 16, 16, t));//北向きのとき奥側
        LOWER_CLOSE = shape;
    }

    public SpawnBoxBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @org.jspecify.annotations.Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level level = ctx.getLevel();

        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(ctx)) {
            return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
        }

        return null;
    }

    @Override
    public void setPlacedBy(
        Level level,
        BlockPos pos,
        BlockState state,
        @Nullable LivingEntity placer,
        ItemStack stack
    ) {
        super.setPlacedBy(level, pos, state, placer, stack);

        BlockPos dummyPos = pos.above();
        BlockState dummyState = KanataBlock.GENERIC_DUMMY_BLOCK
            .get()
            .defaultBlockState()
            .setValue(GenericDummyBlock.MASTER_DIR, Direction.DOWN);
        level.setBlock(dummyPos, dummyState, 3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {

        if (state.is(newState.getBlock())) {
            return;
        }

        if (!level.isClientSide) {
            BlockPos dummyPos = pos.above();
            BlockState dummyState = level.getBlockState(dummyPos);
            if (dummyState.is(KanataBlock.GENERIC_DUMMY_BLOCK.get())) {
                level.setBlock(dummyPos, Blocks.AIR.defaultBlockState(), 35);
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        BlockEntity be = level.getBlockEntity(pos);
        boolean open = false;
        Direction facing = state.getValue(FACING);

        if (be instanceof SpawnBoxBlockEntity box) {
            open = box.openCount;
        }
        return rotateShape(facing, open ? LOWER_OPEN : LOWER_CLOSE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpawnBoxBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(
        Level level,
        BlockState state,
        BlockEntityType<T> type
    ) {
        //        return type == KanataBlockEntity.SPAWN_BOX.get() ? (lvl, pos, st, be) -> SpawnBoxBlockEntity.tick(
        //            lvl,
        //            pos,
        //            (SpawnBoxBlockEntity) be
        //        ) : null;
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getSlaveShape(BlockGetter level, BlockPos slavePos) {
        BlockPos below = slavePos.below();
        BlockEntity be = level.getBlockEntity(below);
        boolean open = false;
        Direction facing = level.getBlockState(below).getValue(FACING);

        if (be instanceof SpawnBoxBlockEntity box) {
            open = box.openCount;
        }

        return rotateShape(facing, open ? UPPER_OPEN : UPPER_CLOSE);
    }
}
