package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class GeneralTabControllerTest {

    private GeneralTabController controller;
    private TourViewModel mockTour;

    @BeforeEach
    void setUp() {
        // Mock the necessary dependencies or objects
        mockTour = mock(TourViewModel.class);
        controller = new GeneralTabController();

        // Setup necessary initial state or interactions before each test
        // Assume that controller is properly initialized with FXML dependencies if needed
    }

    @Test
    void testDisplayTourDetails() {
        // Setup the tour details
        when(mockTour.getName()).thenReturn("Sample Tour");
        when(mockTour.getDescription()).thenReturn("A sample description");

        // Call the method to test
        controller.displayTourDetails(mockTour);

        // Verify the display method behaves as expected.
        // Assertions should check the actual labels in the controller,
        // which requires controller fields to be accessible for the test.
        // Here we'd need to mock JavaFX components and run in a JavaFX thread.
    }
}