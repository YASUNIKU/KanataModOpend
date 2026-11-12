package com.github.yasuniku.kanata.registry;

import com.github.yasuniku.kanata.item.module.ModuleWithRarityRangeFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class KanataLootFunction {

    public static final LootItemFunctionType MODULE_WITH_RARITY_RANGE =
        new LootItemFunctionType(new ModuleWithRarityRangeFunction.Serializer());

    public static void register(IEventBus bus) {
        bus.addListener(KanataLootFunction::onCommonSetup);
    }

    private static void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(
                BuiltInRegistries.LOOT_FUNCTION_TYPE,
                ResourceLocation.fromNamespaceAndPath("kanata", "module_with_rarity_range"),
                MODULE_WITH_RARITY_RANGE
            );
        });
    }
}

