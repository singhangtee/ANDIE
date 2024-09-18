package cosc202.andie;

import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 * Class to apply different styles and customisations to the look of ANDIE.
 */
public class CustomiseAndie {

    /**
     * Language Bundle for Multilingual Support
     */
    static ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /**
     * Orientation of the toolbar, 0 for horizontal, 1 for vertical.
     */
    private static int toolbarOrientation = 0;

    /**
     * Colour choosen by the user from the JColorChooser.
     */
    private static Color choosenColour = Andie.getFrame().getBackground();

    /**
     * Changes the colour of the background in ANDIE.
     * 
     * @param colour The colour to change the background to.
     */
    public static void changeBackgroundColour(Color colour) {
        Andie.getFrame().setBackground(colour);
        Andie.getFrame().setForeground(colour);
        Andie.getFrame().getRootPane().setBorder(BorderFactory.createLineBorder(colour));
    }

    /**
     * Changes the colour of the menubar in ANDIE.
     * 
     * @param colour The colour to change the menubar to.
     */
    public static void changeMenubarColour(Color colour) {
        Andie.getMenubar().setBackground(colour);
    }

    /**
     * Sets the colour to make the toolbar when moving it to another orientation.
     * 
     * @param colour The colour choosen by the user.
     */
    public static void setChoosenColour(Color colour) {
        choosenColour = colour;
    }

    /**
     * Changes the colour of the toolbar in ANDIE.
     * 
     * @param colour The colour to change the toolbar to.
     */
    public static void changeToolbarColour(Color colour) {
        Toolbar.getToolbar().setBackground(colour);

        for (JButton button : Toolbar.getButtons()) {
            button.setBackground(colour.brighter());
        }
    }

    /**
     * Gets the orientation of the toolbar as a int.
     * 
     * @return The orientation the toolbar should be, 0 for X axis, 1 for Y axis.
     */
    public static int getToolbarOrientation() {
        return toolbarOrientation;
    }

    /**
     * 
     * @param selection The choice of location for the toolbar.
     * @return The chosen orientation as a BorderLayout string.
     */
    public static String changeToolbarLayout(String selection) {
        if (selection.equals("Left")) {
            toolbarOrientation = 1;
            Andie.getFrame().remove(Toolbar.getToolbar());
            Andie.getFrame().add(Toolbar.createToolbar(), "West");
            changeToolbarColour(choosenColour);
            Andie.getFrame().revalidate();
            Andie.getFrame().repaint();
            Andie.getFrame().requestFocus();
            return "West";
        } else if (selection.equals("Right")) {
            toolbarOrientation = 1;
            Andie.getFrame().remove(Toolbar.getToolbar());
            Andie.getFrame().add(Toolbar.createToolbar(), "East");
            changeToolbarColour(choosenColour);
            Andie.getFrame().revalidate();
            Andie.getFrame().repaint();
            Andie.getFrame().requestFocus();
            return "East";
        } else if (selection.equals("Top")) {
            toolbarOrientation = 0;
            Andie.getFrame().remove(Toolbar.getToolbar());
            Andie.getFrame().add(Toolbar.createToolbar(), "North");
            changeToolbarColour(choosenColour);
            Andie.getFrame().revalidate();
            Andie.getFrame().repaint();
            Andie.getFrame().requestFocus();
            return "North";
        } else {
            toolbarOrientation = 0;
            Andie.getFrame().remove(Toolbar.getToolbar());
            Andie.getFrame().add(Toolbar.createToolbar(), "South");
            changeToolbarColour(choosenColour);
            Andie.getFrame().revalidate();
            Andie.getFrame().repaint();
            Andie.getFrame().requestFocus();
            return "South";
        }
    }
}
