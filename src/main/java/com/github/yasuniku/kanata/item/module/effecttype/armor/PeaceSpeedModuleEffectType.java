package com.github.yasuniku.kanata.item.module.effecttype.armor;

import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleRarity;
import com.github.yasuniku.kanata.item.module.ModuleSettableToolType;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PeaceSpeedModuleEffectType extends ModuleEffectType {
    private static final List<ModuleRarity> moduleRarityList =
        List.of(ModuleRarity.common, ModuleRarity.uncommon, ModuleRarity.rare);
    private static final Set<ModuleSettableToolType> allowedTools = Set.of(ModuleSettableToolType.armor_boots);

    @Override
    public String getId() {
        return "peace_speed";
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
    public void tickEffect(LivingEntity wearer, ItemStack stack, ModuleEffect moduleEffect) {
        if (!(wearer instanceof ServerPlayer player)) {
            return;
        }
        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        UUID SPEED_BONUS_UUID = UUID.nameUUIDFromBytes((getId() + "_" + moduleEffect.level()).getBytes());
        if (!Utils.isInCombat(player)) {

            // --- 非戦闘時：移動速度アップ ---
            if (speed != null && speed.getModifier(SPEED_BONUS_UUID) == null) {
                speed.addPermanentModifier(new AttributeModifier(
                    SPEED_BONUS_UUID, getId(), 0.05 * moduleEffect.level(), // 移動速度 +15%
                    AttributeModifier.Operation.MULTIPLY_TOTAL
                ));
            }
        } else {
            if (speed != null && speed.getModifier(SPEED_BONUS_UUID) == null) {
                speed.removePermanentModifier(SPEED_BONUS_UUID);
            }
        }
    }
}
