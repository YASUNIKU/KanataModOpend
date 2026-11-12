package com.github.yasuniku.kanata.block.portal;

import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PortalBlockEntity extends BlockEntity {
    public PortalBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.T1_PORTAL.get(), pos, state);
    }
}
