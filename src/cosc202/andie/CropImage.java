package cosc202.andie;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

/**
 * Class to crop image to specified area.
 */
public class CropImage implements ImageOperation, java.io.Serializable {

    /** Start point of the crop area on X axis */
    int startPointX;
    /** Start point of the crop area on Y axis */
    int startPointY;
    /** End point of the crop area on X axis */
    int endPointX;
    /** End point of the crop area on Y axis */
    int endPointY;

    /**
     * Creates a new crop image instance using a start and end point.
     * 
     * @param startPoint The start point of the crop.
     * @param endPoint The end point of the crop.
     */
    public CropImage(Point startPoint, Point endPoint) {
        this.startPointX = (int) startPoint.getX();
        this.startPointY = (int) startPoint.getY();
        this.endPointX = (int) endPoint.getX();
        this.endPointY = (int) endPoint.getY();
    }

    /**
     * Gets the X location of the mouse selection area.
     * 
     * @return The X location of the mouse selection area.
     */
    public int getX() {
        return Math.min(startPointX, endPointX);
    }

    /**
     * Gets the Y location of the mouse selection area.
     * 
     * @return The Y location of the mouse selection area.
     */
    public int getY() {
        return Math.min(startPointY, endPointY);
    }

    /**
     * Gets the width of the mouse selection area.
     * 
     * @return The width of the mouse selection area.
     */
    public int getWidth() {
        return Math.abs(startPointX - endPointX);
    }

    /**
     * Gets the height of the mouse selection area.
     * 
     * @return The height of the mouse selection area.
     */
    public int getHeight() {
        return Math.abs(startPointY - endPointY);
    }

    /**
     * Crops a BufferedImage to the size of a mouse selected area.
     * 
     * @param input The input image to apply the crop to.
     * @return The resulting cropped image, based on the selected area of the
     *         MouseListener.
     */
    public BufferedImage apply(BufferedImage input) throws RasterFormatException {
        BufferedImage output = input.getSubimage(getX(), getY(), getWidth(), getHeight());
        MyMouseListener.setIsRegionSelection(false);
        return output;
    }
}
