package com.github.yasuniku.kanata.block.officedesk;

import com.github.yasuniku.kanata.block.basefurniturecontainer.BaseFurnitureContainerBlockEntity;
import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class OfficeDeskBlockEntity extends BaseFurnitureContainerBlockEntity {
    public OfficeDeskBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.OFFICE_DESK.get(), pos, state, 4, "container.kanata.office_desk.json");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new OfficeDeskMenu(
            pContainerId,
            pPlayerInventory,
            getItems(),
            ContainerLevelAccess.create(Objects.requireNonNull(level), getBlockPos())
        );
    }
}
