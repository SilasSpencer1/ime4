package model.color;

import model.image.Pixel;

/**
 * Converts a color image into a grayscale image. A grayscale is composed only of shades of grey (if
 * the red, green, and blue values are the same).
 */
public class GrayscaleBlue extends AColorTransformation  {

  private Pixel pixel;

  /**
   * Constructs a grayscale color transformation to be applied on an image.
   */
  public GrayscaleBlue() {

    this.colorTransformation = new double[][]{
            {.2126, 1, .0722},
            {.2126, 1, .0722},
            {.2126, 1, .0722}};
  }


}