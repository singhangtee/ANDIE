package cosc202.andie;

import java.awt.image.*;
import java.util.*;
import java.text.DecimalFormat;

/**
 * <p>
 * ImageOperation to apply a Gaussian blur filter.
 * </p>
 * 
 * <p>
 * A Gaussian blur filter smoothes uneven pixel values in an image by cutting
 * out the extreme outliers.
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Gaussian Blur filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the 'local neighbourhood'.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianBlur
     */
    GaussianBlur(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Gaussian Blur filter with the default size.
     * </p>
     * 
     * <p>
     * By default, a Gaussian Blur filter has radius 1.
     * </p>
     * 
     * @see GaussianBlur(int)
     */
    GaussianBlur() {
        this(1);
    }

    /**
     * <p>
     * Constructs a kernel and applies a convolution to the image using the kernel.
     * The kernel is a square matrix of size 2*radius + 1 and the entries in the
     * matrix are determined by a 2-dimensional Gaussian function.
     * </p>
     * 
     * @param input The image to apply the blur to.
     * @return The resulting blurred image.
     */
    public BufferedImage apply(BufferedImage input) {
        DecimalFormat df = new DecimalFormat("#.###");
        int d = 2 * radius + 1;
        float[][] array2D = new float[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                double val = gaussian(j - radius, i - radius, ((double) radius) / 3.0);
                array2D[i][j] = Float.valueOf(df.format(val));
            }
        }

        // flatten the 2D array into an arraylist
        ArrayList<Float> al = new ArrayList<Float>();
        for (int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < array2D[i].length; j++) {
                al.add(array2D[i][j]);
            }
        }

        /**
         * Convert the arraylist into an array of floats, while adding each entry to
         * find the sum of entries "total"
         */
        float[] array = new float[al.size()];
        float total = 0;
        for (int i = 0; i < al.size(); i++) {
            total += al.get(i);
            array[i] = al.get(i);
        }

        /**
         * Convert the kernel array into a "normalised" kernel by dividing each entry by
         * total, solving an issue with the image getting brighter instead of blurring
         * it.
         */
        float[] arrayNorm = new float[array.length];
        for (int i = 0; i < arrayNorm.length; i++) {
            arrayNorm[i] = array[i] / total;
        }

        /**
         * Creates a kernel object with the normalised kernel array and colvolves the
         * image using the kernel.
         */
        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, arrayNorm);
        /*BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);*/
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        Convolution conv = new Convolution();
        output = conv.convolveImage(input, output, kernel, radius);

        return output;
    }

    /**
     * <p>
     * Applies the 2D Gaussian function to a number passed in
     * </p>
     * 
     * @param x     The displacement of the index from the center of the matrix on
     *              the x axis.
     * @param y     The displacement of the index from the center of the matrix on
     *              the y axis.
     * @param sigma One third of the kernel radius.
     * @return The number calculated from the operation.
     */
    public double gaussian(int x, int y, double sigma) {
        double d = ((1 / (2 * Math.PI * (sigma * sigma))) * Math.pow(Math.E, -((x * x + y * y) / (2 * sigma * sigma))));
        return d;
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
