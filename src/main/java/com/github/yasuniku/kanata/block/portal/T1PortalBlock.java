package com.github.yasuniku.kanata.block.portal;

import com.github.yasuniku.kanata.dimension.TownData;
import com.github.yasuniku.kanata.gate.PortalTeleporter;
import com.github.yasuniku.kanata.registry.KanataBlock;
import com.github.yasuniku.kanata.registry.KanataDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class T1PortalBlock extends Block implements EntityBlock {
    public T1PortalBlock(Properties props) {
        super(props);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PortalBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);

        if (level.isClientSide()) {
            return;
        }

        // ブロックが別のブロックに置き換わった場合のみ実行
        if (state.getBlock() != newState.getBlock()) {
            destroyAdjacentPortalBlocks(level, pos);
        }
    }

    private void destroyAdjacentPortalBlocks(Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos adjacent = pos.relative(dir);

            if (level.getBlockState(adjacent).is(KanataBlock.T1_PORTAL_BLOCK.get())) {
                level.destroyBlock(adjacent, false); // ドロップなしで破壊
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

        if (level.isClientSide()) {
            return;
        }
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        //クールダウンチェック
        long now = level.getGameTime();
        long last = player.getPersistentData().getLong("kanata_portal_cooldown");

        if (now - last < 40) { // 40tick = 2秒
            return; // まだクールダウン中
        }

        // クールダウン更新
        player.getPersistentData().putLong("kanata_portal_cooldown", now);

        // サーバー取得
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }

        // ディメンション取得
        ResourceKey<Level> targetKey = KanataDimension.KANATA_TOWN_DIMENSION;
        ServerLevel targetLevel = server.getLevel(targetKey);
        if (targetLevel == null) {
            return;
        }

        TownData data = TownData.get(targetLevel);

        // ★ ディメンション転送（1回だけ実行される）
        player.changeDimension(targetLevel, new PortalTeleporter(data.getActiveStageID()));
    }
}
