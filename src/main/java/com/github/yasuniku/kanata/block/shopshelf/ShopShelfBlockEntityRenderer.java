package com.github.yasuniku.kanata.block.shopshelf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ShopShelfBlockEntityRenderer implements BlockEntityRenderer<ShopShelfBlockEntity> {

    @SuppressWarnings("unused")
    public ShopShelfBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(
        ShopShelfBlockEntity be,
        float partialTicks,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int light,
        int overlay
    ) {

        ItemStackHandler items = be.getItems();
        Direction facing = be.getBlockState().getValue(ShopShelfBlock.FACING);

        float rotY = switch (facing) {
            //case NORTH -> 180f;
            case EAST -> 90f;
            case SOUTH -> 0f;
            case WEST -> 270f;
            default -> 180f;
        };

        poseStack.pushPose();

        // ① ブロック中心へ移動（これがズレ防止の最重要ポイント）
        poseStack.translate(0.5f, 0.5f, 0.5f);

        // ② 向きに合わせて回転
        poseStack.mulPose(Axis.YP.rotationDegrees(rotY));

        // ③ 2段の座標設定
        float[] xs = {-0.30f, 0.00f, 0.30f}; // 横方向に6個並べる
        float yTop = 0.30f;   // 上段の高さ
        float yBottom = -0.2f; // 下段の高さ
        float z = -0.15f;      // 奥行き（棚の手前側）
        float itemSize = 0.35f;

        for (int i = 0; i < 6; i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            poseStack.pushPose();

            // 上段：0〜2、下段：3〜5
            boolean top = i < 3;
            float y = top ? yTop : yBottom;
            float x = xs[i % 3];

            poseStack.translate(x, y, z);

            // アイテムの向き調整
            poseStack.mulPose(Axis.YP.rotationDegrees(180));

            // アイテムのサイズ調整
            poseStack.scale(itemSize, itemSize, itemSize);

            Minecraft
                .getInstance()
                .getItemRenderer()
                .renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, poseStack, buffer, null, 0);

            poseStack.popPose();
        }

        poseStack.popPose();
    }
}
