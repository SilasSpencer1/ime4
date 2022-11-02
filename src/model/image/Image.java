package model.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import utils.ImageUtil;

/**
 * Represents an image that is of the PPM/JPEG/PNG format (a simple, text-based file format to store
 * images) that has a list of pixels. A PPM Image is made up of pixels that have red, green, and
 * blue values of each pixel, row-wise.
 */
public class Image implements IImage {

  private final IPixel[][] image;
  private final String filename;

  /**
   * Constructs a {@code Image} object based on a given file for the purpose of loading an image.
   *
   * @param filename the name of a given file
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null or if the image is null because of a nonexistent file
   */
  public Image(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("The filename cannot be null.");
    }
    this.filename = filename;
    this.image = ImageUtil.readPPM(this.filename);

    if (this.image == null) {
      throw new IllegalArgumentException("The file doesn't exist.");
    }
  }

  /**
   * Constructs a {@code Image} object with the given name based on a given set of pixels for the
   * purpose of creating an image.
   *
   * @param image    the image to be loaded
   * @param filename the name of the image
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null
   */
  public Image(IPixel[][] image, String filename) {
    if (image == null || filename == null || image.length == 0 || image[0].length == 0
        || this.checkImageGrid(image)) {
      throw new IllegalArgumentException("Cannot have a null/empty image or filename.");
    }

    this.image = image;
    this.filename = filename;
  }

  /**
   * Returns true if any of the pixels in a given 2D IPixel array are invalid.
   *
   * @param imageGrid the given 2D IPixel array
   * @return true if any of the pixels in the given 2D IPixel array are invalid, false if they are
   *         all valid
   */
  private boolean checkImageGrid(IPixel[][] imageGrid) throws IllegalArgumentException {
    for (IPixel[] iPixels : imageGrid) {
      for (IPixel iPixel : iPixels) {
        if (iPixel == null) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public IPixel[][] getImage() {
    IPixel[][] imageGrid = new IPixel[this.getHeight()][this.getWidth()];

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        IPixel currPixel = this.image[i][j];
        imageGrid[i][j] = new Pixel(i, j, currPixel.getRed(), currPixel.getGreen(),
            currPixel.getBlue());
      }
    }

    return imageGrid;
  }

  public BufferedImage getBufferedImage() {
    int width = this.getWidth();
    int height = this.getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < this.getImage().length; y += 1) {
      for (int x = 0; x < this.getImage()[0].length; x += 1) {
        IPixel pixel = this.getImage()[y][x];
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        image.setRGB(x, y, color.getRGB());
      }
    }
    return image;
  }


  @Override
  public void flipImageH() {

    for (int row = 0; row<this.image.length; row++){ // Each column is accessed through each row
      for(int col = 0; col<this.image[0].length/2; col++){ // Access each column for half of the image
        IPixel temp = this.image[row][(this.image[0].length) - col - 1]; // Holds the opposite value to swap
        this.image[row][this.image[0].length - col - 1] = this.image[row][col]; // Puts current entry into 'opposite position'
        this.image[row][col] = temp; // Sets the current entry to that of the 'opposite position'
      }
    }
  }




  @Override
  public String getFilename() {
    return this.filename;
  }

  @Override
  public int getHeight() {
    return this.image.length;
  }

  @Override
  public int getWidth() {
    return this.image[0].length;
  }

  @Override
  public void flipImageV() {

    for (int row = 0; row<this.image.length/2; row++){ // Working one row at a time and only do half the image!!!
      IPixel[] temp = this.image[(this.image.length) - row - 1]; // Collect the temp row from the 'other side' of the array
      this.image[this.image.length - row - 1] = this.image[row]; // Put the current row in the row on the 'other side' of the array
      this.image[row] = temp; // Now put the row from the other side in the current row
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image ppmImage = (Image) o;
    return Arrays.deepEquals(image, ppmImage.image) && Objects
        .equals(filename, ppmImage.filename);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(filename);
    result = 31 * result + Arrays.deepHashCode(image);
    return result;
  }
}
