## Welcome

Welcome to Team Bee's ANDIE: A Non-Destructive Image Editor. For our version of ANDIE we've opted to test each new feature thoroughly to ensure they all function as intended at all times. We have made an effort to make our UI be consistent between different menus and effects as well as between languages. This has led to a cohesive and well thought out feel for ANDIE. We've also opted to give the user as much information as possible at all times when encountering errors and edge cases. This comes in the form of many pop-ups that warn the user of any actions that could lead to frustration such as opening a new image without saving changes. All of the part one deliverables are completed as described in the lab book and we have created JUnit tests for all files where it made sense to. We hope you enjoy your ANDIE experience!

## User Guide
### Keyboard Shortcuts
| Keybind    | Action                   |
| ---------- | ------------------------ |
| Ctrl-Z     | Undo                     |
| Ctrl-R     | Redo                     |
| Ctrl-S     | Save                     |
| Ctrl-Plus  | Zoom In                  |
| Ctrl-Minus | Zoom Out                 |
| Ctrl-E     | Export                   |
| Ctrl-O     | Open                     |
| 1          | Start Macro              |
| 2          | Save Macro               |
| 3          | Apply Macro              |
| 4          | Set Drawing to Rectangle |
| 5          | Set Drawing to Oval      |
| 6          | Set Drawing to Line      |

### Drawing Tools
* Changing Colour: Changing the colour of the drawing through the "drawing" tab or the toolbar will change the colour of any text or shapes drawn AFTER the change.  
* Drawing Shapes: Will occur after a shape, rectangle, oval or line is selected and the mouse is dragged and released.
* Drawing Text: Text will be drawn on top of the region selection window after text settings have been set and draw text is selected from either the "drawing" tab or the toolbar. If text settings are not set a pop up will be given each time a user attempts to draw text. The region selection tool can be optionally turned on while this is happening, the text will appear on top of the region selection window, however this is not required if you do not need a exact location.

## Testing
Testing occured throughout the process of implementing features as well as several JUnit tests. Whenever a new feature is being worked on it is tested in a range of ways and scenarios to ensure users will not run into errors wherever possible. An example of this was our testing around file opening, saving, exporting etc. we made sure to test many different orders of operations to ensure all outcomes were covered. This now means a user is well aware of when they are going to delete unsaved work, exporting with the wrong file type and much more. Our JUnit testing was divided up between our group members with each person taking on several files, the testing was done to ensure no edge cases would impact the way ANDIE runs and also to ensure our classes and methods themselves worked as intended. An example of this is FlipTest.java, it creates a new image where the pixel values are known and checks each one has been flipped the correct x and y location.

## Known Bugs/Errors
* InverseColourTest fails testing despite every rgb int value being the same, we cannot figure out why this is failing to pass as even printing the int values of the inversion and the actual inverted hex codes of the values proves they are the same.
* After zooming in or out of an image, user won't be able to select a region accurately using any feature involving selecting a region of the image. We are unable to figure out how to fix it.

## Accessing Deliverables 1 Features
* Sharpen Filter
  * "Filter" tab, will apply with a single click.
* Gaussian Blur Filter
  * "Filter" tab, will prompt the user for a filter radius before applying.
* Median Filter
  * "Filter" tab, will prompt the user for a filter radius before applying.
* Image Inversion
  * "Colour" tab, will apply with a single click.
* Colour Channel Cycling
  * "Colour" tab, will prompt the user for three seperate colour channels before applying.
* Multilingual Support
  * "Settings" tab, will prompt the user to choose either English or Maori, will change all ANDIE text to language selected.
* Image Resize
  * "Transform" tab, offers two options, percentage and width and height. Percentage will prompt the user for a percentage to scale the image by, inputing 10 is equal to scaling it to 10% as ANDIE handles the conversion and will scale accordingly. Width and height will instead scale the image based on a width and a height input in pixels from the user.
* Image Rotations
  * "Transform" tab, will prompt the user for a rotation angle, will then rotate the image clockwise by amount specified. 180 will result in a upside down image, 90 will result in a sideways image, etc.
* Image Flip
  * "Transform" tab, offers two options, horizontal and vertical. The options do as they suggest, flipping the image in either a horizontal or vertical fashion.
* Image Export
  * "File" tab, will open a JFileChooser for the user to select the output folder. User will then have to enter the filename and relevant file extension for the image they are saving.
* Exception Handling
  * Can be accessed in a range of ways, trying to apply something without a image open, trying to open another image with one already open and much more. Get creative, I guarantee we've thought of it! 

## Accessing Deliverables 2 Features
* Extended Filters
  * "Filter" tab, automatically applied to images that are filtered.
* Filters With Negative Results
  * "Filter" tab, automatically applied to images that are filtered.
* Emboss Filter
  * "Filter" tab, will prompt the user for a direction.
* Sobel Filter
  * "Filter" tab, will prompt the user for a direction.
* Brightness Adjustment
  * "Colour" tab, will offer a slider for brightness along with contrast.
* Contrast Adjustment
  * "Colour" tab, will offer a slider for contrast along with brightness.
* Block Averaging
  * "Filter" tab, will prompt the user for a filter radius before applying.
* Random Scattering
  * "Filter" tab, will prompt the user for a filter radius before applying.
* Toolbar
  * Available on the left side of the screen when ANDIE is opened. Can be moved using "Change Toolbar Location" in the "Settings" tab.
* Keyboard Shortcuts
  * Usable at all times when not in menus. See the "User Guide" for available shortcuts.
* Mouse Selection
  * Can be toggled on and off within the "Transform" tab. Used for displaying where drawing text will appear on the screen.
* Crop to Selection
  * "Transform" tab, clicking the button will start image cropping, simply select your preferred region and it will be cropped. 
* Drawing Functions
  * Set shape of drawing under "Drawing" tab. Once these are in use, dragging and releasing the mouse will draw the displayed shape onto the image. See "Colour Customisation" below for changing the colour of shapes. Also available in toolbar.
* Macros
  * "Macro" tab. Also available in keyboard shortcuts, see the "User Guide" for available shortcuts.
* Additional Features
  * Drawing Text
    * Change drawing settings in "Settings" tab. Draw text in "Drawing" tab. Will be drawn on top of the region selection window. See "Colour Customisation" below for changing the colour of text. Also available in toolbar.
  * Saturation
    * "Filter" tab, offers a slider.
  * Colour Customisation
    * "Settings" tab, offers selection of ANDIE colour.
  * Move Toolbar
    * "Settings" tab, offers selection of toolbar location.

## Code Refactoring
ANDIE now opens to the size of the users screen instead of a smaller view and many of the original exit cases in error handling have been replaced with pop-up messages. There were also a couple of extra getter and setter methods added to the orginial code. Additionally the ANDIE frame now requests focus often so the keyboard shortcuts will work. Otherwise pretty much every other feature was built on top of existing ANDIE code.
