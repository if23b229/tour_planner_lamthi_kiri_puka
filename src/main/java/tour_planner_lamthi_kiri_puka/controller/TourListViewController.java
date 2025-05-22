package tour_planner_lamthi_kiri_puka.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import tour_planner_lamthi_kiri_puka.model.Tour;
import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import tour_planner_lamthi_kiri_puka.service.TourService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class TourListViewController {
    private static final String DEFAULT_IMAGE_URL = "C:\\Users\\nerto\\Pictures\\Screenshots";
    private static final Logger logger = LogManager.getLogger(TourListViewController.class);

    @FXML
    private ListView<TourViewModel> tourListView;

    private final ObservableList<TourViewModel> tourList = FXCollections.observableArrayList();
    private final ObjectProperty<TourViewModel> selectedTour = new SimpleObjectProperty<>();

    @Autowired
    private TourService tourService;

    private TabsPaneController detailsController;
    private GeneralTabController generalTabController;
    private RouteTabController routeTabController;

    @FXML
    public void initialize() {
        // This method is called by JavaFX after the FXML fields are injected
        loadTours();

        tourListView.setItems(tourList);
        tourListView.setMinWidth(200);

        tourListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTour.set(newValue);
                showTourDetails(newValue);
            }
        });
    }

    private void loadTours() {
        Platform.runLater(() -> {
            List<Tour> tours = tourService.findAll();
            for (Tour tour : tours) {
                String imagePath = resolveImagePath(tour.getImagePath());
                logger.info("Loading tour: " + tour.getName() + ", Image Path: " + imagePath);
                TourViewModel tourViewModel = new TourViewModel(
                        tour.getName(),
                        tour.getDescription(),
                        tour.getOrigin(),
                        tour.getDestination(),
                        tour.getTransportType(),
                        imagePath
                );
                tourViewModel.setId(tour.getId()); // Set ID in TourViewModel
                tourList.add(tourViewModel);
            }
        });
    }

    private String resolveImagePath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty() || !imagePath.startsWith("file:") && !imagePath.startsWith("http")) {
            logger.info("Using default image path: " + DEFAULT_IMAGE_URL);
            return DEFAULT_IMAGE_URL;
        }
        return imagePath;
    }

    public void deleteTour(TourViewModel tourViewModel) {
        int nextIndex = tourListView.getSelectionModel().getSelectedIndex();
        tourService.deleteById(tourViewModel.getId()); // Delete from the database
        tourList.remove(tourViewModel); // Remove from the UI list
        if (nextIndex != -1) {
            if (nextIndex < tourList.size()) {
                // The next tour moves up by one index after deletion.
                tourListView.getSelectionModel().select(nextIndex - 1);
            } else if (nextIndex > 0) {
                // Adjusting for the shift in indices after deletion so that the correct tour is selected.
                tourListView.getSelectionModel().select(nextIndex - 2);
            }
        } else {
            // Clear details if no selection.
            generalTabController.clearDetails();
        }
    }

    public void selectNextTour() {
        int currentIndex = tourListView.getSelectionModel().getSelectedIndex();
        if (currentIndex < tourList.size() - 1) {
            tourListView.getSelectionModel().selectNext();
        } else if (!tourList.isEmpty()) {
            tourListView.getSelectionModel().selectLast();
        } else {
            generalTabController.clearDetails();
        }
    }

    public ObjectProperty<TourViewModel> selectedTourProperty() {
        return selectedTour;
    }

    public TourViewModel getSelectedTour() {
        return selectedTour.get();
    }

    private void showTourDetails(TourViewModel tour) {
        if (generalTabController != null) {
            generalTabController.setSelectedTour(tour);
            generalTabController.displayTourDetails(tour);
            if (detailsController != null) {
                detailsController.selectGeneralTab();
            }
            generalTabController.enableButtons();
        }
        if (routeTabController != null) {
            routeTabController.displayRoute(tour.getOrigin(), tour.getDestination());
        }
    }

    public void selectAndDisplayTour(TourViewModel newTour) {
        tourListView.getSelectionModel().select(newTour);
        showTourDetails(newTour);
        if (detailsController != null) {
            detailsController.selectGeneralTab();
        }
    }

    public void setGeneralTabController(GeneralTabController generalTabController) {
        this.generalTabController = generalTabController;
    }

    public void setRouteTabController(RouteTabController routeTabController) {
        this.routeTabController = routeTabController;
    }

    public void setTabsPaneController(TabsPaneController tabsPaneController) {
        this.detailsController = tabsPaneController;
    }

    public void addTour(TourViewModel newTour) {
        // Use default image if none is provided
        String imagePath = resolveImagePath(newTour.getImagePath());
        logger.info("Adding new tour: " + newTour.getName() + ", Image Path: " + imagePath);
        Tour tour = new Tour(newTour.getName(), newTour.getDescription(), newTour.getOrigin(), newTour.getDestination(), newTour.getTransportType(), imagePath);
        tourService.save(tour);
        newTour.setId(tour.getId()); // Set ID in TourViewModel after saving
        newTour.setImagePath(imagePath); // Update image path in TourViewModel
        tourList.add(newTour);
        refreshListView(); // Add this line to refresh the list view
    }

    public void updateTour(TourViewModel updatedTour) {
        int index = tourList.indexOf(updatedTour);
        if (index >= 0) {             tourList.set(index, updatedTour);
        }
        tourListView.refresh();
        Tour tour = tourService.findById(updatedTour.getId()).orElse(null);
        if (tour != null) {
            tour.setName(updatedTour.getName());
            tour.setDescription(updatedTour.getDescription());
            tour.setOrigin(updatedTour.getOrigin());
            tour.setDestination(updatedTour.getDestination());
            tour.setTransportType(updatedTour.getTransportType());
            // Use default image if none is provided
            String imagePath = resolveImagePath(updatedTour.getImagePath());
            logger.info("Updating tour: " + updatedTour.getName() + ", Image Path: " + imagePath);
            tour.setImagePath(imagePath);
            tourService.save(tour);
        }
    }

    @FXML
    private void handleAddTour() {
        if (detailsController != null) {
            detailsController.addNewTourTab();
        }
    }

    public void refreshListView() {
        tourListView.setItems(null);
        tourListView.setItems(tourList);
    }

    @FXML
    private void handleDeleteTour() {
        TourViewModel selectedTour = getSelectedTour();
        if (selectedTour != null) {
            deleteTour(selectedTour);
        }
    }
}
