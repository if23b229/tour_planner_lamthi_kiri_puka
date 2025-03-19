package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class GeneralTabController {

    @FXML private String imageFilePath;

    @FXML
    private Label tourNameLabel;
    @FXML
    private Label tourDescriptionLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label transportTypeLabel;

    @FXML private Button deleteButton;
    @FXML
    private Button editButton;

    private TourListViewController tourListController;
    @FXML private TabsPaneController detailsController;

    private TourViewModel selectedTour;
    private MapSectionController mapSectionController;


    @FXML
    public void initialize() {
        // Disable buttons at start
        disableButtons();
    }


    @FXML
    private void handleDelete() {
        if (confirmDeletion()) {
            // Assuming tourListController is already injected and initialized properly
            TourViewModel selectedTour = tourListController.getSelectedTour(); // This method needs to be created in TourListViewController

            if (selectedTour != null) {
                tourListController.deleteTour(selectedTour); // This method also needs to be created or made public in TourListViewController
                tourListController.selectNextTour(); // Make sure this method is public in TourListViewController
            }
        }
    }

    public void setDetailsController(TabsPaneController controller) {
        this.detailsController = controller;
    }

    public void setMapSectionController(MapSectionController mapSectionController) {
        this.mapSectionController = mapSectionController;
    }

    public void setSelectedTour(TourViewModel selectedTour) {
        this.selectedTour = selectedTour;
    }

    public void setTourListController(TourListViewController controller) {
        this.tourListController = controller;
    }

    private boolean confirmDeletion() {
        // Show confirmation dialog here and return true if user confirms
        // This is a simple placeholder for the dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this tour?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }

    public void clearDetails() {
        tourNameLabel.setText("");
        tourDescriptionLabel.setText("");
        fromLabel.setText("");
        toLabel.setText("");
        transportTypeLabel.setText("");
        // Disable the edit and delete buttons
        disableButtons();

    }
    // Call this method when a tour is selected to enable the buttons
    public void enableButtons() {
        editButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    // Call this method when a tour is selected to enable the buttons
    public void disableButtons() {
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }


    @FXML
    private void handleEdit() {
        if (selectedTour != null) {
            System.out.println("Selected Image Path on Edit Button click: " + imageFilePath);
            detailsController.openEditTourTab(selectedTour);
        }
    }

    public void displayTourDetails(TourViewModel tour) {
        tourNameLabel.setText(tour.getName());
        tourDescriptionLabel.setText(tour.getDescription());
        fromLabel.setText(tour.getFrom());
        toLabel.setText(tour.getTo());
        transportTypeLabel.setText(tour.getTransportType());
        if (mapSectionController != null && tour.getImagePath() != null) {
            mapSectionController.updateMapImage(tour.getImagePath());
        }
    }
}

