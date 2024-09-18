package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;
import java.io.Serializable;

/**
 * <p>
 * ImageOperation to apply a Sepia Tone filter.
 * </p>
 * 
 * <p>
 * A Sepia Tone filter gives an image a warm brownish tone, giving it an old-fashioned look.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.BufferedImage
 * @see java.awt.Color
 * @see cosc202.andie.ImageOperation
 * @see java.io.Serializable
 * @version 1.0
 */
public class SepiaToneFilter implements ImageOperation, Serializable {

    /**
     * <p>
     * Apply a Sepia Tone filter to an image.
     * </p>
     * 
     * <p>
     * This filter processes each pixel of the image and adjusts its color to give a sepia effect.
     * </p>
     * 
     * @param input The image to apply the Sepia Tone filter to.
     * @return The resulting image with a sepia tone effect.
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(input.getRGB(x, y));

                // Get the RGB components of the current pixel
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // Calculate the new values using the sepia formula
                int tr = (int)(0.393 * red + 0.769 * green + 0.189 * blue);
                int tg = (int)(0.349 * red + 0.686 * green + 0.168 * blue);
                int tb = (int)(0.272 * red + 0.534 * green + 0.131 * blue);

                // Clamp the values to the 0-255 range
                red = Math.min(255, tr);
                green = Math.min(255, tg);
                blue = Math.min(255, tb);

                // Set the new RGB value to the sepiaImage
                sepiaImage.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }

        return sepiaImage;
    }
}
