package Model;

public class RecipeTag {
    private int recipeTagID;
    private int recipeID;
    private int tagID;

    public RecipeTag(int recipeID, int tagID) {
        this.recipeID = recipeID;
        this.tagID = tagID;
    }

    public RecipeTag() {

    }

    public int getRecipeTagID() {
        return recipeTagID;
    }

    public void setRecipeTagID(int recipeTagID) {
        this.recipeTagID = recipeTagID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }
}
