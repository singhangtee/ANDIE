package cosc202.andie;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

import javax.swing.*;

/**
 * A JPanel component for selecting a region on an image.
 */
public class MyMouseListener extends MouseInputAdapter {

    /**
     * Language Bundle for Multilingual Support
     */
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** Start point of the mouse selection */
    private static Point startPoint;
    /** End point of the mouse selection */
    private static Point endPoint;
    /** The selected area of the mouse */
    private static Rectangle selectedRegion;
    /** The image panel to add the mouse listener to */
    private ImagePanel target;
    /** Is there currently a region being selected by the mouse? */
    private static boolean regionSelectionSelected = false;
    /** Is drawing currently occuring? */
    private static boolean isDrawing = false;
    /** Type of shape to be drawn */
    private static int shapeType;
    /** Colour to draw the given shape */
    private static Color colourToDraw = Color.WHITE;
    /** Is crop being called? */
    private static boolean isCrop = false;
    /** The text to be drawn on the image */
    private static String text;
    /** The font size of the text to be drawn */
    private static int fontSize;
    /** The font of the text to be drawn */
    private static String fontType;
    /** The font font style of the text to be drawn */
    private static int fontStyle;
    /** Is the settings of the text already set? */
    private static boolean isDrawingSettingsMade = false;

    /**
     * Constructs a new MyMouseListener panel.
     * 
     * @param target The image panel to add the mouse listener to.
     */
    public MyMouseListener(ImagePanel target) {
        this.target = target;
    }

    /**
     * Gets the start point of the mouse selection on the image.
     * 
     * @return The start point of the mouse selection.
     */
    public static Point getStartPoint() {
        return startPoint;
    }

    /**
     * Gets the end point of the mouse selection on the image.
     * 
     * @return The end point of the mouse selection.
     */
    public static Point getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the colour of drawing being done on the image.
     * 
     * @param colour The colour to set the drawing tool to.
     */
    public static void setColour(Color colour) {
        colourToDraw = colour;
    }

    /**
     * Calculates the selected region rectangle based on the start and end points.
     * 
     * @return The rectangle representing the selected region.
     */
    private Rectangle calculateSelectedRegion() {
        try {

            BufferedImage tempImage = target.getImage().getCurrentImage();
            int maxWidth = tempImage.getWidth();
            int maxHeight = tempImage.getHeight();

            // so that it wont go outside the image
            if (startPoint.x < 0) {
                startPoint.x = 0;
            }
            if (endPoint.x < 0) {
                endPoint.x = 0;
            }
            if (startPoint.y < 0) {
                startPoint.y = 0;
            }
            if (endPoint.y < 0) {
                endPoint.y = 0;
            }

            // so that it wont go outside the image
            if (startPoint.x > maxWidth) {
                startPoint.x = maxWidth;
            }
            if (endPoint.x > maxWidth) {
                endPoint.x = maxWidth;
            }
            if (startPoint.y > maxHeight) {
                startPoint.y = maxHeight;
            }
            if (endPoint.y > maxHeight) {
                endPoint.y = maxHeight;
            }
            int x = Math.min(startPoint.x, endPoint.x);
            int y = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(startPoint.x - endPoint.x);
            int height = Math.abs(startPoint.y - endPoint.y);
            return new Rectangle(x, y, width, height);
        } catch (Exception e) {
            Object[] options = { bundle.getString("okOption") };
            JOptionPane.showOptionDialog(null, bundle.getString("errorRetrievingImage"), bundle.getString("error"),
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
            int x = 0;
            int y = 0;
            int width = 0;
            int height = 0;
            return new Rectangle(x, y, width, height);
        }
    }

    /**
     * Gets the X location of the mouse selection area.
     * 
     * @return The X location of the mouse selection area.
     */
    public static int getX() {
        return Math.min(startPoint.x, endPoint.x);
    }

    /**
     * Gets the Y location of the mouse selection area.
     * 
     * @return The Y location of the mouse selection area.
     */
    public static int getY() {
        return Math.min(startPoint.y, endPoint.y);
    }

    /**
     * Gets the width of the mouse selection area.
     * 
     * @return The width of the mouse selection area.
     */
    public static int getWidth() {
        return Math.abs(startPoint.x - endPoint.x);
    }

    /**
     * Gets the height of the mouse selection area.
     * 
     * @return The height of the mouse selection area.
     */
    public static int getHeight() {
        return Math.abs(startPoint.y - endPoint.y);
    }

    /**
     * Gets the colour of the shape or text to be drawn.
     * 
     * @return The colour of the shape or text to be drawn.
     */
    public static Color getColor() {
        return colourToDraw;
    }

    /**
     * Gets the selected region.
     * 
     * @return The rectangle representing the selected region.
     */
    public static Rectangle getSelectedRegion() {
        return selectedRegion;
    }

    /**
     * Gets the text being drawn onto the image.
     * 
     * @return The text being drawn onto the image.
     */
    public static String getText() {
        return text;
    }

    /**
     * Gets the font size of the text being drawn onto the image.
     * 
     * @return The font size of the text being drawn onto the image.
     */
    public static int getFontSize() {
        return fontSize;
    }

    /**
     * Gets the font type of the text being drawn onto the image.
     * 
     * @return The font type of the text being drawn onto the image.
     */
    public static String getFont() {
        return fontType;
    }

    /**
     * Gets the font style of the text being drawn onto the image.
     * 
     * @return The font style of the text being drawn onto the image.
     */
    public static int getFontStyle() {
        return fontStyle;
    }

    /**
     * Handles the mouse pressed event to set the start point of the selection.
     * 
     * @param e The MouseEvent object containing information about the event.
     */
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
        endPoint = startPoint;
    }

