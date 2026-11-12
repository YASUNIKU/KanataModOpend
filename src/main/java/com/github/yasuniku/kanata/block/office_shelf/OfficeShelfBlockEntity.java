package com.github.yasuniku.kanata.block.office_shelf;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerBlockEntity;
import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class OfficeShelfBlockEntity extends BaseFurnitureContainerBlockEntity {
    public OfficeShelfBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.OFFICE_SHELF.get(), pos, state, 9, "container.kanata.office_shelf_block.json");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player pPlayer) {
        return new OfficeShelfMenu(
            containerId,
            inventory,
            getItems(),
            ContainerLevelAccess.create(Objects.requireNonNull(level), worldPosition)
        );
    }
}
