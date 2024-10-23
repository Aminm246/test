package recipe.model;

public class InstructionStep {
    private int stepNum;
    private String stepDescription;

    public InstructionStep(int recipeID, int stepNum, String stepDescription) {
        this.stepNum = stepNum;
        this.stepDescription = stepDescription;
    }
    public InstructionStep(int stepNum, String stepDescription) {
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

    @Override
    public String toString() {
        return "InstructionStep{" +
                "stepNum=" + stepNum +
                ", stepDescription='" + stepDescription + '\'' +
                '}';
    }
}
