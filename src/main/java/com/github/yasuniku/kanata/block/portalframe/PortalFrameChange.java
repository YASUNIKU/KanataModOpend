package com.github.yasuniku.kanata.block.portalframe;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.gate.PortalStructureHelper;
import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KanataMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PortalFrameChange {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide()) {
            return;
        }

        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        ItemStack held = event.getItemStack();

        // Echo Shard を持っているか？
        if (!held.is(Items.ECHO_SHARD)) {
            return;
        }

        // ターゲットが泣く黒曜石か？
        if (!level.getBlockState(pos).is(Blocks.CRYING_OBSIDIAN)) {
            return;
        }

        // ブロックを置き換える
        level.setBlockAndUpdate(pos, KanataBlock.T1_PORTAL_FRAME_BLOCK.get().defaultBlockState());

        // サバイバルなら残響の欠片を消費
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }

        PortalStructureHelper.tryActivatePortal(level, pos);

        // イベント成功扱い
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }
}
