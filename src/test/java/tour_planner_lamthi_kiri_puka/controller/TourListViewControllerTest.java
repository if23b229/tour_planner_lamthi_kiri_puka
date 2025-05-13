package tour_planner_lamthi_kiri_puka.controller;

import tour_planner_lamthi_kiri_puka.model.TourViewModel;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourListViewControllerTest {

    private TourListViewController controller;
    private ObservableList<TourViewModel> mockTourList;

    @BeforeEach
    void setUp() {
        mockTourList = FXCollections.observableArrayList();
        controller = new TourListViewController();
        controller.tourList = mockTourList; // Directly assigning the mock list for simplicity
    }

    @Test
    void testAddTour() {
        TourViewModel tour = new TourViewModel();
        controller.addTour(tour);
        assertTrue(mockTourList.contains(tour));
    }

    @Test
    void testDeleteTour() {
        TourViewModel tour = new TourViewModel();
        mockTourList.add(tour);
        controller.deleteTour(tour);
        assertFalse(mockTourList.contains(tour));
    }
}