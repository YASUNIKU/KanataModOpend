package com.github.yasuniku.kanata.block.reinforceddoor;

import com.github.yasuniku.kanata.registry.KanataItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;

public class ReinforcedDoorBlock extends DoorBlock {
    public ReinforcedDoorBlock(BlockBehaviour.Properties properties) {
        super(properties, BlockSetType.IRON);
    }

    @Override
    public InteractionResult use(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hit
    ) {

        ItemStack held = player.getItemInHand(hand);

        if (held.is(KanataItem.REINFORCED_DOOR_CARD_KEY.get())) {

            boolean open = state.getValue(OPEN);
            level.setBlock(pos, state.setValue(OPEN, !open), 10);
            held.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));

            BlockPos otherHalf =
                pos.relative(state.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN);
            BlockState otherState = level.getBlockState(otherHalf);
            if (otherState.getBlock() instanceof DoorBlock) {
                level.setBlock(otherHalf, otherState.setValue(OPEN, !open), 10);
            }

            level.playSound(
                null,
                pos,
                open ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
            );

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void neighborChanged(
        BlockState state,
        Level level,
        BlockPos pos,
        Block block,
        BlockPos fromPos,
        boolean moving
    ) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSignalSource(BlockState state) {
        return false; // 念のため RS 出力も無効
    }
}

