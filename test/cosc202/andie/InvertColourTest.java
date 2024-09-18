package cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

/**
 * Tests for the InvertColour class.
 * 
 * @see InvertColour
 */
public class InvertColourTest {

    @Test
    public void testInvert() {
        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFFEEDDCC); // RGB: 238, 221, 204
        //testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFF887766); // RGB: 136, 119, 102
        //testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204

        /*BufferedImage invertedTestImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFFEEDDCC); // RGB: 238, 221, 204
        testImage.setRGB(1, 0, 0xFFBBAA99); // RGB: 187, 170, 153
        testImage.setRGB(0, 1, 0xFF887766); // RGB: 136, 119, 102
        testImage.setRGB(1, 1, 0xFF554433); // RGB: 85, 68, 51*/

        // Create an instance of InvertColour and apply the operation
        InvertColour invert = new InvertColour();
        BufferedImage invertedTestImage = invert.apply(testImage);


        // Verify that each pixel in the output image is inverted
        for (int y = 0; y < testImage.getHeight(); y++) {
            for (int x = 0; x < testImage.getWidth(); x++) {
                int argb = testImage.getRGB(x,y);
                //int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int rNew = (255-r);
                int gNew = (255-g);
                int bNew = (255-b);

                //argb = (a << 24) | (rNew << 16) | (gNew << 8) | bNew;
                //Assertions.assertEquals(testImage.getRGB(x,y), invertedTestImage.getRGB(x, y));
                //assertEquals(argb, invertedTestImage.getRGB(x, y));
                int argbInverted = invertedTestImage.getRGB(x,y);
                //int aInverted = (argbInverted & 0xFF000000) >> 24;
                int rInverted = (argbInverted & 0x00FF0000) >> 16;
                int gInverted = (argbInverted & 0x0000FF00) >> 8;
                int bInverted = (argbInverted & 0x000000FF);
                //System.out.println(invertedTestImage.getRGB(x,y));
                //Assertions.assertEquals(rNew, rInverted);
            }
        }
    }
}
