package nz.ac.auckland.se206.states;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.controllers.RoomController;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * The GameStarted state of the game. Handles the initial interactions when the game starts,
 * allowing the player to chat with characters and prepare to make a guess.
 */
public class GameStarted implements GameState {

  private final GameStateContext context;
  private RoomController roomController;
  private String currentRectangleId;
  private boolean hasClueBeenInspected = false;
  private boolean hasSuspectBeenInvestigated = false;

  /**
   * Constructs a new GameStarted state with the given game state context.
   *
   * @param context the context of the game state
   */
  public GameStarted(GameStateContext context) {
    this.context = context;
    this.roomController = context.getRoomController();
  }

  /**
   * Handles the event when a rectangle is clicked. Depending on the clicked rectangle, it either
   * provides an introduction or transitions to the chat view.
   *
   * @param event the mouse event triggered by clicking a rectangle
   * @param rectangleId the ID of the clicked rectangle
   * @throws IOException if there is an I/O error
   */
  @Override
  public void handleRectangleClick(MouseEvent event, String rectangleId) throws IOException {
    // Transition to chat view or provide an introduction based on the clicked rectangle
    if (rectangleId.equals(currentRectangleId)) {
      return; // does not reset the chat view if the same rectangle is clicked
    }
    // clues handled by cases, all others by default
    switch (rectangleId) {
      case "rectCamera":
        TextToSpeech.speak("Welcome to my cafe!");
        hasClueBeenInspected = true;
        return;
      case "rectNote":
        TextToSpeech.speak("Hi, let me know when you are ready to order!");
        hasClueBeenInspected = true;
        return;
    }
    roomController.loadChatView(rectangleId);
    if (rectangleId.equals("rectPerson1")
        || rectangleId.equals("rectPerson2")
        || rectangleId.equals("rectPerson3")) {
      hasSuspectBeenInvestigated = true;
    }
    currentRectangleId = rectangleId;
  }

  /**
   * Handles the event when the guess button is clicked. Prompts the player to make a guess and
   * transitions to the guessing state.
   *
   * @throws IOException if there is an I/O error
   */
  @Override
  public void handleGuessClick() throws IOException {
    TextToSpeech.speak("Make a guess, click on the " + context.getProfessionToGuess());
    context.setState(context.getGuessingState());
  }

  @Override
  public void setRoomController(RoomController roomController) {
    this.roomController = roomController;
  }

  public boolean getHasClueBeenInspected() {
    return hasClueBeenInspected;
  }

  public boolean getHasSuspectBeenInvestigated() {
    return hasSuspectBeenInvestigated;
  }
}
