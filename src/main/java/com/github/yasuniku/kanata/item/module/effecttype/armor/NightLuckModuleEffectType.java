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

public class NightLuckModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(
        ModuleRarity.common,
        ModuleRarity.uncommon,
        ModuleRarity.rare,
        ModuleRarity.epic,
        ModuleRarity.legendary
    );
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_leggings);
    private static final int EFFECT_DURATION = Utils.getTickFromSecond(10);

    @Override
    public String getId() {
        return "night_luck";
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

        if (player.level().isNight()) {
            var power = moduleEffect.level() - 1;

            if (!player.hasEffect(MobEffects.LUCK)) {
                player.addEffect(new MobEffectInstance(
                    MobEffects.LUCK, EFFECT_DURATION, power,    // レベル1（幸運+1）
                    true, false
                ));
            }
        } else {
            // 昼 → 幸運解除
            if (player.hasEffect(MobEffects.LUCK)) {
                player.removeEffect(MobEffects.LUCK);
            }
        }
    }
}
