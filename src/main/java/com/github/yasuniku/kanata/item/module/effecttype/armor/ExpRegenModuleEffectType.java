package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public class ExpRegenModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(
        ModuleSettableToolType.armor_head,
        ModuleSettableToolType.armor_chestplate,
        ModuleSettableToolType.armor_leggings,
        ModuleSettableToolType.armor_boots
    );
    private static final int MAX_COOLDOWN = Utils.getTickFromSecond(30);

    private int nowCooldown = MAX_COOLDOWN;

    @Override
    public String getId() {
        return "exp_regen";
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
        if (nowCooldown > 0) {
            nowCooldown--;
        }
        // 経験値付与（1ポイント）
        player.giveExperiencePoints(moduleEffect.level());
        nowCooldown = MAX_COOLDOWN;
    }
}
