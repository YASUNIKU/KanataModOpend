package com.github.yasuniku.kanata.item.module.effecttype.sword;

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

public class AssassinateModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.sword);

    @Override
    public String getId() {
        return "assassinate";
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

        if (Utils.isInCombat(player)) {
            // 戦闘中 → 透明化解除
            if (player.hasEffect(MobEffects.INVISIBILITY)) {
                player.removeEffect(MobEffects.INVISIBILITY);
            }
            return;
        }

        //非戦闘時のみ透明化付与
        if (!player.hasEffect(MobEffects.INVISIBILITY)) {
            player.addEffect(new MobEffectInstance(
                MobEffects.INVISIBILITY, Utils.getTickFromSecond(2),   // 2秒（毎tick更新されるので短くてOK）
                0, true, false
            ));
        }
        if (!player.hasEffect(MobEffects.DAMAGE_BOOST)) {
            var power = switch (moduleEffect.level()) {
                case 2 -> 1;
                case 3 -> 2;
                default -> 0;
            };
            player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_BOOST, Utils.getTickFromSecond(5),   // 5秒
                power,    // レベル1（攻撃力 +3）
                true, false
            ));
        }
    }
}
