package com.github.yasuniku.kanata.item.module;

import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;

public record ModuleEffect(ModuleEffectType type, int level) {
    public static final String KEY_MODULE_ITEM_EFFECT_ID = "effect_id";
    public static final String KEY_MODULE_ITEM_EFFECT_LEVEL = "effect_level";

    public static Optional<ModuleEffect> fromNBT(CompoundTag tag) {
        return ModuleEffectTypeRegistry
            .get(tag.getString(KEY_MODULE_ITEM_EFFECT_ID))
            .map((x) -> new ModuleEffect(x, tag.getInt(KEY_MODULE_ITEM_EFFECT_LEVEL)));
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString(KEY_MODULE_ITEM_EFFECT_ID, type.getId());
        tag.putInt(KEY_MODULE_ITEM_EFFECT_LEVEL, level);
        return tag;
    }

    public ModuleRarity getRarity() {
        return type.getRarityByLevel(level);
    }
}
