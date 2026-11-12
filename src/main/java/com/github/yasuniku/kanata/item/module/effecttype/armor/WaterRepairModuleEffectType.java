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

import static com.github.yasuniku.kanata.item.module.ModuleSettableToolType.*;

public class WaterRepairModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(ModuleRarity.uncommon);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(
        pickaxe,
        shovel,
        axe,
        hoe,
        shears,
        fishing_rod,
        flint_and_steel,
        brush,
        sword,
        bow,
        crossbow,
        trident,
        shield,
        armor_head,
        armor_chestplate,
        armor_leggings,
        armor_boots,
        elytra
    );
    private static final int MAX_COOLDOWN = Utils.getTickFromMinute(30);

    private int nowCooldown = MAX_COOLDOWN;

    @Override
    public String getId() {
        return "water_repair";
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
        // 水中判定
        if (!player.isUnderWater()) {
            return;
        }

        if (nowCooldown > 0) {
            nowCooldown--;
            return;
        }

        // 耐久値回復
        int damage = stack.getDamageValue();
        if (damage > 0) {
            stack.setDamageValue(Math.max(0, damage - moduleEffect.level()));
            nowCooldown = MAX_COOLDOWN;
        }
    }
}
