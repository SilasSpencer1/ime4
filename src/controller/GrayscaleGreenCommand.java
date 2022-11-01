package controller;


import model.color.GrayscaleGreen;

import model.image.Pixel;
import model.layer.ILayerModel;

/**
 * A class representing the command to make an image with a grayscale filter.
 */
public class GrayscaleGreenCommand implements IPhotoCommands {

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    ;
    m.colorTransformCurrent(new GrayscaleGreen());
  }
}