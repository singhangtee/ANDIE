package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.awt.*;

/**
 * Selects the region of a image using MouseListener.
 */
public class SelectRegion implements ImageOperation, java.io.Serializable {

    /** X value of selected area. */
    int x;
    /** Y value of selected area. */
    int y;
    /** Width of selected area. */
    int width;
    /** Height of selected area. */
    int height;
    
    /**
     * Default Constructor
     */
    public SelectRegion(){
    }

    /**
     * <p>
     * Select an area on a given image.
     * </p>
     * 
     * <p>
     * Selects a given region based on X, Y, Width and Height values.
     * </p>
     * 
     * @param input The image to select.
     * @return The resulting selected image.
     */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        Graphics2D g2d = output.createGraphics();
        g2d.setColor(Color.RED);
        g2d.draw(MyMouseListener.getSelectedRegion());
        return output;
    }

}
