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

public class InsanityPotionModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.uncommon, ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_head);
    private static final int MAX_COOLDOWN = Utils.getTickFromSecond(10);
    private static final int EFFECT_DURATION = Utils.getTickFromSecond(10);

    private int nowCooldown = 0;

    @Override
    public String getId() {
        return "insanity_potion";
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
        if (!(wearer instanceof ServerPlayer player)) {
            return;
        }

        if (isActive(player)) {
            var power = switch (moduleEffect.level()) {
                case 2 -> 1;
                case 3 -> 2;
                case 4 -> 3;
                default -> 0;
            };

            wearer.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, EFFECT_DURATION, power));
            wearer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_DURATION, power));

            nowCooldown = MAX_COOLDOWN;
        }
    }

    private boolean isActive(ServerPlayer player) {
        if (nowCooldown > 0) {
            return false;
        }

        if (!Utils.isInCombat(player)) {
            return false;
        }
        return player.hasEffect(MobEffects.POISON);
    }
}
