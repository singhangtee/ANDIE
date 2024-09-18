package cosc202.andie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.BufferedImage;

/**
 *  testing for Tile filter 
 * @c TileFilter.java
 */
public class TileFilterTest {
    
    @Test
    void defaultConstructorTest(){
        TileFilter testFilter = new TileFilter();
        Assertions.assertEquals(1, testFilter.getRadius());
        Assertions.assertNotEquals(2, testFilter.getRadius());
    }

    @Test 
    void constructorTest(){
        TileFilter testFilter = new TileFilter(5);
        Assertions.assertEquals(5, testFilter.getRadius());
        Assertions.assertNotEquals(2, testFilter.getRadius());
    }

    @Test
    void checkEachPixelSameTest() {

        // Create a test image of 2x2 pixels
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
        testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
        testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
        testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0

        // Create an instance of TileFilter and apply the operation
        TileFilter testFilter = new TileFilter();
        //At this tiny size pixel values should be the same
        BufferedImage filteredImage = testFilter.apply(testImage);

        // Check pixel values have been filtered and stayed same
        Assertions.assertEquals(testImage.getRGB(0, 0), filteredImage.getRGB(0, 0));
        Assertions.assertEquals(testImage.getRGB(1, 0), filteredImage.getRGB(1, 0));
        Assertions.assertEquals(testImage.getRGB(0, 1), filteredImage.getRGB(0, 1));
        Assertions.assertEquals(testImage.getRGB(1, 1), filteredImage.getRGB(1, 1));
    }

    @Test
    void imageSizeSameTest(){
        
         // Create a test image of 2x2 pixels
         BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
         testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
         testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
         testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
         testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0
 
         // Create an instance of TileFilter and apply the operation
         TileFilter testFilter = new TileFilter();
         BufferedImage filteredImage = testFilter.apply(testImage);

        // Test dimensions are same
        Assertions.assertEquals(testImage.getHeight(), filteredImage.getHeight());
        Assertions.assertEquals(testImage.getWidth(), filteredImage.getWidth());
    }

    //No way to test for radius values larger than the max (10) or smaller than the min (0) allowed by the Jspinner 
    //as there is no way there could be one of these values passed to radius through the andie interface.

}
