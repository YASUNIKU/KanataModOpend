package com.github.yasuniku.kanata.block.moduleupdatemachine;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.registry.KanataModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class ModuleUpdateMachineBlockEntityRenderer implements BlockEntityRenderer<ModuleUpdateMachineBlockEntity> {

    private final ModuleUpdateMachineModel model;
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "textures/block/module_update_machine_block.png");

    public ModuleUpdateMachineBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new ModuleUpdateMachineModel(ctx.bakeLayer(KanataModelLayers.MODULE_UPDATE_MACHINE));
    }

    @Override
    public void render(
        ModuleUpdateMachineBlockEntity be,
        float partialTicks,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        int packedOverlay
    ) {
        Direction facing = be.getBlockState().getValue(ModuleUpdateMachineBlock.FACING);
        poseStack.pushPose();

        // BlockBench → Javaモデルの標準座標変換
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.scale(1.0F, -1.0F, -1.0F);// 水平回転
        float rotY = switch (facing) {
            case NORTH -> 180f;
            case EAST -> 270f;
            case SOUTH -> 0f;
            case WEST -> 90f;
            default -> 180f;
        };
        poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutout(TEXTURE));

        model.basemachine.render(poseStack, vc, packedLight, packedOverlay);
        var modules = new ModelPart[]{model.module1, model.module3, model.module2, model.module4, model.result_module};

        for (int i = 0; i < modules.length; i++) {
            if (be.shouldRenderModule(i)) {
                modules[i].render(poseStack, vc, packedLight, packedOverlay);
            }
        }

        poseStack.popPose();
    }
}
