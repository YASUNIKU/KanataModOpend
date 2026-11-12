package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public class FireResistModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(
        ModuleRarity.common,
        ModuleRarity.uncommon,
        ModuleRarity.rare,
        ModuleRarity.epic,
        ModuleRarity.legendary
    );
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(
        ModuleSettableToolType.armor_head,
        ModuleSettableToolType.armor_chestplate,
        ModuleSettableToolType.armor_leggings,
        ModuleSettableToolType.armor_boots
    );

    @Override
    public String getId() {
        return "armor_defense";
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

        if (!player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            player.addEffect(new MobEffectInstance(
                MobEffects.FIRE_RESISTANCE, 40,   // 2秒（onArmorTickで更新）
                moduleEffect.level() - 1,    // レベル1（火炎耐性）
                true, false
            ));
        }
    }
}
