package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to rotate a image by a specified angle.
 * </p>
 * 
 * <p>
 * Rotates a image by a user specified angle.
 * </p>
 * 
 * Some code taken from
 * https://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
 * 
 * Some code taken from
 * https://stackoverflow.com/questions/20275424/rotating-image-with-affinetransform
 * 
 * @author Toby Munyard
 * @version 1.0
 */
public class Rotate implements ImageOperation, java.io.Serializable {

    /**
     * The angle to rotate the image.
     */
    private double rotationAngle;

    /**
     * <p>
     * Construct a Rotate using the specified rotation angle.
     * </p>
     * 
     * <p>
     * Rotates a given image by the specified angle anywhere from 1 to
     * 360 degrees.
     * </p>
     * 
     * @param rotationAngle The angle the image is being rotated by.
     */
    Rotate(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    /**
     * <p>
     * Get the current rotation angle rotate the image by.
     * </p>
     * 
     * <p>
     * The angle rotates the image in a clockwise direction, the angle is in degrees, 180 will rotate it upside down, 90 will rotate it sideways, etc. 
     * </p>
     * 
     * @return The angle to rotate the image by.
     */
    public double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * <p>
     * Rotate the given image.
     * </p>
     * 
     * <p>
     * Rotates image by given rotation angle. Can be either clockwise or
     * counterclockwise
     * rotation based on input.
     * </p>
     * 
     * @param input The image to rotate.
     * @return The resulting rotated image.
     */
    public BufferedImage apply(BufferedImage input) {
        // Calculate the new width and height for the rotated image
        double rads = Math.toRadians(rotationAngle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = input.getWidth();
        int h = input.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        // Create a new BufferedImage with the new dimensions
        BufferedImage output = new BufferedImage(newWidth, newHeight, input.getType());
        Graphics2D g2d = output.createGraphics();

        // Translate the image to the center so rotation doesn't cut off part of the
        // image
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        // Rotate the image
        at.rotate(rads, w / 2, h / 2);

        // Transform and draw the rotated image
        g2d.setTransform(at);
        g2d.drawImage(input, 0, 0, null);
        g2d.dispose();

        return output;
    }

}