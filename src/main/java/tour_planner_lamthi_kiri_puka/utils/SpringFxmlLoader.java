package tour_planner_lamthi_kiri_puka.utils;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

//import java.io.IOException;

@Component
public class SpringFxmlLoader {
    
    private final ApplicationContext applicationContext;

    public SpringFxmlLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FXMLLoader load(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        loader.setLocation(getClass().getResource(url));
        return loader;
    }
}
