package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ArmorToughnessModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(
        ModuleRarity.common,
        ModuleRarity.uncommon,
        ModuleRarity.rare,
        ModuleRarity.epic,
        ModuleRarity.legendary
    );
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(
        ModuleSettableToolType.armor_head,
        ModuleSettableToolType.armor_chestplate,
        ModuleSettableToolType.armor_leggings,
        ModuleSettableToolType.armor_boots
    );

    @Override
    public String getId() {
        return "armor_toughness";
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
    public Map<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, ModuleEffect effect) {
        double bonus = effect.level();

        AttributeModifier modifier =
            new AttributeModifier(
                UUID.nameUUIDFromBytes((getId() + "_" + effect.level()).getBytes()),
                getId(),
                bonus,
                AttributeModifier.Operation.ADDITION
            );

        return Map.of(Attributes.ARMOR_TOUGHNESS, modifier);
    }
}
