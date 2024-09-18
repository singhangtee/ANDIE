package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Minimum filter.
 * </p>
 * 
 * 
 * 
 * @see java.awt.image.ConvolveOp
 */
public class MinimumFilter implements ImageOperation, java.io.Serializable {
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Minimum filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the 'local neighbourhood'.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * </p>
     * 
     * @param radius The radius of the newly constructed MinimumFilter
     */
    MinimumFilter(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Minimum filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Minimum Filter has radius 1.
     * </p>
     * 
     * @see MinimumFilter(int)
     */
    MinimumFilter() {
        this(1);
    }

    /**
     * <p>
     * Constructs a tile from input parameters then outputs the image with the tile edited to be the singular Minimum result of all pixels colours.
     * </p
     * >
     * 
     * @param array the initial array that represents the tile
     * @param size the size of the tile
     * @param input the input image
     * @param xScalar the x offset of the tile from x = 0
     * @param yScalar the y offset of the tile from y = 0
     * @return
    */
    private BufferedImage processTile(int x, int y, BufferedImage input, BufferedImage output, int maxX, int maxY){
        int arrSize = 0;
        for (int i = x-radius; i <= x+radius; i++){
            if (i >= 0 && i < maxX){
                for (int j = y-radius;j <= y+radius; j++){
                    if (j >= 0 && j < maxY){
                        arrSize++;
                    }
                }
            }
        }
        int count = 0;
        int[] arr = new int[arrSize];
        for (int i = x-radius; i <= x+radius; i++){
            if (i >= 0 && i < maxX){
                for (int j = y-radius;j <= y+radius; j++){
                    if (j >= 0 && j < maxY){
                        int argb = input.getRGB(i, j);
                        arr[count] = argb;
                        count++;
                    }
                }
            }
        }
        int [] alphas = new int[arrSize];
        int [] reds = new int[arrSize];
        int [] greens = new int[arrSize];
        int [] blues = new int[arrSize];
        for (int i = 0; i < arr.length; i++){
            alphas[i] = (arr[i] & 0xFF000000) >> 24;
            reds[i] = (arr[i] & 0x00FF0000) >> 16;
            greens[i] = (arr[i] & 0x0000FF00) >> 8;
            blues[i] = (arr[i] & 0x000000FF);
        }
        Arrays.sort(reds);
        Arrays.sort(greens);
        Arrays.sort(blues);
        int argb = (alphas[0] << 24) | (reds[0] << 16) | (greens[0] << 8) | blues[0];
        output.setRGB(x, y, argb);
        return output;
    }

    /**
     * <p>
     * Apply a Minimum filter to an image.
     * </p>
     * 
     * <p>
     * Takes in the image and then gets the size of the image which it uses to proccess the image in tiles
     * it then applys the Minimum filter to each tile one at a time appliying the changes each time using the process tile function
     * </p>
     * 
     * @param input The image to apply the Minimum filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        int maxX = (input.getWidth());
        int maxY = (input.getHeight());
        for (int x = 0; x < maxX; x++){
            for (int y = 0; y < maxY; y++){
                output = processTile(x, y, input, output, maxX, maxY);
            }
        }
        return output;
    }

    /**
    * <p>
    * This method returns the radius of the circle.
    * </p>
    *
    * @return The radius of the circle.
    */
    public int getRadius(){
        return radius;
    }


}

