package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply an Emboss filter.
 * </p>
 * 
 * <p>
 * An Emboss filter create the effect of the image being pressed into or 
 * raised out of a sheet of paper. There are eight basic filters 
 * depending on the direction of embossing they simulate.
 * </p>
 * 
 * @see cosc202.andie.Convolution
 */
public class EmbossFilter implements ImageOperation, java.io.Serializable{

    /**
     * The radius of the filter
     */
    private final int RADIUS = 1;
    /**
     * The direction of the emboss filter
     */
    private int choice;
    /**
     * The kernels to apply to the image
     */
    private float[][] kernels = new float[][]{{0, -1, 0, 0, 0, 0, 0, 1, 0},
                                              {0, 0, -1, 0, 0, 0, 1, 0, 0},
                                              {0, 0, 0, 1, 0, -1, 0, 0, 0},
                                              {1, 0, 0, 0, 0, 0, 0, 0, -1},
                                              {0, 1, 0, 0, 0, 0, 0, -1, 0},
                                              {0, 0, 1, 0, 0, 0, -1, 0, 0},
                                              {0, 0, 0, -1, 0, 1, 0, 0, 0},
                                              {-1, 0, 0, 0, 0, 0, 0, 0, 1}};
    
    
    /**
     * Constructor for a new EmbossFilter object
     * 
     * @param choice the direction of the emboss filter
     */
    EmbossFilter(int choice){
        //account for out of bounds choices
        if(choice > 7){choice = 7;}
        if(choice < 0){choice = 0;}
        this.choice = choice;
    }

    /**
     * Constructor for a new EmbossFilter object
     * 
     * @param choice the direction of the emboss filter
     */
    EmbossFilter(){
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
     * Accesor for getChoice
     * 
     * @return choice the direction of the emboss filter
     */
    public int getChoice(){
        return choice;
    }

}
