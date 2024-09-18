
package cosc202.andie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.BufferedImage;

/**
 *  testing for EmbossFilter
 * @c EmbossFilter.java
 */
public class EmbossFilterTest {

    //test the default constructor works
    @Test 
    void defaultConstructorTest(){
        EmbossFilter testFilter = new EmbossFilter();
        Assertions.assertEquals(0, testFilter.getChoice());
        Assertions.assertNotEquals(1, testFilter.getChoice());
    }

    //test the constructor works
    @Test
    void constructorTest(){
        EmbossFilter testFilter = new EmbossFilter(10);
        Assertions.assertEquals(7, testFilter.getChoice());
        Assertions.assertNotEquals(10, testFilter.getChoice());
    }

    //test the apply method works
    @Test
    void testApply(){
    
        BufferedImage testImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);

        EmbossFilter testFilter = new EmbossFilter();
        BufferedImage embossedImage = testFilter.apply(testImage);

        //test the photo size remains the same for images
        Assertions.assertEquals(3, embossedImage.getWidth());
        Assertions.assertEquals(3, embossedImage.getHeight());

    }

    @Test
    void checkEachPixelDifferentTest() {

        // Create a test image of 2x2 pixels
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
        testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
        testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
        testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0

        // Create an instance of EmbossFilter and apply the operation
        EmbossFilter testFilter = new EmbossFilter();
        BufferedImage embossedImage = testFilter.apply(testImage);

        // Check pixel values have been blurred
        Assertions.assertFalse(testImage.getRGB(0, 0) == embossedImage.getRGB(0, 0));
        Assertions.assertFalse(testImage.getRGB(1, 0) == embossedImage.getRGB(1, 0));
        Assertions.assertFalse(testImage.getRGB(0, 1) == embossedImage.getRGB(0, 1));
        Assertions.assertFalse(testImage.getRGB(1, 1) == embossedImage.getRGB(1, 1));
    }

    @Test
    void imageSizeSameTest(){
        
         // Create a test image of 2x2 pixels
         BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
         testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
         testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
         testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
         testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0
 
         // Create an instance of EmbossFilter and apply the operation
         EmbossFilter testFilter = new EmbossFilter();
         BufferedImage embossedImage = testFilter.apply(testImage);

        // Test dimensions are same
        Assertions.assertEquals(testImage.getHeight(), embossedImage.getHeight());
        Assertions.assertEquals(testImage.getWidth(), embossedImage.getWidth());
    }

}