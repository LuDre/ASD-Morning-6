package app.models;

import java.io.Serializable;
import java.util.Vector;

public class RecipeManager implements Serializable {
    private static RecipeManager instance;

    private Vector<Recipe> recipes;

    private static int id = 1;

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
        this.recipes = recipes;
    }

    public Vector<Recipe> getFavRecipes() {
        Vector<Recipe> retList = new Vector<>();

        for(int i = 0; i < this.recipes.size(); i++){
            if(this.recipes.get(i).isFavourite())
                retList.add(recipes.get(i));
        }

        return retList;
    }

    public void addRecipe(Recipe r) {
        r.setId(id);
        this.recipes.add(r);
        id++;
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
