package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class TabsPaneController {
    @FXML
    public TabPane detailsTabPane;
    private TourListViewController tourListController;
    private GeneralTabController generalTabController; // Reference to the GeneralTabController
    @FXML
    public Tab generalTab;

    public void initialize() {
        detailsTabPane.setMinHeight(200);
        loadGeneralTab();
    }

    public void setTourListController(TourListViewController controller) {
        this.tourListController = controller;
    }

    public TabPane getDetailsTabPane() {
        return detailsTabPane;
    }

    public void selectGeneralTab() {
        // This will change the selection to the General tab
        detailsTabPane.getSelectionModel().select(generalTab);
    }

    private void loadGeneralTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GeneralTab.fxml"));
            // This will be the content for your generalTab
            Node generalTabContent = loader.load(); // Load the FXML and get the root node
            generalTabController = loader.getController(); // Get the controller
            generalTab.setContent(generalTabContent); // Set the content of the 'General' tab
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void addNewTourTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewTourForm.fxml"));
            Tab newTab = new Tab("New Tour", loader.load());
            NewTourFormController newTourFormController = loader.getController();
            newTourFormController.setTabPane(detailsTabPane);
            newTourFormController.setTourListController(tourListController);
            newTourFormController.setTabsPaneController(this);
            detailsTabPane.getTabs().add(newTab);
            detailsTabPane.getSelectionModel().select(newTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEditTourTab(TourViewModel tour) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewTourForm.fxml"));
            Tab editTab = new Tab("Edit Tour", loader.load());
            NewTourFormController editTourFormController = loader.getController();
            editTourFormController.setTabPane(detailsTabPane);
            editTourFormController.setTourListController(tourListController);
            editTourFormController.setTourDataForEdit(tour);
            editTourFormController.setTabsPaneController(this);
            detailsTabPane.getTabs().add(editTab);
            detailsTabPane.getSelectionModel().select(editTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Getter for the GeneralTabController
    public GeneralTabController getGeneralTabController() {
        return generalTabController;
    }

}
