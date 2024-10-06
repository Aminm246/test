package recipe;

public class InstructionStep {
    private int recipeID;
    private int stepNum;
    private String stepDescription;

    public InstructionStep(int recipeID, int stepNum, String stepDescription) {
        this.recipeID = recipeID;
        this.stepNum = stepNum;
        this.stepDescription = stepDescription;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
