package com.github.yasuniku.kanata.item.module.effecttype.sword;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class BurnExplosionAttackModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.uncommon, ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.sword);

    @Override
    public String getId() {
        return "burn_explosion_attack";
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
        // --- 炎上中かどうか ---
        if (target.isOnFire()) {

            var power = switch (moduleEffect.level()) {
                case 2 -> 2.0f;
                case 3 -> 3.0f;
                case 4 -> 4.0f;
                default -> 1.0f;
            };
            // 爆発を起こす
            attacker.level().explode(
                attacker,                         // 爆発の原因
                target.getX(), target.getY(), target.getZ(), power,                             // 爆発威力
                Level.ExplosionInteraction.NONE
            );
        }
        return true;
    }
}
