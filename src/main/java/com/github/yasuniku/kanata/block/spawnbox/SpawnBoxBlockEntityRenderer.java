package com.github.yasuniku.kanata.block.spawnbox;

import com.github.yasuniku.kanata.KanataMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class SpawnBoxBlockEntityRenderer implements BlockEntityRenderer<SpawnBoxBlockEntity> {

    private final SpawnBoxModel<?> model;
    ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "textures/block/spawn_box.png");

    public SpawnBoxBlockEntityRenderer() {
        this.model = new SpawnBoxModel<>(SpawnBoxModel.createBodyLayer().bakeRoot());
    }

    @Override
    public void render(
        SpawnBoxBlockEntity spawnBoxBlockEntity,
        float partialTicks,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int light,
        int overlay
    ) {
        poseStack.pushPose();
        // ブロック中心へ移動
        poseStack.translate(0.5, 0, 0.5);

        // FACING に応じて回転
        Direction dir = spawnBoxBlockEntity.getBlockState().getValue(SpawnBoxBlock.FACING);
        poseStack.mulPose(Axis.YP.rotationDegrees(-dir.toYRot()));

        // モデルの原点に戻す
        poseStack.translate(-0.5, 0, -0.5);

        float openness =
            spawnBoxBlockEntity.oOpenness + (spawnBoxBlockEntity.openness - spawnBoxBlockEntity.oOpenness) * partialTicks;

        // ease-out（チェストと同じ）
        float f = 1.0F - openness;
        f = 1.0F - f * f * f;

        // 扉の回転角度（90度開く）
        float angle = f * ((float) Math.PI / 2F);

        model.setDoorAngle(angle);

        // 描画
        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutout(texture)), light, overlay, 1, 1, 1, 1);

        poseStack.popPose();
    }
}
