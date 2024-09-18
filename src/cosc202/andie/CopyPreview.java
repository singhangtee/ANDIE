package cosc202.andie;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

/**
 * <p>
 * This code as of writing is redundant and not actively being used within ANDIE. 
 * It has been left here as an example of our process of iterating, testing and improving our implementations of ANDIE features.
 * It may be used in future but as of 27/03/2024 is not being used.
 * </P>
 * 
 * <p>
 * ImageOperation to replace an old image with a new one.
 * </p>
 * Some code taken from:
 * https://stackoverflow.com/questions/15058663/how-to-serialize-an-object-that-includes-bufferedimages
 */
public class CopyPreview implements ImageOperation, java.io.Serializable {

    /**
     *  Language Bundle for Multilingual Support
     */  
    ResourceBundle bundle = ResourceBundle.getBundle("LanguageBundle");

    /**
     * Preview image to be copied.
     * Transient because it cannot be serialized directly.
     */
    private transient BufferedImage image;

    /**
     * Create a new CopyPreview operation.
     * 
     * @param image The image from the preview panel.
     */
    CopyPreview(BufferedImage image) {
        this.image = image;
    }

    /**
     * <p>
     * Stores the BufferedImage because it's not normally serializable
     * </p>
     * 
     * @param out Writes image out as PNG using input stream.
     */
    private void writeObject(ObjectOutputStream out) {
        try {
            out.defaultWriteObject(); // Serialize non-transient fields
            ImageIO.write(image, "PNG", out); // Serialize image as PNG
        } catch (IOException e) {

            Object[] options = {bundle.getString("okOption")};
            JOptionPane.showOptionDialog(null, bundle.getString("couldNotSerializeImage"), bundle.getString("error"),
                JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
        }

    }

    /**
     * <p>
     * Opens the BufferedImage that was stored.
     * </p>
     * 
     * @param in Opens file using input stream.
     */
    private void readObject(ObjectInputStream in) {
        try {
            in.defaultReadObject(); // Deserialize non-transient fields
            image = ImageIO.read(in); // Deserialize image
        } catch (Exception e) {

            Object[] options = {bundle.getString("okOption")};
            JOptionPane.showOptionDialog(null, bundle.getString("couldNotDeserializeImage"), bundle.getString("error"),
                JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
        }
    }

    /**
     * Copies the specified image into the old images place
     * 
     * @param input The image to be replace the original
     * @return The resulting new image.
     */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // Iterate through each pixel
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Get the color of the pixel from the preview image
                int argb = image.getRGB(x, y);

                // Set the same pixel on the output image to the same color
                output.setRGB(x, y, argb);
            }
        }
        return output;
    }

}
