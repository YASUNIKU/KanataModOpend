package com.github.yasuniku.kanata.block.moduleinstaller;

import com.github.yasuniku.kanata.KanataMod;
import com.github.yasuniku.kanata.utils.TablePart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class ModuleInstallerRenderer implements BlockEntityRenderer<ModuleInstallerBlockEntity> {

    private final ModuleInstallerModel<?> model;
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(KanataMod.MOD_ID, "textures/block/module_installer.png");

    public ModuleInstallerRenderer() {
        // Blockbench → Java Export のモデルを直接 new
        this.model = new ModuleInstallerModel<>(ModuleInstallerModel.createBodyLayer().bakeRoot());
    }

    @Override
    public void render(
        ModuleInstallerBlockEntity be,
        float partialTicks,
        PoseStack pose,
        MultiBufferSource buffer,
        int light,
        int overlay
    ) {
        // MAIN（LEFT）以外には BlockEntity が存在しないが、念のためチェック
        if (be.getBlockState().getValue(ModuleInstallerBlock.PART) != TablePart.LEFT) {
            return;
        }

        pose.pushPose();

        // Blockbench のモデルは原点が 0,0,0 なので、ブロック中心へ移動
        pose.translate(0.5, 1.5, 0.5);

        // Blockbench → Minecraft の上下反転
        pose.scale(1.0f, -1.0f, -1.0f);

        // FACING に応じて回転
        Direction dir = be.getBlockState().getValue(ModuleInstallerBlock.FACING);
        float rotY = switch (dir) {
            case NORTH -> 180f;
            case SOUTH -> 0;
            case WEST -> 90f;
            case EAST -> -90f;
            default -> 0f;
        };

        pose.mulPose(Axis.YP.rotationDegrees(rotY));

        // 描画
        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        model.renderToBuffer(pose, vc, light, overlay, 1, 1, 1, 1);

        pose.popPose();
    }
}


