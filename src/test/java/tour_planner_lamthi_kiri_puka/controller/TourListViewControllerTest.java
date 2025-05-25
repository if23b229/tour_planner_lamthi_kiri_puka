/*
package tour_planner_lamthi_kiri_puka.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

public class TourListViewControllerTest {
    private static final String DEFAULT;
    static {
        try {
            java.lang.reflect.Field f = TourListViewController.class.getDeclaredField("DEFAULT_IMAGE_URL");
            f.setAccessible(true);
            DEFAULT = (String) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testResolveImagePath_null() {
        TourListViewController ctl = new TourListViewController();
        String result = invokeResolve(ctl, null);
        assertEquals(DEFAULT, result);
    }

    @Test
    public void testResolveImagePath_httpValidExtension() {
        TourListViewController ctl = new TourListViewController();
        String url = "http://example.com/image.jpg";
        String result = invokeResolve(ctl, url);
        assertEquals(url, result);
    }

    @Test
    public void testResolveImagePath_httpInvalidExtension() {
        TourListViewController ctl = new TourListViewController();
        String url = "http://example.com/path/";
        String result = invokeResolve(ctl, url);
        assertEquals(DEFAULT, result);
    }

    @Test
    public void testResolveImagePath_fileValid() throws Exception {
        File temp = File.createTempFile("test", ".png");
        TourListViewController ctl = new TourListViewController();
        String result = invokeResolve(ctl, temp.getAbsolutePath());
        assertEquals(temp.toURI().toString(), result);
        temp.delete();
    }

    @Test
    public void testResolveImagePath_directoryReturnsDefault() {
        File dir = new File(System.getProperty("java.io.tmpdir"));
        TourListViewController ctl = new TourListViewController();
        String result = invokeResolve(ctl, dir.getAbsolutePath());
        assertEquals(DEFAULT, result);
    }

    // Helper to call private resolveImagePath
    private String invokeResolve(TourListViewController ctl, String input) {
        try {
            var m = TourListViewController.class.getDeclaredMethod("resolveImagePath", String.class);
            m.setAccessible(true);
            return (String) m.invoke(ctl, input);
        } catch (Exception e) {
            fail(e);
            return null;
        }
    }
}*/