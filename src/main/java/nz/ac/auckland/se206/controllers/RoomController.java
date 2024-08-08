package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.GameTimer;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class RoomController {

  @FXML private Rectangle rectCamera;
  @FXML private Rectangle rectPerson1;
  @FXML private Rectangle rectPerson2;
  @FXML private Rectangle rectPerson3;
  @FXML private Rectangle rectNote;
  @FXML private Label lblProfession;
  @FXML private Button btnGuess;
  @FXML private Pane chatContainer;
  @FXML private Label labelTimer;
  @FXML private Label labelStatus;
  @FXML private Pane clueContainer;
  @FXML private ImageView loadingGif1;
  @FXML private ImageView loadingGif2;
  @FXML private ImageView loadingGif3;

  private static boolean isFirstTimeInit = true;
  private static GameStateContext context;
  private ChatController chatController;
  private GameTimer gameTimer;
  private NoteController noteController;
  private CameraController cameraController;
  private CardController cardController;
  private String currentRectangleSelected;

  /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   */
  @FXML
  public void initialize() {
    // System.out.println("Roomcontroller initialised");
    // System.out.println("label timer : " + labelTimer);
    hideLoadingGif(1);
    hideLoadingGif(2);
    hideLoadingGif(3);
    loadChatView(null);
    if (isFirstTimeInit) {
      context = new GameStateContext(this);
      context.setRoomController(this);
      TextToSpeech.speak("Interrogate the three suspects, and guess who is the thief.");
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
  public void handleRectangleClick(MouseEvent event) throws IOException {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    currentRectangleSelected = clickedRectangle.getId();

    // shows thought bubble gif upon clicking a person
    switch (clickedRectangle.getId()) {
      case "rectPerson1":
        showLoadingGif(1);
        break;
      case "rectPerson2":
        showLoadingGif(2);
        break;
      case "rectPerson3":
        showLoadingGif(3);
        break;
    }

    context.handleRectangleClick(event, clickedRectangle.getId());
  }

  /**
   * Handles the guess button click event.
   *
   * @param event the action event triggered by clicking the guess button
   * @throws IOException if there is an I/O error
   */
  @FXML
  public void handleGuessClick(ActionEvent event) throws IOException {
    if (!(context.readyToGuess())) {
      System.out.println("Not ready to guess yet");
      setStatusMessage("Not ready to guess yet");
      return;
    }
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

      chatController.setGameContext(context);
      chatController.setRoomController(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadClueView(String fileName) {
    try {
      FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + fileName + ".fxml"));
      Parent clueView = loader.load();
      switch (fileName) {
        case "note":
          noteController = loader.getController();
          break;
        case "camera":
          cameraController = loader.getController();
          break;
        case "card":
          cardController = loader.getController();
        default:
          break;
      }
      clueContainer.getChildren().clear();
      clueContainer.getChildren().add(clueView);
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

  public void setStatusMessage(String message) {
    labelStatus.setText(message);
    Timeline timeline =
        new Timeline(new KeyFrame(Duration.seconds(3), event -> labelStatus.setText("")));
    timeline.setCycleCount(1);
    timeline.playFromStart();
  }

  public void showLoadingGif(int gifNumber) {
    switch (gifNumber) {
      case 1:
        loadingGif1.setVisible(true);
        break;
      case 2:
        loadingGif2.setVisible(true);
        break;
      case 3:
        loadingGif3.setVisible(true);
        break;
      default:
        break;
    }
  }

  public void hideLoadingGif(int gifNumber) {
    switch (gifNumber) {
      case 1:
        loadingGif1.setVisible(false);
        break;
      case 2:
        loadingGif2.setVisible(false);
        break;
      case 3:
        loadingGif3.setVisible(false);
        break;
      default:
        break;
    }
  }

  public Pane getClueContainer() {
    return clueContainer;
  }

  public NoteController getNoteController() {
    return noteController;
  }

  public CameraController getCameraController() {
    return cameraController;
  }

  public CardController getCardController() {
    return cardController;
  }

  public String getCurrentRectangleSelected() {
    return currentRectangleSelected;
  }
}
