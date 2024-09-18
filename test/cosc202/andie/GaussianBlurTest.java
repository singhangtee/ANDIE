package cosc202.andie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.BufferedImage;

/**
 *  testing for gaussian blur
 * @c GaussianBlur.java
 */
public class GaussianBlurTest {

    //test the default constructor works
    @Test 
    void defaultConstructorTest(){
        GaussianBlur testGauss = new GaussianBlur();
        Assertions.assertEquals(1, testGauss.getRadius());
        Assertions.assertNotEquals(2, testGauss.getRadius());
    }

    //test the constructor works
    @Test
    void constructorTest(){
        GaussianBlur testGauss = new GaussianBlur(5);
        Assertions.assertEquals(5, testGauss.getRadius());
        Assertions.assertNotEquals(2, testGauss.getRadius());
    }

    //test the constructor works
    @Test
    void testApply(){
    
        BufferedImage testImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);

        GaussianBlur testGauss = new GaussianBlur();
        BufferedImage blurredImage = testGauss.apply(testImage);

        //test the photo size remains the same for images
        Assertions.assertEquals(3, blurredImage.getWidth());
        Assertions.assertEquals(3, blurredImage.getHeight());

    }

    @Test
    void checkEachPixelDifferentTest() {

        // Create a test image of 2x2 pixels
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
        testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
        testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
        testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0

        // Create an instance of Gaussian Blur and apply the operation
        GaussianBlur testGauss = new GaussianBlur();
        BufferedImage blurredImage = testGauss.apply(testImage);

        // Check pixel values have been blurred
        Assertions.assertFalse(testImage.getRGB(0, 0) == blurredImage.getRGB(0, 0));
        Assertions.assertFalse(testImage.getRGB(1, 0) == blurredImage.getRGB(1, 0));
        Assertions.assertFalse(testImage.getRGB(0, 1) == blurredImage.getRGB(0, 1));
        Assertions.assertFalse(testImage.getRGB(1, 1) == blurredImage.getRGB(1, 1));
    }

    @Test
    void imageSizeSameTest(){
        
         // Create a test image of 2x2 pixels
         BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
         testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
         testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
         testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
         testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0
 
         // Create an instance of GaussianBlur and apply the operation
         GaussianBlur testGauss = new GaussianBlur();
         BufferedImage blurredImage = testGauss.apply(testImage);

        // Test dimensions are same
        Assertions.assertEquals(testImage.getHeight(), blurredImage.getHeight());
        Assertions.assertEquals(testImage.getWidth(), blurredImage.getWidth());
    }

    //No way to test for radius values larger than the max (10) or smaller than the min (0) allowed by the Jspinner 
    //as there is no way there could be one of these values passed to radius through the andie interface.
}
