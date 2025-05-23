package Controller;

import Model.InstructionStep;
import Repository.DatabaseConnection;
import Repository.InstructionsRepository;

import java.sql.SQLException;
import java.util.List;

public class InstructionsManager {
    private final InstructionsRepository instructionsRepository;

    public InstructionsManager(DatabaseConnection databaseConnection) {
        this.instructionsRepository = new InstructionsRepository(databaseConnection);
    }

    public InstructionStep getInstruction(int instructionStepID)  {
        InstructionStep instructionStep = instructionsRepository.getInstructionById(instructionStepID);
        if (instructionStep != null) {
            System.out.println("Instruction found: " + instructionStep.getStepDescription());
        } else {
            System.out.println("Instruction with ID " + instructionStepID + " not found.");
        }
        return instructionStep;
    }

    public int insertInstruction(int recipeID, int stepNum, String stepDescription)  {
        InstructionStep instructionStep = new InstructionStep(recipeID, stepNum, stepDescription);
        int instructionID = instructionsRepository.insertInstruction(instructionStep);
        if (instructionID != -1) {
            System.out.println("Instruction created with ID: " + instructionID);
        } else {
            System.out.println("Failed to create instruction.");
        }
        return instructionID;
    }

    public void updateInstruction(int instructionID, String stepDescription)  {
        System.out.println(instructionID + "!!!!!!!!!!!!!!");
        InstructionStep instruction = instructionsRepository.getInstructionById(instructionID);
        System.out.println(":" + instructionID + ":");
        instruction.setStepDescription(stepDescription);
        instructionsRepository.updateInstruction(instruction);

    }

    public List<InstructionStep> getInstructionsByRecipeId(int recipeId)  {
        return instructionsRepository.getInstructionsByRecipeId(recipeId);
    }

    public List<InstructionStep> getAllInstructions() {
        return instructionsRepository.getAllInstructions();
    }

    public void removeInstruction(int instructionID){
        instructionsRepository.deleteInstruction(instructionID);
    }

    @Override
    public String toString() {
        return "InstructionsManager{" +
                "instructionsRepository=" + instructionsRepository +
                '}';
    }
}
