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
    timeline.setCycleCount(timeInSeconds);
    this.context = context;
    this.roomController = roomController;
  }

  private void updateTimer() {
    if (timeRemaining > 1) {
      // System.out.println("Time remaining: " + timeRemaining);
      timeRemaining--;
      roomController.updateTimer(getRemainingTimeFormatted());
    } else {
      // System.out.println("*****TIME IS OUT*****");
      timeRemaining--;
      roomController.updateTimer(getRemainingTimeFormatted());
      context.setStateToGuessing();
      stop();
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
