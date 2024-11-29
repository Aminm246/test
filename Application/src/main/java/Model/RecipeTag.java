package Model;

public class RecipeTag {
    private int recipeTagID;
    private int recipeID;
    private int tagID;
    private Tag tag;

    public RecipeTag(int recipeID, int tagID) {
        this.recipeID = recipeID;
        this.tagID = tagID;
    }

    public RecipeTag() {

    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
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

    @Override
    public String toString() {
        return "RecipeTag{" +
                "recipeTagID=" + recipeTagID +
                ", recipeID=" + recipeID +
                ", tagID=" + tagID +
                ", tag=" + tag +
                '}';
    }
}
