package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Tile filter.
 * </p>
 * 
 * <p>
 * Internaly Known as tile filter this is a block median
 * </p>
 * 
 * 
 * @see java.awt.image.ConvolveOp
 */
public class BlockMeanFilter implements ImageOperation, java.io.Serializable {
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Tile filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the 'local neighbourhood'.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * </p>
     * 
     * @param radius The radius of the newly constructed TileFilter
     */
    BlockMeanFilter(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Tile filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Tile filter has radius 1.
     * </p>
     * 
     * @see TileFilter(int)
     */
    BlockMeanFilter() {
        this(1);
    }

    /**
     * <p>
     * Constructs a tile from input parameters then outputs the image with the tile edited to be the singular median result of all pixels colours.
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
    private BufferedImage processTile(int[] array, int size, BufferedImage input, int xScalar, int yScalar){
        //this could be done way better
        //its very bad that the method passes in the entire image every time
        
        //this is very clunky and inefficent but it works
        int arrCount = 0;
        for (int y = 0; y < (2*radius+1); y++){
            for (int x = 0; x < (2*radius+1); x++){
                int argb = input.getRGB(x+xScalar, y+yScalar);
                array[arrCount] = argb;
                arrCount++;
            }
        }
        int [] alphas = new int[size];
        int [] reds = new int[size];
        int [] greens = new int[size];
        int [] blues = new int[size];
        //seperating colour values to be processed individually
        for (int i = 0; i < array.length; i++){
            alphas[i] = (array[i] & 0xFF000000) >> 24;
            reds[i] = (array[i] & 0x00FF0000) >> 16;
            greens[i] = (array[i] & 0x0000FF00) >> 8;
            blues[i] = (array[i] & 0x000000FF);
        }
        //sorting the arrays so that the tile value can be taken
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        for (int i : array){
            redSum += reds[i];
            greenSum += greens[i];
            blueSum += blues[i];
        }

        //applying changes to the image
        for (int y = 0; y < (2*radius+1); y++){
            for (int x = 0; x < (2*radius+1); x++){
                int argb = (alphas[0] << 24) | (redSum/reds.length << 16) | (greenSum/greens.length << 8) | blueSum/blues.length;
            }
        }
        
        return input;
    }

    private BufferedImage processXEdge(int tempX, BufferedImage input, int maxX, int yScalar){
        //this could be done way better
        //its very bad that the method passes in the entire image every time
        //this is very clunky and inefficent but it works
        int arrCount = 0;
        int[] array = new int[(2*radius+1)*tempX];;
        for (int y = 0; y < (2*radius+1); y++){
            for (int x = 0; x < tempX; x++){
                int argb = input.getRGB(x+((maxX*(2*radius+1))), y+yScalar);
                array[arrCount] = argb;
                arrCount++;
            }
        }
        int [] alphas = new int[array.length];
        int [] reds = new int[array.length];
        int [] greens = new int[array.length];
        int [] blues = new int[array.length];
        
        //seperating colour values to be processed individually
        for (int i = 0; i < array.length; i++){
            alphas[i] = (array[i] & 0xFF000000) >> 24;
            reds[i] = (array[i] & 0x00FF0000) >> 16;
            greens[i] = (array[i] & 0x0000FF00) >> 8;
            blues[i] = (array[i] & 0x000000FF);
        }
        //sorting the arrays so that the tile value can be taken
        //Arrays.sort(alphas);
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        for (int i : array){
            redSum += reds[i];
            greenSum += greens[i];
            blueSum += blues[i];
        }

        //applying changes to the image
        /*for (int y = 0; y < (2*radius+1); y++){
            for (int x = 0; x < (2*radius+1); x++){
                int argb = (alphas[alphas.length/2] << 24) | (reds[reds.length/2] << 16) | (greens[greens.length/2] << 8) | blues[blues.length/2];
                input.setRGB(x+xScalar, y+yScalar, argb);
            }
        }*/
        for (int y = 0; y < (2*radius+1); y++){
            for (int x = 0; x < tempX; x++){
                int argb = (alphas[0] << 24) | (redSum/reds.length << 16) | (greenSum/greens.length << 8) | blueSum/blues.length;
                input.setRGB(x+((maxX*(2*radius+1))), y+yScalar, argb);
            }
        }
        //System.out.println("done");
        return input;
    }

