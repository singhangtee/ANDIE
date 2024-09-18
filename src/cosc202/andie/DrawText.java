package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Draws text of a users choice onto the image. Allows for customisation of all
 * parts of text including, size, colour, style and font.
 */
public class DrawText implements ImageOperation, java.io.Serializable {

    /** The text to be drawn on the image */
    private String text;
    /** The colour of the text to be drawn */
    private Color colourToDraw;
    /** The font size of the text to be drawn */
    private int fontSize;
    /** The font of the text to be drawn */
    private String fontType;
    /** The font font style of the text to be drawn */
    private int fontStyle;
    /** The x coordinate for the selection */
    private int x;
    /** The y coordinate for the selection */
    private int y;
    /** The start point for the selection */
    private Point startPoint;
    /** The end point for the selection */
    private Point endPoint;

    /**
     * Initializes a new instance of DrawText with the specified text as the
     * parameter.
     * 
     * @param text The text to be drawn on the image.
     * @param fontSize The font size of the text to be drawn on the image.
     * @param font The font type of the text to be drawn on the image.
     * @param fontStyle The font style of the text to be drawn on the image.
     */
    public DrawText(String text, int fontSize, String font, int fontStyle) {
        setText(text);
        setFontSize(fontSize);
        setFont(font);
        setFontStyle(fontStyle);
        MyMouseListener.setText(text);
        MyMouseListener.setFontSize(fontSize);
        MyMouseListener.setFont(font);
        MyMouseListener.setFontStyle(fontStyle);
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
     * Sets the start and end point for area selection.
     * 
     * @param start The start point for area selection.
     * @param end   The end point for area selection.
     */
    public void setRegion(Point start, Point end) {
        this.startPoint = start;
        this.endPoint = end;
    }

    /**
     * Gets the text being drawn onto the image.
     * 
     * @return The text being drawn onto the image.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the font size of the text being drawn onto the image.
     * 
     * @return The font size of the text being drawn onto the image.
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Gets the font type of the text being drawn onto the image.
     * 
     * @return The font type of the text being drawn onto the image.
     */
    public String getFont() {
        return fontType;
    }

    /**
     * Gets the font style of the text being drawn onto the image.
     * 
     * @return The font style of the text being drawn onto the image.
     */
    public int getFontStyle() {
        return fontStyle;
    }

    /**
     * Sets the text being drawn onto the image.
     * 
     * @param newText The text being drawn onto the image.
     */
    public void setText(String newText) {
        text = newText;
    }

    /**
     * Sets the font size of the text being drawn onto the image.
     * 
     * @param newFontSize The font size of the text being drawn onto the image.
     */
    public void setFontSize(int newFontSize) {
        fontSize = newFontSize;
    }

    /**
     * Sets the font of the text being drawn onto the image.
     * 
     * @param newFont The font of the text being drawn onto the image.
     */
    public void setFont(String newFont) {
        fontType = newFont;
    }

    /**
     * Sets the font style of the text being drawn onto the image.
     * 
     * @param newFontStyle The font style of the text being drawn onto the image.
     */
    public void setFontStyle(int newFontStyle) {
        fontStyle = newFontStyle;
    }

    /**
     * Called by the apply method to draw a given shape and colour.
     * 
     * @param g2d The graphics object used to draw the shape.
     */
    public void drawText(Graphics2D g2d) {
        x = Math.min(startPoint.x, endPoint.x);
        y = Math.min(startPoint.y, endPoint.y);

        g2d.setColor(colourToDraw);
        Font font = new Font(fontType, fontStyle, fontSize);
        g2d.setFont(font);

        g2d.drawString(text, x, y);

    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics();
        drawText(g2d);
        g2d.dispose();
        return input;
    }

}
