package com.foxy_571.omegacoins.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CoinPressRecipe(Ingredient inputItem, ItemStack output) implements Recipe<SingleRecipeInput> {
    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(inputItem);
        return ingredients;
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput singleRecipeInput, @NotNull Level level) {
        if (!level.isClientSide()) {
            return inputItem.test(singleRecipeInput.getItem(0));
        }
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput singleRecipeInput, HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int value) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.COIN_PRESS_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.COIN_PRESS_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CoinPressRecipe> {
        public static final MapCodec<CoinPressRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CoinPressRecipe::inputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(CoinPressRecipe::output)
        ).apply(inst, CoinPressRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CoinPressRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, CoinPressRecipe::inputItem,
            ItemStack.STREAM_CODEC, CoinPressRecipe::output,
            CoinPressRecipe::new
        );

        @Override
        public @NotNull MapCodec<CoinPressRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, CoinPressRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
