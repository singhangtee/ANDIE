package cosc202.andie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.BufferedImage;

/**
 * Tests for SoftBlur
 * 
 * @see SoftBlur
 */
public class SoftBlurTest {

    @Test
    void checkEachPixelDifferentTest() {

        // Create a test image of 2x2 pixels
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
        testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
        testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
        testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0

        // Create an instance of SoftBlur and apply the operation
        SoftBlur softBlur = new SoftBlur();
        BufferedImage blurredImage = softBlur.apply(testImage);

        // Check pixel values have been blurred
        Assertions.assertFalse(testImage.getRGB(0, 0) == blurredImage.getRGB(0, 0));
        Assertions.assertFalse(testImage.getRGB(1, 0) == blurredImage.getRGB(1, 0));
        Assertions.assertFalse(testImage.getRGB(0, 1) == blurredImage.getRGB(0, 1));
        Assertions.assertFalse(testImage.getRGB(1, 1) == blurredImage.getRGB(1, 1));
    }

    @Test
    void imageSizeSameTest() {

        // Create a test image of 2x2 pixels
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
        testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
        testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
        testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0

        // Create an instance of SoftBlur and apply the operation
        SoftBlur softBlur = new SoftBlur();
        BufferedImage blurredImage = softBlur.apply(testImage);

        // Test dimensions are same
        Assertions.assertEquals(testImage.getHeight(), blurredImage.getHeight());
        Assertions.assertEquals(testImage.getWidth(), blurredImage.getWidth());
    }

}
