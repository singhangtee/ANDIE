package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to flip a image horizontally or vertically.
 * </p>
 * 
 * <p>
 * Flips a image either horizontally or vertically based on user choice.
 * Uses AffineTransform to perform the flip operation.
 * </p>
 * 
 * Some code taken from
 * https://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
 * 
 * @author Toby Munyard
 * @version 1.0
 */
public class Flip implements ImageOperation, java.io.Serializable {

    /**
     * The type of flip operation, vertical if true, or horizontal if false.
     */
    private boolean isVerticalFlip;

    /**
     * <p>
     * Construct a Flip using the specified flip type.
     * </p>
     * 
     * <p>
     * If isVerticalFlip is true the flip operation will be vertical. Otherwise
     * the flip operation will be horizontal.
     * </p>
     * 
     * @param isVerticalFlip Whether the image is flipped vertically, true if
     *                       vertical, false if horizontal.
     */
    Flip(boolean isVerticalFlip) {
        this.isVerticalFlip = isVerticalFlip;
    }

    /**
     * <p>
     * Get the type of flip action occuring.
     * </p>
     * 
     * <p>
     * The type of flip that will occur from this Flip operation. Vertical if true, horizontal if false.
     * </p>
     * 
     * @return Whether the flip is vertical or horizontal, true if vertical, false if horizontal.
     */
    public boolean getIsVerticalFlip() {
        return isVerticalFlip;
    }

    /**
     * <p>
     * Flip the given image.
     * </p>
     * 
     * <p>
     * Flips image either vertically or horizontally based on input.
     * Utilises AffineTransform to scale and translate the instance.
     * </p>
     * 
     * @param input The image to flip.
     * @return The resulting flipped image.
     */
    public BufferedImage apply(BufferedImage input) {
        // Create a new AffineTransform
        AffineTransform transform = new AffineTransform();

        // Create a new BufferedImage with input's dimensions
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        Graphics2D g2d = output.createGraphics();

        if (isVerticalFlip) {
            // Vertical flip

            // Scale and translate the AffineTransform
            transform.concatenate(AffineTransform.getScaleInstance(1, -1));
            transform.concatenate(AffineTransform.getTranslateInstance(0, -input.getHeight()));

            // Transform and draw the vertically flipped image
            g2d.transform(transform);
            g2d.drawImage(input, 0, 0, null);
            g2d.dispose();

            return output;
        } else {
            // Horizontal flip

            // Scale and translate the AffineTransform
            transform.concatenate(AffineTransform.getScaleInstance(-1, 1));
            transform.concatenate(AffineTransform.getTranslateInstance(-input.getWidth(), 0));

            // Transform and draw the horizontally flipped image
            g2d.transform(transform);
            g2d.drawImage(input, 0, 0, null);
            g2d.dispose();

            return output;
        }
    }

}
