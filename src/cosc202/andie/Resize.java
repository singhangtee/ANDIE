package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to scale/resize a image.
 * </p>
 * 
 * <p>
 * Scales a image based on input of either a percentage or by width and height.
 * </p>
 * 
 * @author Toby Munyard
 * @version 1.0
 */
public class Resize implements ImageOperation, java.io.Serializable {

    /**
     * The percentage to scale the image by. Values greater than 100 will scale the
     * image up while values
     * less than 100 will scale the image down.
     */
    private float percentage;
    /**
     * The new width of output image.
     */
    private int width;
    /**
     * The new height of output image.
     */
    private int height;
    /**
     * Is the scaling happening by percentage?
     */
    private boolean isPercentageResize;
    /**
     * Is the image being scaled down?
     * True if percentage less than 100.
     * False if percentage greater than or equal to 100.
     */
    private boolean isScaleDown;

    /**
     * <p>
     * Construct a Resize using the given percentage.
     * </p>
     * 
     * <p>
     * If the percentage is less than 100 it will set the isScaleDown boolean to
     * true else it will be false.
     * This boolean dictates the type of image scaling used, either area averaging
     * or smooth interpolation.
     * </p>
     * 
     * @param percentage The percentage the image is being scaled by.
     */
    Resize(float percentage) {
        if (percentage < 100) {
            isScaleDown = true;
        } else {
            isScaleDown = false;
        }
        this.percentage = percentage / 100;
        isPercentageResize = true;
    }

    /**
     * <p>
     * Construct a Resize using the given width and height.
     * </p>
     * 
     * <p>
     * If the percentage is less than 100 it will set the isScaleDown boolean to
     * true else it will be false.
     * This boolean dictates the type of image scaling used, either area averaging
     * or smooth interpolation.
     * </p>
     * 
     * @param width  The width the image is being scaled to.
     * @param height The height the image is being scaled to.
     */
    Resize(int width, int height) {
        this.width = width;
        this.height = height;
        isPercentageResize = false;
    }

    /**
     * <p>
     * Construct a default Resize if no value given.
     * </p>
     * 
     * <p>
     * Calls the percentage Resize constructor with a value of 100, meaning image
     * stays the same size.
     * </p>
     */
    Resize() {
        this(100);
    }

    /**
     * <p>
     * Get the current percentage to resize the image to.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for resizing, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The percentage to scale the image by.
     */
    public float getPercentage(){
        return percentage;
    }

        /**
     * <p>
     * Get the current width to resize the image to.
     * </p>
     * 
     * <p>
     * The width will be the new width of the output image.
     * </p>
     * @return The width to scale the image to.
     */
    public float getWidth(){
        return width;
    }

        /**
     * <p>
     * Get the current height to resize the image to.
     * </p>
     * 
     * <p>
     * The height will be the new height of the output image. 
     * </p>
     * @return The height to scale the image to.
     */
    public float getHeight(){
        return height;
    }

    /**
     * <p>
     * Resize the given image.
     * </p>
     * 
     * <p>
     * Scales image based on input parameters. Works for both percentage scaling and
     * width/height scaling.
     * Utilises either area averaging or smooth interpolation depending on whether
     * the image is getting
     * larger or smaller.
     * </p>
     * 
     * @param input The image to resize.
     * @return The resulting resized image.
     */
    public BufferedImage apply(BufferedImage input) {
        Image scaledImg;
        BufferedImage output;

        // Scale using width and height
        if (!isPercentageResize) {
            // Is the image being scaled down?
            if (width < input.getWidth() || height < input.getHeight()) {
                // Image being scaled down

                // Scale the image using tbe new width and height using area averaging
                scaledImg = input.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);

                // Create a new BufferedImage with the new dimensions
                output = new BufferedImage(scaledImg.getWidth(null), scaledImg.getHeight(null), input.getType());
                Graphics2D g2d = output.createGraphics();

                // Draw the resized image
                g2d.drawImage(scaledImg, 0, 0, scaledImg.getWidth(null), scaledImg.getHeight(null), null);
                g2d.dispose();

                return output;
            } else {
                // Image being scaled up

                // Scale the image using the new width and height using smooth interpolation
                scaledImg = input.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                // Create a new BufferedImage with the new dimensions
                output = new BufferedImage(scaledImg.getWidth(null), scaledImg.getHeight(null), input.getType());
                Graphics2D g2d = output.createGraphics();

                // Draw the resized image
                g2d.drawImage(scaledImg, 0, 0, scaledImg.getWidth(null), scaledImg.getHeight(null), null);
                g2d.dispose();

                return output;
            }
        }
        // Scale using percentage

        // Is image beng scaled down?
        if (isScaleDown) {
            // Image being scaled down

            // Scale the image using input width * percentage and height * percentage using
            // area avearging
            scaledImg = input.getScaledInstance((int) (input.getWidth() * percentage),
                    (int) (input.getHeight() * percentage), Image.SCALE_AREA_AVERAGING);

            // Create a new BufferedImage with the new dimensions
            output = new BufferedImage(scaledImg.getWidth(null), scaledImg.getHeight(null),input.getType());
            Graphics2D g2d = output.createGraphics();

            // Draw the resized image
            g2d.drawImage(scaledImg, 0, 0, null);
            g2d.dispose();

            return output;
        } else {
            // Image being scaled up

            // Scale the image using input width * percentage and height * percentage using
            // smooth interpolation
            scaledImg = input.getScaledInstance((int) (input.getWidth() * percentage),
                    (int) (input.getHeight() * percentage), Image.SCALE_SMOOTH);

            // Create a new BufferedImage with the new dimensions
            output = new BufferedImage(scaledImg.getWidth(null), scaledImg.getHeight(null),input.getType());
            Graphics2D g2d = output.createGraphics();

            // Draw the resized image
            g2d.drawImage(scaledImg, 0, 0, null);
            g2d.dispose();

            return output;
        }
    }

}