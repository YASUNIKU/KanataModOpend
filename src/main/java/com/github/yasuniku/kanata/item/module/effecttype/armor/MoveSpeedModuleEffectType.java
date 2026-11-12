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

public class MoveSpeedModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.common, ModuleRarity.uncommon, ModuleRarity.rare);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_boots);

    @Override
    public String getId() {
        return "move_speed";
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
        double bonus = 0.03 * effect.level(); // 3%〜9%

        AttributeModifier modifier = new AttributeModifier(
            UUID.nameUUIDFromBytes((getId() + "_" + effect.level()).getBytes()),
            getId(),
            bonus,
            AttributeModifier.Operation.MULTIPLY_TOTAL
        );

        return Map.of(Attributes.MOVEMENT_SPEED, modifier);
    }
}
