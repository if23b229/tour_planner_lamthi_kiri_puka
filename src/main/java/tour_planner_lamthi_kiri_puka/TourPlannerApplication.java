package tour_planner_lamthi_kiri_puka;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tour_planner_lamthi_kiri_puka.utils.SpringFxmlLoader;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TourPlannerApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private static final Logger logger = LoggerFactory.getLogger(TourPlannerApplication.class);

    @Override
    public void init() throws Exception {
        logger.info("Initializing Spring application context...");
        springContext = new SpringApplicationBuilder(TourPlannerApplication.class)
                    .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Starting JavaFX application...");
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        loader.setControllerFactory(springContext::getBean);
        Parent root = loader.load();*/
        // 1) grab your SpringFxmlLoader bean
        SpringFxmlLoader springFxmlLoader = springContext.getBean(SpringFxmlLoader.class);

        // 2) get an FXMLLoader pre-configured with controller-factory and location
        FXMLLoader loader = springFxmlLoader.load("/fxml/MainView.fxml");

        // 3) load the scene graph; controller is pulled from Spring
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Tour Planner Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            logger.info("Primary stage is closing, stopping application...");
            Platform.exit();
        });
    }

    @Override
    public void stop() throws Exception {
        logger.info("Stopping Spring application context...");
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
