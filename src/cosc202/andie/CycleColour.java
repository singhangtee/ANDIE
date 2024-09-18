package cosc202.andie;

import java.awt.image.*;
import java.util.ResourceBundle;

/**
 * ImageOperation to cycle the colour values of an image in a user specified
 * direction.
 */
public class CycleColour implements ImageOperation, java.io.Serializable {

    /**
     *  Language Bundle for Multilingual Support
     */  
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /**
     * The colours the user has chosen to cycle the channels to.
     */
    private String[] colours;

    /**
     * Create a new CycleColour operation.
     * 
     * @param direction The direction the colours should be cycled
     */
    CycleColour(String[] colours) {
        this.colours = colours;
    }

    /**
     * Accessor method for retrieving array[] colours
     * @return Array containing the colours the user has chosen to cycle the channels to.
     */
    public String[] getColours() {
        return colours;
    }

    /**
    * Sets the colors of the object.
    *
    * @param str An array of strings representing the colors to be set.
    * @return The updated array of colors.
    */
    public String[] setColours(String[] str) {
        colours = str;
        return colours;
    }

    /**
     * Cycles each colour value in the direction specified
     * 
     * @param input The image to be colour cycled
     * @return The resulting cycled image.
     */
    public BufferedImage apply(BufferedImage input) {
        int[] rgbValues = new int[3];

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int[] rgb = new int[] {r, g, b};

                //Check each of the colours selected and set the rgbValues accordingly
                for (int i = 0; i < rgb.length; i++) {
                    if (colours[i] == bundle.getString("red")) rgbValues[0] = rgb[i];
                    else if (colours[i] == bundle.getString("green")) rgbValues[1] = rgb[i];
                    else if (colours[i] == bundle.getString("blue")) rgbValues[2] = rgb[i];
                }

                argb = (a << 24) | (rgbValues[0] << 16) | (rgbValues[1] << 8) | rgbValues[2];

                input.setRGB(x, y, argb);
            }
        }

        return input;
    }

}
