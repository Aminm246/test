package edu.metrostate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class retiredToBeDeletedCreateRecipeController {
    @FXML private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private javafx.scene.control.TextField recipeName;
    @FXML private TextField recipeTags;
    @FXML private TextField ingredientQty;
    @FXML private TextField ingredientName;
    @FXML private TextArea instructionStepDesc;
    @FXML private TextField recipeImagePath;
    @FXML private TextField recipeDuration;
    @FXML private TextField recipeServingSize;
    @FXML private TextField instructionStepNum;
    @FXML private TextArea recipeDescription;

    /*
    @FXML private TextField tag1Input, tag2Input, tag3Input, tag4Input, tag5Input;
    @FXML private Button tag1Submit, tag2Submit, tag3Submit, tag4Submit, tag5Submit, tagsSubmit;

    private List<String> tagList = new ArrayList<>();
    */

    public void switchToViewRecipeList(javafx.event.ActionEvent e) throws IOException {
        /*Parent root = FXMLLoader.load(getClass().getResource("recipeListView.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        stage = (Stage) recipeName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipeListView.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    /*
    public void createButton() {
        if(recipeName.getCharacters().toString() =="") {
            System.out.println("empty");
        } else {
            System.out.println(recipeName.getCharacters().toString());
            // Print tags
            if (!tagList.isEmpty()) {
                System.out.println("Tags: " + String.join(", ", tagList));
            } else {
                System.out.println("No tags added");
            }
        }
    }


    public void cancelButton(){
        tag1Input.clear();
        tag2Input.clear();
        tag3Input.clear();
        tag4Input.clear();
        tag5Input.clear();
        tagList.clear();
    }

    public void ingredientAddButton(){

    }

    public void instructionStepAddButton(){

    }
    @FXML
    public void tag1Submit() {
        addTag(tag1Input);
    }
    @FXML
    public void tag2Submit() {
        addTag(tag2Input);
    }
    @FXML
    public void tag3Submit() {
        addTag(tag3Input);
    }
    @FXML
    public void tag4Submit() {
        addTag(tag4Input);
    }
    @FXML
    public void tag5Submit() {
        addTag(tag5Input);
    }
    @FXML
    public void tagsSubmit() {
        for (TextField tagInput : List.of(tag1Input, tag2Input, tag3Input, tag4Input, tag5Input)) {
            if (!tagInput.isDisabled() && !tagInput.getText().trim().isEmpty()) {
                addTag(tagInput);
            }
        }
        System.out.println("All tags submitted: " + tagList);
    }

    private void addTag(TextField tagInput) {
        String tag = tagInput.getText().trim();
        if (!tag.isEmpty() && !tagList.contains(tag)) {
            tagList.add(tag);
            tagInput.setDisable(true);
            System.out.println("Added tag: " + tag);
        }
    }

     */
}
