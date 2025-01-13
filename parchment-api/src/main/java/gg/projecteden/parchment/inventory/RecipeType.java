package gg.projecteden.parchment.inventory;

/**
 * A type of crafting recipe.
 */
public enum RecipeType {
    /**
     * Recipes crafted in the standard crafting table.
     */
    CRAFTING(false),
    /**
     * Recipes for smelting an item inside a furnace.
     */
    SMELTING(true),
    /**
     * Recipes for smelting an item inside a blasting furnace.
     */
    BLASTING(true),
    /**
     * Recipes for smelting an item inside a smoker.
     */
    SMOKING(true),
    /**
     * Recipes for cooking an item on a campfire.
     */
    CAMPFIRE_COOKING(true),
    /**
     * Recipes for carving stones in a stonecutter.
     */
    STONECUTTING(true),
    /**
     * Recipes for smithing an item in a smithing table.
     */
    SMITHING(false),
    ;

    private final boolean singleInput;

    RecipeType(boolean singleInput) {
        this.singleInput = singleInput;
    }

    /**
     * Determines if the recipe only accepts a single item for input.
     * @return true if the recipe only accepts a single item for input
     */
    public boolean isSingleInput() {
        return singleInput;
    }
}