package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to change the Brightness and Contrast of an image.
 * </p>
 */
public class BrightnessContrast  implements ImageOperation, java.io.Serializable {

    /**
     * Defining Brightness and Contrast variables
     */
    private int brightness, contrast;

    /**
     * Create new Brightness and Contrast operation
     * 
     * @param brightness The brightness to change the image to.
     * @param contrast The contrast to change the image to.
     */
    public BrightnessContrast(int brightness, int contrast) {
        this.brightness = brightness;
        this.contrast = contrast;
    }

    /**
     * Adjusts the colour of each colour 
     * 
     * @param colour The colour to adjust.
     * @return Adjusted colour.
     */
    private int adjust(int colour) {
        float newColour = (1+(contrast/(float)100))*((float)colour-(float)127.5)+(float)127.5*(1+(brightness/(float)100));       // Turn every number into floats so it doesn't break
        return (int)newColour;
    }

    /**
     * Applies the Adjustment to the image
     * 
     * @param input The input image.
     * @return The brightened and contrasted image.
     */
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                
                int argb = input.getRGB(x, y);
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int newR = Math.min(Math.max(adjust(r), 0), 255);
                int newG = Math.min(Math.max(adjust(g), 0), 255);
                int newB = Math.min(Math.max(adjust(b), 0), 255);

                argb = (newR << 16) | (newG << 8) | newB;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }
}