    /**
     * Handles the mouse released event to set the end point of the selection and
     * calculate the selected region.
     * 
     * @param e The MouseEvent object containing information about the event.
     */
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
        selectedRegion = calculateSelectedRegion();
        if (startPoint.x == endPoint.x && startPoint.y == endPoint.y) {
            selectedRegion = null;
            target.repaint();
            regionSelectionSelected = false;
            isCrop = false;
        } else {
            if (isCrop) {
                isDrawing = false;
                target.getImage().apply(new CropImage(startPoint, endPoint));
                target.repaint();
                target.getParent().revalidate();
                isCrop = false;
                regionSelectionSelected = false;
            }
            if (isDrawing) {
                DrawShapes drawShapes = new DrawShapes(MyMouseListener.getShapeType());
                drawShapes.setRegion(MyMouseListener.getStartPoint(), MyMouseListener.getEndPoint());
                drawShapes.setColor(MyMouseListener.getColor());
                target.getImage().apply(drawShapes);
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * Handles the mouse dragged event to update the end point of the selection.
     * 
     * @param e The MouseEvent object containing information about the event.
     */
    public void mouseDragged(MouseEvent e) {
        endPoint = e.getPoint();
        selectedRegion = calculateSelectedRegion();
        target.repaint();
    }

    /**
     * Gets whether drawing is enabled or not.
     * 
     * @return True if drawing is enabled.
     */
    public static boolean getIsDrawing() {
        return isDrawing;
    }

    /**
     * Sets whether drawing is enabled or not.
     * 
     * @param isDraw Whether drawing is enabled.
     */
    public static void setIsDrawing(boolean isDraw) {
        isDrawing = isDraw;
    }

    /**
     * Gets whether cropping is enabled or not.
     * 
     * @return True if cropping is enabled.
     */
    public static boolean getIsCrop() {
        return isCrop;
    }

    /**
     * Sets whether cropping is enabled or not.
     * 
     * @param isCropping Whether cropping is enabled.
     */
    public static void setIsCrop(boolean isCropping) {
        isCrop = isCropping;
    }

    /**
     * Gets whether region selection is enabled or not.
     * 
     * @return True if region selection is enabled.
     */
    public static boolean getIsRegionSelected() {
        return regionSelectionSelected;
    }

    /**
     * Sets whether region selection is enabled or not.
     * 
     * @param isRegionSelected Whether region selection is enabled.
     */
    public static void setIsRegionSelection(boolean isRegionSelected) {
        regionSelectionSelected = isRegionSelected;
    }

    /**
     * Gets whether drawing settings have been set.
     * 
     * @return True if drawing settings have been set.
     */
    public static boolean getIsDrawingSettingsMade() {
        return isDrawingSettingsMade;
    }

    /**
     * Sets whether drawing settings have been set.
     * 
     * @param isDrawingSettings Whether drawing settings have been set.
     */
    public static void setIsDrawingSettingsMade(boolean isDrawingSettings) {
        isDrawingSettingsMade = isDrawingSettings;
    }

    /**
     * Sets the type of shape to draw (0: rectangle, 1: oval, 2: line).
     * 
     * @param shape The type of shape to draw.
     */
    public static void setShapeType(int shape) {
        shapeType = shape;
    }

    /**
     * Gets the type of shape to draw.
     * 
     * @return The type of shape to draw.
     */
    public static int getShapeType() {
        return shapeType;
    }

    /**
     * Sets the text being drawn onto the image.
     * 
     * @param newText The text being drawn onto the image.
     */
    public static void setText(String newText) {
        text = newText;
    }

    /**
     * Sets the font size of the text being drawn onto the image.
     * 
     * @param newFontSize The font size of the text being drawn onto the image.
     */
    public static void setFontSize(int newFontSize) {
        fontSize = newFontSize;
    }

    /**
     * Sets the font of the text being drawn onto the image.
     * 
     * @param newFont The font of the text being drawn onto the image.
     */
    public static void setFont(String newFont) {
        fontType = newFont;
    }

    /**
     * Sets the font style of the text being drawn onto the image.
     * 
     * @param newFontStyle The font style of the text being drawn onto the image.
     */
    public static void setFontStyle(int newFontStyle) {
        fontStyle = newFontStyle;
    }

    /**
     * Draws the selected region on the specified graphics context.
     *
     * @param g2d The Graphics2D object to draw on.
     */
    public void drawSelectedRegion(Graphics2D g2d) {
        if (regionSelectionSelected && selectedRegion != null) {
            if (!isDrawing) {
                g2d.setColor(Color.RED);
                g2d.draw(selectedRegion);
            } else {
                g2d.setColor(colourToDraw);
                switch (shapeType) {
                    case 0: // Rectangle
                        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
                        break;
                    case 1: // Oval
                        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
                        break;
                    case 2: // Line
                        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                        break;
                    default:
                        return;
                }
            }
        }
    }

}