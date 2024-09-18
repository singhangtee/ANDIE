package cosc202.andie;

import java.awt.image.*;

/**
 * ImageOperation to invert the colour of an image.
 */
public class InvertColour implements ImageOperation, java.io.Serializable {

    /**<p>
     * Create a new InvertColour operation.
     * </p>
     */
    InvertColour() {

    }

    /**
     * <p>
     * Inverts each colour value
     * </p>
     * 
     * @param input The image to be inverted
     * @return The resulting inverted image.
     */
    public BufferedImage apply(BufferedImage input) {
  
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int rNew = (255-r);
                int gNew = (255-g);
                int bNew = (255-b);

                argb = (a << 24) | (rNew << 16) | (gNew << 8) | bNew;
                input.setRGB(x, y, argb);
            }
        }
        
        return input;
    }
    
}
