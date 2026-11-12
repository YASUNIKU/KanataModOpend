package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Set;

public class FallExplosionModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.rare, ModuleRarity.epic, ModuleRarity.legendary);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_chestplate);
    private static final int MAX_COOLDOWN = Utils.getTickFromSecond(30);

    private int nowCooldown = MAX_COOLDOWN;

    @Override
    public String getId() {
        return "fall_explosion";
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
    public void onFallDamage(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {
        if (!(wearer instanceof Player player)) {
            return;
        }
        if (nowCooldown > 0) {
            return;
        }

        float power = switch (moduleEffect.level()) {
            //case 1 -> 1;
            case 2 -> 2.0f;
            case 3 -> 3.0f;
            default -> 1.0f;
        };
        // 爆発発生
        Level level = player.level();
        level.explode(
            null, player.getX(), player.getY(), player.getZ(), power, // 爆発規模
            Level.ExplosionInteraction.NONE
        );
        nowCooldown = MAX_COOLDOWN;
    }
}
