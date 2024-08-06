package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.GameTimer;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class RoomController {

  @FXML private Rectangle rectCashier;
  @FXML private Rectangle rectPerson1;
  @FXML private Rectangle rectPerson2;
  @FXML private Rectangle rectPerson3;
  @FXML private Rectangle rectWaitress;
  @FXML private Label lblProfession;
  @FXML private Button btnGuess;
  @FXML private Pane chatContainer;
  @FXML private Label labelTimer;

  private static boolean isFirstTimeInit = true;
  private static GameStateContext context;
  private ChatController chatController;
  private GameTimer gameTimer;

  /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   */
  @FXML
  public void initialize() {
    // System.out.println("Roomcontroller initialised");
    // System.out.println("label timer : " + labelTimer);
    context = new GameStateContext(this);
    context.setRoomController(this);
    loadChatView(null);
    if (isFirstTimeInit) {
      TextToSpeech.speak(
          "Chat with the three customers, and guess who is the " + context.getProfessionToGuess());
      isFirstTimeInit = false;
      this.gameTimer = context.getGameTimer();
      // System.out.println("GAME TIMER: " + gameTimer);
      gameTimer.start();
    }
    lblProfession.setText(context.getProfessionToGuess());
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " released");
  }

  /**
   * Handles mouse clicks on rectangles representing people in the room.
   *
   * @param event the mouse event triggered by clicking a rectangle
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    context.handleRectangleClick(event, clickedRectangle.getId());
  }

  /**
   * Handles the guess button click event.
   *
   * @param event the action event triggered by clicking the guess button
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleGuessClick(ActionEvent event) throws IOException {
    context.handleGuessClick();
  }

  public void loadChatView(String rectangleID) {
    try {
      FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/chat.fxml"));
      Parent chatView = loader.load();
      chatController = loader.getController();
      if (rectangleID != null) {
        chatController.setProfession(rectangleID);
      }
      chatContainer.getChildren().clear();
      chatContainer.getChildren().add(chatView);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void updateTimer(String time) {
    if (labelTimer == null) {
      System.out.println("***************labelTimer is null*****************");
    }
    labelTimer.setText(time);
  }

  public Label getlabelTimer() {
    return labelTimer;
  }
}
