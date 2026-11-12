package com.github.yasuniku.kanata.block.portal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public class PortalRenderer implements BlockEntityRenderer<PortalBlockEntity> {
    public PortalRenderer() {
    }

    @Override
    public void render(
        PortalBlockEntity be,
        float partialTicks,
        PoseStack pose,
        MultiBufferSource buffer,
        int light,
        int overlay
    ) {
        // RenderType.endPortal() を使う
        VertexConsumer vc = buffer.getBuffer(RenderType.endPortal());

        pose.pushPose();

        drawTopFace(pose, vc);
        drawBottomFace(pose, vc);

        pose.popPose();
    }

    private void drawTopFace(PoseStack pose, VertexConsumer vc) {
        var mat = pose.last().pose();

        float x1 = 0, x2 = 1;
        float z1 = 0, z2 = 1;
        float y = 1; // 上面

        vc.vertex(mat, x1, y, z1).uv(0, 0).endVertex();
        vc.vertex(mat, x1, y, z2).uv(0, 1).endVertex();
        vc.vertex(mat, x2, y, z2).uv(1, 1).endVertex();
        vc.vertex(mat, x2, y, z1).uv(1, 0).endVertex();
    }

    private void drawBottomFace(PoseStack pose, VertexConsumer vc) {
        var mat = pose.last().pose();

        float x1 = 0, x2 = 1;
        float z1 = 0, z2 = 1;
        float y = 0; // 下面

        vc.vertex(mat, x1, y, z1).uv(0, 0).endVertex();
        vc.vertex(mat, x2, y, z1).uv(1, 0).endVertex();
        vc.vertex(mat, x2, y, z2).uv(1, 1).endVertex();
        vc.vertex(mat, x1, y, z2).uv(0, 1).endVertex();
    }
}
