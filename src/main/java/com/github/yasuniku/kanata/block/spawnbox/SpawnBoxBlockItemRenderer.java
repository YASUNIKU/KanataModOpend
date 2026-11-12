package com.github.yasuniku.kanata.block.spawnbox;

import com.github.yasuniku.kanata.registry.KanataBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("unused")
public class SpawnBoxBlockItemRenderer extends BlockEntityWithoutLevelRenderer {

    @Nullable
    private SpawnBoxBlockEntity dummy;

    public SpawnBoxBlockItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(
        ItemStack stack,
        ItemDisplayContext context,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int light,
        int overlay
    ) {
        if (dummy == null) {
            dummy = new SpawnBoxBlockEntity(BlockPos.ZERO, KanataBlock.SPAWN_BOX_BLOCK.get().defaultBlockState());
        }

        poseStack.pushPose();

        poseStack.translate(0.5F, 0.2F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.scale(0.8F, 0.8F, 0.8F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(dummy, poseStack, buffer, light, overlay);

        poseStack.popPose();
    }
}
