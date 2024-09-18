package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a Saturation filter.
 * </p>
 */
public class Saturator implements ImageOperation, java.io.Serializable{

    /** Amount of saturation to apply. */
    private float amount;

    /**
     * constructor for new Saturator object
     * @param amount the saturation multiplier
     */
    Saturator(float amount){

        //ensure amount can't be less than 0 or more than 2
        if(amount > 2.0f){
            amount = 2.0f;
        }else if(amount < 0f){
            amount = 0f;
        }

        this.amount = amount;
    }

    /**
     * Default constructor for saturator, default satruation amount = 1x
     */
    Saturator(){
        this.amount = 1f;
    }

    /**
     * Apply method, applies the saturation effect to a bufferedimage with
     * the given amount of saturation
     * 
     * @param image the image to be saturated
     * @return saturated the saturated image
     */
    public BufferedImage apply(BufferedImage image){
        int x = image.getWidth();
        int y = image.getHeight();
        BufferedImage saturated = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                Color unsaturated = new Color(image.getRGB(i, j));
                float[] hsb = Color.RGBtoHSB(unsaturated.getRed(), unsaturated.getGreen(), unsaturated.getBlue(), null);

                hsb[1] *= amount;
                hsb[1] = Math.max(0.0f, Math.min(1, hsb[1]));
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

                
                saturated.setRGB(i, j, rgb);
            }
        }

        return saturated;

    }

    /**
     * getter method for data field choice - useful for testing
     * 
     * @return choice
     */
    public float getAmount(){
        return amount;
    }

}
