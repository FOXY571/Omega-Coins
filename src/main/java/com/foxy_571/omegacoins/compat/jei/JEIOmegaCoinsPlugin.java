package com.foxy_571.omegacoins.compat.jei;

import com.foxy_571.omegacoins.OmegaCoins;
import com.foxy_571.omegacoins.block.ModBlocks;
import com.foxy_571.omegacoins.recipes.CoinPressRecipe;
import com.foxy_571.omegacoins.recipes.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class JEIOmegaCoinsPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(OmegaCoins.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CoinPressRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<CoinPressRecipe> coinPressRecipes = recipeManager.getAllRecipesFor(ModRecipes.COIN_PRESS_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(CoinPressRecipeCategory.COIN_PRESS_RECIPE_TYPE, coinPressRecipes);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.COIN_PRESS), CoinPressRecipeCategory.COIN_PRESS_RECIPE_TYPE);
    }
}
