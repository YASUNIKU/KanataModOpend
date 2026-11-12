package com.github.yasuniku.kanata.entity.client;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.entity.custom.RobotEntity;
import com.github.yasuniku.kanata.registry.KanataModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RobotRenderer extends MobRenderer<RobotEntity, RobotModel<RobotEntity>> {
    public RobotRenderer(
        EntityRendererProvider.Context pContext
    ) {
        super(pContext, new RobotModel<>(pContext.bakeLayer(KanataModelLayers.ROBOT_LAYER)), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(RobotEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "textures/entity/robot.png");
    }

    @Override
    public void render(
        RobotEntity pEntity,
        float pEntityYaw,
        float pPartialTicks,
        PoseStack pMatrixStack,
        MultiBufferSource pBuffer,
        int pPackedLight
    ) {
        this.shadowRadius = RobotEntity.BASE_SIZE_WIDTH * pEntity.getScale();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    protected void scale(RobotEntity robotEntity, PoseStack poseStack, float pPartialTickTime) {
        float scale = robotEntity.getScale();
        poseStack.scale(scale, scale, scale);
    }
}
