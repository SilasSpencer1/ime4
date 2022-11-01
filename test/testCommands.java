import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.CreateImageLayerCommand;
import controller.GrayscaleCommand;
import controller.IImageProcessingController;
import controller.IPhotoCommands;
import controller.LoadAllCommand;
import controller.LoadSingleCommand;
import controller.SaveAllCommand;
import controller.SaveSingleCommand;
import controller.SetCurrentCommand;
import java.io.File;
import model.color.Grayscale;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.layer.ILayer;
import model.layer.ILayerModel;
import model.layer.Layer;
import model.layer.LayerModel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests for the command classes ones that implement {@link IPhotoCommands} that are
 * called in the controller (the controller uses the command design pattern) to make sure that they
 * work properly when given an {@link ILayerModel}.
 */
public class testCommands {

  IImageProcessingController controller;
  Readable in;
  Appendable out;
  ILayerModel model;
  ILayer exLayer;
  ILayer exLayer2;
  ILayer exLayer3;
  IPixel[][] grid1;
  IPixel[][] grid2;
  IPixel[][] grid3;
  IPixel[][] grid4;
  IImage exImage;
  IImage exImage2;
  IImage exImage3;
  IImage exImage4;
  Appendable mockAppendable;
  ILayerModel mockModel;
  private IPixel[][] sampleImageGrid1By2;

  @Before
  public void setUp() {
    out = new StringBuilder();
    model = new LayerModel();
    exLayer = new Layer("first");
    exLayer2 = new Layer("second");
    exLayer3 = new Layer("third");
    grid1 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    grid2 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage = new Image(grid1, "Grid1");
    exImage2 = new Image(grid2, "Grid2");
    grid3 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage3 = new Image(grid3, "Grid3");
    grid4 = new IPixel[][]{
            {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
                    new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
                    new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
            {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
                    new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
            {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
                    new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage4 = new Image(grid4, "Grid4");
    this.sampleImageGrid1By2 = new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
            new Pixel(0, 1, 50, 200, 10)}};
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("third");
    model.loadLayer(exImage3);
    mockAppendable = new StringBuilder();
  }





  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageLayerWithNullName() {
    new CreateImageLayerCommand(null).runCommand(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCreateImageLayer() {
    new CreateImageLayerCommand("Layer 2").runCommand(null);
  }

  @Test
  public void testCreateImageLayerCommand() {
    new CreateImageLayerCommand("fourth").runCommand(model);
    assertEquals(4, model.getLayers().size());
    assertTrue(model.getLayers().get(3).isVisible());
  }





  @Test(expected = IllegalArgumentException.class)
  public void testNullGrayscale() {
    new GrayscaleCommand().runCommand(null);
  }

  @Test
  public void testGrayscaleCommand() {
    model.setCurrent("first");
    new GrayscaleCommand().runCommand(model);
    IImage grayImg = new Grayscale().apply(new Image(exImage.getImage(), exImage.getFilename()));
    assertArrayEquals(grayImg.getImage(), model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", model.getLayers().get(0).getImage().getFilename());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullLoadAllCommand() {
    new LoadAllCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelLoadAllCommand() {
    new LoadAllCommand("Tester1").runCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLoadSingleCommand() {
    new LoadSingleCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelLoadSingleCommand() {
    new LoadAllCommand("Tester2").runCommand(null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullSaveAllCommand() {
    new SaveAllCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSaveAllCommand() {
    SaveAllCommand saveAll = new SaveAllCommand("testing");
    File f = new File("testing");
    f.delete();
    saveAll.runCommand(null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSaveSingleCommand() {
    new SaveSingleCommand("file").runCommand(null);
  }



  @Test(expected = IllegalArgumentException.class)
  public void testNullSetCurrentCommand() {
    new SetCurrentCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSetCurrentCommand() {
    new SetCurrentCommand("third").runCommand(null);
  }

  @Test
  public void testSetCurrentCommand() {
    assertEquals("Layer #1, Name of Layer: first, Image Filename: Grid1, Visibility: true\n"
                    + "Layer #2, Name of Layer: second, Image Filename: Grid2, Visibility: true\n"
                    + "Layer #3, Name of Layer: third, Image Filename: Grid3, Visibility: true\n"
                    + "Number of valid layers created: 3\n"
                    + "Current Layer: Name of Layer: third, Image Filename: Grid3, Visibility: true\n",
            model.toString());
    new SetCurrentCommand("second").runCommand(model);
    assertEquals("Layer #1, Name of Layer: first, Image Filename: Grid1, Visibility: true\n"
                    + "Layer #2, Name of Layer: second, Image Filename: Grid2, Visibility: true\n"
                    + "Layer #3, Name of Layer: third, Image Filename: Grid3, Visibility: true\n"
                    + "Number of valid layers created: 3\n"
                    + "Current Layer: Name of Layer: second, Image Filename: Grid2, Visibility: true\n",
            model.toString());
  }
}