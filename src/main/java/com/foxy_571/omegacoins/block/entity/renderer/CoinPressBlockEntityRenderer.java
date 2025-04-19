package com.foxy_571.omegacoins.block.entity.renderer;

import com.foxy_571.omegacoins.block.custom.CoinPressBlock;
import com.foxy_571.omegacoins.block.entity.CoinPressBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CoinPressBlockEntityRenderer implements BlockEntityRenderer<CoinPressBlockEntity> {
    public CoinPressBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(@NotNull CoinPressBlockEntity blockEntity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = blockEntity.getTheItem();

        poseStack.pushPose();
        poseStack.translate(0.5f, 1, 0.5f);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(270));

        if (blockEntity.getBlockState().getBlock() instanceof CoinPressBlock)
        {
            Direction direction = blockEntity.getBlockState().getValue(CoinPressBlock.FACING);
            poseStack.mulPose(Axis.ZP.rotationDegrees(getRotation(direction)));
        }

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
        poseStack.popPose();
    }

    private float getRotation(Direction direction) {
        return switch (direction) {
            case EAST -> 90;
            case NORTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);

        return LightTexture.pack(blockLight, skyLight);
    }
}
