package com.github.yasuniku.kanata.block.spawnbox;

import com.github.yasuniku.kanata.entity.custom.RobotEntity;
import com.github.yasuniku.kanata.registry.KanataBlockEntity;
import com.github.yasuniku.kanata.registry.KanataEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SpawnBoxBlockEntity extends BlockEntity {

    public boolean openCount = false;
    public float openness = 0f;
    public float oOpenness = 0f;
    private boolean spawned = false;

    public SpawnBoxBlockEntity(BlockPos pos, BlockState state) {
        super(KanataBlockEntity.SPAWN_BOX.get(), pos, state);
    }

    // ============================================================
    // Tick
    // ============================================================
    public static void tick(Level level, BlockPos pos, SpawnBoxBlockEntity be) {
        be.oOpenness = be.openness;

        Direction dir = be.getBlockState().getValue(SpawnBoxBlock.FACING);

        // プレイヤー検知
        Player player = level.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, 3.0, false);

        // モブスポーン
        if (player != null && !be.spawned && !level.isClientSide) {
            Mob mob = KanataEntity.ROBOT.get().create(level);
            if (mob != null) {
                if (mob instanceof RobotEntity robot) {
                    robot.setVariant("small");
                }

                float yaw = dir.toYRot();
                mob.moveTo(pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, yaw, 0);

                mob.setYRot(yaw);
                mob.setYHeadRot(yaw);
                mob.yRotO = yaw;
                mob.yHeadRotO = yaw;

                //noinspection OverrideOnly,deprecation
                mob.finalizeSpawn(
                    (ServerLevel) level,
                    level.getCurrentDifficultyAt(mob.blockPosition()),
                    MobSpawnType.SPAWNER,
                    null,
                    null
                );

                level.addFreshEntity(mob);
            }
            be.spawned = true;
        }

        // 周囲のエンティティ検知
        AABB area =
            new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);

        boolean entityNearby = !level.getEntities(null, area).isEmpty();
        be.openCount = entityNearby;

        // 内部のエンティティ押し出し
        if (!level.isClientSide && entityNearby) {
            AABB inside = new AABB(
                pos.getX() + 0.2,
                pos.getY() + 0.2,
                pos.getZ() + 0.2,
                pos.getX() + 0.8,
                pos.getY() + 1.8,
                pos.getZ() + 0.8
            );

            List<Entity> insideEntities = level.getEntities(null, inside);

            double dx = dir.getStepX() * 0.1;
            double dz = dir.getStepZ() * 0.1;

            for (Entity e : insideEntities) {
                e.setDeltaMovement(e.getDeltaMovement().add(dx, 0, dz));
            }
        }

        // 開閉アニメーション
        if (be.openCount) {
            be.openness += 0.1f;
        } else {
            be.openness -= 0.1f;
        }
        be.openness = Mth.clamp(be.openness, 0f, 1f);
    }

    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        BlockPos top = pos.above(2).east().south();

        return new AABB(pos, top);
    }

    // ============================================================
    // NBT
    // ============================================================
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.spawned = tag.getBoolean("spawned");
        this.openCount = tag.getBoolean("openCount");
        this.openness = tag.getFloat("openness");
        this.oOpenness = tag.getFloat("oOpenness");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("spawned", this.spawned);
        tag.putBoolean("openCount", this.openCount);
        tag.putFloat("openness", this.openness);
        tag.putFloat("oOpenness", this.oOpenness);
    }
}

