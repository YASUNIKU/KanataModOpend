package com.github.yasuniku.kanata.item.module;

import com.github.yasuniku.kanata.item.module.effecttype.MissingModuleEffectType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.item.module.effecttype.armor.*;
import com.github.yasuniku.kanata.item.module.effecttype.sword.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class ModuleEffectTypeRegistry {
    private static final Map<String, ModuleEffectType> EFFECT_TYPE_MAP = new ConcurrentHashMap<>();
    private static final Map<ModuleRarity, List<ModuleEffect>> MODULE_RARITY_LIST_MAP = Map.of(
        ModuleRarity.common,
        new ArrayList<>(),
        ModuleRarity.uncommon,
        new ArrayList<>(),
        ModuleRarity.rare,
        new ArrayList<>(),
        ModuleRarity.epic,
        new ArrayList<>(),
        ModuleRarity.legendary,
        new ArrayList<>()
    );

    public static <T extends ModuleEffectType> T register(T moduleEffectType) {
        EFFECT_TYPE_MAP.put(moduleEffectType.getId(), moduleEffectType);
        if (moduleEffectType instanceof MissingModuleEffectType) {
            return moduleEffectType;
        }
        for (var moduleRarity : moduleEffectType.getModuleRarityList()) {
            Objects
                .requireNonNull(MODULE_RARITY_LIST_MAP.get(moduleRarity))
                .add(new ModuleEffect(moduleEffectType, moduleEffectType.getLevelByRarity(moduleRarity)));
        }
        return moduleEffectType;
    }

    public static List<String> getEffectIdList() {
        return new ArrayList<>(EFFECT_TYPE_MAP.keySet());
    }

    public static Optional<ModuleEffectType> get(String effectId) {
        return Optional.ofNullable(EFFECT_TYPE_MAP.get(effectId));
    }

    public static ModuleEffect getRandomModuleEffectByRarity(ModuleRarity moduleRarity, int seed) {
        var list = Objects.requireNonNull(MODULE_RARITY_LIST_MAP.get(moduleRarity));
        return list.get(new Random(seed).nextInt(list.size()));
    }

    //全体
    public static final WaterRepairModuleEffectType WATER_REPAIR_MODULE_EFFECT_TYPE =
        register(new WaterRepairModuleEffectType());

    //剣
    public static final AttackDamageModuleEffectType ATTACK_DAMAGE_MODULE_EFFECT_TYPE =
        register(new AttackDamageModuleEffectType());
    public static final LifeStealModuleEffectType LIFE_STEAL_MODULE_EFFECT_TYPE =
        register(new LifeStealModuleEffectType());
    public static final AttackSpeedModuleEffectType ATTACK_SPEED_MODULE_EFFECT_TYPE =
        register(new AttackSpeedModuleEffectType());
    public static final GlowingAttackModuleEffectType GLOWING_ATTACK_MODULE_EFFECT_TYPE =
        register(new GlowingAttackModuleEffectType());
    public static final BurnExplosionAttackModuleEffectType BURN_EXPLOSION_ATTACK_MODULE_EFFECT_TYPE =
        register(new BurnExplosionAttackModuleEffectType());
    public static final AssassinateModuleEffectType ASSASSINATE_MODULE_EFFECT_TYPE =
        register(new AssassinateModuleEffectType());
    //    public static final FreezingAttackModuleEffectType FREEZING_ATTACK_MODULE_EFFECT_TYPE =
    //        register(new FreezingAttackModuleEffectType());

    //防具全体
    public static final ArmorDefenseModuleEffectType ARMOR_DEFENSE_MODULE_EFFECT_TYPE =
        register(new ArmorDefenseModuleEffectType());
    public static final ArmorToughnessModuleEffectType ARMOR_TOUGHNESS_MODULE_EFFECT_TYPE =
        register(new ArmorToughnessModuleEffectType());
    public static final ExpRegenModuleEffectType EXP_REGEN_MODULE_EFFECT_TYPE =
        register(new ExpRegenModuleEffectType());
    public static final FireResistModuleEffectType FIRE_RESIST_MODULE_EFFECT_TYPE =
        register(new FireResistModuleEffectType());
    //頭
    public static final SunlightChargingModuleEffectType SUNLIGHT_CHARGING_MODULE_EFFECT_TYPE =
        register(new SunlightChargingModuleEffectType());
    public static final InsanityPotionModuleEffectType INSANITY_POTION_MODULE_EFFECT_TYPE =
        register(new InsanityPotionModuleEffectType());
    public static final KillerAirModuleEffectType KILLER_AIR_MODULE_EFFECT_TYPE =
        register(new KillerAirModuleEffectType());
    public static final HungerVisionModuleEffectType HUNGER_VISION_MODULE_EFFECT_TYPE =
        register(new HungerVisionModuleEffectType());
    //胴
    public static final MaxHealthModuleEffectType MAX_HEALTH_MODULE_EFFECT_TYPE =
        register(new MaxHealthModuleEffectType());
    public static final PoisonTrailModuleEffectType POISON_TRAIL_MODULE_EFFECT_TYPE =
        register(new PoisonTrailModuleEffectType());
    public static final MaxHealthResistanceModuleEffectType MAX_HEALTH_RESISTANCE_MODULE_EFFECT_TYPE =
        register(new MaxHealthResistanceModuleEffectType());
    public static final AwakeModuleEffectType AWAKE_MODULE_EFFECT_TYPE = register(new AwakeModuleEffectType());
    //脚
    public static final EmergencyEscapeModuleEffectType EMERGENCY_ESCAPE_MODULE_EFFECT_TYPE =
        register(new EmergencyEscapeModuleEffectType());
    public static final KillerJumpModuleEffectType KILLER_JUMP_MODULE_EFFECT_TYPE =
        register(new KillerJumpModuleEffectType());
    public static final NightLuckModuleEffectType NIGHT_LUCK_MODULE_EFFECT_TYPE =
        register(new NightLuckModuleEffectType());
    //靴
    public static final MoveSpeedModuleEffectType MOVE_SPEED_MODULE_EFFECT_TYPE =
        register(new MoveSpeedModuleEffectType());
    public static final PeaceSpeedModuleEffectType PEACE_SPEED_MODULE_EFFECT_TYPE =
        register(new PeaceSpeedModuleEffectType());
    public static final FallExplosionModuleEffectType FALL_EXPLOSION_MODULE_EFFECT_TYPE =
        register(new FallExplosionModuleEffectType());

    public static final MissingModuleEffectType MISSING_MODULE_EFFECT_TYPE = register(new MissingModuleEffectType());
}
