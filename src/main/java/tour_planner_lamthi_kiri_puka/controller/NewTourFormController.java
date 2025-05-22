package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import tour_planner_lamthi_kiri_puka.service.OpenRouteService;
import tour_planner_lamthi_kiri_puka.service.TourService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class NewTourFormController {

    @FXML
    public TextField nameField;
    @FXML
    public TextField descriptionField;
    @FXML
    private AutoCompleteTextField fromField;
    @FXML
    private AutoCompleteTextField toField;
    @FXML
    private ComboBox<String> transportTypeField; // Use ComboBox for transport type
    @FXML
    private Button uploadImageButton;
    @FXML
    private ImageView imagePreview;
    @FXML
    private AnchorPane imagePreviewPane;

    private String imageFilePath;
    private TabPane tabPane;
    private TourListViewController tourListController;
    private TabsPaneController tabsPaneController;
    private TourViewModel selectedTour;
    private OpenRouteService openRouteService;
    private TourService tourService;

    private static final Logger logger = LogManager.getLogger(NewTourFormController.class);

    // No-argument constructor
    public NewTourFormController() {}

    // Setter for OpenRouteService
    public void setOpenRouteService(OpenRouteService openRouteService) {
        this.openRouteService = openRouteService;
        initializeAutoCompleteFields();
    }

    // Setter for TourService
    public void setTourService(TourService tourService) {
        this.tourService = tourService;
    }

    @FXML
    public void initialize() {
        imagePreview.fitWidthProperty().bind(imagePreviewPane.widthProperty());
        imagePreview.fitHeightProperty().bind(imagePreviewPane.heightProperty());
        initializeTransportTypeField(); // Initialize transport type ComboBox
    }

    private void initializeAutoCompleteFields() {
        fromField.setOpenRouteService(openRouteService);
        toField.setOpenRouteService(openRouteService);
    }


    private void initializeTransportTypeField() {
        transportTypeField.getItems().addAll("Car", "Train", "Bus", "Bike", "Walk", "Tram", "Subway", "Boat",
                "Scooter", "Motorbike", "Taxi", "Rideshare (e.g., Uber, Lyft)");
        transportTypeField.setPrefWidth(nameField.getPrefWidth());
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTourListController(TourListViewController tourListController) {
        this.tourListController = tourListController;
    }

    public void setTabsPaneController(TabsPaneController tabsPaneController) {
        this.tabsPaneController = tabsPaneController;
    }

    @FXML
    private void handleImageSelection() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageFilePath = selectedFile.toURI().toString();
            Image image = new Image(imageFilePath);
            imagePreview.setImage(image);
            logger.info("Image selected: " + imageFilePath);
        } else {
            logger.info("No Image selected");
        }
    }

    @FXML
    public void handleSave() {
        logger.info("Save button clicked");

        if (!validateInput()) {
            return; // If validation fails, do not proceed with saving
        }

        // Log the text field values
        logger.info("Name: " + nameField.getText());
        logger.info("Description: " + descriptionField.getText());
        logger.info("From: " + fromField.getText());
        logger.info("To: " + toField.getText());
        logger.info("Transport Type: " + transportTypeField.getValue());
        logger.info("Selected Image Path: " + imageFilePath);

        if (selectedTour != null) {
            selectedTour.setName(nameField.getText());
            selectedTour.setDescription(descriptionField.getText());
            selectedTour.setOrigin(fromField.getText());
            selectedTour.setDestination(toField.getText());
            selectedTour.setTransportType(transportTypeField.getValue());
            if (imageFilePath != null && !imageFilePath.isEmpty()) {
                selectedTour.setImagePath(imageFilePath);
            }
            tourListController.updateTour(selectedTour);
            closeCurrentTab();
            tourListController.selectAndDisplayTour(selectedTour);
        } else {
            TourViewModel newTour = new TourViewModel(
                    nameField.getText(),
                    descriptionField.getText(),
                    fromField.getText(),
                    toField.getText(),
                    transportTypeField.getValue(),
                    imageFilePath
            );
            tourListController.addTour(newTour);
            closeCurrentTab();
            tourListController.selectAndDisplayTour(newTour);
        }
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage.append("Name is required.\n");
        }
        if (descriptionField.getText() == null || descriptionField.getText().trim().isEmpty()) {
            errorMessage.append("Description is required.\n");
        }
        if (fromField.getText() == null || fromField.getText().trim().isEmpty()) {
            errorMessage.append("Origin is required.\n");
        }
        if (toField.getText() == null || toField.getText().trim().isEmpty()) {
            errorMessage.append("Destination is required.\n");
        }
        if (transportTypeField.getValue() == null || transportTypeField.getValue().trim().isEmpty()) {
            errorMessage.append("Transport type is required.\n");
        }

        if (errorMessage.length() > 0) {
            showAlert("Invalid Input", errorMessage.toString());
            return false;
        }

        try {
            boolean isFromValid = openRouteService.isValidLocation(fromField.getText());
            boolean isToValid = openRouteService.isValidLocation(toField.getText());
            logger.info("Validation results - From: " + isFromValid + ", To: " + isToValid);

            if (!isFromValid || !isToValid) {
                String invalidLocations = "";
                if (!isFromValid) {
                    invalidLocations += "Origin location is not recognized.\n";
                }
                if (!isToValid) {
                    invalidLocations += "Destination location is not recognized.\n";
                }
                showAlert("Invalid Locations", invalidLocations);
                return false;
            }
        } catch (IOException e) {
            logger.error("An error occurred while validating locations", e);
            showAlert("Error", "An error occurred while validating locations: " + e.getMessage());
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeCurrentTab() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab != null) {
            tabPane.getTabs().remove(currentTab);
        }
    }

    public void setTourDataForEdit(TourViewModel tour) {
        selectedTour = tour;
        nameField.setText(tour.getName());
        descriptionField.setText(tour.getDescription());
        fromField.setText(tour.getOrigin());
        toField.setText(tour.getDestination());
        transportTypeField.setValue(tour.getTransportType());
        imageFilePath = tour.getImagePath();
        if (imageFilePath != null) {
            Image image = new Image(imageFilePath);
            imagePreview.setImage(image);
        }
    }

    @FXML
    private void handleCancel() {
        closeCurrentTab();
    }
}
