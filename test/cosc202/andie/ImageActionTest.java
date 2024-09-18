package cosc202.andie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


public class ImageActionTest{

    ImagePanel a = new ImagePanel();

    @BeforeEach
    void setUp(){
        ImageAction.setTarget(a);
    }
   

    @Test
    void setTargetTest(){
        Assertions.assertEquals(ImageAction.target ,a , "Not the same");
    }

    @Test
    void getTargetTest(){
        ImagePanel b = ImageAction.getTarget();
        Assertions.assertEquals(ImageAction.target ,b , "Not the same");
    }

}

