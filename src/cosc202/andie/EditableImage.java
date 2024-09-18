package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.JOptionPane;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to
 * it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can
 * be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always
 * undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original
 * image
 * and the result of applying the current set of operations to it.
 * The operations themselves are stored on a {@link Stack}, with a second
 * {@link Stack}
 * being used to allow undone operations to be redone.
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
class EditableImage {

    // Language Bundle for Multilingual Support
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /**
     * The current image, the result of applying {@link ops} to {@link original}.
     */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The current stack of macro operations to apply. */
    private Stack<ImageOperation> macroOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;
    /** The file where the macro operation sequence is stored. */
    private String macroFilename;
    /** Is the macro currently recording? */
    private boolean macroRecording = false;

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack
     * of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        macroOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
        macroFilename = null;
    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Returns the current stack of image operations. Used for testing in
     * EditableImageTest.
     * </p>
     * 
     * @return The current stack of image operations.
     */
    public Stack<ImageOperation> getOps() {
        return ops;
    }

    /**
     * <p>
     * Returns the current stack of image operations to be redone. Used for testing
     * in EditableImageTest.
     * </p>
     * 
     * @return The current stack of image operations to be redone.
     */
    public Stack<ImageOperation> getRedoOps() {
        return redoOps;
    }

    /**
     * <p>
     * Returns the current stack of image operations stored in the macro. Used for
     * testing in EditableImageTest.
     * </p>
     * 
     * @return The current stack of image operations for the macro.
     */
    public Stack<ImageOperation> getMacroOps() {
        return macroOps;
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage.
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that
     * assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally
     * used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so
     * the
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knowledge of some details about the internals of the
     * BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href=
     * "https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to
     * <a href=
     * "https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under
     * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code>
     * added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try
     * to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     * @throws IOException If something goes wrong.
     */
    public void open(String filePath) throws IOException {
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        original = ImageIO.read(imageFile);
        current = deepCopy(original);

        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();

        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
            ops.clear();
            redoOps.clear();
        }
        this.refresh();
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved
     * as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws IOException If something goes wrong.
     */
    public void save() throws IOException {

        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        // Write image file based on file extension
        try {
            String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();

            // Check if the file is being saved as an image file, if not then return
            if (!ImageIO.write(original, extension, new File(imageFilename))) {
                Object[] options = { bundle.getString("okOption") };
                JOptionPane.showOptionDialog(null, bundle.getString("saveFileExtensionNotSuitable"),
                        bundle.getString("error"),
                        JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                return;
            }

            ImageIO.write(original, extension, new File(imageFilename));
            // Write operations file
            FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(this.ops);
            objOut.close();
            fileOut.close();
        } catch (NullPointerException e) {

            Object[] options = { bundle.getString("okOption") };
            JOptionPane.showOptionDialog(null, bundle.getString("fileIsNull"), bundle.getString("error"),
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
        }

    }

    /**
     * <p>
     * Save an image to a specified file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws IOException If something goes wrong.
     */
    public void saveAs(String imageFilename) throws IOException {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * <p>
     * Export an image.
     * </p>
     * 
     * <p>
     * Export an image to the file provided as a parameter.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws IOException If something goes wrong.
     */
    public void export(String imageFilename) throws IOException {

        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        // Write image file based on file extension
        String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(current, extension, new File(imageFilename));
        // Check the file extension is an image extension
        if (!ImageIO.write(current, extension, new File(imageFilename))) {
            throw new IOException(bundle.getString("notAnImageFile"));
        }
    }

    /**
     * <p>
     * Starts a macro recording.
     * </p>
     * 
     * <p>
     * Saves any imageOperations after this call to the macroOps stack.
     * Clears the current macro stack.
     * </p>
     * 
     * @throws Exception If something goes wrong.
     */
    public void startMacro() throws Exception{

        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        macroOps.clear();

        macroRecording = true;
    }

    /**
     * <p>
     * Save a macro to a specified file.
     * </p>
     * 
     * <p>
     * Saves a macro to a file named the same as the parameter.
     * This can be applied at any time to any image.
     * Will overwrite existing macro.
     * </p>
     * 
     * @throws IOException If something goes wrong.
     */
    public void saveMacro() throws IOException {

        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        // Stop the recording
        macroRecording = false;

        // Save the macro in the same directory as the file being edited
        this.macroFilename = this.imageFilename.substring(0, this.imageFilename.lastIndexOf("\\")) + "\\Macro.ops";

        // Write macro file
        FileOutputStream fileOut = new FileOutputStream(this.macroFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.macroOps);
        objOut.close();
        fileOut.close();
    }

    /**
     * <p>
     * Apply a stack of {@link ImageOperation} saved as a macro to this image.
     * </p>
     * 
     * @throws IOException If something goes wrong.
     */
    public void applyMacro() throws IOException {

        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        try {
            Stack<ImageOperation> tempStack = new Stack<>();
            tempStack.addAll(macroOps);
            while (!tempStack.isEmpty()) {
                ImageOperation currOperation = tempStack.pop();
                current = currOperation.apply(current);
                ops.add(currOperation);
            }
        } catch (NullPointerException e) {
            Object[] options = { bundle.getString("okOption") };
            JOptionPane.showOptionDialog(null, bundle.getString("couldNotApplyMacro"), bundle.getString("error"),
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
        }

    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        
        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        try {
            if (macroRecording) {
                macroOps.add(op);
            }
            current = op.apply(current);
            ops.add(op);

        } catch (NullPointerException e) {

            Object[] options = { bundle.getString("okOption") };
            JOptionPane.showOptionDialog(null, bundle.getString("imageNotFound"), bundle.getString("error"),
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
        }
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {

        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        try {
            redoOps.push(ops.pop());
            refresh();
        } catch (EmptyStackException e) {

            Object[] options = { bundle.getString("okOption") };
            JOptionPane.showOptionDialog(null, bundle.getString("noOpsToUndo"), bundle.getString("message"),
                    JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        }
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo() {

        // Update bundle in case of language change
        bundle = ResourceBundle.getBundle("LanguageBundle");

        try {
            apply(redoOps.pop());
        } catch (EmptyStackException e) {

            Object[] options = { bundle.getString("okOption") };
            JOptionPane.showOptionDialog(null, bundle.getString("noOpsToRedo"), bundle.getString("message"),
                    JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        }
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the
     *         {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in
     * sequence.
     * This is useful when undoing changes to the image, or in any other case where
     * {@link current}
     * cannot be easily incrementally updated.
     * </p>
     */
    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op : ops) {
            current = op.apply(current);
        }
    }

}
