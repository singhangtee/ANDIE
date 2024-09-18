package cosc202.andie;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;

/**
 * tests for the Sharpen Filter
 * @c SharpenFilter.java
 */
public class SharpenFilterTest {
    
     // tests the apply method works
    @Test
    void testApply(){
        BufferedImage testImage = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(2, 0, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(0, 1, 0xFFAABBCC); // RGB: 170, 187, 204
        testImage.setRGB(1, 1, 0xFF0000); //RGB: 255, 0, 0
        testImage.setRGB(2, 1, 0x00FF00); //RGB: 0, 255, 0
        testImage.setRGB(0, 2, 0x0000FF); //RGB: 0, 0, 255
        testImage.setRGB(1, 2, 0xFFFF00); //RGB: 255, 255, 0
        testImage.setRGB(2, 2, 0xFF00FF); //RGB: 255, 0, 255

        SharpenFilter testFilter = new SharpenFilter();
        BufferedImage sharpenedImage = testFilter.apply(testImage);

        //test the photo size remains the same
        Assertions.assertEquals(3, sharpenedImage.getWidth());
        Assertions.assertEquals(3, sharpenedImage.getHeight());

        BufferedImage testImage2 = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
       
        testImage.setRGB(1, 1, 0xFF0000); //RGB: 255, 0, 0
        testImage.setRGB(2, 1, 0x00FF00); //RGB: 0, 255, 0
        testImage.setRGB(0, 2, 0x0000FF); //RGB: 0, 0, 255
        testImage.setRGB(1, 2, 0xFFFF00); //RGB: 255, 255, 0
        testImage.setRGB(2, 2, 0xFF00FF); //RGB: 255, 0, 255

        SharpenFilter testFilter2 = new SharpenFilter();
        BufferedImage sharpenedImage2 = testFilter2.apply(testImage2);

        //test the photo size remains the same, even with a size smaller than 3x3, which is the size of the kernel.
        Assertions.assertEquals(2, sharpenedImage2.getWidth());
        Assertions.assertEquals(2, sharpenedImage2.getHeight());
    }
}
