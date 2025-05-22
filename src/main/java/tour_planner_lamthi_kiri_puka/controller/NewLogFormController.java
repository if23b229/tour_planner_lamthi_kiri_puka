package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourLog;
import tour_planner_lamthi_kiri_puka.service.TourLogService;
import tour_planner_lamthi_kiri_puka.service.TourService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NewLogFormController {

    @FXML
    private DatePicker logDatePicker;
    @FXML
    private TextArea commentField;
    @FXML
    private TextField difficultyField;
    @FXML
    private TextField totalDistanceField;
    @FXML
    private TextField totalTimeField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField logDetailsField;

    @Autowired
    private TourLogService tourLogService;
    @Autowired
    private TourService tourService;

    private Long editLogId;
    private Long tourId;

    private TabPane tabPane;

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    @FXML
    private void handleSave() {
        if (!validateInputs()) {
            return;
        }

        TourLog newLog = new TourLog();
        newLog.setTour(tourService.findById(tourId).orElseThrow(() -> new IllegalArgumentException("Invalid Tour ID")));
        newLog.setLogDate(logDatePicker.getValue().toString());
        newLog.setComment(commentField.getText());
        newLog.setDifficulty(difficultyField.getText());
        newLog.setTotalDistance(totalDistanceField.getText());
        newLog.setTotalTime(totalTimeField.getText());
        newLog.setRating(ratingField.getText());
        newLog.setLogDetails(logDetailsField.getText());

        if (editLogId != null) {
            newLog.setId(editLogId);
            tourLogService.updateLog(newLog);
        } else {
            tourLogService.saveLog(newLog);
        }

        closeCurrentTab();
    }

    public void loadLogEdit(TourLog log) {
        if (log != null) {
            this.editLogId = log.getId();
            logDatePicker.setValue(LocalDate.parse(log.getLogDate()));
            commentField.setText(log.getComment());
            difficultyField.setText(log.getDifficulty());
            totalDistanceField.setText(log.getTotalDistance());
            totalTimeField.setText(log.getTotalTime());
            ratingField.setText(log.getRating());
            logDetailsField.setText(log.getLogDetails());
        } else {
            this.editLogId = null;
            logDatePicker.setValue(LocalDate.now());
        }
    }

    private void closeCurrentTab() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab != null) {
            tabPane.getTabs().remove(currentTab);
        }
    }

    @FXML
    private void handleCancel() {
        closeCurrentTab();
    }

    @FXML
    private boolean validateInputs() {
        String errorMessage = "";

        if (logDatePicker.getValue() == null) {
            errorMessage += "No valid log date!\n";
        }
        if (commentField.getText() == null || commentField.getText().isEmpty()) {
            errorMessage += "No valid comment!\n";
        }
        if (difficultyField.getText() == null || difficultyField.getText().isEmpty()) {
            errorMessage += "No valid difficulty!\n";
        }
        if (totalDistanceField.getText() == null || totalDistanceField.getText().isEmpty()) {
            errorMessage += "No valid total distance!\n";
        }
        if (totalTimeField.getText() == null || totalTimeField.getText().isEmpty()) {
            errorMessage += "No valid total time!\n";
        }
        if (ratingField.getText() == null || ratingField.getText().isEmpty()) {
            errorMessage += "No valid rating!\n";
        }
        if (logDetailsField.getText() == null || logDetailsField.getText().isEmpty()) {
            errorMessage += "No valid log details!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}