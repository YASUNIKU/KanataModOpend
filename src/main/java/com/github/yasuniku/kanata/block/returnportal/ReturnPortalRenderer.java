package com.github.yasuniku.kanata.block.returnportal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class ReturnPortalRenderer implements BlockEntityRenderer<ReturnPortalBlockEntity> {
    public ReturnPortalRenderer(BlockEntityRendererProvider.Context ignoredCtx) {
    }

    @Override
    public void render(
        ReturnPortalBlockEntity be,
        float partialTicks,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        int packedOverlay
    ) {
        poseStack.pushPose();

        // ブロック中心へ移動
        poseStack.translate(0.5D, 1.0D, 0.5D);

        // ======== プレイヤーの方向へ向ける処理 ========
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        double dx = camPos.x - (be.getBlockPos().getX() + 0.5);
        double dz = camPos.z - (be.getBlockPos().getZ() + 0.5);

        float yaw = (float) (Math.atan2(dz, dx) * (180F / Math.PI)) - 90F;

        // Y軸回転（水平回転）
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));

        // 板ポリゴンのサイズ
        float w = 1.0F;
        float h = 2.0F;
        float z = 0.01F; // ブロック面から少し浮かせる

        VertexConsumer vc = buffer.getBuffer(RenderType.endPortal());//RenderType.entityCutout(TEXTURE)

        Matrix4f mat = poseStack.last().pose();

        // 四角形1枚（片面）: 左下→右下→右上→左上
        vc
            .vertex(mat, -w / 2, -h / 2, z)
            .color(255, 255, 255, 255)
            .uv(0.0F, 1.0F)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 0, 1)
            .endVertex();
        vc
            .vertex(mat, w / 2, -h / 2, z)
            .color(255, 255, 255, 255)
            .uv(1.0F, 1.0F)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 0, 1)
            .endVertex();
        vc
            .vertex(mat, w / 2, h / 2, z)
            .color(255, 255, 255, 255)
            .uv(1.0F, 0.0F)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 0, 1)
            .endVertex();
        vc
            .vertex(mat, -w / 2, h / 2, z)
            .color(255, 255, 255, 255)
            .uv(0.0F, 0.0F)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 0, 1)
            .endVertex();

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ReturnPortalBlockEntity be) {
        return true;
    }
}
