package nz.ac.auckland.se206.states;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.controllers.RoomController;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * The Guessing state of the game. Handles the logic for when the player is making a guess about the
 * profession of the characters in the game.
 */
public class Guessing implements GameState {

  private final GameStateContext context;
  private RoomController roomController;

  /**
   * Constructs a new Guessing state with the given game state context.
   *
   * @param context the context of the game state
   */
  public Guessing(GameStateContext context) {
    this.context = context;
  }

  /**
   * Handles the event when a rectangle is clicked. Checks if the clicked rectangle is a customer
   * and updates the game state accordingly.
   *
   * @param event the mouse event triggered by clicking a rectangle
   * @param rectangleId the ID of the clicked rectangle
   * @throws IOException if there is an I/O error
   */
  @Override
  public void handleRectangleClick(MouseEvent event, String rectangleId) throws IOException {
    if (rectangleId.equals("rectCamera") || rectangleId.equals("rectNote")) {
      TextToSpeech.speakMp3("src/main/resources/sounds/clicksuspect.mp3");
      roomController.setStatusMessage("You should click on the suspects", 3);
      return;
    }

    // String clickedProfession = context.getProfession(rectangleId);
    if (rectangleId.equals("rectPerson1")) {
      TextToSpeech.speakMp3("src/main/resources/sounds/youwon.mp3");
      roomController.setStatusMessage("Victory!\nThis is the thief!", 60);
    } else {
      TextToSpeech.speakMp3("src/main/resources/sounds/youlost.mp3");
      roomController.setStatusMessage("Defeat!\nThis is not the thief!", 60);
    }
    context.setState(context.getGameOverState());
  }

  /**
   * Handles the event when the guess button is clicked. Since the player has already guessed, it
   * notifies the player.
   *
   * @throws IOException if there is an I/O error
   */
  @Override
  public void handleGuessClick() throws IOException {
    TextToSpeech.speak("You have already guessed!");
  }

  @Override
  public void setRoomController(RoomController roomController) {
    this.roomController = roomController;
  }
}
