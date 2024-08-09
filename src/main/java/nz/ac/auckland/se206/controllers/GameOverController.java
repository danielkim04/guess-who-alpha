package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController {

  @FXML private Label labelStatus;

  public void setStatusMessage(String message) {
    labelStatus.setText(message);
  }
}
