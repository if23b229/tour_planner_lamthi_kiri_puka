/*package tour_planner_lamthi_kiri_puka.controller;

import javafx.scene.control.TabPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class TabsPaneControllerTest {

    private TabsPaneController controller;
    private TabPane mockTabPane;

    @BeforeEach
    void setUp() {
        mockTabPane = mock(TabPane.class);
        controller = new TabsPaneController();

        // Assume the TabPane is injected or initialized in the actual class
        controller.detailsTabPane = mockTabPane;
    }

    @Test
    void testSelectGeneralTab() {
        controller.selectGeneralTab();
        // Verify that the general tab is selected
        // This assumes a 'generalTab' member exists and is a mock
        verify(mockTabPane).getSelectionModel().select(controller.generalTab);
    }
}*/