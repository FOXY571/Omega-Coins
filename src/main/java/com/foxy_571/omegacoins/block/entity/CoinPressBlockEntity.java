package com.foxy_571.omegacoins.block.entity;

import com.foxy_571.omegacoins.recipes.CoinPressRecipe;
import com.foxy_571.omegacoins.recipes.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.ContainerSingleItem;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CoinPressBlockEntity extends BlockEntity implements ContainerSingleItem.BlockContainerSingleItem {
    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            callUpdated();
        }
    };

    public CoinPressBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COIN_PRESS.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
    }

    public void craftItem() {
        Optional<RecipeHolder<CoinPressRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return;
        }

        itemHandler.setStackInSlot(0, recipe.get().value().output());
        level.playSound(null, getBlockPos(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.DUST_PLUME, getBlockPos().getX() + 0.5, getBlockPos().getY() + 1.2, getBlockPos().getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private Optional<RecipeHolder<CoinPressRecipe>> getCurrentRecipe() {
        return level.getRecipeManager().getRecipeFor(ModRecipes.COIN_PRESS_RECIPE_TYPE.get(), new SingleRecipeInput(itemHandler.getStackInSlot(0)), level);
    }

    @Override
    public @NotNull ItemStack getTheItem() {
        return itemHandler.getStackInSlot(0);
    }

    @Override
    public void setTheItem(@NotNull ItemStack itemStack) {
        itemHandler.setStackInSlot(0, itemStack);
    }

    @Override
    public @NotNull ItemStack splitTheItem(int amount) {
        ItemStack itemStack = BlockContainerSingleItem.super.splitTheItem(amount);
        callUpdated();
        return itemStack;
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return isEmpty();
    }

    @Override
    public @NotNull BlockEntity getContainerBlockEntity() {
        return this;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, itemHandler.getStackInSlot(0));

        Containers.dropContents(level, worldPosition, inventory);
    }

    private void callUpdated() {
        setChanged();
        if (!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
