package cosc202.andie;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * Actions provided by the Settings menu.
 * </p>
 * 
 * <p>
 * Language is currently the only setting that can be changed.
 * </p>
 */
public class SettingsActions {

    /**
     * Language Bundle for Multilingual Support
     */
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /**
     * A list of actions for the Settings menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Setting menu actions.
     * </p>
     */
    public SettingsActions() {
        actions = new ArrayList<Action>();
        actions.add(new ChangeLanguageAction(bundle.getString("changeLanguage"), null,
                bundle.getString("changeLanguageDesc"), Integer.valueOf(KeyEvent.VK_L)));
        actions.add(new CustomiseAndieAction(bundle.getString("customiseAndie"), null,
                bundle.getString("customiseAndieDesc"), null));
        actions.add(new MoveToolbarAction(bundle.getString("moveToolbar"), null, bundle.getString("moveToolbarDesc"),
                null));
        actions.add(new CustomiseText(bundle.getString("customiseText"), null, bundle.getString("customiseTextDesc"),
                null));
    }

    /**
     * <p>
     * Create a menu containing the list of Settings actions.
     * </p>
     * 
     * @return The created menu of settings options.
     */
    public JMenu createMenu() {
        JMenu settingsMenu = new JMenu(bundle.getString("settings"));

        for (Action action : actions) {
            settingsMenu.add(new JMenuItem(action));
        }

        return settingsMenu;
    }

    /**
     * <p>
     * Action to change the language of the GUI.
     * </p>
     */
    public class ChangeLanguageAction extends ImageAction {

        /**
         * <p>
         * Create a new change language action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ChangeLanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /*
         * Callback for when change language action is triggered
         */
        public void actionPerformed(ActionEvent e) {

            // New language
            String language = "";

            // Array of languages for combo box
            String[] languages = { "English", "Maori" };

            // Defining Object for Mulilingual Support
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };

            // Pop-up dialog box to ask for the new language
            JComboBox<String> languageBox = new JComboBox<>(languages);
            int option = JOptionPane.showOptionDialog(null, languageBox, bundle.getString("chooseALanguage"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                language = languageBox.getSelectedItem().toString();

                // Change the language
                new ChangeLanguage(language);
            }
        }
    }

    /**
     * <p>
     * Action to customise ANDIE's visuals.
     * </p>
     */
    public class CustomiseAndieAction extends ImageAction {

        /**
         * <p>
         * Create a customise ANDIE action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        CustomiseAndieAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /*
         * Callback for when customise ANDIE action is triggered
         */
        public void actionPerformed(ActionEvent e) {

            // Colour choice from user input
            Color colourChoice;

            // Defining Object for Mulilingual Support
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };

            JColorChooser colorChooser = new JColorChooser();
            int option = JOptionPane.showOptionDialog(null, colorChooser, bundle.getString("chooseAColour"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                colourChoice = colorChooser.getColor();
                CustomiseAndie.setChoosenColour(colourChoice);
                CustomiseAndie.changeBackgroundColour(colourChoice);
                CustomiseAndie.changeToolbarColour(colourChoice);
                CustomiseAndie.changeMenubarColour(colourChoice);
            }
        }

    }

    /**
     * <p>
     * Action to customise ANDIE's toolbar location.
     * </p>
     */
    public class MoveToolbarAction extends ImageAction {

        /**
         * <p>
         * Create a move toolbar action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MoveToolbarAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /*
         * Callback for when move toolbar action is triggered
         */
        public void actionPerformed(ActionEvent e) {

            // The side of the screen choosen by user
            String location = "";

            // Array of orientations for combo box
            String[] places = { bundle.getString("left"), bundle.getString("right"), bundle.getString("top"),
                    bundle.getString("bottom") };

            // Defining Object for Mulilingual Support
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };

            // Pop-up dialog box to ask for the new location
            JComboBox<String> placeBox = new JComboBox<>(places);
            int option = JOptionPane.showOptionDialog(null, placeBox, bundle.getString("chooseLocation"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                location = placeBox.getSelectedItem().toString();
                CustomiseAndie.changeToolbarLayout(location);
            }

        }

    }

    /**
     * <p>
     * Action to customise ANDIE's text drawing tool.
     * </p>
     */
    public class CustomiseText extends ImageAction {

