package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;

public class IntroController {

  @FXML private Button startButton;

  @FXML
  public void initialize() {}

  @FXML
  public void onClicked(ActionEvent event) throws IOException {
    App.setRoot("room");
  }
}
