package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class FileActions {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;

    /**
     * Has the currently opened file been saved? True by default because no file
     * opened yet.
     */
    private static boolean isFileSaved = true;

    String imageFilepath;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(bundle.getString("open"), null, bundle.getString("openDesc"),
                Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(bundle.getString("save"), null, bundle.getString("saveDesc"),
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(bundle.getString("saveAs"), null, bundle.getString("saveAsDesc"),
                Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(bundle.getString("export"), null, bundle.getString("exportDesc"),
                Integer.valueOf(KeyEvent.VK_V)));
        actions.add(
                new FileExitAction(bundle.getString("exit"), null, bundle.getString("exitDesc"), Integer.valueOf(0)));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("file"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            boolean continueAnyway = true;

            // Defining Object for Mulilingual Support
            Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };

            // Has the file been saved after making changes?
            if (!isFileSaved) {
                int optionRotate = JOptionPane.showOptionDialog(null,
                        bundle.getString("openNewImageWithoutSaving"),
                        bundle.getString("warning"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
                if (optionRotate == JOptionPane.OK_OPTION) {
                    continueAnyway = true;
                } else if (optionRotate != JOptionPane.OK_OPTION) {
                    continueAnyway = false;
                    return;
                }
            }

            // Continue even though file has unsaved changes
            if (continueAnyway) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        isFileSaved = false;
                        imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().open(imageFilepath);
                    } catch (IOException ex) {
                        JOptionPane.showOptionDialog(null, bundle.getString("incorrectFileType"),
                                bundle.getString("error"),
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                    }
                }

                target.repaint();
                target.getParent().revalidate();
            }

        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().save();
                isFileSaved = true;
            } catch (IOException ex) {

                Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                JOptionPane.showOptionDialog(null, bundle.getString("couldNotSaveImage"), bundle.getString("error"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);
                } catch (IOException ex) {

                    Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                    JOptionPane.showOptionDialog(null, bundle.getString("couldNotSaveImage"), bundle.getString("error"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                }
            }
        }

    }

    /**
     * <p>
     * Action to export an image.
     * </p>
     * 
     * @see EditableImage#export(String)
     */
    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Create a new file-export action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-export action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().export(imageFilepath);
                } catch (IOException ex) {

                    Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                    JOptionPane.showOptionDialog(null, bundle.getString("couldNotExportImage"),
                            bundle.getString("error"),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                }
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (!isFileSaved) {

                Object[] options = { bundle.getString("okOption"), bundle.getString("cancelOption") };
                int optionRotate = JOptionPane.showOptionDialog(null,
                        bundle.getString("closeImageWithoutSaving"),
                        bundle.getString("warning"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                if (optionRotate == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (optionRotate == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }

    }

    /**
     * <p>
     * Public method to return whether file is saved
     * </p>
     * 
     * @return whether the file has been saved
     */
    public static boolean returnIsFileSaved() {
        return isFileSaved;
    }

}
