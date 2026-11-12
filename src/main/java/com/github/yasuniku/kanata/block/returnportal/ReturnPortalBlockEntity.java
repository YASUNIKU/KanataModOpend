package com.github.yasuniku.kanata.block.returnportal;

import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReturnPortalBlockEntity extends BlockEntity {
    public ReturnPortalBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.RETURN_PORTAL.get(), pos, state);
    }
}