    private BufferedImage processYEdge(int tempY, BufferedImage input, int xScalar, int maxY){
        //this could be done way better
        //its very bad that the method passes in the entire image every time
        //this is very clunky and inefficent but it works
        int arrCount = 0;
        int[] array = new int[(2*radius+1)*tempY];;
        for (int y = 0; y < tempY; y++){
            for (int x = 0; x < (2*radius+1); x++){
                int argb = input.getRGB(x+xScalar, y+((maxY*(2*radius+1))));
                array[arrCount] = argb;
                arrCount++;
            }
        }
        int [] alphas = new int[array.length];
        int [] reds = new int[array.length];
        int [] greens = new int[array.length];
        int [] blues = new int[array.length];
        
        //seperating colour values to be processed individually
        for (int i = 0; i < array.length; i++){
            alphas[i] = (array[i] & 0xFF000000) >> 24;
            reds[i] = (array[i] & 0x00FF0000) >> 16;
            greens[i] = (array[i] & 0x0000FF00) >> 8;
            blues[i] = (array[i] & 0x000000FF);
        }
        //sorting the arrays so that the tile value can be taken
        //Arrays.sort(alphas);
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        for (int i : array){
            redSum += reds[i];
            greenSum += greens[i];
            blueSum += blues[i];
        }

        //applying changes to the image
        /*for (int y = 0; y < (2*radius+1); y++){
            for (int x = 0; x < (2*radius+1); x++){
                int argb = (alphas[alphas.length/2] << 24) | (reds[reds.length/2] << 16) | (greens[greens.length/2] << 8) | blues[blues.length/2];
                input.setRGB(x+xScalar, y+yScalar, argb);
            }
        }*/
        for (int y = 0; y < tempY; y++){
            for (int x = 0; x < (2*radius+1); x++){
                int argb = (alphas[0] << 24) | (redSum/reds.length << 16) | (greenSum/greens.length << 8) | blueSum/blues.length;
                input.setRGB(x+xScalar, y+((maxY*(2*radius+1))), argb);
            }
        }
        //System.out.println("done");
        return input;
    }

    private BufferedImage processEdge(int tempX, int tempY, BufferedImage input, int maxX, int maxY){
        //this could be done way better
        //its very bad that the method passes in the entire image every time
        //this is very clunky and inefficent but it works
        int arrCount = 0;
        int[] array = new int[tempX*tempY];;
        for (int y = 0; y < tempY; y++){
            for (int x = 0; x < tempX; x++){
                int argb = input.getRGB(x+(maxX*(2*radius+1)), y+((maxY*(2*radius+1))));
                array[arrCount] = argb;
                arrCount++;
            }
        }
        int [] alphas = new int[array.length];
        int [] reds = new int[array.length];
        int [] greens = new int[array.length];
        int [] blues = new int[array.length];
        
        //seperating colour values to be processed individually
        for (int i = 0; i < array.length; i++){
            alphas[i] = (array[i] & 0xFF000000) >> 24;
            reds[i] = (array[i] & 0x00FF0000) >> 16;
            greens[i] = (array[i] & 0x0000FF00) >> 8;
            blues[i] = (array[i] & 0x000000FF);
        }
        //sorting the arrays so that the tile value can be taken
        //Arrays.sort(alphas);
        //Arrays.sort(reds);
        //Arrays.sort(greens);
        //Arrays.sort(blues);
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        for (int i : array){
            redSum += reds[i];
            greenSum += greens[i];
            blueSum += blues[i];
        }
        
        //applying changes to the image
        /*for (int y = 0; y < (2*radius+1); y++){
            for (int x = 0; x < (2*radius+1); x++){
                int argb = (alphas[alphas.length/2] << 24) | (reds[reds.length/2] << 16) | (greens[greens.length/2] << 8) | blues[blues.length/2];
                input.setRGB(x+xScalar, y+yScalar, argb);
            }
        }*/
        for (int y = 0; y < tempY; y++){
            for (int x = 0; x < tempX; x++){
                int argb = (alphas[0] << 24) | (redSum/reds.length << 16) | (greenSum/greens.length << 8) | blueSum/blues.length;
                input.setRGB(x+(maxX*(2*radius+1)), y+((maxY*(2*radius+1))), argb);
            }
        }
        //System.out.println("done");
        return input;
    }
    /**
     * <p>
     * Apply a Tile filter to an image.
     * </p>
     * 
     * <p>
     * Takes in the image and then gets the size of the image which it uses to proccess the image in tiles
     * it then applys the tile filter to each tile one at a time appliying the changes each time using the process tile function
     * </p>
     * 
     * @param input The image to apply the Tile filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = (2*radius+1) * (2*radius+1);
        int [] array = new int[size];
        int maxX = (input.getWidth()/(2*radius+1));
        int maxY = (input.getHeight()/(2*radius+1));
        for (int x = 0; x < maxX; x++){
            for (int y = 0; y < maxY; y++){
                input = processTile(array, size, input, x*(2*radius+1), y*(2*radius+1));
            }
            
        }
        //its pretty bad that 3 different functions have been made to handle the edge cases - especially since the functions are really similar
        int tempX = input.getWidth() - (maxX*(2*radius+1));
        int tempY = input.getHeight() - (maxY*(2*radius+1));
        if (tempX>0){
            for (int y = 0; y < maxY; y++){
                input = processXEdge(tempX, input, maxX, y*(2*radius+1));
            }
        }
        if (tempY>0){
            for (int x = 0; x < maxX; x++){
                input = processYEdge(tempY, input, x*(2*radius+1), maxY);
            }
        }
        if ((tempY>0)&&(tempX>0)){
            input = processEdge(tempX, tempY, input, maxX, maxY);
        }
        return input;
    }

    /**
     * Gets the radius of the filter.
     * 
     * @return The radius of the filter.
     */
    public int getRadius(){
        return radius;
    }


}

