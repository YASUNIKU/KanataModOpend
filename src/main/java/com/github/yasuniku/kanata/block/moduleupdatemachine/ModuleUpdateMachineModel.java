package com.github.yasuniku.kanata.block.moduleupdatemachine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ModuleUpdateMachineModel extends Model {

    //    public static final ModelLayerLocation LAYER_LOCATION =
    //        new ModelLayerLocation(
    //            ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "module_update_machine_block"),
    //            "main"
    //        );

    public final ModelPart basemachine;

    public final ModelPart module1;
    public final ModelPart module2;
    public final ModelPart module3;
    public final ModelPart module4;
    public final ModelPart result_module;

    public ModuleUpdateMachineModel(ModelPart root) {
        super(RenderType::entityCutout);

        this.basemachine = root.getChild("basemachine");
        this.module1 = root.getChild("module1");
        this.module2 = root.getChild("module2");
        this.module3 = root.getChild("module3");
        this.module4 = root.getChild("module4");

        this.result_module = root.getChild("result_module");
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // basemachine
        partdefinition.addOrReplaceChild(
            "basemachine",
            CubeListBuilder
                .create()
                .texOffs(0, 40)
                .addBox(-8.0F, -8.0F, -8.0F, 16.0F, 2.0F, 16.0F)
                .texOffs(0, 18)
                .addBox(-8.0F, -6.0F, -6.0F, 16.0F, 8.0F, 14.0F)
                .texOffs(0, 0)
                .addBox(-8.0F, 6.0F, -8.0F, 16.0F, 2.0F, 16.0F)
                .texOffs(0, 58)
                .addBox(-8.0F, 2.0F, 0.0F, 16.0F, 4.0F, 8.0F)
                .texOffs(60, 18)
                .addBox(4.0F, 2.0F, -6.0F, 4.0F, 4.0F, 6.0F)
                .texOffs(60, 28)
                .addBox(-8.0F, 2.0F, -6.0F, 4.0F, 4.0F, 6.0F)
                .texOffs(48, 58)
                .addBox(-4.0F, 5.0F, -8.0F, 8.0F, 1.0F, 8.0F),
            PartPose.offset(0.0F, 16.0F, 0.0F)
        );

        // module1
        partdefinition.addOrReplaceChild(
            "module1",
            CubeListBuilder.create().texOffs(64, 8).addBox(-5.0F, -12.0F, -7.0F, 4.0F, 2.0F, 1.0F),
            PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        // module2
        partdefinition.addOrReplaceChild(
            "module2",
            CubeListBuilder.create().texOffs(64, 14).addBox(-5.0F, -9.0F, -7.0F, 4.0F, 2.0F, 1.0F),
            PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        // module3
        partdefinition.addOrReplaceChild(
            "module3",
            CubeListBuilder.create().texOffs(64, 11).addBox(-4.0F, -2.0F, 0.0F, 4.0F, 2.0F, 1.0F),
            PartPose.offset(5.0F, 14.0F, -7.0F)
        );

        // module4
        partdefinition.addOrReplaceChild(
            "module4",
            CubeListBuilder.create().texOffs(64, 38).addBox(-4.0F, 1.0F, 0.0F, 4.0F, 2.0F, 1.0F),
            PartPose.offset(5.0F, 14.0F, -7.0F)
        );

        // result_module
        partdefinition.addOrReplaceChild(
            "result_module",
            CubeListBuilder.create().texOffs(64, 0).addBox(-7.0F, 5.0F, 0.0F, 4.0F, 2.0F, 6.0F),
            PartPose.offset(5.0F, 14.0F, -7.0F)
        );

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(
        PoseStack pPoseStack,
        VertexConsumer pBuffer,
        int pPackedLight,
        int pPackedOverlay,
        float pRed,
        float pGreen,
        float pBlue,
        float pAlpha
    ) {

    }
}
