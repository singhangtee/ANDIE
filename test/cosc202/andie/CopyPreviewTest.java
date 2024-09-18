package cosc202.andie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

/**
 * Tests for the CopyPreview class.
 * 
 * @see CopyPreview
 */
public class CopyPreviewTest {

    @Test
    public void testApply() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        // Create another test image with different RGB values
        BufferedImage resultImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(1, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(0, 1, 0xFFAABBCC); // RGB: 170, 187, 204
        testImage.setRGB(1, 1, 0xFF778899); // RGB: 119, 136, 153

        CopyPreview copyPreview = new CopyPreview(testImage);
        resultImage = copyPreview.apply(testImage);

        // Verify that each pixel has been copied
        for (int y = 0; y < resultImage.getHeight(); y++) {
            for (int x = 0; x < resultImage.getWidth(); x++) {
                int argb = resultImage.getRGB(x, y);
                assertEquals(argb, testImage.getRGB(x, y));
            }
        }
    }
}
