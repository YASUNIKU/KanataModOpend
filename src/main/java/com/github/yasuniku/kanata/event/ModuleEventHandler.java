package com.github.yasuniku.kanata.event;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleHelper;
import com.github.yasuniku.kanata.item.module.ModuleInstance;
import com.github.yasuniku.kanata.item.module.effecttype.ModuleEffectType;
import com.github.yasuniku.kanata.registry.KanataAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = KanataMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModuleEventHandler {

    // 攻撃時の効果発動
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        if (!(event.getTarget() instanceof LivingEntity target)) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        AttributeInstance attributeInstance = player.getAttribute(KanataAttributes.COMBAT_STATE.get());
        if (attributeInstance != null) {
            attributeInstance.setBaseValue(160);
        }
        // ★ 全スロットを走査
        for (ItemStack stack : getAllEquippedItems(player)) {
            for (ModuleInstance inst : ModuleHelper.getModules(stack)) {
                inst.getEffect().type().onHit(player, target, stack, inst.getEffect());
            }
        }
    }

    // 毎Tick処理
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;

        AttributeInstance attributeInstance = player.getAttribute(KanataAttributes.COMBAT_STATE.get());
        if (attributeInstance == null) {
            return;
        }

        double timer = attributeInstance.getBaseValue();
        if (timer > 0) {
            attributeInstance.setBaseValue(timer - 1);
        }

        for (ItemStack stack : getAllEquippedItems(player)) {
            for (ModuleInstance inst : ModuleHelper.getModules(stack)) {
                inst.getEffect().type().tickEffect(player, stack, inst.getEffect());
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance inst = player.getAttribute(KanataAttributes.COMBAT_STATE.get());
            if (inst != null) {
                inst.setBaseValue(160); // 8秒
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        for (ItemStack stack : getAllEquippedItems(player)) {
            for (ModuleInstance inst : ModuleHelper.getModules(stack)) {
                inst.getEffect().type().onLivingHurt(player, stack, inst.getEffect());
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        for (ItemStack stack : getAllEquippedItems(player)) {
            for (ModuleInstance inst : ModuleHelper.getModules(stack)) {
                inst.getEffect().type().onLivingHurt(player, stack, inst.getEffect());
            }
        }
    }

    @SubscribeEvent
    public static void onMobKilled(LivingDeathEvent event) {
        LivingEntity target = event.getEntity();

        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }

        for (ItemStack stack : getAllEquippedItems(player)) {
            for (ModuleInstance inst : ModuleHelper.getModules(stack)) {
                inst.getEffect().type().onMobKilled(player, target, stack, inst.getEffect());
            }
        }
    }

    @SubscribeEvent
    public static void onFallDamage(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }
        for (ItemStack stack : getAllEquippedItems(player)) {
            for (ModuleInstance inst : ModuleHelper.getModules(stack)) {
                inst.getEffect().type().onFallDamage(player, stack, inst.getEffect());
            }
        }
    }

    // AttributeModifier の付与
    @SubscribeEvent
    public static void onAttribute(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlot slot = event.getSlotType();
        // アイテムに付与されている module を取得
        var modules = ModuleHelper.getModules(stack);

        for (var inst : modules) {
            ModuleEffect effect = inst.getEffect();
            ModuleEffectType type = effect.type();

            // ★ この module がどのスロットで発動するか判定
            if (!type.canApplyToSlot(slot)) {
                continue; // 違うスロットなら無視
            }

            // ★ この module が持つ AttributeModifier を取得
            Map<Attribute, AttributeModifier> modifiers = type.getAttributeModifiers(stack, effect);

            // ★ このスロットに対してのみ Modifier を追加
            for (var entry : modifiers.entrySet()) {
                event.addModifier(entry.getKey(), entry.getValue());
            }
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        // モジュールが付与されていないなら何もしない
        List<ModuleInstance> modules = ModuleHelper.getModules(stack);
        if (modules.isEmpty()) {
            return;
        }

        tooltip.add(Component.literal("Modules:").withStyle(ChatFormatting.GOLD));

        for (ModuleInstance inst : modules) {
            ModuleEffect effect = inst.getEffect();
            Component component = effect.type().getDisplayName(effect);

            tooltip.add(component);
        }
    }

    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, KanataAttributes.COMBAT_STATE.get());
    }

    private static List<ItemStack> getAllEquippedItems(Player player) {
        List<ItemStack> list = new ArrayList<>();

        // メインハンド
        list.add(player.getMainHandItem());

        // オフハンド
        list.add(player.getOffhandItem());

        // 防具（頭・胴・脚・足）
        for (ItemStack armor : player.getArmorSlots()) {
            list.add(armor);
        }

        return list;
    }
}
