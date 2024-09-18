package cosc202.andie;
import java.awt.image.*;

/**
 * ImageOperation to apply a spoft blur filter.
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {

    /**
     * Construct a Soft Blur filter
     */
    SoftBlur() {}

    
    /**
     * Apply soft blur filter to a image.
     * 
     * @param input The image to apply the soft blur filter to.
     * @return The resulting blurred image.
     */
    public BufferedImage apply (BufferedImage input) {
        // Some work to do here...
        // The values for the kernel as a 9-element array
    float [] array = { 0 , 1/8.0f, 0 , 1/8.0f, 1/2.0f, 1/8.0f, 0 , 1/8.0f, 0 };
    // Make a 3x3 filter from the array
    Kernel kernel = new Kernel(3, 3, array);
    // Apply this as a convolution - same code as in MeanFilter
    //BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
    BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
    Convolution conv = new Convolution();
    output = conv.convolveImage(input, output, kernel, 1);
    // And we're done
    return output;
    }

}