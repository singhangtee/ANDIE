package cosc202.andie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.awt.image.BufferedImage;
/**
 * Tests for the Saturator class.
 * 
 * @see Saturator
 */
public class SaturatorTest {

    // tests the apply method works
    @Test
    void testApply(){
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(1, 0, 0xFFFF0000); // RGB: 255, 0, 0
        testImage.setRGB(0, 1, 0xFF00FF00); // RGB: 0, 255, 0
        testImage.setRGB(1, 1, 0xFF0000FF); // RGB: 0, 0, 255
        testImage.setRGB(0, 0, 0xFF000000); // RGB: 0, 0, 0
 
        Saturator testFilter = new Saturator();
        BufferedImage satImage = testFilter.apply(testImage);

        
 
        //test the photo size remains the same
        Assertions.assertEquals(2, satImage.getWidth());
        Assertions.assertEquals(2, satImage.getHeight());

        //test each pixel remains the same(default constructor passes no saturation)
        Assertions.assertTrue(testImage.getRGB(0, 0) == satImage.getRGB(0, 0));
        Assertions.assertTrue(testImage.getRGB(1, 0) == satImage.getRGB(1, 0));
        Assertions.assertTrue(testImage.getRGB(0, 1) == satImage.getRGB(0, 1));
        Assertions.assertTrue(testImage.getRGB(1, 1) == satImage.getRGB(1, 1));
 
        BufferedImage testImage2 = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204
 
        Saturator testFilter2 = new Saturator(1.5f);
        BufferedImage satImage2 = testFilter2.apply(testImage2);
 
        //test the photo size remains the same
        Assertions.assertEquals(2, satImage2.getWidth());
        Assertions.assertEquals(2, satImage2.getHeight());

        //test each pixel is not the same as image is now saturated
        Assertions.assertFalse(testImage.getRGB(0, 0) == satImage2.getRGB(0, 0));
        Assertions.assertFalse(testImage.getRGB(1, 0) == satImage2.getRGB(1, 0));
        Assertions.assertFalse(testImage.getRGB(0, 1) == satImage2.getRGB(0, 1));
        Assertions.assertFalse(testImage.getRGB(1, 1) == satImage2.getRGB(1, 1));
    }

    @Test 
    void ConstructorTest(){
        Saturator testSat = new Saturator();
        Saturator testSat2 = new Saturator(1.5f);
        Saturator testSat3 = new Saturator(-1f);
        Saturator testSat4 = new Saturator(5f);

        //test the default constructor works and makes the amount = 1
        Assertions.assertEquals(testSat.getAmount(), 1f); 
        Assertions.assertEquals(testSat2.getAmount(), 1.5f);
        Assertions.assertEquals(testSat3.getAmount(), 0f);
        Assertions.assertEquals(testSat4.getAmount(), 2f);
    }
}
