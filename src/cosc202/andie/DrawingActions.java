package cosc202.andie;

import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import java.awt.Color;

/**
 * <p>
 * Actions provided by the Drawing menu.
 * </p>
 * 
 * <p>
 * The drawing menu contains actions that draw shapes on the image.
 * </p>
 * 
 * @author Toby Munyard
 * @version 1.0
 */
public class DrawingActions {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** A list of actions for the Drawing menu. */
    protected ArrayList<Action> actions;

    /*
     * Create a SettingsActions instance to allow for text settings to pop up if not
     * already set.
     */
    SettingsActions settingsActions = new SettingsActions();

    /**
     * <p>
     * Create a set of Drawing menu actions.
     * </p>
     */
    public DrawingActions() {
        actions = new ArrayList<Action>();
        actions.add(new ResestCursorAction(bundle.getString("resetCursor"), null,
                bundle.getString("resetCursorDesc"), null));
        actions.add(new ChangeColourAction(bundle.getString("changeColour"), null,
                bundle.getString("changeColourDesc"), null));
        actions.add(new DrawTextAction(bundle.getString("drawText"), null,
                bundle.getString("drawTextDesc"), null));
        actions.add(new SetRectangleAction(bundle.getString("setRectangle"), null,
                bundle.getString("setRectangleDesc"), null));
        actions.add(new SetOvalAction(bundle.getString("setOval"), null,
                bundle.getString("setOvalDesc"), null));
        actions.add(new SetLineAction(bundle.getString("setLine"), null,
                bundle.getString("setLineDesc"), null));
    }

    /**
     * <p>
     * Create a menu containing the list of Drawing actions.
     * </p>
     * 
     * @return The transform menu UI element.
     */
    public JMenu createMenu() {
        JMenu drawingMenu = new JMenu(bundle.getString("drawing"));

        for (Action action : actions) {
            drawingMenu.add(new JMenuItem(action));
        }

        return drawingMenu;
    }

    /**
     * <p>
     * Action to change colour of drawing on a image.
     * </p>
     */
    public class ResestCursorAction extends ImageAction {

        /**
         * <p>
         * Create a new ResetCursor action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResestCursorAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the reset cursor action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ResestCursorAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            MyMouseListener.setIsDrawing(false);
            MyMouseListener.setIsCrop(false);
            MyMouseListener.setIsRegionSelection(false);

            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to change colour of drawing on a image.
     * </p>
     */
    public class ChangeColourAction extends ImageAction {

        /**
         * <p>
         * Create a new ChangeColour action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ChangeColourAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the change colour action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ChangeColourAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            JColorChooser colourChooser = new JColorChooser();

            // Defining Object for Mulilingual Support
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };

            int option = JOptionPane.showOptionDialog(null, colourChooser, bundle.getString("chooseAColour"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                Color colour = colourChooser.getColor();
                MyMouseListener.setColour(colour);
            }

            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to draw text on a image.
     * </p>
     */
    public class DrawTextAction extends ImageAction {

        /**
         * <p>
         * Create a new DrawText action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        DrawTextAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the draw text action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the DrawTextAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (MyMouseListener.getIsDrawingSettingsMade()) {
                DrawText drawText = new DrawText(MyMouseListener.getText(), MyMouseListener.getFontSize(),
                        MyMouseListener.getFont(), MyMouseListener.getFontStyle());
                drawText.setRegion(MyMouseListener.getStartPoint(), MyMouseListener.getEndPoint());
                drawText.setColor(MyMouseListener.getColor());
                drawText.setText(MyMouseListener.getText());
                drawText.setFontSize(MyMouseListener.getFontSize());
                drawText.setFont(MyMouseListener.getFont());
                drawText.setFontStyle(MyMouseListener.getFontStyle());
                target.getImage().apply(drawText);
                target.repaint();
                target.getParent().revalidate();
            } else {
                Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                int option = JOptionPane.showOptionDialog(null, bundle.getString("drawingPopUp"),
                        bundle.getString("drawingPopUpTitle"),
                        JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);

                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    Action textSettings = settingsActions.new CustomiseText(bundle.getString("customiseText"),
                            null, bundle.getString("customiseTextDesc"), null);
                    textSettings.actionPerformed(e);
                    target.repaint();
                    target.getParent().revalidate();
                }
            }
        }
    }

    /**
     * <p>
     * Action to set the drawing to a rectangle on a image.
     * </p>
     */
    public class SetRectangleAction extends ImageAction {

        /**
         * <p>
         * Create a new SetRectangle action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SetRectangleAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the set rectangle action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SetRectangleAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            MyMouseListener.setIsRegionSelection(true);
            MyMouseListener.setIsDrawing(true);
            MyMouseListener.setShapeType(0);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to set the drawing to a oval on a image.
     * </p>
     */
    public class SetOvalAction extends ImageAction {

        /**
         * <p>
         * Create a new SetOval action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SetOvalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the set oval action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SetOvalAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            MyMouseListener.setIsRegionSelection(true);
            MyMouseListener.setIsDrawing(true);
            MyMouseListener.setShapeType(1);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to set the drawing to a line on a image.
     * </p>
     */
    public class SetLineAction extends ImageAction {

        /**
         * <p>
         * Create a new SetLine action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SetLineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the set line action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SetLineAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            MyMouseListener.setIsRegionSelection(true);
            MyMouseListener.setIsDrawing(true);
            MyMouseListener.setShapeType(2);
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
