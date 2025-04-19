package com.foxy_571.omegacoins.block.custom;

import com.foxy_571.omegacoins.block.entity.CoinPressBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoinPressBlock extends BaseEntityBlock {
    public static final MapCodec<CoinPressBlock> CODEC = simpleCodec(CoinPressBlock::new);

    public CoinPressBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new CoinPressBlockEntity(blockPos, blockState);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CoinPressBlockEntity coinPressBlockEntity) {
                ItemStack item = coinPressBlockEntity.getTheItem();
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, item);
                    coinPressBlockEntity.setTheItem(ItemStack.EMPTY);
                } else if (item.isEmpty()) {
                    coinPressBlockEntity.setTheItem(stack.consumeAndReturn(1, player));
                    return ItemInteractionResult.CONSUME;
                }
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.getBlock() instanceof PistonHeadBlock) {
            if (neighborState.getValue(PistonHeadBlock.FACING) == Direction.DOWN) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof CoinPressBlockEntity coinPressBlockEntity) {
                    coinPressBlockEntity.craftItem();
                }
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CoinPressBlockEntity coinPressBlockEntity) {
                coinPressBlockEntity.drops();
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
