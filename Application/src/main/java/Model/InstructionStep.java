package Model;

public class InstructionStep {
    private int instructionStepID;
    private int recipeID;
    private int stepNum;
    private String stepDescription;

    public InstructionStep(int instructionStepID, int recipeID, int stepNum, String stepDescription) {
        this.recipeID = recipeID;
        this.instructionStepID = instructionStepID;
        this.stepNum = stepNum;
        this.stepDescription = stepDescription;
    }
    public InstructionStep(int recipeID, int stepNum, String stepDescription) {
        this.recipeID = recipeID;
        this.stepNum = stepNum;
        this.stepDescription = stepDescription;
    }

    public InstructionStep() {

    }

    public int getInstructionStepID() {
        return instructionStepID;
    }

    public void setInstructionStepID(int instructionStepID) {
        this.instructionStepID = instructionStepID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
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

    @Override
    public String toString() {
        return "InstructionStep{" +
                "instructionStepID=" + instructionStepID +
                ", recipeID=" + recipeID +
                ", stepNum=" + stepNum +
                ", stepDescription='" + stepDescription + '\'' +
                '}';
    }
}
