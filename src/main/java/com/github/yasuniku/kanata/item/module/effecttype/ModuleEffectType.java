package com.github.yasuniku.kanata.item.module.effecttype;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleHelper;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class ModuleEffectType {

    public abstract String getId();

    public abstract Set<ModuleSettableToolType> getAllowedTools();

    public abstract List<ModuleRarity> getModuleRarityList();

    public ModuleRarity getRarity(ModuleEffect moduleEffect) {
        return getRarityByLevel(moduleEffect.level());
    }

    public ModuleRarity getRarityByLevel(int level) {
        return getModuleRarityList().get(level - 1);
    }

    public int getLevelByRarity(ModuleRarity rarity) {
        return getModuleRarityList().indexOf(rarity) + 1;
    }

    public boolean canApplyTo(ModuleSettableToolType toolType) {
        return getAllowedTools().contains(toolType);
    }

    public boolean canApplyToSlot(EquipmentSlot slot) {
        return ModuleHelper.canActiveItemSlotByToolType(getAllowedTools()).contains(slot);
    }

    public boolean onHit(LivingEntity attacker, LivingEntity target, ItemStack weapon, ModuleEffect moduleEffect) {
        return true;
    }

    public void tickEffect(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {
    }

    public void onLivingHurt(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {

    }

    public void onLivingDamage(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {

    }

    public void onMobKilled(ServerPlayer player, LivingEntity target, ItemStack stack, ModuleEffect moduleEffect) {

    }

    public void onFallDamage(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {

    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, ModuleEffect moduleEffect) {
        return Collections.emptyMap();
    }

    // ============================================================
    // 表示名（ツールチップ用）
    // ============================================================

    public Component getDisplayName(ModuleEffect moduleEffect) {
        return switch (getRarity(moduleEffect)) {
            //case common ->  Component.literal(getId() + " Lv." +moduleEffect.level()).withStyle(ChatFormatting.AQUA);;
            case uncommon ->
                Component.literal(getId() + " Lv." + moduleEffect.level()).withStyle(ChatFormatting.DARK_RED);
            case rare -> Component.literal(getId() + " Lv." + moduleEffect.level()).withStyle(ChatFormatting.AQUA);
            case epic ->
                Component.literal(getId() + " Lv." + moduleEffect.level()).withStyle(ChatFormatting.LIGHT_PURPLE);
            case legendary -> Component.literal(getId() + " Lv." + moduleEffect.level()).withStyle(ChatFormatting.GOLD);
            default -> Component.literal(getId() + " Lv." + moduleEffect.level()).withStyle(ChatFormatting.GREEN);
        };
    }
}
