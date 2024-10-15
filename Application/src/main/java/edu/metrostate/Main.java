package edu.metrostate;

import ingredient.model.Ingredient;
import ingredient.model.IngredientsInventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import recipe.model.InstructionStep;
import recipe.model.Recipe;
import recipe.model.RecipeManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cookbook v0.1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createRecipe2.fxml"));
        Parent root = loader.load();

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
//        Parent root = loader.load();
//        Ingredient tomato = new Ingredient("Tomato");
//        Ingredient lettuce = new Ingredient("Lettuce");
//        Ingredient bun = new Ingredient("Hamburger Bun");
//        Ingredient beefPatty = new Ingredient("Beef Patty");
//        ArrayList<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(tomato);
//        ingredients.add(lettuce);
//        ingredients.add(bun);
//        ingredients.add(beefPatty);
//        ArrayList<String> tag = new ArrayList<>();
//        tag.add("Burgers");
//        String description = "A tasty burger";
//        String imagePath = "edu/metrostate/images/burger_image.jpg";
//        List<BigDecimal> quantity = new ArrayList<>();
//        quantity.add(BigDecimal.valueOf(0.5));
//        quantity.add(BigDecimal.valueOf(200));
//        quantity.add(BigDecimal.valueOf(2));
//        quantity.add(BigDecimal.valueOf(1));
//        List<InstructionStep> steps = new ArrayList<>();
//        InstructionStep instructionStep1 = new InstructionStep(1, 1, "Cook the beef patty on a hot grill until it's done to your preference.");
//        InstructionStep instructionStep2 = new InstructionStep(1, 2, "Place the patty on a bun and add your toppings");
//        InstructionStep instructionStep3 = new InstructionStep(1, 3, "Close the bun, serve with sides, and enjoy your burger!");
//        steps.add(instructionStep1);
//        steps.add(instructionStep2);
//        steps.add(instructionStep3);
//        Recipe recipe = new Recipe(1, "Burger", 1, tag, 45, 3, description, imagePath, ingredients, quantity, steps);
//        RecipeManager recipeManager = new RecipeManager();
//        recipeManager.addRecipe(recipe);
//        System.out.println(recipe.toString());
//        System.out.println(recipeManager.toString());
//
//        viewRecipeController controller = loader.getController();
//        controller.setRecipe(recipe);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

        createRecipeController2 controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void viewPage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
        try {
            Parent root = loader.load();
            createRecipeController2 controller = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}