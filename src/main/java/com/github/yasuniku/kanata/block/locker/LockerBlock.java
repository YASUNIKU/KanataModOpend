package com.github.yasuniku.kanata.block.locker;

import com.github.yasuniku.kanata.block.GenericDummyBlock;
import com.github.yasuniku.kanata.block.IMasterBlock;
import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerBlock;
import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import static com.github.yasuniku.kanata.utils.Utils.rotateShape;

public class LockerBlock extends BaseFurnitureContainerBlock implements IMasterBlock {
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public LockerBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LockerBlockEntity(pos, state);
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

        BlockPos dummyPos = pos.relative(Direction.UP);
        BlockState dummyState = KanataBlock.GENERIC_DUMMY_BLOCK
            .get()
            .defaultBlockState()
            .setValue(GenericDummyBlock.MASTER_DIR, Direction.DOWN);
        level.setBlock(dummyPos, dummyState, 3);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockPos dummyPos = pos.relative(Direction.UP);

        level.destroyBlock(pos, !player.isCreative());
        level.destroyBlock(dummyPos, !player.isCreative());

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) {
            return;
        }

        if (!level.isClientSide) {
            level.destroyBlock(pos.relative(Direction.UP), true);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return rotateShape(facing, SHAPE);
    }

    @Override
    public VoxelShape getSlaveShape(BlockGetter level, BlockPos slavePos) {
        return Block.box(1, 0, 1, 15, 16, 15);
    }
}
