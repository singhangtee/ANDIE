package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Draws shapes onto the input image based on user input of type of shape,
 * rectangle, oval or line. As well as colour of the shape.
 */
public class DrawShapes implements ImageOperation, java.io.Serializable {

    /** The type of shape to be drawn */
    private int shapeType;
    /** The colour of the shape to be drawn */
    private Color colourToDraw;
    /** The x coordinate for the selection */
    private int x;
    /** The y coordinate for the selection */
    private int y;
    /** The width for the selection */
    private int width;
    /** The height for the selection */
    private int height;
    /** The start point for the selection */
    private Point startPoint;
    /** The end point for the selection */
    private Point endPoint;

    /**
     * Initializes a new instance of DrawShapes with the specified shape type as the
     * parameter.
     * 
     * @param shapeType The type of shape to be drawn.
     */
    public DrawShapes(int shapeType) {
        this.shapeType = shapeType;
        MyMouseListener.setShapeType(shapeType);
    }

    /**
     * Sets the colour for the drawing tool.
     * 
     * @param colour The colour for the drawing tool.
     */
    public void setColor(Color colour) {
        colourToDraw = colour;
    }

    /**
     * Set the region of the shape drawing tool.
     * 
     * @param start The start position of the region of the shape drawing tool.
     * @param end The end position of the region of the shape drawing tool.
     */
    public void setRegion(Point start, Point end) {
        this.startPoint = start;
        this.endPoint = end;
    }

    /**
     * Gets the type of shape being drawn.
     * 
     * @return The type of shape being drawn.
     */
    public int getShapeType() {
        return shapeType;
    }

    /**
     * Sets the type of shape being drawn.
     * 
     * @param shapeType The type of shape being drawn.
     */
    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    /**
     * Called by the apply method to draw a given shape and colour.
     * 
     * @param g2d The graphics object used to draw the shape.
     */
    public void drawShape(Graphics2D g2d) {
        x = Math.min(startPoint.x, endPoint.x);
        y = Math.min(startPoint.y, endPoint.y);
        width = Math.abs(startPoint.x - endPoint.x);
        height = Math.abs(startPoint.y - endPoint.y);

        g2d.setColor(colourToDraw);

        switch (shapeType) {
            case 0: // Rectangle
                g2d.fillRect(x, y, width, height);
                break;
            case 1: // Oval
                g2d.fillOval(x, y, width, height);
                break;
            case 2: // Line
                g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                break;
            default:
                return;
        }

    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics();
        drawShape(g2d);
        g2d.dispose();
        return input;
    }
}