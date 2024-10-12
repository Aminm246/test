package edu.metrostate;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class MainToolbar extends ToolBar {

    Button recipeButton;
    Button ingredientsButton;
    Button suggestionButton;
    Button calorieButton;
    Button favoritesButton;

    public MainToolbar() {
        recipeButton = new Button("Recipes");
        ingredientsButton = new Button("Ingredients");
        suggestionButton = new Button("Recipe Suggestions");
        calorieButton = new Button("Calorie Tracker");
        favoritesButton = new Button("Favorites");

        recipeButton.getStyleClass().add("navbar");
        ingredientsButton.getStyleClass().add("navbar");
        suggestionButton.getStyleClass().add("navbar");
        calorieButton.getStyleClass().add("navbar");
        favoritesButton.getStyleClass().add("navbar");

        getItems()
                .addAll(recipeButton, ingredientsButton,suggestionButton,calorieButton,favoritesButton);
    }
}
