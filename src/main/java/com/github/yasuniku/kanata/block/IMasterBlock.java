package com.github.yasuniku.kanata.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IMasterBlock {
    VoxelShape getSlaveShape(BlockGetter level, BlockPos slavePos);
}
