package cosc202.andie;

import org.junit.jupiter.api.*;
import java.awt.image.BufferedImage;

/**
 * Tests for the Flip class.
 * 
 * @see Flip
 */
public class FlipTest {

    @Test
    void flipHorizontalTest() {
        Flip testFlip = new Flip(true);
        Assertions.assertEquals(testFlip.getIsVerticalFlip(), true);
    }

    @Test
    void horizontalFlipPixelsTest() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        Flip testFlip = new Flip(false);
        BufferedImage flippedImage = testFlip.apply(testImage);

        // Verify that each pixel in the output image is flipped horizontally
        Assertions.assertEquals(testImage.getRGB(0,0), flippedImage.getRGB(1,0));
        Assertions.assertEquals(testImage.getRGB(1,0), flippedImage.getRGB(0,0));
        Assertions.assertEquals(testImage.getRGB(0,1), flippedImage.getRGB(1,1));
        Assertions.assertEquals(testImage.getRGB(1,1), flippedImage.getRGB(0,1));
    }

    @Test
    void flipVerticalTest() {
        Flip testFlip = new Flip(false);
        Assertions.assertEquals(testFlip.getIsVerticalFlip(), false);
    }

    @Test
    void verticalFlipPixelsTest() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        Flip testFlip = new Flip(true);
        BufferedImage flippedImage = testFlip.apply(testImage);

        // Verify that each pixel in the output image is flipped vertically
        Assertions.assertEquals(testImage.getRGB(0,0), flippedImage.getRGB(0,1));
        Assertions.assertEquals(testImage.getRGB(1,0), flippedImage.getRGB(1,1));
        Assertions.assertEquals(testImage.getRGB(0,1), flippedImage.getRGB(0,0));
        Assertions.assertEquals(testImage.getRGB(1,1), flippedImage.getRGB(1,0));
    }
}
