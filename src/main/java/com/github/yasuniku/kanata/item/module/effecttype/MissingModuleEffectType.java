package com.github.yasuniku.kanata.item.module.effecttype;

import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;

import java.util.List;
import java.util.Set;

import static com.github.yasuniku.kanata.item.module.ModuleSettableToolType.*;

public class MissingModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList = List.of(
        ModuleRarity.common,
        ModuleRarity.uncommon,
        ModuleRarity.rare,
        ModuleRarity.epic,
        ModuleRarity.legendary
    );
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

    @Override
    public String getId() {
        return "__SECRET_INTERNALS_DO_NOT_USE_OR_YOU_WILL_BE_FIRED";
    }

    @Override
    public Set<ModuleSettableToolType> getAllowedTools() {
        return allowedTools;
    }

    @Override
    public List<ModuleRarity> getModuleRarityList() {
        return moduleRarityList;
    }
}
