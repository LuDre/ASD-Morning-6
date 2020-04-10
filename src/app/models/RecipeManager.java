package app.models;

import java.io.Serializable;
import java.util.Vector;

public class RecipeManager implements Serializable {
    private static RecipeManager instance;

    private Vector<Recipe> recipes;

    public RecipeManager() {
        this.recipes = new Vector<>();
    }

    public static RecipeManager getInstance() {
        if (instance == null)
            instance = new RecipeManager();

        return instance;
    }

    public static void setInstance(RecipeManager rm) {
        instance = rm;
    }

    public Vector<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Vector<Recipe> recipes) {
        for(Recipe recipe : recipes){
            addRecipe(recipe);
        }
    }

    public Vector<Recipe> getFavRecipes() {
        Vector<Recipe> retList = new Vector<>();

        for (Recipe recipe : this.recipes) {
            if (recipe.isFavourite())
                retList.add(recipe);
        }

        return retList;
    }

    public void addRecipe(Recipe r) throws IllegalArgumentException {
        if(r == null){
            throw new IllegalArgumentException("Not possible to add null!");
        }
        this.recipes.add(r);
    }

    public void deleteRecipe(Recipe r){
        this.recipes.remove(r);
    }

    public void updateRecipe(int id, Recipe newRecipe) {
        for (Recipe r : this.recipes) {
            if (r.getId() == id) {
                r.setName(newRecipe.getName());
                r.setDescription(newRecipe.getDescription());
                r.setType(newRecipe.getType());
                r.setPrepTime(newRecipe.getPrepTime());
                r.setCookTime(newRecipe.getCookTime());
                r.setFavourite(newRecipe.isFavourite());
                r.setGuideEnabled(newRecipe.isGuideEnabled());
            }
        }
        //TODO photos update
    }
}
