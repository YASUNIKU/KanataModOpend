package com.github.yasuniku.kanata.block.moduleinstaller;

import com.github.yasuniku.kanata.utils.TablePart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jspecify.annotations.Nullable;

public class ModuleInstallerBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<TablePart> PART = EnumProperty.create("part", TablePart.class);

    public ModuleInstallerBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(PART, TablePart.LEFT));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == TablePart.LEFT ? new ModuleInstallerBlockEntity(pos, state) : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hit
    ) {

        if (!level.isClientSide) {

            Direction dir = state.getValue(FACING);
            TablePart part = state.getValue(PART);

            // MAIN（LEFT）を逆算
            BlockPos mainPos = GetMainPos(part, pos, dir);

            BlockEntity be = level.getBlockEntity(mainPos);

            if (be instanceof MenuProvider provider) {
                NetworkHooks.openScreen((ServerPlayer) player, provider, mainPos);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    // 設置時の BlockState
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level level = ctx.getLevel();

        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(ctx)) {

            return this
                .defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(PART, TablePart.LEFT);
        }

        return null;
    }

    @Override
    public void setPlacedBy(
        Level level,
        BlockPos pos,
        BlockState state,
        @Nullable LivingEntity placer,
        ItemStack stack
    ) {

        Direction dir = state.getValue(FACING);

        BlockPos right = pos.relative(dir.getCounterClockWise());

        level.setBlock(pos, state.setValue(PART, TablePart.LEFT), 3);
        level.setBlock(right, state.setValue(PART, TablePart.RIGHT), 3);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        TablePart part = state.getValue(PART);
        Direction dir = state.getValue(FACING);

        // MAIN（LEFT）の位置を逆算
        BlockPos mainPos = GetMainPos(part, pos, dir);

        BlockPos right = mainPos.relative(dir.getCounterClockWise());

        level.destroyBlock(mainPos, !player.isCreative());
        level.destroyBlock(right, !player.isCreative());

        super.playerWillDestroy(level, pos, state, player);
    }

    private BlockPos GetMainPos(TablePart part, BlockPos pos, Direction dir) {
        return (part == TablePart.LEFT) ? pos : pos.relative(dir.getClockWise());
    }

    @SuppressWarnings("deprecation")
    @Override
    public @Nullable MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return super.getMenuProvider(pState, pLevel, pPos);
    }
}
