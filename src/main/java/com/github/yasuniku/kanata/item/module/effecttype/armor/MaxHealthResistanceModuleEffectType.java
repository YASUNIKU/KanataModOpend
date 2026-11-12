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

public class MaxHealthResistanceModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_chestplate);
    private static final int EFFECT_DURATION = Utils.getTickFromSecond(2);

    @Override
    public String getId() {
        return "max_health_resistance";
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

        if (player.getHealth() >= player.getMaxHealth()) {
            var power = moduleEffect.level() - 1;
            // --- 最大体力 → 耐性付与 ---
            if (!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
                player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    EFFECT_DURATION,
                    power,
                    true,
                    false
                ));
            }
        }
    }
}
