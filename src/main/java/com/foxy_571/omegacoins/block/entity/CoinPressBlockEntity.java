package com.foxy_571.omegacoins.block.entity;

import com.foxy_571.omegacoins.item.ModItems;
import com.foxy_571.omegacoins.recipes.CoinPressRecipe;
import com.foxy_571.omegacoins.recipes.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CoinPressBlockEntity extends BlockEntity {
    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
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
    }

    private Optional<RecipeHolder<CoinPressRecipe>> getCurrentRecipe() {
        return level.getRecipeManager().getRecipeFor(ModRecipes.COIN_PRESS_RECIPE_TYPE.get(), new SingleRecipeInput(itemHandler.getStackInSlot(0)), level);
    }

    public ItemStack getTheItem() {
        return itemHandler.getStackInSlot(0);
    }

    public void SetTheItem(ItemStack stack) {
        itemHandler.setStackInSlot(0, stack);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, itemHandler.getStackInSlot(0));

        Containers.dropContents(level, worldPosition, inventory);
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
