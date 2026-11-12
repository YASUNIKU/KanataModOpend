package com.github.yasuniku.kanata.block.office_shelf;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OfficeShelfBlock extends BaseFurnitureContainerBlock {
    public OfficeShelfBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OfficeShelfBlockEntity(pos, state);
    }
}
