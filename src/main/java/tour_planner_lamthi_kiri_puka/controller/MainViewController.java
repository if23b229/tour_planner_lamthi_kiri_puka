package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import tour_planner_lamthi_kiri_puka.service.ReportService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class MainViewController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TourListViewController tourListController;
    @FXML
    private TabsPaneController detailsController;
    @FXML
    private SplitPane mainSplitPane;

    @Autowired
    private ReportService reportService;

    @Autowired
    private GeneralTabController generalTabController;
    @Autowired
    private RouteTabController routeTabController;
    @Autowired
    private TourLogsTabController tourLogsTabController;

    @FXML
    public void initialize() {
        // For saving new tour
        detailsController.setTourListController(tourListController);

        assert tourListController != null : "fx:id=\"tourList\" was not injected: check FXML file 'MainView.fxml'.";
        assert detailsController != null : "fx:id=\"details\" was not injected: check FXML file 'MainView.fxml'.";

        // Pass the reference of GeneralTabController to TourListViewController
        tourListController.setTabsPaneController(detailsController);
        GeneralTabController generalTab = detailsController.getGeneralTabController();
        tourListController.setGeneralTabController(generalTab);
        tourListController.setRouteTabController(routeTabController);

        // Set initial divider position
        mainSplitPane.setDividerPositions(0.3);

        // Resizing for mainSplitPane
        mainSplitPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            // Keep the divider at 30% of the width of the SplitPane.
            mainSplitPane.setDividerPositions(0.3);
        });

        setupControllers();
    }

    public void initializeWithStage(Stage stage) {
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {});
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {});
    }

    private void setupControllers() {
        GeneralTabController generalTabController = detailsController.getGeneralTabController();
        tourListController.setGeneralTabController(generalTabController);
        generalTabController.setTourListController(tourListController);
        generalTabController.setDetailsController(detailsController);

        // Set up TourLogsTabController
        tourListController.selectedTourProperty().addListener((obs, oldTour, newTour) -> {
            tourLogsTabController.setTour(newTour);
        });
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

    @FXML
    private void handleGenerateTourReport() {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                TourViewModel selectedTour = tourListController.getSelectedTour();
                if (selectedTour != null) {
                    Long tourId = selectedTour.getId();
                    reportService.generateTourReport(tourId, file.getAbsolutePath());
                    showAlert("Success", "Report generated successfully.");
                } else {
                    showAlert("Error", "No tour selected.");
                }
            } catch (FileNotFoundException e) {
                showAlert("Error", "File not found: " + e.getMessage());
            } catch (Exception e) {
                showAlert("Error", "Failed to generate report: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleGenerateSummaryReport() {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                reportService.generateSummaryReport(file.getAbsolutePath());
                showAlert("Success", "Summary report generated successfully.");
            } catch (FileNotFoundException e) {
                showAlert("Error", "File not found: " + e.getMessage());
            } catch (Exception e) {
                showAlert("Error", "Failed to generate summary report: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
