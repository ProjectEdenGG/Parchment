package gg.projecteden.parchment.inventory;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public class CraftRecipeType {
    public static net.minecraft.world.item.crafting.RecipeType asNMS(RecipeType recipeType) {
        return switch (recipeType) {
            case CRAFTING -> net.minecraft.world.item.crafting.RecipeType.CRAFTING;
            case SMELTING -> net.minecraft.world.item.crafting.RecipeType.SMELTING;
            case BLASTING -> net.minecraft.world.item.crafting.RecipeType.BLASTING;
            case SMOKING -> net.minecraft.world.item.crafting.RecipeType.SMOKING;
            case CAMPFIRE_COOKING -> net.minecraft.world.item.crafting.RecipeType.CAMPFIRE_COOKING;
            case STONECUTTING -> net.minecraft.world.item.crafting.RecipeType.STONECUTTING;
            case SMITHING -> net.minecraft.world.item.crafting.RecipeType.SMITHING;
        };
    }

    public static RecipeType asBukkit(net.minecraft.world.item.crafting.RecipeType recipeType) {
        if (recipeType == net.minecraft.world.item.crafting.RecipeType.CRAFTING) {
            return RecipeType.CRAFTING;
        } else if (recipeType == net.minecraft.world.item.crafting.RecipeType.SMELTING) {
            return RecipeType.SMELTING;
        } else if (recipeType == net.minecraft.world.item.crafting.RecipeType.BLASTING) {
            return RecipeType.BLASTING;
        } else if (recipeType == net.minecraft.world.item.crafting.RecipeType.SMOKING) {
            return RecipeType.SMOKING;
        } else if (recipeType == net.minecraft.world.item.crafting.RecipeType.CAMPFIRE_COOKING) {
            return RecipeType.CAMPFIRE_COOKING;
        } else if (recipeType == net.minecraft.world.item.crafting.RecipeType.STONECUTTING) {
            return RecipeType.STONECUTTING;
        } else if (recipeType == net.minecraft.world.item.crafting.RecipeType.SMITHING) {
            return RecipeType.SMITHING;
        }
        throw new IllegalArgumentException("Unknown recipe type");
    }

    public static net.minecraft.world.item.crafting.RecipeType<AbstractCookingRecipe> asCookingRecipe(RecipeType recipeType) {
        try {
            return (net.minecraft.world.item.crafting.RecipeType<AbstractCookingRecipe>) asNMS(recipeType);
        } catch (ClassCastException exc) {
            throw new IllegalArgumentException("Recipe type must be a cooking recipe");
        }
    }
}