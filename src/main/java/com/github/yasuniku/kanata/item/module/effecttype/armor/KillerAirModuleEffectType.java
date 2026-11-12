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

public class KillerAirModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.common, ModuleRarity.uncommon, ModuleRarity.rare);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_head);

    @Override
    public String getId() {
        return "killer_air";
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
    public void onMobKilled(ServerPlayer player, LivingEntity target, ItemStack stack, ModuleEffect moduleEffect) {
        int level = moduleEffect.level();

        int airVolume = switch (level) {
            //case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            default -> 1;
        };

        // 空気ゲージを回復
        int air = player.getAirSupply();
        int newAir = Math.min(air + Utils.getTickFromSecond(airVolume), player.getMaxAirSupply()); // +40（約2秒）

        player.setAirSupply(newAir);
    }
}
