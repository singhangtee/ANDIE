package cosc202.andie;

import java.awt.image.*;
import java.io.Serializable;

/**
 * <p>
 * ImageOperation to apply a Vignette filter.
 * </p>
 * 
 * <p>
 * A Sepia Tone filter gives an image a vignette effect, darkening the corners of the image to draw focus to the center.
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
public class VignetteFilter implements ImageOperation, Serializable {
    
    /**
     * <p>
     * Apply a vignette filter to the input image
     * </p>
     * 
     * <p>
     * This filter calculate each pixel from the center of the imageApply a vignette effect to each pixel based on its distance from the center, 
     * typically by reducing the brightness or saturation.
     * </p>
     * 
     * @param input The image to apply the vignette filter to.
     * @return The resulting image with a vignette effect.
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Maximum distance from the center of the image
        double maxDistance = Math.sqrt(centerX * centerX + centerY * centerY);

        // Vignette strength
        double vignetteStrength = 0.5; // Adjust as needed

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Calculate distance from center
                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                double normalizedDistance = distance / maxDistance;

                // Calculate vignette factor (0 at the center, 1 at the corners)
                double vignetteFactor = Math.pow(1 - normalizedDistance, vignetteStrength);

                // Get RGB values of the pixel
                int rgb = input.getRGB(x, y);

                // Apply vignette effect
                int red = (int) (vignetteFactor * ((rgb >> 16) & 0xFF));
                int green = (int) (vignetteFactor * ((rgb >> 8) & 0xFF));
                int blue = (int) (vignetteFactor * (rgb & 0xFF));

                // Combine RGB values
                int modifiedRGB = (red << 16) | (green << 8) | blue;

                // Set the modified pixel in the output image
                output.setRGB(x, y, modifiedRGB);
            }
        }

        return output;
    }
}
