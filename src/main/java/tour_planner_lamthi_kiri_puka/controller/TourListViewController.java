package tour_planner_lamthi_kiri_puka.controller;

import com.tourplannerapp.model.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TourListViewController {
    @FXML
    private ListView<TourViewModel> tourListView;

    public ObservableList<TourViewModel> tourList = FXCollections.observableArrayList();

    private TabsPaneController detailsController;
    private GeneralTabController generalTabController;


    @FXML
    public void initialize() {
        tourListView.setItems(tourList);
        tourListView.setMinWidth(200);

        // Set the listener for selection changes in the tour list view
        tourListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showTourDetails(newValue);
            }
        });


    }


    public void deleteTour(TourViewModel tour) {
        int nextIndex = tourListView.getSelectionModel().getSelectedIndex();
        tourList.remove(tour);
        if (nextIndex != -1) {
            if (nextIndex < tourList.size()) {
                // The next tour moves up by one index after deletion, adjust selection accordingly.
                tourListView.getSelectionModel().select(nextIndex-1);
            } else if (nextIndex > 0) {
                // Adjusting for the shift in indices after deletion, ensuring the correct tour is selected.
                tourListView.getSelectionModel().select(nextIndex - 2);
            }
        } else {
            // Clear details if no selection could be made.
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

    public TourViewModel getSelectedTour() {
        return tourListView.getSelectionModel().getSelectedItem();
    }


    // This method is called when a tour is selected in the list
    private void showTourDetails(TourViewModel tour) {
        generalTabController.setSelectedTour(tour);
        generalTabController.displayTourDetails(tour);
        detailsController.selectGeneralTab();
        generalTabController.enableButtons();
    }

    public void selectAndDisplayTour(TourViewModel newTour) {
        // Select the newly added tour
        tourListView.getSelectionModel().select(newTour);

        // Manually invoke showing tour details if needed
        showTourDetails(newTour);

        // Ensure the GeneralTab is selected to display the details
        detailsController.selectGeneralTab();
    }


    // Method to set the GeneralTabController from MainViewController during initialization
    public void setGeneralTabController(GeneralTabController generalTabController) {
        this.generalTabController = generalTabController;
    }

    public void setTabsPaneController(TabsPaneController tabsPaneController) {
        this.detailsController = tabsPaneController;
    }

    public void addTour(TourViewModel newTour) {
        tourList.add(newTour);
    }


    @FXML
    private void handleAddTour() {
        detailsController.addNewTourTab();
        // Set properties or show dialog to gather information
        // ...


    }

    public void updateTour(TourViewModel updatedTour) {
        // Logic to update the tour in your data source and refresh the list view
        // For example, if you're using an ObservableList for your ListView, find the tour and update it
        int index = tourList.indexOf(updatedTour);
        if (index >= 0) {
            tourList.set(index, updatedTour);
        }
        // Refresh the ListView if necessary
        tourListView.refresh();
    }

}
