package tour_planner_lamthi_kiri_puka.controller;

import com.tourplannerapp.model.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

import java.io.File;

public class NewTourFormController {
    @FXML public TextField nameField;
    @FXML public TextField descriptionField;
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private TextField transportTypeField;
    private String imageFilePath;
    private TabPane tabPane;
    private TourListViewController tourListController;

    private TabsPaneController tabsPaneController;

    private TourViewModel selectedTour;

    private MapSectionController mapSectionController;


    // This method allows injecting the main TabPane from the MainViewController
    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    // This method allows injecting the TourListViewController from the MainViewController
    public void setTourListController(TourListViewController tourListController) {
        this.tourListController = tourListController;
    }

    public void setTabsPaneController(TabsPaneController tabsPaneController) {
        this.tabsPaneController = tabsPaneController;
    }


    @FXML
    private void handleImageSelection() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageFilePath = selectedFile.toURI().toString();
            System.out.println("Image selected: " + imageFilePath);
        } else {
            System.out.println("No Image selected");
        }
    }

    @FXML
    public void handleSave() {
        System.out.println("Save button clicked");

        // Log the text field values
        System.out.println("Name: " + nameField.getText());
        System.out.println("Description: " + descriptionField.getText());
        System.out.println("From: " + fromField.getText());
        System.out.println("To: " + toField.getText());
        System.out.println("Transport Type: " + transportTypeField.getText());
        System.out.println("Selected Image Path: " + imageFilePath);

        if (selectedTour != null) {
            // Update the selectedTour with new data from the form
            selectedTour.setName(nameField.getText());
            selectedTour.setDescription(descriptionField.getText());
            selectedTour.setFrom(fromField.getText());
            selectedTour.setTo(toField.getText());
            selectedTour.setTransportType(transportTypeField.getText());
            // Only update the image path if a new one was selected
            if (imageFilePath != null && !imageFilePath.isEmpty()) {
                selectedTour.setImagePath(imageFilePath);
            }

            // Log existing image path before and after saving
            System.out.println("Existing tour image path before saving: " + selectedTour.getImagePath());

            // Update the tour list view to reflect the changes
            tourListController.updateTour(selectedTour);

            // Log the image path again to confirm it's saved
            System.out.println("Existing tour image path after saving: " + selectedTour.getImagePath());

            // Update the tour list view to reflect the changes
            tourListController.updateTour(selectedTour);
            closeCurrentTab();
            tourListController.selectAndDisplayTour(selectedTour);
        } else {
            // Create a new TourViewModel with data from text fields
            TourViewModel newTour = new TourViewModel(
                    nameField.getText(),
                    descriptionField.getText(),
                    fromField.getText(),
                    toField.getText(),
                    transportTypeField.getText(),
                    imageFilePath // Make sure this variable is properly initialized after image selection
            );

            // Add the new tour to the list in TourListViewController and close the tab
            tourListController.addTour(newTour);
            closeCurrentTab();
            tourListController.selectAndDisplayTour(newTour);
        }
    }


    private void closeCurrentTab() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab != null) {
            tabPane.getTabs().remove(currentTab);
        }
    }



    public void setTourDataForEdit(TourViewModel tour) {
        selectedTour = tour; // Ensure you have a selectedTour member variable
        nameField.setText(tour.getName());
        descriptionField.setText(tour.getDescription());
        fromField.setText(tour.getFrom());
        toField.setText(tour.getTo());
        transportTypeField.setText(tour.getTransportType());
        imageFilePath = tour.getImagePath();
    }

    @FXML
    private void handleCancel() {
        // Logic to close the tab without saving
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
    }
}
