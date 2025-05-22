package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourLog;
import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import tour_planner_lamthi_kiri_puka.service.TourLogService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TourLogsTabController {

    @FXML
    private TableView<TourLog> logsTableView;
    @FXML
    private TableColumn<TourLog, String> dateColumn;
    @FXML
    private TableColumn<TourLog, String> commentColumn;
    @FXML
    private TableColumn<TourLog, Integer> difficultyColumn;
    @FXML
    private TableColumn<TourLog, Double> totalDistanceColumn;
    @FXML
    private TableColumn<TourLog, Double> totalTimeColumn;
    @FXML
    private TableColumn<TourLog, Integer> ratingColumn;

    private final ObservableList<TourLog> logList = FXCollections.observableArrayList();

    @Autowired
    private TourLogService tourLogService;

    @FXML
    public void initialize() {
        // Initialize the columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        totalDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("totalDistance"));
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        logsTableView.setItems(logList);
    }

    public void setTour(TourViewModel tour) {
        if (tour != null) {
            List<TourLog> tourLogs = tourLogService.findByTourId(tour.getId());
            logList.setAll(tourLogs);
        } else {
            logList.clear();
        }
    }

    @FXML
    private void handleAddLog() {
        // Your code to handle adding a log
        System.out.println("Add Log button clicked");
        // Implement the logic for adding a log here
    }

    @FXML
    private void handleEditLog() {
        TourLog selectedLog = logsTableView.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            // Your code to handle editing the selected log
            System.out.println("Edit Log button clicked for log: " + selectedLog);
            // Implement the logic for editing a log here
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Log Selected");
            alert.setContentText("Please select a log in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteLog() {
        TourLog selectedLog = logsTableView.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Log");
            alert.setContentText("Are you sure you want to delete this log?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                tourLogService.deleteById(selectedLog.getId());
                logList.remove(selectedLog);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Log Selected");
            alert.setContentText("Please select a log in the table.");
            alert.showAndWait();
        }
    }
}

