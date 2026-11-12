package com.github.yasuniku.kanata.block.officedesk;

import com.github.yasuniku.kanata.block.GenericDummyBlock;
import com.github.yasuniku.kanata.block.IMasterBlock;
import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerBlock;
import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class OfficeDeskBlock extends BaseFurnitureContainerBlock implements IMasterBlock {

    public OfficeDeskBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OfficeDeskBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(
        Level level,
        BlockPos pos,
        BlockState state,
        @Nullable LivingEntity placer,
        ItemStack stack
    ) {
        Direction dir = state.getValue(FACING);

        super.setPlacedBy(level, pos, state, placer, stack);

        BlockPos dummyPos = pos.relative(dir.getCounterClockWise());
        BlockState dummyState = KanataBlock.GENERIC_DUMMY_BLOCK
            .get()
            .defaultBlockState()
            .setValue(GenericDummyBlock.MASTER_DIR, dir.getClockWise());
        level.setBlock(dummyPos, dummyState, 3);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) {
            return;
        }

        if (!level.isClientSide) {
            BlockPos rightPos = pos.relative(state.getValue(FACING).getCounterClockWise());
            level.destroyBlock(rightPos, true);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public VoxelShape getSlaveShape(BlockGetter level, BlockPos slavePos) {
        return Shapes.block();
    }
}
