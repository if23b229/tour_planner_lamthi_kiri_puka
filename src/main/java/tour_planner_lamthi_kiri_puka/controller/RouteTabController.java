package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.service.OpenRouteService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

@Controller
public class RouteTabController {
    @Autowired
    private OpenRouteService openRouteService;

    @FXML
    private WebView mapView;

    private WebEngine webEngine;

    private static final Logger logger = LogManager.getLogger(RouteTabController.class);

    private TabsPaneController detailsController;

    public void setDetailsController(TabsPaneController detailsController) {
        this.detailsController = detailsController;
    }

    @FXML
    public void initialize() {
        webEngine = mapView.getEngine();
        // Load the map initially
        try {
            webEngine.load(getClass().getResource("/map.html").toExternalForm());
        } catch (NullPointerException e) {
            logger.error("Map file not found", e);
            showAlert("Error", "Map file not found: " + e.getMessage());
        }
    }

    public void displayRoute(String startLocation, String endLocation) {
        try {
            // Get coordinates for the start and end locations
            double[] startCoordinates = openRouteService.getCoordinates(startLocation);
            double[] endCoordinates = openRouteService.getCoordinates(endLocation);

            if (startCoordinates != null && endCoordinates != null) {
                JsonNode route = openRouteService.getRoute(startCoordinates[0], startCoordinates[1], endCoordinates[0], endCoordinates[1]);
                if (route != null && route.get("features") != null && route.get("features").size() > 0) {
                    clearRouteOnMap();
                    displayRouteOnMap(route);
                } else {
                    logger.warn("Could not fetch route details: The location names were not understood.");
                    showAlert("Error", "Could not fetch route details: The location names were not understood.");
                }
            } else {
                logger.warn("Could not fetch coordinates for the locations: The location names were not understood.");
                showAlert("Error", "Could not fetch coordinates for the locations: The location names were not understood.");
            }
        } catch (IOException e) {
            logger.error("Could not fetch route details", e);
            showAlert("Error", "Could not fetch route details: " + e.getMessage());
        }
    }

    private void displayRouteOnMap(JsonNode route) {
        // Convert the JSON route to a JavaScript function call
        String coordinates = route.get("features").get(0).get("geometry").get("coordinates").toString();
        String script = "displayRoute(" + coordinates + ")";
        webEngine.executeScript(script);
    }

    private void clearRouteOnMap() {
        // Clear the existing route from the map
        String script = "clearRoute()";
        webEngine.executeScript(script);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
