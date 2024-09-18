package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

/**
 * Class for convolving images using kernels.
 */
public class Convolution {
    

    /**
     * Default Constructor
     */
    Convolution(){}

    /**
     * <p>
     * Convolution Method
     * </p>
     * 
     * <p>
     * Convolves an image with a specefied kernel.
     * Pixels outside the image take the colour value of the nearest valid pixel.
     * </p>
     * 
     * @param input The image to convolve
     * @param output The convolved image
     * @param kernel The kernel to apply
     * @param radius The radius of the kernel
     * @return The convolved image
     */
    public BufferedImage convolveImage(BufferedImage input, BufferedImage output, Kernel kernel, int radius) {
        float[] matrix = kernel.getKernelData(null);
        int col = kernel.getWidth();
        for (int y=0; y < input.getHeight(); y++) {         // For each pixel in image
            for (int x=0; x < input.getWidth(); x++) {
                float newA = 0;
                float newR = 0;
                float newG = 0;
                float newB = 0;
                for (int ky = -radius; ky <= radius; ky++) {        // For each pixel in kernel
                    for (int kx = -radius; kx <= radius; kx++) {
                        if (matrix[(ky+radius)*col+kx+radius] == 0) continue;   // Ignore pixels that are 0 in the kernel

                        int pixelX = Math.min(Math.max(x+kx, 0), input.getWidth()-1);   // If pixel is outside of the image, use the nearest pixel instead
                        int pixelY = Math.min(Math.max(y+ky, 0), input.getHeight()-1);

                        int argb = input.getRGB(pixelX, pixelY);    // Get colour of the pixel
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);

                        newA += a * matrix[(ky+radius)*col+kx+radius];      // Add pixels weighted colour to the final pixel colour
                        newR += r * matrix[(ky+radius)*col+kx+radius];
                        newG += g * matrix[(ky+radius)*col+kx+radius];
                        newB += b * matrix[(ky+radius)*col+kx+radius];
                    }
                }

                int a = (int) Math.min(Math.max(newA, 0), 255);         // Turn into int and clamp to colour range
                int r = (int) Math.min(Math.max(newR, 0), 255);
                int g = (int) Math.min(Math.max(newG, 0), 255);
                int b = (int) Math.min(Math.max(newB, 0), 255);
                
                int argb = (a << 24) | (r << 16) | (g << 8) | b;        // Set output pixel colour to new colour
                output.setRGB(x, y, argb);
            }
        }
        return output;
    }

    /**
     * <p>
     * Convolution with offset Method
     * </p>
     * 
     * <p>
     * Convolves an image with a specefied kernel.
     * 
     * Pixels outside the image take the colour value of the nearest valid pixel.
     * 
     * Negative results from the convolution are accounted for by being shifted by an 
     * offset instead of being clipped - useful for edge detection filters. 
     * </p>
     * 
     * @param input The image to convolve
     * @param output The convolved image
     * @param kernel The kernel to apply
     * @param radius The radius of the kernel
     * @return The convolved image
     */
    public BufferedImage convolveImageWithOffset(BufferedImage input, BufferedImage output, Kernel kernel, int radius) {
        float[] matrix = kernel.getKernelData(null);
        int col = kernel.getWidth();
        for (int y=0; y < input.getHeight(); y++) {         // For each pixel in image
            for (int x=0; x < input.getWidth(); x++) {
                float newA = 0;
                float newR = 0;
                float newG = 0;
                float newB = 0;
                for (int ky = -radius; ky <= radius; ky++) {        // For each pixel in kernel
                    for (int kx = -radius; kx <= radius; kx++) {
                        if (matrix[(ky+radius)*col+kx+radius] == 0) continue;   // Ignore pixels that are 0 in the kernel

                        int pixelX = Math.min(Math.max(x+kx, 0), input.getWidth()-1);   // If pixel is outside of the image, use the nearest pixel instead
                        int pixelY = Math.min(Math.max(y+ky, 0), input.getHeight()-1);

                        int argb = input.getRGB(pixelX, pixelY);    // Get colour of the pixel
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);

                        newA += a * matrix[(ky+radius)*col+kx+radius];      // Add pixels weighted colour to the final pixel colour
                        newR += r * matrix[(ky+radius)*col+kx+radius];
                        newG += g * matrix[(ky+radius)*col+kx+radius];
                        newB += b * matrix[(ky+radius)*col+kx+radius];
                    }
                }

                int a = (int) Math.min(Math.max(newA, 0), 255);         // Turn into int and clamp to colour range
                int r = (int) Math.min(Math.max(newR / 2 + 127, 0), 255);
                int g = (int) Math.min(Math.max(newG / 2 + 127, 0), 255);
                int b = (int) Math.min(Math.max(newB / 2 + 127, 0), 255);
                
                int argb = (a << 24) | (r << 16) | (g << 8) | b;        // Set output pixel colour to new colour
                output.setRGB(x, y, argb);
            }
        }
        return output;
    }
}
