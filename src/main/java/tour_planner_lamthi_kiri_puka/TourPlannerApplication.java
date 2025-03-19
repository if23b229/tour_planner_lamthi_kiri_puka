package tour_planner_lamthi_kiri_puka;

import tour_planner_lamthi_kiri_puka.controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TourPlannerApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        // Use Parent as the root if you're not sure about the specific container
        Parent root = loader.load();
        MainViewController controller = loader.getController();

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Tour Planner Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
