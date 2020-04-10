package app.controllers;

import app.enums.MealType;
import app.models.Instruction;
import app.models.Recipe;
import app.models.RecipeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label lblMessage;

    @FXML
    private Button btnAddRecipe;

    @FXML
    private ListView listAllRecipes;

    @FXML
    private ListView listFavRecipes;

    @FXML
    private TextField txtSearchAll;

    @FXML
    private TextField txtSearchFav;

    private ObservableList<Recipe> recipeAllObservableList;
    private ObservableList<Recipe> recipeFavObservableList;

    private FilteredList<Recipe> filteredFavRecipes;
    private FilteredList<Recipe> filteredAllRecipes;

    private SortedList<Recipe> sortedAllRecipes;
    private SortedList<Recipe> sortedFavRecipes;

    private FXMLLoader mLLoader;

    public MainController() {

    }

    @FXML
    private void initialize() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initViewElements();
    }

    private void initViewElements() {
        setInfoMessage("Welcome to COOK! - " + RecipeManager.getInstance().getRecipes().size() + " Recipes loaded");
        setListViewContextMenu(listAllRecipes);
        setListViewContextMenu(listFavRecipes);
        fillListViews();
        addSearchListener();
    }

    private void addSearchListener() {
        filteredAllRecipes = new FilteredList<Recipe>(FXCollections.<Recipe>observableArrayList(RecipeManager.getInstance().getRecipes()));

        txtSearchAll.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAllRecipes.setPredicate(recipe ->{

                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(recipe.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if(recipe.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (recipe.getPrepTime().toString().contains(lowerCaseFilter)) {
                    return true;
                } else if (recipe.getCookTime().toString().contains(lowerCaseFilter)) {
                    return true;
                } else if (recipe.getType().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        sortedAllRecipes = new SortedList<>(filteredAllRecipes);
        listAllRecipes.setItems(sortedAllRecipes);

        FilteredList<Recipe> filteredFavRecipes = new FilteredList<Recipe>(FXCollections.<Recipe>observableArrayList(RecipeManager.getInstance().getFavRecipes()));

        txtSearchFav.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredFavRecipes.setPredicate(recipe ->{

                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(recipe.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if(recipe.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (recipe.getPrepTime().toString().contains(lowerCaseFilter)) {
                    return true;
                } else if (recipe.getCookTime().toString().contains(lowerCaseFilter)) {
                    return true;
                } else if (recipe.getType().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        sortedFavRecipes = new SortedList<>(filteredFavRecipes);
        listFavRecipes.setItems(sortedFavRecipes);
    }

    private void fillListViews() {
        listAllRecipes.setCellFactory(recipeListView -> new RecipeListViewCell());
        listFavRecipes.setCellFactory(recipeListView -> new RecipeListViewCell());
    }

    private void setListViewContextMenu(ListView lv){

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");

        editItem.setOnAction((event) -> {
            openEditView((Recipe) lv.getSelectionModel().getSelectedItem());
        });

        deleteItem.setOnAction((event) -> {
            Recipe r = (Recipe) lv.getSelectionModel().getSelectedItem();
            RecipeManager.getInstance().deleteRecipe(r);
            fillListViews();
        });
        contextMenu.getItems().addAll(editItem,deleteItem);

        lv.setContextMenu(contextMenu);

    }

    private void setInfoMessage(String msg) {
        lblMessage.setStyle("-fx-background-color:yellow; -fx-text-fill:black;");
        lblMessage.setText("INFO --> " + msg);
    }

    private void setErrorMessage(String msg) {
        lblMessage.setStyle("-fx-background-color:red; -fx-text-fill:white;");
        lblMessage.setText("FAILURE --> " + msg);
    }

    private void setSuccessMessage(String msg) {
        lblMessage.setStyle("-fx-background-color:green; -fx-text-fill:white;");
        lblMessage.setText("SUCCESS --> " + msg);
    }

    private void updateView() {
        addSearchListener();    //TODO maybe other solution than adding the listener again :-)
        setSuccessMessage("Data updated");
    }

    public void onActionbtnAddRecipe(ActionEvent actionEvent) {
        setInfoMessage("ADD window is open!");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/views/editRecipe.fxml"));
        Parent rootAddView = null;
        try {
            AddRecipeController addRecipeController = new AddRecipeController();

            fxmlLoader.setController(addRecipeController);
            rootAddView = (Parent) fxmlLoader.load();
            Stage stageAddView = new Stage();
            stageAddView.initModality(Modality.APPLICATION_MODAL);
            stageAddView.setTitle("ADD RECIPE");
            stageAddView.setResizable(false);
            stageAddView.setScene(new Scene(rootAddView));

            stageAddView.setOnHidden((WindowEvent event1) -> {
                updateView();
            });

            stageAddView.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openEditView(Recipe r) {
        setInfoMessage("EDIT window is open!");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/views/editRecipe.fxml"));
        Parent rootEditView = null;
        try {
            EditRecipeController editRecipeController = new EditRecipeController(r);

            fxmlLoader.setController(editRecipeController);
            rootEditView = (Parent) fxmlLoader.load();
            Stage stageEditView = new Stage();
            stageEditView.initModality(Modality.APPLICATION_MODAL);
            stageEditView.setTitle("EDIT RECIPE");
            stageEditView.setResizable(false);
            stageEditView.setScene(new Scene(rootEditView));

            stageEditView.setOnHidden((WindowEvent event1) -> {
                updateView();
            });

            stageEditView.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printData() {
        for (Recipe r : RecipeManager.getInstance().getRecipes()) {
            System.out.println("====== [" + r.getName() + "] ======");
            System.out.println(r);

            for (Instruction i : r.getCookInstructions()) {
                System.out.println(i);
            }

            System.out.println("=======================");
        }
    }
}
