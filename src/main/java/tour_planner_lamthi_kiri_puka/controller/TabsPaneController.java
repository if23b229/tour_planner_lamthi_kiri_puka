package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import tour_planner_lamthi_kiri_puka.model.TourLog;
import tour_planner_lamthi_kiri_puka.service.TourLogService;
import tour_planner_lamthi_kiri_puka.service.TourService;
import tour_planner_lamthi_kiri_puka.utils.SpringFxmlLoader;//utilsSprinFxmlLoader.java muss gemacht werden
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TabsPaneController {
    @Autowired
    private TourService tourService;

    @Autowired
    private TourLogService tourLogService;

    @Autowired
    private SpringFxmlLoader springFxmlLoader; // Add this line

    @FXML
    private TabPane detailsTabPane;

    private TourListViewController tourListController;
    private GeneralTabController generalTabController;
    private RouteTabController routeTabController;

    @FXML
    private Tab generalTab;

    public void initialize() {
        detailsTabPane.setMinHeight(200);
        loadGeneralTab();
    }

    public void setTourListController(TourListViewController controller) {
        this.tourListController = controller;
    }

    public TourListViewController getTourListController() {
        return tourListController;
    }

    public TabPane getDetailsTabPane() {
        return detailsTabPane;
    }

    public void selectGeneralTab() {
        detailsTabPane.getSelectionModel().select(generalTab);
    }

    private void loadGeneralTab() {
        try {
            FXMLLoader loader = springFxmlLoader.load("/fxml/GeneralTab.fxml"); // Use the instance method
            Node generalTabContent = loader.load();
            generalTabController = loader.getController();
            generalTab.setContent(generalTabContent);
        } catch (IOException e) {
            e.printStackTrace(); // Replace with proper logging
        }
    }

    public void addNewTourTab() {
        try {
            FXMLLoader loader = springFxmlLoader.load("/fxml/NewTourForm.fxml"); // Use the instance method
            //FXMLLoader loader = FXMLLoader.load(getClass(). getClassLoader(). getResource("fxml/NewTourForm.fxml"));
            Tab newTab = new Tab("New Tour", loader.load());
            NewTourFormController newTourFormController = loader.getController();
            newTourFormController.setTabsPaneController(this);
            detailsTabPane.getTabs().add(newTab);
            detailsTabPane.getSelectionModel().select(newTab);
        } catch (IOException e) {
            e.printStackTrace(); // Replace with proper logging
        }
    }

    public void openEditTourTab(TourViewModel tour) {
        try {
            FXMLLoader loader = springFxmlLoader.load("/fxml/NewTourForm.fxml"); // Use the instance method
            Tab editTab = new Tab("Edit Tour", loader.load());
            NewTourFormController editTourFormController = loader.getController();
            editTourFormController.setTourDataForEdit(tour);
            editTourFormController.setTabsPaneController(this);
            detailsTabPane.getTabs().add(editTab);
            detailsTabPane.getSelectionModel().select(editTab);
        } catch (IOException e) {
            e.printStackTrace(); // Replace with proper logging
        }
    }

    public void openLogTourTab(TourViewModel tour) {
        openLogTourTab(tour, null);
    }

    public void openLogTourTab(TourViewModel tour, TourLog log) {
        try {
            FXMLLoader loader = springFxmlLoader.load("/fxml/NewLogForm.fxml"); // Use the instance method
            Tab logTab = new Tab("Tour Log", loader.load());
            NewLogFormController newLogFormController = loader.getController();
            newLogFormController.loadLogEdit(log);
            newLogFormController.setTourId(tour.getId());
            newLogFormController.setTabPane(detailsTabPane);
            detailsTabPane.getTabs().add(logTab);
            detailsTabPane.getSelectionModel().select(logTab);
        } catch (IOException e) {
            e.printStackTrace(); // Replace with proper logging
        }
    }

    public void openNewLogTab(TourViewModel tour) {
        openLogTourTab(tour);
    }

    public void setGeneralTabController(GeneralTabController generalTabController) {
        this.generalTabController = generalTabController;
    }

    public GeneralTabController getGeneralTabController() {
        return generalTabController;
    }

    public void setRouteTabController(RouteTabController routeTabController) {
        this.routeTabController = routeTabController;
    }

    public RouteTabController getRouteTabController() {
        return routeTabController;
    }
}
