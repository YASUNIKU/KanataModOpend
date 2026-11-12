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

public class AttackDamageModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(
        ModuleRarity.common,
        ModuleRarity.uncommon,
        ModuleRarity.rare,
        ModuleRarity.epic,
        ModuleRarity.legendary
    );
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.sword);

    @Override
    public String getId() {
        return "attack_damage";
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
    public Map<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, ModuleEffect moduleEffect) {
        double bonus = switch (moduleEffect.level()) {
            case 1 -> 1.0;
            case 2 -> 1.5;
            case 3 -> 2.0;
            case 4 -> 2.5;
            case 5 -> 3.0;
            default -> 9999;
        };

        AttributeModifier modifier =
            new AttributeModifier(
                UUID.nameUUIDFromBytes((getId() + "_" + moduleEffect.level()).getBytes()),
                getId(),
                bonus,
                AttributeModifier.Operation.ADDITION
            );

        return Map.of(Attributes.ATTACK_DAMAGE, modifier);
    }
}
