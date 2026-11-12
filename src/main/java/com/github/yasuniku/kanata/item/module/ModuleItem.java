package com.github.yasuniku.kanata.item.module;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleItem extends Item {
    public ModuleItem(Properties properties) {
        super(properties);
    }

    // ============================================================
    // ツールチップ表示
    // ============================================================
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        var inst = ModuleInstance.fromItemStack(stack);

        // モジュールかどうか判定
        if (inst.isEmpty()) {
            tooltip.add(Component.literal("Invalid Module").withStyle(ChatFormatting.RED));
            return;
        }

        // ModuleInstance を作成
        ModuleEffect effect = inst.get().getEffect();

        // 表示名
        Component name = effect.type().getDisplayName(effect);
        tooltip.add(name);

        // レアリティ
        ModuleRarity rarity = inst.get().getRarity();
        tooltip.add(Component.literal("Rarity: " + rarity.name()).withStyle(ChatFormatting.GOLD));

        // 対応ツール
        String allowed = effect.type().getAllowedTools().stream().map(Enum::name).collect(Collectors.joining(", "));
        tooltip.add(Component.literal("Allowed: " + allowed).withStyle(ChatFormatting.GRAY));
    }
}
