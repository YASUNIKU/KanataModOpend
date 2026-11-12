package com.github.yasuniku.kanata.block.moduleinstaller;

import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

public class ModuleInstallerBlockEntity extends BlockEntity implements MenuProvider {
    public ModuleInstallerBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.MODULE_INSTALLER.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Module Installer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        var level = getLevel();
        if (level == null) {
            return null;
        }
        return new ModuleInstallerMenu(id, inv, ContainerLevelAccess.create(level, getBlockPos()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Override
    public AABB getRenderBoundingBox() {
        Direction dir = getBlockState().getValue(ModuleInstallerBlock.FACING);

        BlockPos pos = getBlockPos();
        BlockPos right = pos.relative(dir.getCounterClockWise());

        int minX = Math.min(pos.getX(), right.getX());
        int minY = pos.getY();
        int minZ = Math.min(pos.getZ(), right.getZ());

        int maxX = Math.max(pos.getX(), right.getX()) + 1;
        int maxY = pos.getY() + 2;
        int maxZ = Math.max(pos.getZ(), right.getZ()) + 1;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
