package tour_planner_lamthi_kiri_puka;

import org.springframework.boot.ExitCodeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ApplicationExitListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExitListener.class);

    @EventListener
    public void onApplicationEvent(ExitCodeEvent event) {
        logger.info("Application is exiting with exit code: {}", event.getExitCode());
    }
}