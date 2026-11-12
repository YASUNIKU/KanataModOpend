package com.github.yasuniku.kanata.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(BlockEntityRenderDispatcher.class)
public class MixinBlockEntityRenderDispatcher {
    private static final Map<BlockEntityType<?>, Integer> renderCounts = new HashMap<>();
    private static long lastLogTime = 0;

    @Inject(method = "render", at = @At("HEAD"))
    private <E extends BlockEntity> void onRender(
        E blockEntity,
        float tickDelta,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        CallbackInfo ci
    ) {
        renderCounts.merge(blockEntity.getType(), 1, Integer::sum);

        long now = System.currentTimeMillis();
        // 1秒ごとに集計を出力
        if (now - lastLogTime > 1000) {
            System.out.println("=== BlockEntity Render Counts (per sec) ===");
            renderCounts
                .entrySet()
                .stream()
                .sorted(Map.Entry.<BlockEntityType<?>, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(e.getKey()) + ": " + e.getValue()));
            renderCounts.clear();
            lastLogTime = now;
        }
    }
}