        /**
         * <p>
         * Create a customise text action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        CustomiseText(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /*
         * Callback for when customise text action is triggered
         */
        public void actionPerformed(ActionEvent e) {

            // Create a JFrame to hold the text settings panel
            JFrame textSettingsFrame = new JFrame(bundle.getString("changeTextSettings"));

            // Text Font
            JLabel chooseFontLabel = new JLabel(bundle.getString("fontType"));
            String[] fontTypes = { bundle.getString("timesRoman"), bundle.getString("helvetica"), bundle.getString("courierNew"), bundle.getString("algerian")};
            JComboBox<String> fontsCmb = new JComboBox<String>(fontTypes);

            // Text Emphasis
            JPanel emphasisPanel = new JPanel(new BorderLayout());
            JCheckBox bold = new JCheckBox(bundle.getString("bold"));
            JCheckBox italic = new JCheckBox(bundle.getString("italic"));
            emphasisPanel.add(bold, BorderLayout.NORTH);
            emphasisPanel.add(italic, BorderLayout.SOUTH);

            // Text Size
            final int min = 1;
            final int max = 100;
            JLabel chooseSizeLabel = new JLabel(bundle.getString("fontSize"));
            JSlider fontSizeSlider = new JSlider(min, max, 12);
            fontSizeSlider.setMajorTickSpacing(25);
            fontSizeSlider.setMinorTickSpacing(5);
            fontSizeSlider.setPaintTicks(true);
            fontSizeSlider.setPaintLabels(true);

            SpinnerNumberModel fontSizeModel = new SpinnerNumberModel(12, min, max, 1);
            JSpinner fontSizeSpinner = new JSpinner(fontSizeModel);

            JTextField textContentField = new JTextField("[YOUR TEXT HERE]");
            textContentField.setPreferredSize(new Dimension(300, 150));

            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(1, new JLabel("1"));
            labelTable.put(25, new JLabel("25"));
            labelTable.put(50, new JLabel("50"));
            labelTable.put(75, new JLabel("75"));
            labelTable.put(100, new JLabel("100"));

            fontSizeSlider.setLabelTable(labelTable);

            // ActionListeners
            fontsCmb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateFont(textContentField, fontsCmb, fontSizeSpinner, bold, italic);
                }
            });
            fontSizeSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    fontSizeSpinner.setValue(fontSizeSlider.getValue());
                    updateFont(textContentField, fontsCmb, fontSizeSpinner, bold, italic);
                }
            });
            fontSizeSpinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    fontSizeSlider.setValue((int) fontSizeSpinner.getValue());
                    updateFont(textContentField, fontsCmb, fontSizeSpinner, bold, italic);
                }
            });
            bold.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateFont(textContentField, fontsCmb, fontSizeSpinner, bold, italic);
                }
            });
            italic.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateFont(textContentField, fontsCmb, fontSizeSpinner, bold, italic);
                }
            });

            // Apply Button
            JButton applyButton = new JButton(bundle.getString("apply"));
            applyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    // Set chosen values
                    String text = textContentField.getText();
                    int fontSize = fontSizeSlider.getValue();
                    String fontType = (String) fontsCmb.getSelectedItem();

                    String fontString;
                    int fontStyle = Font.PLAIN;

                    if (fontType.equals(bundle.getString("helvetica"))) {
                        fontString = "SansSerif";
                    } else if (fontType.equals(bundle.getString("courierNew"))) {
                        fontString = "Monospaced";
                    } else if (fontType.equals(bundle.getString("algerian"))) {
                        fontString = "Algerian";
                    } else {
                        fontString = "Serif";
                    }

                    if (bold.isSelected() && italic.isSelected()) {
                        fontStyle = Font.BOLD | Font.ITALIC;
                    } else if (bold.isSelected()) {
                        fontStyle = Font.BOLD;
                    } else if (italic.isSelected()) {
                        fontStyle = Font.ITALIC;
                    }

                    MyMouseListener.setText(text);
                    MyMouseListener.setFontSize(fontSize);
                    MyMouseListener.setFont(fontString);
                    MyMouseListener.setFontStyle(fontStyle);
                    MyMouseListener.setIsDrawingSettingsMade(true);
                    target.repaint();
                    target.getParent().revalidate();
                    // Close the preview frame
                    textSettingsFrame.dispose();
                }
            });
            // Cancel Button
            JButton cancelButton = new JButton(bundle.getString("cancelOption"));
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Close the frame without applying any changes
                    textSettingsFrame.dispose();
                }
            });

            textSettingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            textSettingsFrame.setSize(450, 350);
            textSettingsFrame.setLocationRelativeTo(null);

            textSettingsFrame.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(5, 5, 5, 5);

            c.gridwidth = 3;
            textSettingsFrame.add(textContentField, c);
            c.gridwidth = 1;
            c.gridy = 1;
            textSettingsFrame.add(chooseFontLabel, c);
            c.gridx = 1;
            textSettingsFrame.add(fontsCmb, c);
            c.gridx = 2;
            textSettingsFrame.add(emphasisPanel, c);
            c.gridx = 0;
            c.gridy = 2;
            textSettingsFrame.add(chooseSizeLabel, c);
            c.gridx = 1;
            textSettingsFrame.add(fontSizeSlider, c);
            c.gridx = 2;
            textSettingsFrame.add(fontSizeSpinner, c);
            c.gridy = 3;
            c.gridx = 0;
            textSettingsFrame.add(applyButton, c);
            c.gridx = 2;
            textSettingsFrame.add(cancelButton, c);

            textSettingsFrame.setAlwaysOnTop(true);
            textSettingsFrame.setVisible(true);
        }
        
        /**
         * Updates the font of the textField in the GUI
         * 
         * @param textField
         * @param fontComboBox
         * @param fontSize
         * @param bold
         * @param italic
         */
        private void updateFont(JTextField textField, JComboBox<String> fontComboBox, JSpinner fontSize, JCheckBox bold, JCheckBox italic) {
            String selectedFont = (String) fontComboBox.getSelectedItem();
            if (selectedFont.equals(bundle.getString("helvetica"))) {
                selectedFont = "SansSerif";
            } else if (selectedFont.equals(bundle.getString("courierNew"))) {
                selectedFont = "Monospaced";
            } else if (selectedFont.equals(bundle.getString("algerian"))) {
                selectedFont = "Algerian";
            } else {
                selectedFont = "Serif";
            }

            int fontStyle = Font.PLAIN;
            if (bold.isSelected() && italic.isSelected()) {
                fontStyle = Font.BOLD | Font.ITALIC;
            } else if (bold.isSelected()) {
                fontStyle = Font.BOLD;
            } else if (italic.isSelected()) {
                fontStyle = Font.ITALIC;
            }

            Integer selectedSize = (Integer) fontSize.getValue();
            if (selectedFont != null && selectedSize != null) {
                Font newFont = new Font(selectedFont, fontStyle, selectedSize);
                textField.setFont(newFont);
            }
        }
    }
}
