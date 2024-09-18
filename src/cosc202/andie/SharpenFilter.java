package cosc202.andie;

import java.awt.image.*;

/**
 * ImageOperation to apply a sharpen filter.
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    

    /**
     * Default Constructor for a Sharpen Filter;
     */
    SharpenFilter(){}

    /**
     * Apply sharpen filter to a image.
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting sharpened image.
     */
    public BufferedImage apply(BufferedImage input) {
        float [] array = {  0       , -1/2f , 0,
                            -1/2f   , 3     , -1/2f,
                            0       , -1/2f , 0};

        Kernel kernel = new Kernel(3, 3, array);
        //BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

        Convolution conv = new Convolution();
        output = conv.convolveImage(input, output, kernel, 1);
        
        return output;
    }

}
