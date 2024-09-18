package cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

public class CycleColourTest {
    @Test

    void checkColoursTest() {
        /*BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(0, 0, 0xFF112233); // RGB: 17, 34, 51
        testImage.setRGB(1, 0, 0xFFEEDDCC); // RGB: 238, 221, 204
        //testImage.setRGB(1, 0, 0xFF445566); // RGB: 68, 85, 102
        testImage.setRGB(0, 1, 0xFF778899); // RGB: 119, 136, 153
        testImage.setRGB(1, 1, 0xFF887766); // RGB: 136, 119, 102
        //testImage.setRGB(1, 1, 0xFFAABBCC); // RGB: 170, 187, 204
        CycleColour cycle = new CycleColour(null);*/
        CycleColour cycle = new CycleColour(null);
        Assertions.assertEquals(null, cycle.getColours());
        String[] str = new String[]{"red"};
        cycle.setColours(str);
        Assertions.assertEquals(str, cycle.getColours());
    }

}
