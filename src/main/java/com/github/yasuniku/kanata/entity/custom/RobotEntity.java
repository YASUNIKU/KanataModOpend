package com.github.yasuniku.kanata.entity.custom;

import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RobotEntity extends Monster implements IEntityAdditionalSpawnData {
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public static final float BASE_SIZE_HEIGHT = 30 / 16.0f;
    public static final float BASE_SIZE_WIDTH = 1.0f;

    private record VariantProperty(String name, float scale, int maxHealth, float attackDamage) {
    }

    private RobotEntity.VariantProperty variant = new VariantProperty("default", 10.0f, 1, 1);

    // @formatter:off
    private static final Map<String, VariantProperty> VARIANTS = Stream.of(
        new VariantProperty("small",  0.8f, 10, 1),
        new VariantProperty("medium", 1.2f, 20, 2),
        new VariantProperty("large",  1.6f, 40, 4)
    ).collect(Collectors.toUnmodifiableMap(VariantProperty::name, v -> v));
    // @formatter:on

    public RobotEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.variant.name());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        this.setVariant(buffer.readUtf());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Variant", this.variant.name());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant")) {
            this.setVariant(tag.getString("Variant"));
        }
    }

    @Override
    public float getScale() {
        return variant.scale();
    }

    public void setVariant(String variantName) {
        var gotVariant = VARIANTS.get(variantName);
        if (gotVariant != null) {
            this.variant = gotVariant;
        }

        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(this.variant.maxHealth());

        // ★ 当たり判定を更新
        this.refreshDimensions();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(
        ServerLevelAccessor level,
        DifficultyInstance difficulty,
        MobSpawnType spawnType,
        @Nullable SpawnGroupData spawnData,
        @Nullable CompoundTag dataTag
    ) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);

        if (Objects.equals(variant.name(), "default")) {
            this.setVariant(Objects.requireNonNull(Utils.getRandomKey(VARIANTS)));
        }
        this.setHealth(this.getMaxHealth());

        return spawnData;
    }

    @Override
    public void tick() {
        super.tick();

        boolean moving = this.getDeltaMovement().horizontalDistanceSqr() > 0.0001;

        if (moving) {
            walkAnimationState.startIfStopped(this.tickCount);
        } else {
            walkAnimationState.stop();
        }

        if (this.swinging && this.swingTime == 1) {
            this.attackAnimationState.start(this.tickCount);
        }
    }

    @Override
    protected void registerGoals() {
        // 0: 水に浮く（必須）
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // 1: プレイヤーを攻撃対象にする
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(
            1,
            new NearestAttackableTargetGoal<>(
                this,
                Monster.class,
                10,
                true,
                false,
                (mob) -> mob.getMobType() == MobType.UNDEAD
            )
        );

        // 2: 近接攻撃
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));

        // 3: プレイヤーを追跡
        this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 1.0D, 32.0F));

        // 4: 徘徊
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5D));

        // 5: プレイヤーを見る
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));

        // 6: ランダムに見回す
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttribute() {
        return Monster
            .createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
            .add(Attributes.ATTACK_DAMAGE, 3.0f)
            .add(Attributes.ATTACK_SPEED, 2.0D);
    }
}
