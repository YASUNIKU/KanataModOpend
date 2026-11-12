package com.github.yasuniku.kanata.item;

import com.github.yasuniku.kanata.block.returnportal.ReturnPortalBlock;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DebugItem extends Item {
    public DebugItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            System.out.println("[KanataMod] オーバーワールドに飛んだよ！");
            ReturnPortalBlock.reloadDimension(player);
        }
        return super.use(level, player, hand);
    }
}
