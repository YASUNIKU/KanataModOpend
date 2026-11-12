package com.github.yasuniku.kanata.block.moduleinstaller;
// Made with Blockbench 5.1.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ModuleInstallerModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    @SuppressWarnings("unused")
    public static final ModelLayerLocation LAYER_LOCATION =
        new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("kanata", "module_installer"), "main");
    private final ModelPart monitor;
    private final ModelPart pc;
    private final ModelPart desk;
    @SuppressWarnings("unused")
    private final ModelPart leg;
    private final ModelPart keyboard;
    private final ModelPart machine;
    private final ModelPart mouse;

    public ModuleInstallerModel(ModelPart root) {
        this.monitor = root.getChild("monitor");
        this.pc = root.getChild("pc");
        this.desk = root.getChild("desk");
        this.leg = this.desk.getChild("leg");
        this.keyboard = root.getChild("keyboard");
        this.machine = root.getChild("machine");
        this.mouse = root.getChild("mouse");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition monitor = partdefinition.addOrReplaceChild(
            "monitor",
            CubeListBuilder
                .create()
                .texOffs(44, 56)
                .addBox(-20.0F, -17.0F, 7.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(60, 46)
                .addBox(-18.0F, -24.0F, 9.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 19)
                .addBox(-22.0F, -27.0F, 7.0F, 10.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(17.0F, 24.0F, -5.0F)
        );

        PartDefinition pc =
            partdefinition.addOrReplaceChild("pc", CubeListBuilder.create(), PartPose.offset(22.0F, 24.0F, -4.0F));

        PartDefinition cube_r1 = pc.addOrReplaceChild(
            "cube_r1",
            CubeListBuilder
                .create()
                .texOffs(0, 32)
                .addBox(-5.0F, -11.0F, -1.0F, 6.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-5.0F, 0.0F, -1.0F, 0.0F, 0.0873F, 0.0F)
        );

        PartDefinition desk = partdefinition.addOrReplaceChild(
            "desk",
            CubeListBuilder
                .create()
                .texOffs(0, 0)
                .addBox(-30.0F, -16.0F, -4.0F, 32.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
            PartPose.offset(22.0F, 24.0F, -4.0F)
        );

        PartDefinition leg = desk.addOrReplaceChild(
            "leg",
            CubeListBuilder
                .create()
                .texOffs(28, 61)
                .addBox(-24.0F, -13.0F, 10.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 61)
                .addBox(-24.0F, -13.0F, -2.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 61)
                .addBox(4.0F, -13.0F, -2.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 62)
                .addBox(4.0F, -13.0F, 10.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 32)
                .addBox(-24.0F, -2.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(36, 44)
                .addBox(4.0F, -2.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-5.0F, 0.0F, -1.0F)
        );

        PartDefinition keyboard =
            partdefinition.addOrReplaceChild("keyboard", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition cube_r2 = keyboard.addOrReplaceChild(
            "cube_r2",
            CubeListBuilder
                .create()
                .texOffs(20, 56)
                .addBox(-7.0F, -1.0F, -1.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.0F, -0.1745F, 0.0F)
        );

        PartDefinition machine = partdefinition.addOrReplaceChild(
            "machine",
            CubeListBuilder
                .create()
                .texOffs(0, 19)
                .addBox(3.0F, -1.0F, -3.0F, 14.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(60, 29)
                .addBox(9.0F, -16.0F, 7.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 55)
                .addBox(9.0F, -16.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 62)
                .addBox(8.0F, -14.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(5.0F, 8.0F, -3.0F)
        );

        PartDefinition mouse =
            partdefinition.addOrReplaceChild("mouse", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r3 = mouse.addOrReplaceChild(
            "cube_r3",
            CubeListBuilder
                .create()
                .texOffs(64, 62)
                .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(5.0F, -16.0F, -3.0F, 0.0F, 0.1309F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(
        Entity entity,
        float limbSwing,
        float limbSwingAmount,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {

    }

    @Override
    public void renderToBuffer(
        PoseStack poseStack,
        VertexConsumer vertexConsumer,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        monitor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        pc.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        desk.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        keyboard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        machine.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mouse.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
