package com.github.yasuniku.kanata.gate;

import com.github.yasuniku.kanata.utils.Vector2DInt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class PortalTeleporter implements ITeleporter {
    @SuppressWarnings("FieldCanBeLocal")
    private final double Y = 32;

    @SuppressWarnings("FieldCanBeLocal")
    private final int STAGE_SIZE = 131072;
    private final int id;

    public PortalTeleporter(int id) {
        this.id = id;
    }

    @Override
    public Entity placeEntity(
        Entity entity,
        ServerLevel currentLevel,
        ServerLevel destLevel,
        float yaw,
        Function<Boolean, Entity> repositionEntity
    ) {

        // ★ Forge 1.20.x では必須：エンティティを再配置する
        repositionEntity.apply(false);
        Vector2DInt teleportPos = idToTeleportPosition(id);
        // ★ チャンクを強制ロード
        ChunkPos chunkPos =
            new ChunkPos(new BlockPos(teleportPos.x() * STAGE_SIZE, (int) Y, teleportPos.y() * STAGE_SIZE));

        destLevel
            .getChunkSource()
            .addRegionTicket(net.minecraft.server.level.TicketType.POST_TELEPORT, chunkPos, 1, entity.getId());
        // ★ プレイヤーを移動

        entity.teleportTo(teleportPos.x() * STAGE_SIZE, Y, teleportPos.y() * STAGE_SIZE);
        return entity;
    }

    private static Vector2DInt idToTeleportPosition(int id) {
        return new Vector2DInt(id % 100, id / 100);
    }
}
