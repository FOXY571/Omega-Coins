package com.foxy_571.omegacoins.compat;

import com.foxy_571.omegacoins.OmegaCoins;
import com.foxy_571.omegacoins.block.ModBlocks;
import com.foxy_571.omegacoins.recipes.CoinPressRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoinPressingRecipeCategory implements IRecipeCategory<CoinPressRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(OmegaCoins.MOD_ID, "coin_pressing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(OmegaCoins.MOD_ID, "textures/gui/jei/coin_press.png");

    public static final RecipeType<CoinPressRecipe> COIN_PRESS_RECIPE_TYPE = new RecipeType<>(UID, CoinPressRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CoinPressingRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE, 0, 0, 82, 26);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.COIN_PRESS));
    }

    @Override
    public @NotNull RecipeType<CoinPressRecipe> getRecipeType() {
        return COIN_PRESS_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei_title.omegacoins.coin_pressing");
    }

    @Override
    @Nullable
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull CoinPressRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 5).addIngredients(recipe.getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 5).addItemStack(recipe.getResultItem(null));
    }
}
