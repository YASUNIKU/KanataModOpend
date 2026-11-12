package com.github.yasuniku.kanata.entity.client;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.github.yasuniku.kanata.entity.animations.ModAnimationDefinitions;
import com.github.yasuniku.kanata.entity.custom.RobotEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.monster.Monster;

@SuppressWarnings("ALL")
//public class RobotModel<T extends Entity> extends HierarchicalModel<T> {
public class RobotModel<R extends Monster> extends HierarchicalModel<RobotEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leg_left;
    private final ModelPart leg_left_2;
    private final ModelPart leg_left_3;
    private final ModelPart leg_right;
    private final ModelPart leg_right_2;
    private final ModelPart leg_right_3;
    private final ModelPart arm;
    private final ModelPart arm_2;
    private final ModelPart arm_3;
    private final ModelPart arm_finger_upper;
    private final ModelPart arm_finger_lower;

    private final AnimationState walkState = new AnimationState();

    public RobotModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.leg_left = this.body.getChild("leg_left");
        this.leg_left_2 = this.leg_left.getChild("leg_left_2");
        this.leg_left_3 = this.leg_left_2.getChild("leg_left_3");
        this.leg_right = this.body.getChild("leg_right");
        this.leg_right_2 = this.leg_right.getChild("leg_right_2");
        this.leg_right_3 = this.leg_right_2.getChild("leg_right_3");
        this.arm = this.root.getChild("arm");
        this.arm_2 = this.arm.getChild("arm_2");
        this.arm_3 = this.arm_2.getChild("arm_3");
        this.arm_finger_upper = this.arm_3.getChild("arm_finger_upper");
        this.arm_finger_lower = this.arm_3.getChild("arm_finger_lower");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root =
            partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0f, 0.0F));

        PartDefinition body = root.addOrReplaceChild(
            "body",
            CubeListBuilder
                .create()
                .texOffs(0, 0)
                .addBox(-6.0F, -16.0F, -8.0F, 12.0F, 10.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(40, 35)
                .addBox(-4.0F, -15.0F, 5.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 8)
                .addBox(-2.0F, -14.0F, 6.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        PartDefinition back_antenna_right_r1 = body.addOrReplaceChild(
            "back_antenna_right_r1",
            CubeListBuilder
                .create()
                .texOffs(14, 56)
                .addBox(0.0F, -6.0F, -1.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 49)
                .addBox(5.0F, -6.0F, -1.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-3.0F, -11.0F, 7.0F, -0.3491F, 0.0F, 0.0F)
        );

        PartDefinition leg_left =
            body.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offset(7.0F, -12.0F, 3.0F));

        PartDefinition leg_left_1_r1 = leg_left.addOrReplaceChild(
            "leg_left_1_r1",
            CubeListBuilder
                .create()
                .texOffs(26, 35)
                .addBox(-2.0F, -2.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -1.0036F, 0.0F, 0.0F)
        );

        PartDefinition leg_left_2 =
            leg_left.addOrReplaceChild("leg_left_2", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -4.0F));

        PartDefinition leg_left_2_1_r1 = leg_left_2.addOrReplaceChild(
            "leg_left_2_1_r1",
            CubeListBuilder
                .create()
                .texOffs(14, 36)
                .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.5672F, 0.0F, 0.0F)
        );

        PartDefinition leg_left_3 = leg_left_2.addOrReplaceChild(
            "leg_left_3",
            CubeListBuilder
                .create()
                .texOffs(42, 23)
                .addBox(-2.0F, 2.0F, -8.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 5.0F, 3.0F)
        );

        PartDefinition leg_left_3_1_r1 = leg_left_3.addOrReplaceChild(
            "leg_left_3_1_r1",
            CubeListBuilder
                .create()
                .texOffs(48, 52)
                .addBox(1.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F)
        );

        PartDefinition leg_right =
            body.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offset(-8.0F, -12.0F, 3.0F));

        PartDefinition leg_right_1_r1 = leg_right.addOrReplaceChild(
            "leg_right_1_r1",
            CubeListBuilder
                .create()
                .texOffs(0, 36)
                .addBox(-2.0F, -2.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -1.0036F, 0.0F, 0.0F)
        );

        PartDefinition leg_right_2 =
            leg_right.addOrReplaceChild("leg_right_2", CubeListBuilder.create(), PartPose.offset(1.0F, 3.0F, -4.0F));

        PartDefinition leg_right_2_1_r1 = leg_right_2.addOrReplaceChild(
            "leg_right_2_1_r1",
            CubeListBuilder
                .create()
                .texOffs(0, 49)
                .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.5672F, 0.0F, 0.0F)
        );

        PartDefinition leg_right_3 = leg_right_2.addOrReplaceChild(
            "leg_right_3",
            CubeListBuilder
                .create()
                .texOffs(40, 44)
                .addBox(-1.0F, 2.0F, -8.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 5.0F, 3.0F)
        );

        PartDefinition leg_right_3_1_r1 = leg_right_3.addOrReplaceChild(
            "leg_right_3_1_r1",
            CubeListBuilder
                .create()
                .texOffs(54, 52)
                .addBox(1.0F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F)
        );

        PartDefinition arm =
            root.addOrReplaceChild("arm", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, 2.0F));

        PartDefinition arm_1_r1 = arm.addOrReplaceChild(
            "arm_1_r1",
            CubeListBuilder
                .create()
                .texOffs(26, 23)
                .addBox(-2.0F, -7.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, -0.6981F, 0.0F, 0.0F)
        );

        PartDefinition arm_2 =
            arm.addOrReplaceChild("arm_2", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 3.0F));

        PartDefinition arm_2_1_r1 = arm_2.addOrReplaceChild(
            "arm_2_1_r1",
            CubeListBuilder
                .create()
                .texOffs(30, 48)
                .addBox(-1.0F, -9.0F, -2.0F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F)
        );

        PartDefinition arm_3 = arm_2.addOrReplaceChild(
            "arm_3",
            CubeListBuilder
                .create()
                .texOffs(0, 23)
                .addBox(-2.0F, -3.0F, -7.0F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -4.0F, -6.0F)
        );

        PartDefinition arm_finger_upper = arm_3.addOrReplaceChild(
            "arm_finger_upper",
            CubeListBuilder
                .create()
                .texOffs(50, 15)
                .addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 48)
                .addBox(-1.0F, -6.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -1.0F, -5.0F)
        );

        PartDefinition arm_finger_lower = arm_3.addOrReplaceChild(
            "arm_finger_lower",
            CubeListBuilder
                .create()
                .texOffs(40, 52)
                .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0)
                .addBox(-1.0F, 4.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -1.0F, -5.0F)
        );

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(
        RobotEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch
    ) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        // ★ 攻撃アニメーション（最優先）
        entity.attackAnimationState.updateTime(ageInTicks, 1.0f);
        if (entity.attackAnimationState.isStarted()) {
            this.animate(entity.attackAnimationState, ModAnimationDefinitions.attack, ageInTicks);
        }
        // ★ 歩行アニメーション
        entity.walkAnimationState.updateTime(ageInTicks, 1.0f);
        this.animate(entity.walkAnimationState, ModAnimationDefinitions.walk, ageInTicks);
    }

    @Override
    public void renderToBuffer(
        PoseStack poseStack,
        VertexConsumer buffer,
        int packedLight,
        int packedOverlay,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
