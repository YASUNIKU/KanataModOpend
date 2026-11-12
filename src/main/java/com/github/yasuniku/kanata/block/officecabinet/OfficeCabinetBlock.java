package com.github.yasuniku.kanata.block.officecabinet;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OfficeCabinetBlock extends BaseFurnitureContainerBlock {
    public OfficeCabinetBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OfficeCabinetBlockEntity(pos, state);
    }
}
