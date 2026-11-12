package com.github.yasuniku.kanata.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public enum ScrapArmorMaterials implements ArmorMaterial {
    SCRAP(
        "kanata:scrap",
        20,
        new int[]{2, 5, 6, 2},
        15,
        SoundEvents.ARMOR_EQUIP_IRON,
        0.0f,
        0.0f,
        () -> Ingredient.of(Items.IRON_INGOT)
    );

    private final String name;
    private final int durabilityMultiplier;
    private final int[] defense;
    private final int enchantmentValue;
    private final net.minecraft.sounds.SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final java.util.function.Supplier<Ingredient> repairIngredient;

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    ScrapArmorMaterials(
        String name,
        int durabilityMultiplier,
        int[] defense,
        int enchantmentValue,
        net.minecraft.sounds.SoundEvent equipSound,
        float toughness,
        float knockbackResistance,
        java.util.function.Supplier<Ingredient> repairIngredient
    ) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.defense = defense;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_PER_SLOT[type.getSlot().getIndex()] * durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return defense[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public net.minecraft.sounds.SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
