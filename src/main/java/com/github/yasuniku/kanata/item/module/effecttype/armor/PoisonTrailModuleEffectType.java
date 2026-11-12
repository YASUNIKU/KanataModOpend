package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public class PoisonTrailModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.common, ModuleRarity.uncommon, ModuleRarity.rare);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_chestplate);
    private static final int MAX_COOLDOWN = Utils.getTickFromSecond(1);

    private int nowCooldown = 0;

    @Override
    public String getId() {
        return "poison_trail";
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

        // プレイヤーが動いていないなら生成しない
        if (player.walkDist - player.walkDistO <= 0.01f) {
            return;
        }
        if (!Utils.isInCombat(player)) {
            return;
        }

        // クールダウン（毎 5 tick に 1 回生成）
        if (nowCooldown > 0) {
            return;
        }
        var level = player.level();
        var power = moduleEffect.level();

        // --- AreaEffectCloud の生成 ---
        AreaEffectCloud cloud = new AreaEffectCloud(level, player.getX(), player.getY(), player.getZ());
        cloud.setOwner(player);
        cloud.setRadius(0.4f + (power * 0.4f));            // 半径
        cloud.setDuration(Utils.getTickFromSecond(power));            // 20tick（1秒）
        cloud.setRadiusPerTick(-0.03f);   // 徐々に縮む
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, Utils.getTickFromSecond(1 + power), 0)); // 毒 3秒

        level.addFreshEntity(cloud);
        nowCooldown = MAX_COOLDOWN;
    }
}
