package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameTimer {
  private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
  private int timeInSeconds;
  private int timeRemaining;

  public GameTimer(int minutes) {
    timeInSeconds = minutes * 60;
    timeRemaining = timeInSeconds;
    timeline.setCycleCount(timeInSeconds);
  }

  private void updateTimer() {
    if (timeRemaining > 0) {
      timeRemaining--;
      // GUI update implementation here
    } else {
      // timer stop implementation here
    }
  }

  public void start() {
    timeline.playFromStart();
  }

  public void stop() {
    timeline.stop();
  }

  public String getRemainingTime() {
    int minutes = timeRemaining / 60;
    int seconds = timeRemaining % 60;
    return String.format("%02d:%02d", minutes, seconds);
  }
}
