package cosc202.andie;

import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Transform menu.
 * </p>
 * 
 * <p>
 * The Transofrm menu contains actions that alter the image, size, orientation
 * and rotation.
 * This includes:
 * A Resize action which changes the image width and height based on either a
 * percentage or width and height.
 * A Rotate action which changes the image rotation angle based on an angle
 * given.
 * A flip action which can flip the image either horizontally or vertically
 * based on input.
 * </p>
 * 
 * @author Toby Munyard
 * @version 1.0
 */
public class TransformActions {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** A list of actions for the Transform menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Transform menu actions.
     * </p>
     */
    public TransformActions() {
        actions = new ArrayList<Action>();
        actions.add(new ResizePercentageAction(bundle.getString("resizePercentage"), null,
                bundle.getString("resizePercentageDesc"), null));
        actions.add(new ResizeWidthAndHeightAction(bundle.getString("resizeWidthAndHeight"), null,
                bundle.getString("resizeWidthAndHeightDesc"), null));
        actions.add(new RotateAction(bundle.getString("rotate"), null, bundle.getString("rotateDesc"), null));
        actions.add(new FlipHorizontalAction(bundle.getString("flipHorizontal"), null,
                bundle.getString("flipHorizontalDesc"), null));
        actions.add(new FlipVerticalAction(bundle.getString("flipVertical"), null, bundle.getString("flipVerticalDesc"),
                null));
        actions.add(new SelectRegionAction(bundle.getString("selectRegion"), null, bundle.getString("selectRegionDesc"),
                null));
        actions.add(new CropImageAction(bundle.getString("cropImage"), null, bundle.getString("cropImageDesc"), null));
    }

    /**
     * <p>
     * Create a menu containing the list of Transform actions.
     * </p>
     * 
     * @return The transform menu UI element.
     */
    public JMenu createMenu() {
        JMenu transformMenu = new JMenu(bundle.getString("transform"));

        for (Action action : actions) {
            transformMenu.add(new JMenuItem(action));
        }

        return transformMenu;
    }

    /**
     * <p>
     * Action to resize the image using a percentage.
     * </p>
     */
    public class ResizePercentageAction extends ImageAction {

        /**
         * <p>
         * Create a new resize percentage action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResizePercentageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the resize percentage action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ResizePercentageAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int percentage = 100;
            // Pop-up dialog box to ask for the resize values.
            SpinnerNumberModel percentageModel = new SpinnerNumberModel(100, 1, 1000, 1);
            JSpinner percentageSpinner = new JSpinner(percentageModel);
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
            int optionPercentage = JOptionPane.showOptionDialog(null, percentageSpinner,
                    bundle.getString("enterResizePercentage"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (optionPercentage == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (optionPercentage == JOptionPane.OK_OPTION) {
                percentage = percentageModel.getNumber().intValue();

                target.getImage().apply(new Resize(percentage));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to resize the image using a width and a height.
     * </p>
     */
    public class ResizeWidthAndHeightAction extends ImageAction {

        /**
         * <p>
         * Create a new resize width and height action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResizeWidthAndHeightAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the resize width and height action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ResizeWidthAndHeightAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // New width and height
            int width = 0;
            int height = 0;

            // JSpinners to get the width and height
            SpinnerNumberModel widthModel = new SpinnerNumberModel(100, 1, 10000, 1);
            JSpinner widthSpinner = new JSpinner(widthModel);
            SpinnerNumberModel heightModel = new SpinnerNumberModel(100, 1, 10000, 1);
            JSpinner heightSpinner = new JSpinner(heightModel);

            // Panel to hold the new width and height spinners
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // BoxLayout lays out conents vertically
            panel.add(new JLabel(bundle.getString("enterResizeWidth")));
            panel.add(widthSpinner);
            panel.add(Box.createVerticalStrut(15)); // Add spacing between the spinners
            panel.add(new JLabel(bundle.getString("enterResizeHeight")));
            panel.add(heightSpinner);

            // Defining Object for Mulilingual Support
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };

            // Put the panel into a single JOptionPane pop-up
            int optionWidthAndHeight = JOptionPane.showOptionDialog(null, panel,
                    bundle.getString("enterWidthAndHeight"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            if (optionWidthAndHeight == JOptionPane.OK_OPTION) {
                width = widthModel.getNumber().intValue();
                height = heightModel.getNumber().intValue();
            } else {
                return;
            }

            target.getImage().apply(new Resize(width, height));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to rotate the image.
     * </p>
     */
    public class RotateAction extends ImageAction {

        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RotateAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the rotate action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            double rotation = 0;

            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
            SpinnerNumberModel rotateModel = new SpinnerNumberModel(90, -360, 360, 1);
            JSpinner rotateSpinner = new JSpinner(rotateModel);
            int optionRotate = JOptionPane.showOptionDialog(null, rotateSpinner, bundle.getString("enterRotationAngle"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            if (optionRotate == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (optionRotate == JOptionPane.OK_OPTION) {
                rotation = rotateModel.getNumber().intValue();

                target.getImage().apply(new Rotate(rotation));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    /**
     * <p>
     * Action to flip the image horizontally.
     * </p>
     */
    public class FlipHorizontalAction extends ImageAction {

        /**
         * <p>
         * Create a new flip horizontal action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FlipHorizontalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the flip horizontal action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipHorizontalAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isVerticalFlip = false;

            target.getImage().apply(new Flip(isVerticalFlip));
            target.repaint();
            target.getParent().revalidate();

        }
    }

    /**
     * <p>
     * Action to flip the image vertically.
     * </p>
     */
    public class FlipVerticalAction extends ImageAction {

        /**
         * <p>
         * Create a new flip action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FlipVerticalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the flip vertical action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipVerticalAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isVerticalFlip = true;

            target.getImage().apply(new Flip(isVerticalFlip));
            target.repaint();
            target.getParent().revalidate();

        }
    }

    /**
     * <p>
     * Action to select a region within an image.
     * </p>
     */
    public class SelectRegionAction extends ImageAction {

        /**
         * <p>
         * Create a new regionSelection action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SelectRegionAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the select region action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SelectRegionAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            MyMouseListener.setIsDrawing(false);
            MyMouseListener.setIsRegionSelection(true);
        }
    }

    /**
     * <p>
     * Action to crop a image to selected size.
     * </p>
     */
    public class CropImageAction extends ImageAction {

        /**
         * <p>
         * Create a new CropImage action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        CropImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the crop image action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the CropImageAction is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            MyMouseListener.setIsDrawing(false);
            MyMouseListener.setIsRegionSelection(true);
            MyMouseListener.setIsCrop(true);
        }
    }

}
