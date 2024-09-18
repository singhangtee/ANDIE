package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

/**
 * <p>
 * ImageOperation to apply a Sobel edge detection filter.
 * </p>
 * 
 * <p>
 * A Sobel Filter is a typical edge detection filter which highlights 
 * the areas of the image where there are rapid changes in brightness
 * </p>
 * 
 * @see cosc202.andie.Convolution
 */
public class SobelFilter implements ImageOperation, java.io.Serializable {

    /**
     * The radius of the filter
     */ 
    private final int RADIUS = 1;
    /**
     * The direction of the filter
     */
    private int choice;
    /**
     * The kernels to apply to the image
     */
    private float[][] kernels = new float[][]{{-0.5f, 0f, 0.5f, -1f, 0f, 1f, -0.5f, 0f, 0.5f},
                                              {-0.5f, -1f, -0.5f, 0f, 0f, 0f, 0.5f, 1f, 0.5f}};
    
    
    /**
     * Constructor for a new SobelFilter object.
     * 
     * @param choice The direction of the filter (vertical or horizontal)
     */
    SobelFilter(int choice){
        if(choice > 1){choice = 1;}
        if(choice < 0){choice = 0;}
        this.choice = choice;
    }

    /**
     * Default Constructor for a new SobelFilter object.
     * 
     * @param choice the direction of the filter (vertical or horizontal), by default it is the 
     * horizontal direction.
     */
    SobelFilter(){
        this.choice = 0;
    }

    /**
     * applies the filter to the image via a convoltion
     * 
     * @param input The image to be filtered.
     * @return The resulting filtered image.
     * @see cosc202.andie.Convolution 
     */
    public BufferedImage apply(BufferedImage input) {
        float [] array = kernels[choice];
        
        Convolution convolution = new Convolution();

        Kernel kernel = new Kernel(2*RADIUS+1, 2*RADIUS+1, array);
        //BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

        output = convolution.convolveImageWithOffset(input, output, kernel, RADIUS);
        return output;
    }

    /**
     * getter method for data field choice - useful for testing
     * 
     * @return choice
     */
    public int getChoice(){
        return choice;
    }

}
