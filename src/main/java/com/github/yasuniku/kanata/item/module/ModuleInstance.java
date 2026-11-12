package com.github.yasuniku.kanata.item.module;

import com.github.yasuniku.kanata.registry.KanataItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;
import static com.github.yasuniku.kanata.item.module.ModuleHelper.ROOT_KEY;

public class ModuleInstance {
    public static String KEY_EFFECT = "effect";
    private final ItemStack stack;

    public static Optional<ModuleInstance> fromItemStack(ItemStack stack) {
        var inst = new ModuleInstance(stack);
        if (!stack.is(KanataItem.MODULE_ITEM.get())) {
            return Optional.empty();
        }

        try {
            inst.getEffect();
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(inst);
    }

    public static ModuleInstance generateFromEffect(ModuleEffect moduleEffect) {
        var inst = new ModuleInstance(new ItemStack(KanataItem.MODULE_ITEM.get()));
        inst.setEffect(moduleEffect);
        return inst;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    private ModuleInstance(ItemStack itemStack) {
        this.stack = itemStack;
    }

    public ModuleEffect getEffect() {
        return Optional
            .ofNullable(stack.getTagElement(ROOT_KEY))
            .map(tag -> tag.getCompound(KEY_EFFECT))
            .flatMap(ModuleEffect::fromNBT)
            .orElseGet(() -> new ModuleEffect(ModuleEffectTypeRegistry.MISSING_MODULE_EFFECT_TYPE, 1));
    }

    public void setEffect(ModuleEffect effect) {
        CompoundTag tag = stack.getOrCreateTagElement(ROOT_KEY);
        tag.put(KEY_EFFECT, effect.toNBT());
    }

    public ModuleRarity getRarity() {
        return getEffect().getRarity();
    }

    public ResourceLocation getModelResourceLocation() {
        String modelName = switch (getRarity()) {
            //case common -> "module_common";
            case uncommon -> "module_uncommon";
            case rare -> "module_rare";
            case epic -> "module_epic";
            case legendary -> "module_legendary";
            default -> "module_common";
        };
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/modules/" + modelName);
    }
}
