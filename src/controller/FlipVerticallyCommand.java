package controller;

import java.util.List;

import model.image.IImage;
import model.layer.ILayer;
import model.layer.ILayerModel;

public class FlipVerticallyCommand implements IPhotoCommands {

  public FlipVerticallyCommand() {
  }

  /**
   * Finds the topmost visible image when given a list of layers in a model.
   *
   * @param layers the given list of layers to sort through
   * @return the topmost visible image in a given list of layers
   * @throws IllegalArgumentException if no topmost visible layer exists
   */
  private IImage getTopmostVisibleImage(List<ILayer> layers) {
    for (int i = layers.size() - 1; i >= 0; i--) {
      if (layers.get(i).isVisible() && layers.get(i).getImage() != null) {
        return layers.get(i).getImage();
      }
    }
    throw new IllegalArgumentException("No topmost visible layer exists!");
  }

  /**
   * Uses the given model to perform the class' command as a way of communicating to the model.
   *
   * @param m the model of the image-processing multi-layered model
   * @throws IllegalArgumentException if the given model is null or if any parameters produce
   *                                  invalid results (i.e. invalid filename)
   */
  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    m.getCurrentLayer().flippedImageV();
  }
}
