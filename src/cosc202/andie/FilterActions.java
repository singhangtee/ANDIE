package cosc202.andie;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction(bundle.getString("meanFilter"), null, bundle.getString("meanFilterDesc"),
                Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new MedianFilterAction(bundle.getString("medianFilter"), null, bundle.getString("medianFilterDesc"),
                Integer.valueOf(KeyEvent.VK_N)));
        actions.add(new SoftBlurAction(bundle.getString("softBlur"), null, bundle.getString("softBlurDesc"),
                Integer.valueOf(KeyEvent.VK_B)));
        actions.add(new GaussianBlurAction(bundle.getString("gaussianBlur"), null, bundle.getString("gaussianBlurDesc"),
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new SharpenFilterAction(bundle.getString("sharpenFilter"), null,
                bundle.getString("sharpenFilterDesc"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new EmbossFilterAction(bundle.getString("embossfilter"), null, 
                bundle.getString("embossFilterDesc"), Integer.valueOf(KeyEvent.VK_V)));
        actions.add(new SobelFilterAction(bundle.getString("sobelfilter"), null, 
                bundle.getString("sobelFilterDesc"), Integer.valueOf(KeyEvent.VK_L)));
        actions.add(new TileFilterAction(bundle.getString("tileFilter"), null,
                bundle.getString("tileFilterDesc"), Integer.valueOf(KeyEvent.VK_T)));
        actions.add(new BlockMeanAction(bundle.getString("blockMean"), null, bundle.getString("blockMeanDesc"),
                Integer.valueOf(KeyEvent.VK_H)));
        actions.add(new RandomScatteringAction(bundle.getString("randomScattering"), null,
                bundle.getString("randomScatteringDesc"), Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new MinimumFilterAction(bundle.getString("minimumFilter"), null,
                bundle.getString("minimumFilterDesc"), Integer.valueOf(KeyEvent.VK_P)));
        actions.add(new MaximumFilterAction(bundle.getString("maximumFilter"), null,
                bundle.getString("maximumFilterDesc"), Integer.valueOf(KeyEvent.VK_Q)));
        actions.add(new SepiaToneFilterAction(bundle.getString("sepiaToneFilter"), null,
                bundle.getString("sepiaToneFilterDesc"), Integer.valueOf(KeyEvent.VK_J)));
        actions.add(new VignetteFilterAction(bundle.getString("vignetteFilter"), null,
            bundle.getString("vignetteFilterDesc"), Integer.valueOf(KeyEvent.VK_I)));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("filter"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the mean filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            
                //create and apply the filter with the radius
                target.getImage().apply(new MeanFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
            
        }

    }

    /**
     * <p>
     * Action to blur an image with a soft blur filter.
     * </p>
     * 
     * @see SoftBlur
     */
    public class SoftBlurAction extends ImageAction {

        /**
         * <p>
         * Create a new soft blur action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SoftBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the softblur filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SoftBlurAction is triggered.
         * Applied when soft blur is triggered in menu.
         * {@link SoftBlur}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            target.getImage().apply(new SoftBlur());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to Sharpen an image with a sharpen filter.
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * Create a new sharpen-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new SharpenFilter());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * Create a new median-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the median filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MedianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();

                // Create and apply the filter
                target.getImage().apply(new MedianFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
     * <p>
     * Action to blur an image with a Gaussian blur filter.
     * </p>
     * 
     * @see GaussianBlur
     */
    public class GaussianBlurAction extends ImageAction {

        /**
         * Create a new sharpen-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the GaussianBlurAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link GaussianBlur}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // set default value for the radius of the filter
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();

                // Create and apply the filter
                target.getImage().apply(new GaussianBlur(radius));
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
     * <p>
     * Action to Emboss an image with emboss filter.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction {

        /**
         * Create a new emboss-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It prompts the user for a direction, then applies an appropriate
         * {@link EmbossFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("topOption"), bundle.getString("topRightOption"), bundle.getString("rightOption"), bundle.getString("bottomRightOption"), bundle.getString("bottomOption"), bundle.getString("bottomLeftOption"), bundle.getString("leftOption"), bundle.getString("topLeftOption")};

            // Pop-up dialog box to ask for the radius value.
            //SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            //JSpinner directionSpinner = new JSpinner(radiusModel);
            int choice = JOptionPane.showOptionDialog(null, "Choose a direction:",
                "Options:", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
            // Check the return value from the dialog box.
            if (choice >= 0 && choice < options.length) {
                // Create and apply the filter
                target.getImage().apply(new EmbossFilter(choice));
                target.repaint();
                target.getParent().revalidate();
            } else {
                return;
            }

        }

    }

    /**
     * <p>
     * Action to Emboss an image with emboss filter.
     * </p>
     * 
     * @see SobelFilter
     */
    public class SobelFilterAction extends ImageAction {

        /**
         * Create a new Sobel edge detection-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SobelFilterAction is triggered.
         * It prompts the user for a direction, then applies an appropriate
         * {@link SobelFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("HorizontalSobelOption"), bundle.getString("VerticalSobelOption")};

            // Pop-up dialog box to ask for the radius value.
            //SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            //JSpinner directionSpinner = new JSpinner(radiusModel);
            int choice = JOptionPane.showOptionDialog(null, "Choose a direction:",
                "Options:", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
            // Check the return value from the dialog box.
            if (choice >= 0 && choice < options.length) {
                // Create and apply the filter
                target.getImage().apply(new SobelFilter(choice));
                target.repaint();
                target.getParent().revalidate();
            } else {
                return;
            }

        }

    }

    /**
     * <p>
     * Action to Randomly scatter pixels in a radius.
     * </p>
     * 
     * @see RandomScattering
     */
    public class RandomScatteringAction extends ImageAction {

        /**
         * Create a new Random Scatter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RandomScatteringAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the RandomScatteringAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link RandomScattering}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // set default value for the radius of the filter
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();

                // Create and apply the filter
                target.getImage().apply(new RandomScattering(radius));
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
     * <p>
     * Action to blur an image with a Tile filter.
     * </p>
     * 
     * @see TileFilter
     */
    public class TileFilterAction extends ImageAction {

        /**
         * Create a new TileFilter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        TileFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Tile filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the TileFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link TileFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();

                // Create and apply the filter
                target.getImage().apply(new TileFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    
    

    /**
     * <p>
     * Action to blur an image with a block mean filter.
     * </p>
     * 
     * @see BlockMeanFilter
     */
    public class BlockMeanAction extends ImageAction {

        /**
         * Create a new block mean-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BlockMeanAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the block Mean filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the BlockMeanAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link BlockMeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new TileFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**<p>
     * Action to blur an image with a Maximum filter.
     * </p>
     * 
     * @see MaximumFilter
     */
    public class MaximumFilterAction extends ImageAction {

        /**
         * Create a new Maximum-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MaximumFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Maximum filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MaximumFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MaximumFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();

                // Create and apply the filter
                target.getImage().apply(new MaximumFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**<p>
     * Action to blur an image with a Minimum filter.
     * </p>
     * 
     * @see MinimumFilter
     */
    public class MinimumFilterAction extends ImageAction {

        /**
         * Create a new Minimum-filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MinimumFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Minimum filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MinimumFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MinimumFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            
            // Defining Object for Mulilingual Support
            Object[] options = {bundle.getString("okOption"), bundle.getString("cancelOption")};

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("enterFilterRadius"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();

                // Create and apply the filter
                target.getImage().apply(new MinimumFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            }
        }

    }

    /**
    * <p>
    * Action to apply a sepia tone filter to an image.
    * </p>
    * 
    * @see SepiaToneFilter
    */
    public class SepiaToneFilterAction extends ImageAction {

        /**
         * Create a new sepia tone filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SepiaToneFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SepiaToneFilterAction is triggered.
         * It applies the sepia tone filter to the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new SepiaToneFilter());
            target.repaint();
            target.getParent().revalidate();
        }
    }

        /**
    * <p>
    * Action to apply a vignette filter to an image.
    * </p>
    * 
    * @see SepiaToneFilter
    */
    public class VignetteFilterAction extends ImageAction {

        /**
         * Create a new vignette filter action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        VignetteFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the VignetteFilterAction is triggered.
         * It applies the vignette filter to the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new VignetteFilter());
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
