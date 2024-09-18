package cosc202.andie;

import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Assertions;

/**
 * Tests for the EditableImage class.
 * 
 * @see EditableImage
 */
public class EditableImageTest {

    @Test
    void testUndo() {

        // Create a editableImage
        EditableImage testEditableImage = new EditableImage();

        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);

        // Do a operation on the image
        InvertColour invert = new InvertColour();
        testImage = invert.apply(testImage);
        testEditableImage.getOps().push(invert);

        // Undo the operation
        testEditableImage.getRedoOps().push(testEditableImage.getOps().pop());

        // Check its been put onto the redoOps stack correctly
        Assertions.assertEquals(testEditableImage.getRedoOps().pop(), invert);

        // Check the other stack is now empty
        Assertions.assertTrue(testEditableImage.getOps().isEmpty());
    }

    @Test
    void testRedo() {

        // Create a editableImage
        EditableImage testEditableImage = new EditableImage();

        // Create a test image (2x2 pixels) with known RGB values
        BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);

        //Do a operation on the image
        InvertColour invert = new InvertColour();
        testImage = invert.apply(testImage);
        testEditableImage.getOps().push(invert);

        //Undo the operation
        testEditableImage.getRedoOps().push(testEditableImage.getOps().pop());

        //Redo the operation
        testEditableImage.getOps().push(testEditableImage.getRedoOps().pop());

        //Check the operation was redone
        Assertions.assertEquals(testEditableImage.getOps().pop(), invert);

        //Check the stack is now empty
        Assertions.assertTrue(testEditableImage.getRedoOps().isEmpty());
    }

}
