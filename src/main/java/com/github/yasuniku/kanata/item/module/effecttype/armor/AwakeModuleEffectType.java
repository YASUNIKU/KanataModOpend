package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public class AwakeModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_chestplate);
    private static final int MAX_COOLDOWN = Utils.getTickFromMinute(1);
    private static final int EFFECT_DURATION = 2;

    private int nowCooldown = MAX_COOLDOWN;

    @Override
    public String getId() {
        return "awake";
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
        if (!(wearer instanceof ServerPlayer player)) {
            return;
        }

        if (!(Utils.isInCombat(player))) {
            nowCooldown = MAX_COOLDOWN;
            return;
        }
        if (nowCooldown > 0) {
            nowCooldown--;
            return;
        }

        // 発光（Glow）
        player.addEffect(new MobEffectInstance(
            MobEffects.GLOWING, EFFECT_DURATION,   // 10秒
            0, true, false
        ));

        // 攻撃力上昇（Strength）
        player.addEffect(new MobEffectInstance(
            MobEffects.DAMAGE_BOOST, EFFECT_DURATION,   // 10秒
            1,     // レベル2
            true, false
        ));

        // リジェネ（Regeneration）
        player.addEffect(new MobEffectInstance(
            MobEffects.REGENERATION, EFFECT_DURATION,   // 10秒
            1, true, false
        ));
    }
}
