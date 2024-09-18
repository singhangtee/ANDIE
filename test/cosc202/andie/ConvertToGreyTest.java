package cosc202.andie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

/**
 * Tests for the ConvertToGrey class.
 * 
 * Some code provided by ChatGPT, largely code involving selecting RGB values and checking if a pixel has been made grey.
 * 
 * @see ConvertToGrey
 */
public class ConvertToGreyTest {

    @Test
    public void testApply() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        // Create an instance of ConvertToGrey and apply the operation
        ConvertToGrey converter = new ConvertToGrey();
        BufferedImage resultImage = converter.apply(testImage);

        // Verify that the output image has the correct dimensions
        assertEquals(testImage.getWidth(), resultImage.getWidth());
        assertEquals(testImage.getHeight(), resultImage.getHeight());

        // Verify that each pixel in the output image is greyscale
        for (int y = 0; y < resultImage.getHeight(); y++) {
            for (int x = 0; x < resultImage.getWidth(); x++) {
                int argb = resultImage.getRGB(x, y);
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;
                assertEquals(r, g);
                assertEquals(g, b);
            }
        }
    }
}
