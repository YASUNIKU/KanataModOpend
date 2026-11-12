package com.github.yasuniku.kanata.block.moduleslotadder;

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

import javax.annotation.Nullable;
import java.util.Objects;

public class ModuleSlotAdderBlockEntity extends BlockEntity implements MenuProvider {

    public ModuleSlotAdderBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.MODULE_SLOT_ADDER.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Module Slot Adder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new ModuleSlotAdderMenu(
            id,
            inv,
            ContainerLevelAccess.create(Objects.requireNonNull(this.getLevel()), this.getBlockPos())
        );
    }

    // クライアント同期（必要なら NBT を返す）
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    // BER の描画範囲（上下 2 ブロックなので高さ 2）
    @Override
    public AABB getRenderBoundingBox() {
        Direction dir = getBlockState().getValue(ModuleSlotAdderBlock.FACING);

        BlockPos pos = getBlockPos();
        BlockPos right = pos.relative(dir.getCounterClockWise());

        int minX = Math.min(pos.getX(), right.getX());
        int minY = pos.getY();
        int minZ = Math.min(pos.getZ(), right.getZ());

        int maxX = Math.max(pos.getX(), right.getX()) + 1;
        int maxY = pos.getY() + 2; // 上下 2 ブロック分
        int maxZ = Math.max(pos.getZ(), right.getZ()) + 1;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
