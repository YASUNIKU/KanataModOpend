package com.github.yasuniku.kanata.event;

import com.github.yasuniku.kanata.dimension.generator.town.TownChunkGenerator;
import com.github.yasuniku.kanata.item.module.ModuleEffect;
import com.github.yasuniku.kanata.item.module.ModuleEffectTypeRegistry;
import com.github.yasuniku.kanata.item.module.ModuleHelper;
import com.github.yasuniku.kanata.item.module.ModuleInstance;
import com.github.yasuniku.kanata.registry.KanataItem;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KanataCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event
            .getDispatcher()
            .register(Commands
                .literal("giveModule")
                .then(Commands.argument("effectId", StringArgumentType.string()).suggests((ctx, builder) -> {
                    for (String id : ModuleEffectTypeRegistry.getEffectIdList()) {
                        builder.suggest(id);
                    }
                    return builder.buildFuture();
                }).then(Commands.argument("level", IntegerArgumentType.integer(1)).suggests((ctx, builder) -> {
                    String effectId = StringArgumentType.getString(ctx, "effectId");

                    ModuleEffectTypeRegistry.get(effectId).ifPresent((type) -> {
                        int maxLevel = type.getModuleRarityList().size();
                        for (int lv = 1; lv <= maxLevel; lv++) {
                            builder.suggest(lv);
                        }
                    });
                    return builder.buildFuture();
                }).executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();

                    String effectId = StringArgumentType.getString(ctx, "effectId");
                    int level = IntegerArgumentType.getInteger(ctx, "level");

                    var moduleEffectType = ModuleEffectTypeRegistry.get(effectId);
                    if (moduleEffectType.isEmpty()) {
                        ctx.getSource().sendFailure(Component.literal("存在しないeffectIDが指定されました"));
                        return 0;
                    }
                    ItemStack stack = ModuleInstance
                        .generateFromEffect(new ModuleEffect(moduleEffectType.get(), level))
                        .getItemStack();

                    ctx
                        .getSource()
                        .sendSuccess(() -> Component.literal("Give module " + effectId + " Lv." + level), false);

                    player.addItem(stack);
                    return 1;
                }))));
        event.getDispatcher().register(Commands.literal("installModule").executes(ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();

            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            if (!offHand.is(KanataItem.MODULE_ITEM.get())) {
                return 0;
            }

            var inst = ModuleInstance.fromItemStack(offHand);
            if (inst.isEmpty()) {
                return 0;
            }
            ModuleHelper.installModule(mainHand, inst.get());

            return 1;
        }));
        event.getDispatcher().register(Commands.literal("readModules").executes(ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();

            ItemStack mainHand = player.getMainHandItem();

            var moduleList = ModuleHelper.getModules(mainHand);
            for (var inst : moduleList) {
                ctx.getSource().sendSuccess(
                    () -> Component.literal("moduleId:" + inst.getEffect().type().getId() + "/level:" + inst
                        .getEffect()
                        .level()), false
                );
            }
            return 1;
        }));
        event.getDispatcher().register(Commands.literal("regenChunk").executes(ctx -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();

            ServerLevel level = player.serverLevel();

            ChunkGenerator generator = level.getChunkSource().getGenerator();

            if (!(generator instanceof TownChunkGenerator townGenerator)) {
                ctx.getSource().sendFailure(Component.literal("TownChunkGeneratorではありません"));
                return 0;
            }

            ChunkPos chunkPos = player.chunkPosition();

            townGenerator.regenerateChunk(level, chunkPos);

            ctx
                .getSource()
                .sendSuccess(
                    () -> Component.literal(String.format(
                        "Chunk regenerated (%d, %d)",
                        chunkPos.x,
                        chunkPos.z
                    )), false
                );

            return 1;
        }));
    }
}
