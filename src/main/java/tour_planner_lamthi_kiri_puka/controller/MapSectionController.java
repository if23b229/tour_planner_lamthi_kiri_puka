package tour_planner_lamthi_kiri_puka.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MapSectionController {
    @FXML private ImageView mapImageView;
    @FXML private AnchorPane mapAnchorPane;

    public void initialize() {
        // Bind the ImageView's size to the AnchorPane size, maintaining aspect ratio
        mapImageView.fitWidthProperty().bind(mapAnchorPane.widthProperty());
        mapImageView.fitHeightProperty().bind(mapAnchorPane.heightProperty());
        mapImageView.setPreserveRatio(true);

        mapAnchorPane.setMinSize(300, 200); // Adjust sizes as needed
        mapAnchorPane.setMaxHeight(400);
    }

    public void updateMapImage(String imagePath) {
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            Image image = new Image(imagePath);
            mapImageView.setImage(image);//data binding
        } else {
            mapImageView.setImage(null); // Add your default image or clear it.
        }
    }
}