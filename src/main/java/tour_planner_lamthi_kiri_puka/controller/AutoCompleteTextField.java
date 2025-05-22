//Die Klasse OpenRouteService muss erstellt und programmiert werden
package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.service.OpenRouteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.List;

public class AutoCompleteTextField extends TextField {
    private OpenRouteService openRouteService;
    private final ObservableList<String> suggestions = FXCollections.observableArrayList();
    private final ListView<String> suggestionList = new ListView<>(suggestions);
    private final Popup suggestionPopup = new Popup();
    private boolean userInput = false;

    public AutoCompleteTextField() {
        setupListeners();
    }

    public void setOpenRouteService(OpenRouteService openRouteService) {
        this.openRouteService = openRouteService;
    }

    private void setupListeners() {
        focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                suggestionPopup.hide();
            }
        });

        textProperty().addListener((observable, oldText, newText) -> {
            if (userInput && newText.length() > 1) {
                fetchSuggestions(newText);
            } else {
                suggestions.clear();
                suggestionPopup.hide();
            }
        });

        suggestionList.setOnMouseClicked(event -> {
            setText(suggestionList.getSelectionModel().getSelectedItem());
            suggestionPopup.hide();
        });

        this.setOnKeyPressed(event -> userInput = true);
        this.setOnMouseClicked(event -> userInput = true);
    }

    private void fetchSuggestions(String query) {
        try {
            List<String> results = openRouteService.getSuggestions(query);
            suggestions.setAll(results);
            if (!suggestions.isEmpty()) {
                suggestionPopup.getContent().clear();
                suggestionPopup.getContent().add(new VBox(suggestionList));

                // Calculate the position relative to the text field
                Point2D p = localToScreen(0, getHeight());

                // Position the popup directly below the text field
                suggestionPopup.show(this, p.getX(), p.getY());
            } else {
                suggestionPopup.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
            suggestionPopup.hide();
            showErrorPopup("Unable to fetch suggestions");
        }
    }

    private void showErrorPopup(String message) {
        Stage errorStage = new Stage();
        VBox root = new VBox();
        root.getChildren().add(new javafx.scene.control.Label(message));
        javafx.scene.Scene scene = new javafx.scene.Scene(root, 300, 100);
        errorStage.setScene(scene);
        errorStage.setTitle("Error");
        errorStage.show();
    }

    public void setTextFromController(String text) {
        userInput = false;
        setText(text);
    }
}
