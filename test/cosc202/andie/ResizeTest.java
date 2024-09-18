package cosc202.andie;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.*;

/**
 * Tests for the Resize class.
 * 
 * @see Resize
 */
public class ResizeTest {

    @Test
    void getResizeInitialValue() {
        Resize testResize = new Resize();
        Assertions.assertEquals(testResize.getPercentage(), 100 / 100);
    }

    @Test
    void getConstructorWidthValue() {
        Resize testResize = new Resize(500, 10);
        Assertions.assertEquals(testResize.getWidth(), 500);
        Assertions.assertFalse(testResize.getWidth() == 10);
    }

    @Test
    void getConstructorHeightValue() {
        Resize testResize = new Resize(500, 10);
        Assertions.assertEquals(testResize.getHeight(), 10);
        Assertions.assertFalse(testResize.getHeight() == 500);
    }

    @Test
    void imageGotSmallerTest() {
        // Create a test image (4x4 pixels)
        BufferedImage testImage = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);

        // Half the image size
        Resize testResize = new Resize(50);
        BufferedImage resizedImage = testResize.apply(testImage);

        // Check the width and height have been halfed
        Assertions.assertEquals(resizedImage.getWidth(), testImage.getWidth() / 2);
        Assertions.assertEquals(resizedImage.getHeight(), testImage.getHeight() / 2);
    }

    @Test
    void imageGotLargerTest() {
        // Create a test image (2x2 pixels)
        BufferedImage testImage = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);

        // Double the image size
        Resize testResize = new Resize(200);
        BufferedImage resizedImage = testResize.apply(testImage);

        // Check the width and height have been doubled
        Assertions.assertEquals(resizedImage.getWidth(), testImage.getWidth() * 2);
        Assertions.assertEquals(resizedImage.getHeight(), testImage.getHeight() * 2);
    }
}
