package tour_planner_lamthi_kiri_puka.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainViewController {
    @FXML private BorderPane mainBorderPane;
    @FXML private TourListViewController tourListController;
    @FXML private TabsPaneController detailsController;

    @FXML private SplitPane mainSplitPane;
    @FXML
    private GeneralTabController generalTabController;


    @FXML
    public void initialize() {
        //For save new tour
        detailsController.setTourListController(tourListController);


        assert tourListController != null : "fx:id=\"tourList\" was not injected: check your FXML file 'MainView.fxml'.";
        assert detailsController != null : "fx:id=\"details\" was not injected: check your FXML file 'MainView.fxml'.";


        // Pass the reference of GeneralTabController to TourListViewController
        tourListController.setTabsPaneController(detailsController);
        GeneralTabController generalTab = detailsController.getGeneralTabController();
        tourListController.setGeneralTabController(generalTab);



        // Set initial divider position
        mainSplitPane.setDividerPositions(0.3);

        // Resizing for mainSpliPpane
        mainSplitPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            // Keep the divider at 30% of the width of the SplitPane.
            mainSplitPane.setDividerPositions(0.3);
        });
        setupControllers();
    }



    public void initializeWithStage(Stage stage) {
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Adjust layout if necessary
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            // Adjust layout if necessary
        });
    }


    private void setupControllers() {
        // Assuming 'tabsPaneController' and 'tourListViewController' are already loaded from FXML
        GeneralTabController generalTabController = detailsController.getGeneralTabController();
        tourListController.setGeneralTabController(generalTabController);
        generalTabController.setTourListController(tourListController);
        generalTabController.setDetailsController(detailsController);
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Tour Planner");
        alert.setHeaderText("About Tour Planner Application");
        alert.setContentText("This application is designed to help plan and organize tours efficiently.");
        alert.showAndWait();
    }
}
