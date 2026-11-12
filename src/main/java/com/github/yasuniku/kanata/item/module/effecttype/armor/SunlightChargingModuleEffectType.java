package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public class SunlightChargingModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.common, ModuleRarity.uncommon, ModuleRarity.rare);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_head);
    private static final int MAX_COOLDOWN = Utils.getTickFromSecond(20);

    private int nowCooldown = 0;

    @Override
    public String getId() {
        return "sunlight_charging";
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
            var saturation = switch (moduleEffect.level()) {
                case 2 -> 1.5f;
                case 3 -> 2f;
                default -> 1.0f;
            };
            player.getFoodData().eat(1, saturation);
            nowCooldown = MAX_COOLDOWN;
        }
    }

    private boolean isActive(ServerPlayer player) {
        if (nowCooldown > 0) {
            return false;
        }

        if (Utils.isInCombat(player)) {
            return false;
        }

        if (!player.level().isDay()) {
            return false;
        }
        if (!player.level().canSeeSky(player.blockPosition())) {
            return false;
        }
        if (player.level().isRaining()) {
            return false;
        }

        // --- 食料ゲージチェック ---
        FoodData food = player.getFoodData();
        return food.getFoodLevel() < 20;
    }
}
