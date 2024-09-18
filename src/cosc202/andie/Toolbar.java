package cosc202.andie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Class for creating a toolbar containing commonly used actions within ANDIE.
 */
public class Toolbar {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    // The toolbar to contain the different commonly used actions in ANDIE.
    private static JToolBar toolBar = new JToolBar();

    // The buttons added to the toolbar
    private static ArrayList<JButton> buttons = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Toolbar() {
    }

    /**
     * Gets the toolbar used in ANDIE for holding commonly used actions.
     * 
     * @return The toolbar used in ANDIE.
     */
    public static JToolBar getToolbar() {
        return toolBar;
    }

    /**
     * Gets the buttons on the toolbar.
     * 
     * @return The buttons on the toolbar used in ANDIE.
     */
    public static ArrayList<JButton> getButtons() {
        return buttons;
    }

    /**
     * <p>
     * Creates a new toolbar that will hold commonly used actions in ANDIE.
     * </p>
     * 
     * <p>
     * The toolbar can be changed by altering the image paths for each button image,
     * the tooltips or the action performed by each button.
     * </p>
     * 
     * @return The toolbar used in ANDIE.
     */
    public static JToolBar createToolbar() {

        toolBar.removeAll();
        buttons.clear();

        ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

        /**
         * Create a EditActions instance to allow for EditActions to be
         * performed on button presses
         */
        EditActions editActions = new EditActions();

        /*
         * Create a TransformActions instance to allow for TransformActions to be
         * performed on button presses
         */
        TransformActions transformActions = new TransformActions();

        /*
         * Create a ColourActions instance to allow for ColourActions to be
         * performed on button presses
         */
        ColourActions colourActions = new ColourActions();

        /*
         * Create a ViewActions instance to allow for ViewActions to be
         * performed on button presses
         */
        ViewActions viewActions = new ViewActions();

        /*
         * Create a DrawingActions instance to allow for DrawingActions to be
         * performed on button presses
         */
        DrawingActions drawingActions = new DrawingActions();

        // Make it so toolbar is not movable
        toolBar.setFloatable(false);

        // Set toolbar to display buttons on preffered axis
        toolBar.setLayout(new BoxLayout(toolBar, CustomiseAndie.getToolbarOrientation()));

        // Undo button
        JButton undoButton = new JButton();
        ImageIcon undoIcon = new ImageIcon("src\\images\\undo-icon.png");
        undoButton.setIcon(new ImageIcon(resizeImage(undoIcon)));
        undoButton.setToolTipText(bundle.getString("undo"));
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action undo = editActions.new UndoAction(bundle.getString("undo"),
                        undoIcon, bundle.getString("undoDesc"), null);
                undo.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(undoButton);

        // Redo button
        JButton redoButton = new JButton();
        ImageIcon redoIcon = new ImageIcon("src\\images\\redo-icon.png");
        redoButton.setIcon(new ImageIcon(resizeImage(redoIcon)));
        redoButton.setToolTipText(bundle.getString("redo"));
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action redo = editActions.new RedoAction(bundle.getString("redo"),
                        redoIcon, bundle.getString("redoDesc"), null);
                redo.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(redoButton);

        // Reset Cursor button
        JButton resetCursorButton = new JButton();
        ImageIcon resetCursorIcon = new ImageIcon("src\\images\\mouse-selection-icon.png");
        resetCursorButton.setIcon(new ImageIcon(resizeImage(resetCursorIcon)));
        resetCursorButton.setToolTipText(bundle.getString("resetCursor"));
        resetCursorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action resetCursor = drawingActions.new ResestCursorAction(bundle.getString("resetCursor"),
                        resetCursorIcon, bundle.getString("resetCursorDesc"), null);
                resetCursor.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(resetCursorButton);

        // Change colour of drawing
        JButton changeColourButton = new JButton();
        ImageIcon changeColourIcon = new ImageIcon("src\\images\\change-colour-icon.png");
        changeColourButton.setIcon(new ImageIcon(resizeImage(changeColourIcon)));
        changeColourButton.setToolTipText(bundle.getString("changeColour"));
        changeColourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action changeColour = drawingActions.new ChangeColourAction(bundle.getString("changeColour"),
                        changeColourIcon, bundle.getString("changeColourDesc"), null);
                changeColour.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(changeColourButton);

        // Region selection button
        JButton regionSelectionbutton = new JButton();
        ImageIcon regionSelectionIcon = new ImageIcon("src\\images\\region-select-icon.png");
        regionSelectionbutton.setIcon(new ImageIcon(resizeImage(regionSelectionIcon)));
        regionSelectionbutton.setToolTipText(bundle.getString("mouseSelection"));
        regionSelectionbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyMouseListener.setIsDrawing(false);
                MyMouseListener.setIsRegionSelection(true);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(regionSelectionbutton);

        // Crop Image button
        JButton cropImageButton = new JButton();
        ImageIcon cropImageIcon = new ImageIcon("src\\images\\crop-image-icon.png");
        cropImageButton.setIcon(new ImageIcon(resizeImage(cropImageIcon)));
        cropImageButton.setToolTipText(bundle.getString("cropImage"));
        cropImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action cropImage = transformActions.new CropImageAction(bundle.getString("cropImage"),
                        cropImageIcon, bundle.getString("cropImageDesc"), null);
                cropImage.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(cropImageButton);

        // Set drawing to rectangle button
        JButton setRectangleButton = new JButton();
        ImageIcon setRectangleIcon = new ImageIcon("src\\images\\set-rectangle-icon.png");
        setRectangleButton.setIcon(new ImageIcon(resizeImage(setRectangleIcon)));
        setRectangleButton.setToolTipText(bundle.getString("setRectangle"));
        setRectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action setRectangle = drawingActions.new SetRectangleAction(bundle.getString("setRectangle"),
                        setRectangleIcon, bundle.getString("setRectangleDesc"), null);
                setRectangle.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(setRectangleButton);

        // Set drawing to oval button
        JButton setOvalButton = new JButton();
        ImageIcon setOvalIcon = new ImageIcon("src\\images\\set-oval-icon.png");
        setOvalButton.setIcon(new ImageIcon(resizeImage(setOvalIcon)));
        setOvalButton.setToolTipText(bundle.getString("setOval"));
        setOvalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action setOval = drawingActions.new SetOvalAction(bundle.getString("setOval"),
                        setOvalIcon, bundle.getString("setOvalDesc"), null);
                setOval.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(setOvalButton);

        // Set drawing to a line button
        JButton setLineButton = new JButton();
        ImageIcon setLineIcon = new ImageIcon("src\\images\\set-line-icon.png");
        setLineButton.setIcon(new ImageIcon(resizeImage(setLineIcon)));
        setLineButton.setToolTipText(bundle.getString("setLine"));
        setLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action setLine = drawingActions.new SetLineAction(bundle.getString("setLine"),
                        setLineIcon, bundle.getString("setLineDesc"), null);
                setLine.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(setLineButton);

        // Draw Text button
        JButton drawTextButton = new JButton();
        ImageIcon drawShapeIcon = new ImageIcon("src\\images\\text-icon.png");
        drawTextButton.setIcon(new ImageIcon(resizeImage(drawShapeIcon)));
        drawTextButton.setToolTipText(bundle.getString("drawText"));
        drawTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action drawShape = drawingActions.new DrawTextAction(bundle.getString("drawText"),
                        drawShapeIcon, bundle.getString("drawTextDesc"), null);
                drawShape.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(drawTextButton);

        // Resize percentage button
        JButton resizePercentageButton = new JButton();
        ImageIcon resizePercentageIcon = new ImageIcon("src\\images\\resize-percentage-icon.png");
        resizePercentageButton.setIcon(new ImageIcon(resizeImage(resizePercentageIcon)));
        resizePercentageButton.setToolTipText(bundle.getString("resizePercentage"));
        resizePercentageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action resizePercentage = transformActions.new ResizePercentageAction(
                        bundle.getString("resizePercentage"),
                        resizePercentageIcon, bundle.getString("resizePercentageDesc"), null);
                resizePercentage.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(resizePercentageButton);

        // Resize width and height button
        JButton resizeWidthAndHeightButton = new JButton();
        ImageIcon resizeWidthAndHeightIcon = new ImageIcon("src\\images\\resize-width-height-icon.png");
        resizeWidthAndHeightButton.setIcon(new ImageIcon(resizeImage(resizeWidthAndHeightIcon)));
        resizeWidthAndHeightButton.setToolTipText(bundle.getString("resizeWidthAndHeight"));
        resizeWidthAndHeightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action resizeWidthAndHeight = transformActions.new ResizeWidthAndHeightAction(
                        bundle.getString("resizeWidthAndHeight"),
                        resizeWidthAndHeightIcon, bundle.getString("resizeWidthAndHeightDesc"), null);
                resizeWidthAndHeight.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(resizeWidthAndHeightButton);

        // Rotate button
        JButton rotateButton = new JButton();
        ImageIcon rotateIcon = new ImageIcon("src\\images\\rotate-icon.png");
        rotateButton.setIcon(new ImageIcon(resizeImage(rotateIcon)));
        rotateButton.setToolTipText(bundle.getString("rotate"));
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action rotate = transformActions.new RotateAction(bundle.getString("rotate"),
                        rotateIcon, bundle.getString("rotateDesc"), null);
                rotate.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(rotateButton);

        // Flip horizontally button
        JButton flipHorizontalButton = new JButton();
        ImageIcon flipHorizontalIcon = new ImageIcon("src\\images\\flip-horizontal-icon.png");
        flipHorizontalButton.setIcon(new ImageIcon(resizeImage(flipHorizontalIcon)));
        flipHorizontalButton.setToolTipText(bundle.getString("flipHorizontal"));
        flipHorizontalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action flipHorizontal = transformActions.new FlipHorizontalAction(bundle.getString("flipHorizontal"),
                        flipHorizontalIcon, bundle.getString("flipHorizontalDesc"), null);
                flipHorizontal.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(flipHorizontalButton);

        // Flip vertically button
        JButton flipVerticalButton = new JButton();
        ImageIcon flipVerticalIcon = new ImageIcon("src\\images\\flip-vertical-icon.png");
        flipVerticalButton.setIcon(new ImageIcon(resizeImage(flipVerticalIcon)));
        flipVerticalButton.setToolTipText(bundle.getString("flipVertical"));
        flipVerticalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action flipVertical = transformActions.new FlipVerticalAction(bundle.getString("flipVertical"),
                        flipVerticalIcon, bundle.getString("flipVerticalDesc"), null);
                flipVertical.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(flipVerticalButton);

        // Invert colour button
        JButton invertColourButton = new JButton();
        ImageIcon invertColourIcon = new ImageIcon("src\\images\\invert-colour-icon.png");
        invertColourButton.setIcon(new ImageIcon(resizeImage(invertColourIcon)));
        invertColourButton.setToolTipText(bundle.getString("invert"));
        invertColourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action invertColour = colourActions.new InvertColourAction(bundle.getString("invert"),
                        invertColourIcon, bundle.getString("invertDesc"), null);
                invertColour.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(invertColourButton);

        // Greyscale button
        JButton greyscaleButton = new JButton();
        ImageIcon greyscaleIcon = new ImageIcon("src\\images\\greyscale-icon.png");
        greyscaleButton.setIcon(new ImageIcon(resizeImage(greyscaleIcon)));
        greyscaleButton.setToolTipText(bundle.getString("greyscale"));
        greyscaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action greyscale = colourActions.new ConvertToGreyAction(bundle.getString("greyscale"),
                        greyscaleIcon, bundle.getString("greyscaleDesc"), null);
                greyscale.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(greyscaleButton);

        // Zoom in button
        JButton zoomInButton = new JButton();
        ImageIcon zoomInIcon = new ImageIcon("src\\images\\zoom-in-icon.png");
        zoomInButton.setIcon(new ImageIcon(resizeImage(zoomInIcon)));
        zoomInButton.setToolTipText(bundle.getString("zoomIn"));
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action zoomIn = viewActions.new ZoomInAction(bundle.getString("zoomIn"),
                        zoomInIcon, bundle.getString("zoomIn"), null);
                zoomIn.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(zoomInButton);

        // Zoom out button
        JButton zoomOutButton = new JButton();
        ImageIcon zoomOutIcon = new ImageIcon("src\\images\\zoom-out-icon.png");
        zoomOutButton.setIcon(new ImageIcon(resizeImage(zoomOutIcon)));
        zoomOutButton.setToolTipText(bundle.getString("zoomOut"));
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action zoomOut = viewActions.new ZoomOutAction(bundle.getString("zoomOut"),
                        zoomOutIcon, bundle.getString("zoomOut"), null);
                zoomOut.actionPerformed(e);
                Andie.getFrame().requestFocus();
            }
        });
        buttons.add(zoomOutButton);

        for (JButton button : buttons) {
            toolBar.add(button);
        }

        toolBar.validate();
        toolBar.repaint();
        return toolBar;
    }

    /**
     * Removes everything from the toolbar and adds it all again to refresh it.
     */
    public static void refreshToolbar() {
        toolBar.removeAll();

        toolBar = createToolbar();
        toolBar.revalidate();
        toolBar.repaint();
    }

    /**
     * Resizes a given image to be 20*20 pixels so they all fit the toolbar nicely.
     * 
     * @param icon The image that is being resized.
     * @return The original image resized to 20*20 pixels.
     */
    private static Image resizeImage(ImageIcon icon) {
        Image resized = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return resized;
    }

}
