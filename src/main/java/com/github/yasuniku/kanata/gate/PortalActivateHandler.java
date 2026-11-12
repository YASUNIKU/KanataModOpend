package com.github.yasuniku.kanata.gate;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.registry.KanataBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KanataMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PortalActivateHandler {
    @SubscribeEvent
    public static void onRightClickBlock(BlockEvent.EntityPlaceEvent event) {
        Level level = (Level) event.getLevel();
        if (level.isClientSide()) {
            return;
        }

        BlockPos placedPos = event.getPos();
        Block placedBlock = level.getBlockState(placedPos).getBlock();

        if (placedBlock == KanataBlock.T1_PORTAL_FRAME_BLOCK.get()) {
            PortalStructureHelper.tryActivatePortal(level, placedPos);
        }
    }
}
