package com.github.yasuniku.kanata.item.module.effecttype.sword;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public class GlowingAttackModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.sword);

    @Override
    public String getId() {
        return "glowing_attack";
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
    public boolean onHit(LivingEntity attacker, LivingEntity target, ItemStack weapon, ModuleEffect moduleEffect) {
        int level = moduleEffect.level();

        int glowTime = switch (level) {
            //case 1 -> 40f;
            case 2 -> 60;
            case 3 -> 100;
            default -> 40;
        };
        target.addEffect(new MobEffectInstance(MobEffects.GLOWING, glowTime));
        return true;
    }
}
