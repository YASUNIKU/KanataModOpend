package com.github.yasuniku.kanata.block.spawnbox;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

@SuppressWarnings("unused")
public class SpawnBoxModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor

    private final ModelPart group;
    private final ModelPart body;
    private final ModelPart door_left;
    private final ModelPart door_right;

    public SpawnBoxModel(ModelPart root) {
        this.group = root.getChild("group");
        this.body = this.group.getChild("body");
        this.door_left = this.group.getChild("door_left");
        this.door_right = this.group.getChild("door_right");
    }

    public void setDoorAngle(float angle) {
        // 左扉
        this.door_left.yRot = -angle;

        // 右扉
        this.door_right.yRot = angle;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition group = partdefinition.addOrReplaceChild(
            "group",
            CubeListBuilder.create(),
            PartPose.offsetAndRotation(8.0F, 8.0F, 8.0F, (float) Math.PI, 0.0f, 0.0f)
        );

        PartDefinition body = group.addOrReplaceChild(
            "body",
            CubeListBuilder
                .create()
                .texOffs(0, 18)
                .addBox(-8.0F, -32.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(64, 0)
                .addBox(-6.0F, -30.0F, 6.0F, 12.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36)
                .addBox(6.0F, -30.0F, -6.0F, 2.0F, 28.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36)
                .addBox(-8.0F, -30.0F, -6.0F, 2.0F, 28.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0)
                .addBox(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 8.0F, 0.0F)
        );

        PartDefinition door_left = group.addOrReplaceChild(
            "door_left",
            CubeListBuilder
                .create()
                .texOffs(64, 30)
                .addBox(-7.0F, -27.0F, -1.0F, 8.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(7.0F, 5.0F, -7.0F)
        );

        PartDefinition door_right = group.addOrReplaceChild(
            "door_right",
            CubeListBuilder
                .create()
                .texOffs(64, 60)
                .addBox(-1.0F, -27.0F, -1.0F, 8.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(-7.0F, 5.0F, -7.0F)
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
        group.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
