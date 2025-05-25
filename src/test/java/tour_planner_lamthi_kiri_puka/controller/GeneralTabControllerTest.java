/*
package tour_planner_lamthi_kiri_puka.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tour_planner_lamthi_kiri_puka.model.TourViewModel;

public class GeneralTabControllerTest {
    private GeneralTabController controller;
    private DummyTabsPaneController dummyDetails;

    static class DummyTabsPaneController extends TabsPaneController {
        boolean editCalled = false;
        boolean logCalled = false;
        TourViewModel editParam;
        TourViewModel logParam;

        @Override
        public void openEditTourTab(TourViewModel tour) {
            this.editCalled = true;
            this.editParam = tour;
        }

        @Override
        public void openNewLogTab(TourViewModel tour) {
            this.logCalled = true;
            this.logParam = tour;
        }
    }

    @BeforeEach
    public void setUp() {
        controller = new GeneralTabController();
        dummyDetails = new DummyTabsPaneController();
        controller.setDetailsController(dummyDetails);
    }

    @Test
    public void testHandleEditCallsOpenEditTourTab() {
        TourViewModel tvm = new TourViewModel();
        controller.setSelectedTour(tvm);
        invokeHandle("handleEdit");
        assertTrue(dummyDetails.editCalled);
        assertSame(tvm, dummyDetails.editParam);
    }

    @Test
    public void testHandleAddLogCallsOpenNewLogTab() {
        TourViewModel tvm = new TourViewModel();
        controller.setSelectedTour(tvm);
        invokeHandle("handleAddLog");
        assertTrue(dummyDetails.logCalled);
        assertSame(tvm, dummyDetails.logParam);
    }


    // helper to invoke private methods
    private void invokeHandle(String methodName) {
        try {
            var m = GeneralTabController.class.getDeclaredMethod(methodName);
            m.setAccessible(true);
            m.invoke(controller);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
*/