package cosc202.andie;

import javax.swing.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * <p>
 * Contains code for creating several different keyboard shortcuts that be used
 * while inside ANDIE.
 * </p>
 * 
 * <p>
 * The inputs required for each action are documented on the README page within
 * the ANDIE repository.
 * </p>
 */
public class KeyboardShortcut {

    private static boolean ctrlPressed = false;
    private static boolean otherKeyPressed = false;

    /**
     * Creates a KeyListener containing all ANDIE keyboard shortcuts. This is then
     * added to the main ANDIE frame.
     * 
     * @return The KeyListener containing all the shortcuts for ANDIE.
     */
    public static KeyListener createShortcuts() {

        ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

        /*
         * Create a TransformActions instance to allow for TransformActions to be
         * performed on key presses
         */
        TransformActions transformActions = new TransformActions();

        /*
         * Create a ColourActions instance to allow for ColourActions to be
         * performed on key presses
         */
        ColourActions colourActions = new ColourActions();

        /*
         * Create a ViewActions instance to allow for ViewActions to be
         * performed on key presses
         */
        ViewActions viewActions = new ViewActions();

        /*
         * Create a FileActions instance to allow for FileActions to be
         * performed on key presses
         */
        FileActions fileActions = new FileActions();

        /*
         * Create a MacroActions instance to allow for MacroActions to be
         * performed on key presses
         */
        MacroActions macroActions = new MacroActions();

        /*
         * Create a EditActions instance to allow for EditActions to be
         * performed on key presses
         */
        EditActions editActions = new EditActions();

        /*
         * Create a DrawingActions instance to allow for DrawingActions to be
         * performed on key presses
         */
        DrawingActions drawingActions = new DrawingActions();

        /*
         * Contains all the shortcuts for ANDIE in a single KeyListener.
         * Means a ton cannot be pressed at the same time, stopping several issues.
         * Uses the different actions files to create new actions of required
         * functionality.
         */
        KeyListener shortcuts = (new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_1) {
                    Action startMacro = macroActions.new MacroStartAction(bundle.getString("macroStart"),
                            null, bundle.getString("macroStart"), null);
                    startMacro.actionPerformed(new ActionEvent(e, 0, null));
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    Action saveMacro = macroActions.new MacroSaveAction(bundle.getString("macroSave"),
                            null, bundle.getString("macroSave"), null);
                    saveMacro.actionPerformed(new ActionEvent(e, 0, null));
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    Action applyMacro = macroActions.new MacroApplyAction(bundle.getString("macroApply"),
                            null, bundle.getString("macroApply"), null);
                    applyMacro.actionPerformed(new ActionEvent(e, 0, null));
                } else if (e.getKeyCode() == KeyEvent.VK_4) {
                    Action setRectangle = drawingActions.new SetRectangleAction(bundle.getString("setRectangle"), null,
                            bundle.getString("setRectangleDesc"), null);
                    setRectangle.actionPerformed(new ActionEvent(e, 0, null));
                } else if (e.getKeyCode() == KeyEvent.VK_5) {
                    Action setOval = drawingActions.new SetOvalAction(bundle.getString("setOval"), null,
                            bundle.getString("setOvalDesc"), null);
                    setOval.actionPerformed(new ActionEvent(e, 0, null));
                } else if (e.getKeyCode() == KeyEvent.VK_6) {
                    Action setLine = drawingActions.new SetLineAction(bundle.getString("setLine"), null,
                            bundle.getString("setLineDesc"), null);
                    setLine.actionPerformed(new ActionEvent(e, 0, null));
                } else {
                    otherKeyPressed = true;
                }

                // Check if both control and another key are pressed
                if (ctrlPressed && otherKeyPressed) {
                    if (e.getKeyCode() == KeyEvent.VK_S) {
                        Action saveImage = fileActions.new FileSaveAction(bundle.getString("save"),
                                null, bundle.getString("save"), null);
                        saveImage.actionPerformed(new ActionEvent(e, 0, null));
                    }
                    if (e.getKeyCode() == KeyEvent.VK_Z) {
                        Action undoAction = editActions.new UndoAction(bundle.getString("undo"),
                                null, bundle.getString("undo"), null);
                        undoAction.actionPerformed(new ActionEvent(e, 0, null));
                    }
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        Action redoAction = editActions.new RedoAction(bundle.getString("redo"),
                                null, bundle.getString("redo"), null);
                        redoAction.actionPerformed(new ActionEvent(e, 0, null));
                    }
                    if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
                        Action zoomIn = viewActions.new ZoomInAction(bundle.getString("zoomIn"),
                                null, bundle.getString("zoomIn"), null);
                        zoomIn.actionPerformed(new ActionEvent(e, 0, null));
                    }
                    if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                        Action zoomOut = viewActions.new ZoomOutAction(bundle.getString("zoomOut"),
                                null, bundle.getString("zoomOut"), null);
                        zoomOut.actionPerformed(new ActionEvent(e, 0, null));
                    }
                    if (e.getKeyCode() == KeyEvent.VK_E) {
                        Action exportImage = fileActions.new FileExportAction(bundle.getString("export"),
                                null, bundle.getString("export"), null);
                        exportImage.actionPerformed(new ActionEvent(e, 0, null));
                    }
                    if (e.getKeyCode() == KeyEvent.VK_O) {
                        Action openImage = fileActions.new FileOpenAction(bundle.getString("open"),
                                null, bundle.getString("open"), null);
                        openImage.actionPerformed(new ActionEvent(e, 0, null));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = false;
                } else {
                    otherKeyPressed = false;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Do nothing, we don't need it
            }
        });
        return shortcuts;
    }
}