package com.github.yasuniku.kanata.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WireBlock extends RotatedPillarBlock {

    public WireBlock() {
        super(Properties.of().strength(0.5F).noOcclusion().sound(SoundType.CHAIN));
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    // 当たり判定
    private static final VoxelShape SHAPE_Y = Block.box(6.5, 0, 6.5, 9.5, 16, 9.5);

    private static final VoxelShape SHAPE_X = Block.box(0, 6.5, 6.5, 16, 9.5, 9.5);

    private static final VoxelShape SHAPE_Z = Block.box(6.5, 6.5, 0, 9.5, 9.5, 16);

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> SHAPE_X;
            case Z -> SHAPE_Z;
            default -> SHAPE_Y;
        };
    }
}
