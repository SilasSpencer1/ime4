package controller;


import model.color.GrayscaleRed;
import model.image.Pixel;
import model.layer.ILayerModel;

/**
 * A class representing the command to make an image with a grayscale filter.
 */
public class GrayscaleRedCommand implements IPhotoCommands {

  private String color;
  private Pixel pixel;

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    ;
    m.colorTransformCurrent(new GrayscaleRed());
  }
}