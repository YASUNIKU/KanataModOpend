package com.github.yasuniku.kanata.block.returnportal;

import com.github.yasuniku.kanata.dimension.TownData;
import com.github.yasuniku.kanata.gate.ReturnPortalTeleporter;
import com.github.yasuniku.kanata.registry.KanataDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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

import javax.annotation.Nullable;

public class ReturnPortalBlock extends Block implements EntityBlock {
    public ReturnPortalBlock(Properties props) {
        super(props);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            player.sendSystemMessage(Component.literal("ビルボードに触れました！"));
            reloadDimension(player);
        }
        super.entityInside(state, level, pos, entity);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ReturnPortalBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        // JSONモデルではなく BlockEntityRenderer で描画する
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public static void reloadDimension(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        MinecraftServer server = serverPlayer.getServer();
        if (server == null) {
            return;
        }
        ServerLevel respawnLevel = server.getLevel(serverPlayer.getRespawnDimension());
        if (respawnLevel == null) {
            return;
        }

        ServerLevel kanataLevel = server.getLevel(KanataDimension.KANATA_TOWN_DIMENSION);
        if (kanataLevel == null) {
            return;
        }
        TownData data = TownData.get(kanataLevel);

        player.changeDimension(respawnLevel, new ReturnPortalTeleporter());

        // TODO:あとで直す←何を直すのか調べる
        data.incrementActiveStageID();
    }
}
