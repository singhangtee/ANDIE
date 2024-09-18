package cosc202.andie;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.imageio.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
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
public class Andie {

    // Set up the main GUI frame
    private static JFrame frame = new JFrame("ANDIE");

    // Add in menus for various types of action the user may perform.
    private static JMenuBar menuBar = new JMenuBar();

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggered via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see TransformActions
     * @see SettingsActions
     * @see MacroActions
     * @see DrawingActions
     * 
     * @throws Exception if something goes wrong.
     */
    public static void createAndShowGUI() throws Exception {

        // Language Bundle for Multilingual Support
        ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!FileActions.returnIsFileSaved()) {
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
        });

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add the keyboard shortcuts
        KeyListener keyboardShortcuts = KeyboardShortcut.createShortcuts();
        frame.addKeyListener(keyboardShortcuts);

        refreshMenu();
        Toolbar.refreshToolbar();

        frame.setJMenuBar(menuBar);
        frame.add(Toolbar.getToolbar(), BorderLayout.NORTH);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        // Needed to make the shortcuts available
        frame.requestFocus();
    }

    /**
     * <p>
     * Removes all menu bar elements and adds them again to refresh the menu.
     * </p>
     */
    private static void refreshMenu() {
        menuBar.removeAll();

        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual
        // content.
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window.
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image.
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Actions that transform the image such as resize, rotate and flip.
        TransformActions transformActions = new TransformActions();
        menuBar.add(transformActions.createMenu());

        // Actions that change the settings of GUI
        SettingsActions settingsActions = new SettingsActions();
        menuBar.add(settingsActions.createMenu());

        MacroActions macroActions = new MacroActions();
        menuBar.add(macroActions.createMenu());

        DrawingActions drawingActions = new DrawingActions();
        menuBar.add(drawingActions.createMenu());
    }

    /**
     * <p>
     * Reset the language and set it to default.
     * </p>
     * 
     * @param locale The locale to set the default language to.
     */
    public static void changeLanguage(Locale locale) {
        Locale.setDefault(locale);
        ResourceBundle.clearCache();
        refreshMenu();
        Toolbar.refreshToolbar();

        frame.revalidate();
        frame.repaint();
        frame.requestFocus();
    }

    /**
     * Returns the main frame currently in use by ANDIE. Used in the Toolbar class
     * to get the frame in order to set it back in focus so keybinds work correctly.
     * 
     * @return The current main ANDIE frame.
     */
    public static JFrame getFrame() {
        return frame;
    }

    /**
     * Returns the main menuBar currently in use by ANDIE. Used in the to change the
     * colour of it in the CustomiseAndie classs.
     * 
     * @return The current main ANDIE menuBar.
     */
    public static JMenuBar getMenubar() {
        return menuBar;
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Sets language default
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {

        Preferences prefs = Preferences.userNodeForPackage(Andie.class);

        Locale.setDefault(new Locale(prefs.get("language", "en"),
                prefs.get("country", "NZ")));

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}