import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class NewTourFormControllerTest {

    private NewTourFormController controller;
    private TourListViewController mockTourListController;

    @BeforeEach
    void setUp() {
        mockTourListController = mock(TourListViewController.class);
        controller = new NewTourFormController();
        controller.setTourListController(mockTourListController);

        // Mocking JavaFX components
        controller.nameField = mock(TextField.class);
        controller.descriptionField = mock(TextField.class);
        //... other fields

        // Initialize JavaFX thread if required for TestFX testing
    }

    @Test
    void testHandleSaveNewTour() {
        // Assuming the text fields are set
        when(controller.nameField.getText()).thenReturn("New Tour");
        //... other fields

        // Call the save handler
        controller.handleSave();

        // Verify the tour list controller received a new tour
        verify(mockTourListController).addTour(any(TourViewModel.class));
    }
}