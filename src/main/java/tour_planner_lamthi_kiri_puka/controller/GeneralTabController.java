package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
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
    @FXML
    private Button logtour;

    private TourListViewController tourListController;
    @FXML private TabsPaneController detailsController;

    private TourViewModel selectedTour;

     @FXML private ImageView mapImageView;
    @FXML private AnchorPane mapAnchorPane;

    private static final Logger logger = LogManager.getLogger(GeneralTabController.class);

    @FXML
    public void initialize() {
        disableButtons();

        mapImageView.fitWidthProperty().bind(mapAnchorPane.widthProperty());
        mapImageView.fitHeightProperty().bind(mapAnchorPane.heightProperty());
    }

    @FXML
    private void handleDelete() {
        if (confirmDeletion()) {
            TourViewModel selectedTour = tourListController.getSelectedTour();

            if (selectedTour != null) {
                tourListController.deleteTour(selectedTour);
                tourListController.selectNextTour();
            }
        }
    }

    public void setDetailsController(TabsPaneController controller) {
        this.detailsController = controller;
    }

    public void setSelectedTour(TourViewModel selectedTour) {
        this.selectedTour = selectedTour;
        if (selectedTour != null && selectedTour.getImagePath() != null) {
            this.imageFilePath = selectedTour.getImagePath();
            updateMapImage(selectedTour.getImagePath());//construct a new Image(imagePath, true) to load it asynchronously into the ImageView.
        }
    }

    public void setTourListController(TourListViewController controller) {
        this.tourListController = controller;
    }

    private boolean confirmDeletion() {
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
        mapImageView.setImage(null);
        disableButtons();
    }

    public void enableButtons() {
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        logtour.setDisable(false);
    }

    public void disableButtons() {
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        logtour.setDisable(true);
    }

    @FXML
    private void handleEdit() {
        if (selectedTour != null) {
            logger.info("Selected Image Path on Edit Button click: " + imageFilePath);
            detailsController.openEditTourTab(selectedTour);
        }
    }

    @FXML
    private void handleAddLog() {
        if (detailsController != null) {
            TourViewModel selectedTour = detailsController.getTourListController().getSelectedTour();
            if (selectedTour != null) {
                detailsController.openNewLogTab(selectedTour);
            }
        }
    }

    public void displayTourDetails(TourViewModel tour) {
        tourNameLabel.setText(tour.getName());
        tourDescriptionLabel.setText(tour.getDescription());
        fromLabel.setText(tour.getOrigin());
        toLabel.setText(tour.getDestination());
        transportTypeLabel.setText(tour.getTransportType());
        updateMapImage(tour.getImagePath());
    }

    private void updateMapImage(String imagePath) {
        try {
            Image image = new Image(imagePath, true);
            image.exceptionProperty().addListener((observable, oldValue, exception) -> {
                if (exception != null) {
                    logger.error("Error loading image", exception);
                }
            });
            mapImageView.setImage(image);
        } catch (IllegalArgumentException e) {
            logger.error("Could not load image", e);
        }
    }
}
