package cosc202.andie;

import java.util.*;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply random scattering.
 * </p>
 * 
 * <p>
 * Random Scattering replaces each pixel in the image with a random pixel
 * in a specefied radius.
 * </p>
 */
public class RandomScattering implements ImageOperation, java.io.Serializable {
    /**
     * The size of radius to use.
     */
    private int radius;

    /**
     * Declaring Random
     */
    static final Random R = new Random();

    /**
     * <p>
     * Create the radius of Random Scattering.
     * </p>
     * 
     * @param radius The radius of pixels to be selected from
     */
    RandomScattering(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Default radius of Random Scattering
     * </p>
     * 
     * <p>
     * By default, Random scattering has a radius of 1.
     * </p>
     * 
     * @see RandomScattering(int)
     */
    RandomScattering() {
        this(1);
    }
    
    /**
     * <p>
     * Apply Random Scattering to an image.
     * </p>
     * 
     * @param input The input image.
     * @return The filtered image.
     */
    public BufferedImage apply(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();

        //BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; ++y) {       // For each pixel in input image
            for (int x = 0; x < width; ++x) {
                ArrayList<Integer> validPixelY = new ArrayList<Integer>();
                ArrayList<Integer> validPixelX = new ArrayList<Integer>();

                for (int ry = -radius; ry <= radius; ry++) {        // For each pixel in radius
                    for (int rx = -radius; rx <= radius; rx++) {
                        int yCoord = y+ry;
                        int xCoord = x+rx;
                        if (yCoord >=0 && yCoord < height && xCoord >= 0 && xCoord < width) {   // If pixel in image, add it to ArrayList
                            validPixelY.add(yCoord);
                            validPixelX.add(xCoord);
                        }
                    }
                }

                // Select a random pixel, get its colour and set output to this colour
                int selectedPixel = R.nextInt(validPixelY.size());
                int argb = input.getRGB(validPixelX.get(selectedPixel), validPixelY.get(selectedPixel));
                output.setRGB(x, y, argb);
            }
        }
        return output;
    }
}
