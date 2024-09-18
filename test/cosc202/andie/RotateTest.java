package cosc202.andie;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.*;

/**
 * Tests for the Rotate class.
 * 
 * @see CopyPreview
 */
public class RotateTest {
    
    @Test
    void getRotateAngleValue(){
        Rotate testRotate = new Rotate(90);
        Assertions.assertEquals(testRotate.getRotationAngle(), 90);
    }

    @Test
    void rotate90Test() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        Rotate testRotate = new Rotate(90);
        BufferedImage rotatedImage = testRotate.apply(testImage);

        // Verify that each pixel in the output image is flipped vertically
        Assertions.assertEquals(testImage.getRGB(0,0), rotatedImage.getRGB(1,0));
        Assertions.assertEquals(testImage.getRGB(1,0), rotatedImage.getRGB(1,1));
        Assertions.assertEquals(testImage.getRGB(0,1), rotatedImage.getRGB(0,0));
        Assertions.assertEquals(testImage.getRGB(1,1), rotatedImage.getRGB(0,1));
    }

    @Test
    void falseRotate90Test() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        Rotate testRotate = new Rotate(90);
        BufferedImage rotatedImage = testRotate.apply(testImage);

        // Verify that each pixel in the output image is not rotated incorrectly
        Assertions.assertFalse(testImage.getRGB(0,0) == rotatedImage.getRGB(0,1));
        Assertions.assertFalse(testImage.getRGB(1,0) == rotatedImage.getRGB(0,0));
        Assertions.assertFalse(testImage.getRGB(0,1) == rotatedImage.getRGB(1,1));
        Assertions.assertFalse(testImage.getRGB(1,1) == rotatedImage.getRGB(1,0));
    }

    @Test
    void rotate180Test() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        Rotate testRotate = new Rotate(180);
        BufferedImage rotatedImage = testRotate.apply(testImage);

        // Verify that each pixel in the output image is rotated 180 degrees
        Assertions.assertEquals(testImage.getRGB(0,0), rotatedImage.getRGB(1,1));
        Assertions.assertEquals(testImage.getRGB(1,0), rotatedImage.getRGB(0,1));
        Assertions.assertEquals(testImage.getRGB(0,1), rotatedImage.getRGB(1,0));
        Assertions.assertEquals(testImage.getRGB(1,1), rotatedImage.getRGB(0,0));
    }

    @Test
    void falseRotate180Test() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        Rotate testRotate = new Rotate(180);
        BufferedImage rotatedImage = testRotate.apply(testImage);

        // Verify that each pixel in the output image is not rotated incorrectly
        Assertions.assertFalse(testImage.getRGB(0,0) == rotatedImage.getRGB(1,0));
        Assertions.assertFalse(testImage.getRGB(1,0) == rotatedImage.getRGB(1,1));
        Assertions.assertFalse(testImage.getRGB(0,1) == rotatedImage.getRGB(0,0));
        Assertions.assertFalse(testImage.getRGB(1,1) == rotatedImage.getRGB(0,1));
    }

}
