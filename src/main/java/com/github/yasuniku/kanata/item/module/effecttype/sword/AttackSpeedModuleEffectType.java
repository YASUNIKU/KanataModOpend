package com.github.yasuniku.kanata.item.module.effecttype.sword;

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

public class AttackSpeedModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.common, ModuleRarity.uncommon, ModuleRarity.rare);
    private static final Set<ModuleSettableToolType> allowedTools =
        Set.of(ModuleSettableToolType.sword, ModuleSettableToolType.axe);

    @Override
    public String getId() {
        return "attack_speed";
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
        double bonus = switch (effect.level()) {
            case 1 -> 0.1;
            case 2 -> 0.2;
            case 3 -> 0.3;
            default -> 0;
        };

        AttributeModifier modifier = new AttributeModifier(
            UUID.nameUUIDFromBytes((getId() + "_" + effect.level()).getBytes()),
            getId(),
            bonus,
            AttributeModifier.Operation.ADDITION
        );

        return Map.of(Attributes.ATTACK_SPEED, modifier);
    }
}
