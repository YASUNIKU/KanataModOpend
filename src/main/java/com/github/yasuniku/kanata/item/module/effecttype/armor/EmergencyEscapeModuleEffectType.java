package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public class EmergencyEscapeModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_leggings);
    private static final int MAX_COOLDOWN = Utils.getTickFromMinute(10);
    private static final int EFFECT_DURATION = 200;

    private int nowCooldown = 0;

    @Override
    public String getId() {
        return "emergency_escape";
    }

    @Override
    public Set<ModuleSettableToolType> getAllowedTools() {
        return allowedTools;
    }

    @Override
    public List<ModuleRarity> getModuleRarityList() {
        return moduleRarityList;
    }

    @Override
    public void tickEffect(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {
        if (0 < nowCooldown) {
            nowCooldown--;
        }
    }

    @Override
    public void onLivingDamage(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {
        if (nowCooldown != 0) {
            return;
        }
        if (wearer.getHealth() > wearer.getMaxHealth() * 0.3f) {
            return;
        }
        // 黄色い体力
        wearer.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, EFFECT_DURATION, 2));

        // 移動速度アップ
        wearer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_DURATION, 1));

        // 攻撃力ダウン
        wearer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, EFFECT_DURATION, 1));

        nowCooldown = MAX_COOLDOWN;
    }
}
