package cosc202.andie;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(bundle.getString("greyscale"), null, bundle.getString("greyscaleDesc"),
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new InvertColourAction(bundle.getString("invert"), null, bundle.getString("invertDesc"),
                Integer.valueOf(KeyEvent.VK_I)));
        actions.add(new CycleColourAction(bundle.getString("cycleColour"), null, bundle.getString("cycleColourDesc"),
                Integer.valueOf(KeyEvent.VK_C)));
        actions.add(new BrightnessContrastAction(bundle.getString("brightnessContrast"), null,
                bundle.getString("brightnessContrastDesc"),
                Integer.valueOf(KeyEvent.VK_B)));
        actions.add(new SaturatorAction(bundle.getString("Saturator"), null,
                bundle.getString("saturatorDesc"), Integer.valueOf(KeyEvent.VK_P)));
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu colourMenu = new JMenu(bundle.getString("colour"));

        for (Action action : actions) {
            colourMenu.add(new JMenuItem(action));
        }

        return colourMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to invert the colour of an image.
     * </p>
     */
    public class InvertColourAction extends ImageAction {

        /**
         * Create a new invert colour action.
         */
        InvertColourAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Callback for when the invert colour action is triggered.
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new InvertColour());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to cycle the colour values of an image.
     * </p>
     * 
     * <p>
     * Allows for user to seamlessly change the colour by picking three different
     * colour channel values.
     * </p>
     */
    public class CycleColourAction extends ImageAction {

        /**
         * Create a new cycle colour action.
         */
        CycleColourAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the cycle colour action is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Create a JFrame to hold the preview panel
            JFrame previewFrame = new JFrame(bundle.getString("colourCyclePreview"));

            // Close without exiting program
            previewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Size fits all the content well
            previewFrame.setSize(300, 100);

            // Makes frame appear in the middle of the screen
            previewFrame.setLocationRelativeTo(null);

            previewFrame.setLayout(new BorderLayout());
            JLabel chooseColourText = new JLabel(bundle.getString("chooseColours"));
            previewFrame.add(chooseColourText);

            // Arrays of colour order for comboBoxes
            String[] coloursRed = { bundle.getString("red"), bundle.getString("green"), bundle.getString("blue") };
            String[] coloursGreen = { bundle.getString("green"), bundle.getString("blue"), bundle.getString("red") };
            String[] coloursBlue = { bundle.getString("blue"), bundle.getString("red"), bundle.getString("green") };

            // Pop-up dialog box to ask for the new language
            JComboBox<String> colourChooser1 = new JComboBox<>(coloursRed);
            JComboBox<String> colourChooser2 = new JComboBox<>(coloursGreen);
            JComboBox<String> colourChooser3 = new JComboBox<>(coloursBlue);

            // Create apply and cancel buttons
            JButton applyButton = new JButton(bundle.getString("apply"));
            applyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    // Get the colours choosen by the user
                    String colour1 = colourChooser1.getSelectedItem().toString();
                    String colour2 = colourChooser2.getSelectedItem().toString();
                    String colour3 = colourChooser3.getSelectedItem().toString();
                    String[] colours = new String[] { colour1, colour2, colour3 };

                    // Apply the effect
                    target.getImage().apply(new CycleColour(colours));

                    target.repaint();
                    target.getParent().revalidate();
                    // Close the preview frame
                    previewFrame.dispose();
                }
            });

            JButton cancelButton = new JButton(bundle.getString("cancelOption"));
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Close the preview frame without applying any changes
                    previewFrame.dispose();
                }
            });

            // Panel to hold the colour choices
            JPanel colourChooserPanel = new JPanel(new GridLayout(1, 3));
            colourChooserPanel.add(colourChooser1);
            colourChooserPanel.add(colourChooser2);
            colourChooserPanel.add(colourChooser3);

            // Panel to hold the buttons
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

            buttonPanel.add(applyButton);
            buttonPanel.add(cancelButton);

            // Create another panel to hold the other two panels
            JPanel optionsPanel = new JPanel(new BorderLayout());

            // Add the colour choices to the panel
            optionsPanel.add(colourChooserPanel, BorderLayout.CENTER);

            // Add the buttonPanel to the panel
            optionsPanel.add(buttonPanel, BorderLayout.SOUTH);

            // Add the button panel to the frame
            previewFrame.add(optionsPanel, BorderLayout.SOUTH);

            // Make the frame visible
            previewFrame.setVisible(true);
        }

    }

    /**
     * <p>
     * Action to change the Brightness and Contrast of an image.
     * </p>
     * 
     * @see BrightnessContrast
     */
    public class BrightnessContrastAction extends ImageAction {

        /**
         * <p>
         * Create a new Brightness and Contrast action
         * </p>
         */
        BrightnessContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Brightness and Contrast action is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            JFrame frame = new JFrame(bundle.getString("brightnessContrast"));

            final int min = -100;
            final int max = 100;

            /*
             * Creating Brightness Label, Slider and Spinner
             */
            JLabel bLabel = new JLabel(bundle.getString("brightness"));

            JSlider bSlider = new JSlider(min, max, 0);
            bSlider.setMajorTickSpacing(50);
            bSlider.setMinorTickSpacing(10);
            bSlider.setPaintTicks(true);
            bSlider.setPaintLabels(true);

            SpinnerNumberModel bSpinnerModel = new SpinnerNumberModel(0, min, max, 1);
            JSpinner bSpinner = new JSpinner(bSpinnerModel);

            /*
             * ChangeListeners to update slider and spinner to the same number when changed
             */
            bSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    bSpinner.setValue(bSlider.getValue());
                }
            });

            bSpinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    bSlider.setValue((int) bSpinner.getValue());
                }
            });

            /*
             * Creating Contrast Label, Slider and Spinner
             */
            JLabel cLabel = new JLabel(bundle.getString("contrast"));

            JSlider cSlider = new JSlider(min, max, 0);
            cSlider.setMajorTickSpacing(50);
            cSlider.setMinorTickSpacing(10);
            cSlider.setPaintTicks(true);
            cSlider.setPaintLabels(true);

            SpinnerNumberModel cSpinnerModel = new SpinnerNumberModel(0, min, max, 1);
            JSpinner cSpinner = new JSpinner(cSpinnerModel);

            /*
             * ChangeListeners to update slider and spinner to the same number when changed
             */
            cSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    cSpinner.setValue(cSlider.getValue());
                }
            });

            cSpinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    cSlider.setValue((int) cSpinner.getValue());
                }
            });

            /*
             * Create apply and cancel buttons
             */
            JButton applyButton = new JButton(bundle.getString("apply"));
            applyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    // Set chosen values
                    int brightness = bSlider.getValue();
                    int contrast = cSlider.getValue();

                    // Apply the effect
                    target.getImage().apply(new BrightnessContrast(brightness, contrast));
                    target.repaint();
                    target.getParent().revalidate();
                    // Close the preview frame
                    frame.dispose();
                }
            });

            JButton cancelButton = new JButton(bundle.getString("cancelOption"));
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Close the preview frame without applying any changes
                    frame.dispose();
                }
            });

            /*
             * Creating Frame
             */
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 190);
            frame.setLocationRelativeTo(null);

            frame.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.EAST;
            c.insets = new Insets(5, 5, 5, 5);

            // Making the grid layout of the frame
            frame.add(bLabel, c);
            c.gridx = 1;
            c.gridwidth = 2;
            frame.add(bSlider, c);
            c.gridwidth = 1;
            c.gridx = 3;
            frame.add(bSpinner, c);
            c.gridx = 0;
            c.gridy = 1;
            frame.add(cLabel, c);
            c.gridx = 1;
            c.gridwidth = 2;
            frame.add(cSlider, c);
            c.gridwidth = 1;
            c.gridx = 3;
            frame.add(cSpinner, c);
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 2;
            frame.add(applyButton, c);
            c.gridx = 2;
            frame.add(cancelButton, c);

            frame.setVisible(true);
        }

    }

    /**
     * <p>
     * Action to change image saturation levels.
     * </p>
     * 
     * @see SobelFilter
     */
    public class SaturatorAction extends ImageAction {

        /**
         * Create a new saturation action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SaturatorAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SaturatorAction is triggered.
         * It prompts the user for a amount or saturation to change.
         * {@link Saturator}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Defining Object for Mulilingual Support
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };

            JSlider slider = new JSlider(0, 200, 100);
            slider.setMajorTickSpacing(25);
            slider.setMinorTickSpacing(5);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setLabelTable(slider.createStandardLabels(25));

            Dimension size = new Dimension(300, 50);
            slider.setPreferredSize(size);

            int choice = JOptionPane.showOptionDialog(null, slider, bundle.getString("chooseSaturationAmount"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            if (choice == JOptionPane.OK_OPTION) {
                float amount = (float) slider.getValue() / 100;
                target.getImage().apply(new Saturator(amount));
                target.repaint();
                target.getParent().revalidate();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }

            // Pop-up dialog box to ask for the radius value.
            // SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            // JSpinner directionSpinner = new JSpinner(radiusModel);
            /*
             * int choice = JOptionPane.showOptionDialog(null, "Choose a direction:",
             * "Options:",
             * JOptionPane.DEFAULT_OPTION,
             * JOptionPane.PLAIN_MESSAGE,
             * null, options, options[0]);
             * // Check the return value from the dialog box.
             * if (choice >= 0 && choice < options.length) {
             * // Create and apply the filter
             * target.getImage().apply(new SobelFilter(choice));
             * target.repaint();
             * target.getParent().revalidate();
             * } else {
             * return;
             * }
             */

        }

    }

}
