package com.github.yasuniku.kanata.item.module;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.yasuniku.kanata.KanataMod.MOD_ID;
import static com.github.yasuniku.kanata.utils.Utils.clamp;

public class ModuleHelper {
    public static final String ROOT_KEY = MOD_ID;
    public static final String KEY_MODULES = "modules";
    public static final String KEY_MAX_INSTALLABLE_MODULE_COUNT = "max_installable_module_count";

    public static List<ModuleInstance> getModules(ItemStack stack) {
        CompoundTag rootData = stack.getTagElement(ROOT_KEY);
        if (rootData == null || !rootData.contains(KEY_MODULES)) {
            return List.of();
        }

        return rootData.getList(KEY_MODULES, ListTag.TAG_COMPOUND).stream().flatMap(tag -> {
            ItemStack moduleStack = ItemStack.of((CompoundTag) tag);
            moduleStack.setCount(1);
            return ModuleInstance.fromItemStack(moduleStack).stream();
        }).toList();
    }

    public static void setModules(ItemStack stack, List<ModuleInstance> modules) {
        CompoundTag rootData = stack.getOrCreateTagElement(ROOT_KEY);
        ListTag moduleData = new ListTag();
        for (ModuleInstance inst : modules) {
            if (inst.getEffect().type().canApplyTo(classifyToolType(stack))) {
                moduleData.add(inst.getItemStack().save(new CompoundTag()));
            }
        }
        rootData.put(KEY_MODULES, moduleData);
    }

    public static boolean canInstallModule(ItemStack stack, ModuleInstance newModule) {
        ModuleSettableToolType toolType = classifyToolType(stack);
        if (toolType == null) {
            return false;
        }
        if (!newModule.getEffect().type().canApplyTo(toolType)) {
            return false;
        }
        return getModules(stack).size() < getMaxInstallableModuleCount(stack);
    }

    public static void installModule(ItemStack stack, ModuleInstance newModule) {
        if (!canInstallModule(stack, newModule)) {
            throw new IllegalArgumentException("moduleのインストールに失敗したよ");
        }

        List<ModuleInstance> modules = getModules(stack);
        modules.add(newModule);
        setModules(stack, modules);
    }

    public static int getMaxInstallableModuleCount(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }

        CompoundTag rootData = stack.getTagElement(ROOT_KEY);
        if (rootData != null && rootData.contains(KEY_MAX_INSTALLABLE_MODULE_COUNT)) {
            return rootData.getInt(KEY_MAX_INSTALLABLE_MODULE_COUNT);
        }
        return 0;
    }

    public static void setMaxInstallableModuleCount(ItemStack stack, int settingCount) {

        CompoundTag rootData = stack.getOrCreateTagElement(ROOT_KEY);
        rootData.putInt(KEY_MAX_INSTALLABLE_MODULE_COUNT, clamp(settingCount, 0, 5));
    }

    public static void addMaxInstallableModuleCount(ItemStack stack, int delta) {
        setMaxInstallableModuleCount(stack, getMaxInstallableModuleCount(stack) + delta);
    }

    public static @Nullable ModuleSettableToolType classifyToolType(ItemStack stack) {
        Item item = stack.getItem();
        // --- 武器・ツール ---
        if (item instanceof SwordItem) {
            return ModuleSettableToolType.sword;
        }
        if (item instanceof AxeItem) {
            return ModuleSettableToolType.axe;
        }
        if (item instanceof PickaxeItem) {
            return ModuleSettableToolType.pickaxe;
        }
        if (item instanceof ShovelItem) {
            return ModuleSettableToolType.shovel;
        }
        if (item instanceof HoeItem) {
            return ModuleSettableToolType.hoe;
        }

        // --- 防具 ---
        if (item instanceof ArmorItem armor) {
            return switch (armor.getType()) {
                case HELMET -> ModuleSettableToolType.armor_head;
                case CHESTPLATE -> ModuleSettableToolType.armor_chestplate;
                case LEGGINGS -> ModuleSettableToolType.armor_leggings;
                case BOOTS -> ModuleSettableToolType.armor_boots;
            };
        }

        // --- 盾 ---
        if (item instanceof ShieldItem) {
            return ModuleSettableToolType.shield;
        }

        return null;
    }

    public static Set<EquipmentSlot> canActiveItemSlotByToolType(Set<ModuleSettableToolType> toolTypes) {
        Set<EquipmentSlot> result = new HashSet<>();

        for (ModuleSettableToolType toolType : new ArrayList<>(toolTypes)) {
            switch (toolType) {
                case armor_head -> result.add(EquipmentSlot.HEAD);
                case armor_chestplate -> result.add(EquipmentSlot.CHEST);
                case armor_leggings -> result.add(EquipmentSlot.LEGS);
                case armor_boots -> result.add(EquipmentSlot.FEET);
                case shield -> result.add(EquipmentSlot.OFFHAND);
                default -> result.add(EquipmentSlot.MAINHAND);
            }
        }

        return result;
    }

    public static List<ItemStack> generateModuleItemsFromTool(ItemStack tool) {
        return getModules(tool).stream().map(x -> x.getItemStack().copy()).toList();
    }

    public static ItemStack generateModuleItemByRarity(ModuleRarity rarity, int seed) {
        return ModuleInstance
            .generateFromEffect(ModuleEffectTypeRegistry.getRandomModuleEffectByRarity(rarity, seed))
            .getItemStack();
    }
}
