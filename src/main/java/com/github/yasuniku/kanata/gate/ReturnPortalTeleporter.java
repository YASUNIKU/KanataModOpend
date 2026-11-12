package com.github.yasuniku.kanata.gate;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class ReturnPortalTeleporter implements ITeleporter {

    public ReturnPortalTeleporter() {
    }

    @Override
    public Entity placeEntity(
        Entity entity,
        ServerLevel currentLevel,
        ServerLevel destLevel,
        float yaw,
        Function<Boolean, Entity> repositionEntity
    ) {
        if (!(entity instanceof ServerPlayer player)) {
            return entity;
        }

        // ★ Forge 1.20.x では必須：エンティティを再配置する
        repositionEntity.apply(false);

        BlockPos blockpos = player.getRespawnPosition();
        float f = player.getRespawnAngle();

        if (player.getRespawnDimension() != destLevel.dimension()) {
            blockpos = null;
        }

        Optional<Vec3> optional = Optional.empty();
        if (blockpos != null) {
            optional = Player.findRespawnPositionAndUseSpawnBlock(destLevel, blockpos, f, false, true);
        }

        ServerLevel serverLevel1 =
            optional.isPresent() ? destLevel : Objects.requireNonNull(player.getServer()).overworld();

        if (optional.isPresent()) {
            BlockState blockstate = serverLevel1.getBlockState(blockpos);
            boolean flag1 = blockstate.is(Blocks.RESPAWN_ANCHOR);
            Vec3 vec3 = optional.get();
            if (blockstate.is(BlockTags.BEDS) || flag1) {
                Vec3 vec31 = Vec3.atBottomCenterOf(blockpos).subtract(vec3).normalize();
                f = (float) Mth.wrapDegrees(Mth.atan2(vec31.z, vec31.x) * 57.2957763671875 - 90.0);
            }

            blockpos = BlockPos.containing(vec3);
        } else {
            if (blockpos != null) {
                player.connection.send(new ClientboundGameEventPacket(
                    ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE,
                    0.0F
                ));
            }
            blockpos = destLevel.getSharedSpawnPos();
            f = destLevel.getSharedSpawnAngle();
        }

        // ★ チャンクを強制ロード
        ChunkPos chunkPos = new ChunkPos(blockpos);

        destLevel
            .getChunkSource()
            .addRegionTicket(net.minecraft.server.level.TicketType.POST_TELEPORT, chunkPos, 1, entity.getId());
        // ★ プレイヤーを移動

        player.setYRot(f);
        player.teleportTo(blockpos.getX(), blockpos.getY(), blockpos.getZ());

        return player;
    }
}
