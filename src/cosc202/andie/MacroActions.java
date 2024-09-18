package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;

/**
 * <p>
 * Actions provided by the Macro menu.
 * </p>
 * 
 * <p>
 * Macros are used to perform several actions at once from a single button press.
 * The macro can be changed at any time.
 * The macro is stored in a "macro.ops" file.
 * </p>
 */
public class MacroActions {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** A list of actions for the Macro menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Macro menu actions.
     * </p>
     */
    public MacroActions() {
        actions = new ArrayList<Action>();
        actions.add(new MacroStartAction(bundle.getString("macroStart"), null, bundle.getString("macroStartDesc"),
                null));
        actions.add(new MacroSaveAction(bundle.getString("macroSave"), null, bundle.getString("macroSaveDesc"),
                null));
        actions.add(new MacroApplyAction(bundle.getString("macroApply"), null, bundle.getString("macroApplyDesc"),
                null));
    }

    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     * 
     * @return The Macro menu UI element.
     */
    public JMenu createMenu() {
        JMenu macroMenu = new JMenu(bundle.getString("macro"));

        for (Action action : actions) {
            macroMenu.add(new JMenuItem(action));
        }

        return macroMenu;
    }

    /**
     * <p>
     * Action to start a recording for the creation of a macro.
     * </p>
     * 
     * @see EditableImage#startMacro()
     */
    public class MacroStartAction extends ImageAction {

        /**
         * <p>
         * Create a new macro-start action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MacroStartAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the macro-start action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MacroStartAction is triggered.
         * It starts the macro recording.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().startMacro();
            } catch (Exception ex) {

                Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                JOptionPane.showOptionDialog(null, bundle.getString("couldNotStartMacro"), bundle.getString("error"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
            }
        }

    }

    /**
     * <p>
     * Action to save a stack of actions as a macro.
     * </p>
     * 
     * @see EditableImage#saveMacro()
     */
    public class MacroSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new macro-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MacroSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the macro-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MacroSaveAction is triggered.
         * It saves the macro to a new ops file.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().saveMacro();
            } catch (Exception ex) {

                Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                JOptionPane.showOptionDialog(null, bundle.getString("couldNotSaveMacro"), bundle.getString("error"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
            }
        }

    }

    /**
     * <p>
     * Action to apply the currently saved macro.
     * </p>
     * 
     * @see EditableImage#applyMacro()
     */
    public class MacroApplyAction extends ImageAction {

        /**
         * <p>
         * Create a new macro-apply action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MacroApplyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the macro-apply action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MacroApplyAction is triggered.
         * Applys the currently saved macro.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().applyMacro();
            } catch (IOException ex) {

                Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                JOptionPane.showOptionDialog(null, bundle.getString("couldNotApplyMacro"), bundle.getString("error"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }
}
