package com.github.yasuniku.kanata.item.module;

import java.util.Optional;

public enum ModuleRarity {
    common, uncommon, rare, epic, legendary, unique, cursed;

    public Optional<ModuleRarity> nextRarity() {
        return Optional.ofNullable(switch (this) {
            case common -> uncommon;
            case uncommon -> rare;
            case rare -> epic;
            case epic -> legendary;
            default -> null;
        });
    }
}
