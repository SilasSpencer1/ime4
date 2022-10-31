
package utils;


import controller.CreateImageLayerCommand;
import controller.IPhotoCommands;
import controller.LoadAllCommand;
import controller.LoadSingleCommand;
import controller.SaveAllCommand;
import controller.SaveSingleCommand;
import controller.SetCurrentCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * This class contains utility methods to help get the known controller commands of an
 * IImageProcessingController.
 */
public class ControllerUtils {

  /**
   * Represents a static method that gets the known commands that can be used in an {@code
   * IImageProcessingController} and returns them as a Map.
   *
   * @return the map of known commands associated with the controller
   */
  public static Map<String, Function<Scanner, IPhotoCommands>> getKnownCommands() {
    Map<String, Function<Scanner, IPhotoCommands>> knownCommands =
        new HashMap<>();

    knownCommands.putIfAbsent("create", scanner -> new CreateImageLayerCommand(scanner.next()));
    knownCommands.putIfAbsent("current", scanner -> new SetCurrentCommand(scanner.next()));
    knownCommands.putIfAbsent("load", scanner -> new LoadSingleCommand(scanner.next()));
    knownCommands.putIfAbsent("loadall", scanner -> new LoadAllCommand(scanner.next()));
    knownCommands.putIfAbsent("save", scanner -> new SaveSingleCommand(scanner.next()));
    knownCommands.putIfAbsent("saveall", scanner -> new SaveAllCommand(scanner.next()));

    return knownCommands;
  }
}