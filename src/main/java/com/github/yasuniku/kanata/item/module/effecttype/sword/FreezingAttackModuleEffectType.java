package com.github.yasuniku.kanata.item.module.effecttype.sword;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class FreezingAttackModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.sword);

    @Override
    public String getId() {
        return "freezing_attack";
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
        // 現在の凍結ゲージ
        int current = target.getTicksFrozen();

        // 凍結ゲージを進める（例：10 tick 分）
        int add = 1000;

        // 最大値を超えないようにする
        int max = target.getTicksRequiredToFreeze();
        int next = Math.min(current + add, max);

        target.setTicksFrozen(next);
        return true;
    }
}
