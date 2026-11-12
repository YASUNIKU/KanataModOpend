package com.github.yasuniku.kanata.block.cardboardbox;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.entity.custom.RobotEntity;
import com.github.yasuniku.kanata.registry.KanataEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.github.yasuniku.kanata.utils.Utils.rotateShape;

public class CardboardBoxBlock extends HorizontalDirectionalBlock {
    private static final ResourceLocation LOOT_TABLE =
        ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "gameplay/cardboard_box.json");
    public static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 8.0D, 16.0D);

    public CardboardBoxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return rotateShape(facing, SHAPE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(
        BlockState state,
        Level level,
        BlockPos pos,
        net.minecraft.world.entity.player.Player player,
        InteractionHand hand,
        BlockHitResult hitResult
    ) {

        if (!level.isClientSide) {
            level.destroyBlock(pos, true, player);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void spawnAfterBreak(
        BlockState state,
        ServerLevel level,
        BlockPos pos,
        ItemStack tool,
        boolean dropExperience
    ) {

        super.spawnAfterBreak(state, level, pos, tool, dropExperience);

        RandomSource random = level.getRandom();

        // 20%でRobot
        if (random.nextFloat() < 0.20F) {

            Mob mob = KanataEntity.ROBOT.get().create(level);
            if (mob != null) {
                if (mob instanceof RobotEntity robot) {
                    robot.setVariant("small");
                }

                mob.moveTo(pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, 0, 0);
                mob.finalizeSpawn(
                    level,
                    level.getCurrentDifficultyAt(mob.blockPosition()),
                    MobSpawnType.SPAWNER,
                    null,
                    null
                );

                level.addFreshEntity(mob);
            }
        } else {

            LootTable lootTable = level.getServer().getLootData().getLootTable(LOOT_TABLE);

            LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .create(LootContextParamSets.CHEST);
            lootTable
                .getRandomItems(params)
                .forEach(stack -> Containers.dropItemStack(
                    level,
                    pos.getX() + 0.5D,
                    pos.getY() + 0.5D,
                    pos.getZ() + 0.5D,
                    stack
                ));
        }
    }
}
