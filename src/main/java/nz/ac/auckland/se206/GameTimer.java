package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.auckland.se206.controllers.RoomController;

public class GameTimer {
  private Timeline timeline;
  private int timeInSeconds;
  private int timeRemaining;
  private GameStateContext context;
  private RoomController roomController;

  public GameTimer(int minutes, GameStateContext context, RoomController roomController) {
    timeInSeconds = minutes * 60;
    timeRemaining = timeInSeconds;
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
    timeline.setCycleCount(timeInSeconds + 10);
    this.context = context;
    this.roomController = roomController;
  }

  private void updateTimer() {
    if (timeRemaining > 1) {
      timeRemaining--;
      roomController.updateTimer(getRemainingTimeFormatted());
    } else {
      timeRemaining--;
      roomController.updateTimer(getRemainingTimeFormatted());
      if (context.getGameState() instanceof nz.ac.auckland.se206.states.GameStarted) {
        context.setStateToGuessing();
        // initialise 10 second timer
        timeRemaining = 10;
        roomController.setStatusMessage("Time is up!\nYou have 10 seconds to make a guess.");
        roomController.updateTimer(getRemainingTimeFormatted());
      } else if (timeRemaining == 0) {
        context.setStateToGameOver();
        roomController.setStatusMessage("Time is up!\nGame over.");
        stop();
      } else {
        roomController.updateTimer(getRemainingTimeFormatted());
      }
    }
  }

  public void start() {
    timeline.playFromStart();
  }

  public void stop() {
    timeline.stop();
  }

  public int getRemainingTime() {
    return timeRemaining;
  }

  public String getRemainingTimeFormatted() {
    int minutes = timeRemaining / 60;
    int seconds = timeRemaining % 60;
    return String.format("%02d:%02d", minutes, seconds);
  }
}
