package com.github.yasuniku.kanata.item.module;

import com.github.yasuniku.kanata.registry.KanataLootFunction;
import com.github.yasuniku.kanata.utils.Utils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModuleWithRarityRangeFunction extends LootItemConditionalFunction {

    private final Map<ModuleRarity, Double> rarityWeights;

    protected ModuleWithRarityRangeFunction(LootItemCondition[] conditions, Map<ModuleRarity, Double> rarityWeights) {
        super(conditions);
        this.rarityWeights = rarityWeights;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {

        RandomSource random = context.getRandom();
        Optional<ModuleRarity> generatedRarity = Optional.empty();
        var randD = random.nextDouble();
        for (var entry : getRatioForRarity().entrySet()) {
            double ratioRarity = entry.getValue();
            if (randD < ratioRarity) {
                generatedRarity = Optional.of(entry.getKey());
                break;
            }
            randD -= ratioRarity;
        }

        var moduleEffect = ModuleEffectTypeRegistry.getRandomModuleEffectByRarity(
            generatedRarity.orElseGet(() -> Utils
                .getMapFirstEntry(getRatioForRarity())
                .map(Map.Entry::getKey)
                .orElse(ModuleRarity.common)), random.nextInt()
        );

        return ModuleInstance.generateFromEffect(moduleEffect).getItemStack();
    }

    @Override
    public LootItemFunctionType getType() {
        return KanataLootFunction.MODULE_WITH_RARITY_RANGE;
    }

    public Map<ModuleRarity, Double> getRatioForRarity() {
        double total = rarityWeights.values().stream().mapToDouble(Double::doubleValue).sum();
        Map<ModuleRarity, Double> result = new HashMap<>();
        for (var entry : rarityWeights.entrySet()) {
            var ratio = entry.getValue() / total;
            if (ratio > 0) {
                result.put(entry.getKey(), ratio);
            }
        }
        return result;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<ModuleWithRarityRangeFunction> {

        @Override
        public ModuleWithRarityRangeFunction deserialize(
            JsonObject json,
            JsonDeserializationContext ctx,
            LootItemCondition[] conditions
        ) {
            Map<ModuleRarity, Double> rarityWeights = new HashMap<>();
            for (var rarity : ModuleRarity.values()) {
                if (json.has(rarity.name())) {
                    rarityWeights.put(rarity, GsonHelper.getAsDouble(json, rarity.name()));
                }
            }

            return new ModuleWithRarityRangeFunction(conditions, rarityWeights);
        }

        @Override
        public void serialize(JsonObject json, ModuleWithRarityRangeFunction function, JsonSerializationContext ctx) {

            for (var rarity : ModuleRarity.values()) {
                var weight = function.rarityWeights.get(rarity);
                if (weight != null && weight != 0) {
                    json.addProperty(rarity.name(), weight);
                }
            }
        }
    }
}
