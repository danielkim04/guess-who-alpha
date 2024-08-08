package nz.ac.auckland.se206.controllers;

import java.io.IOException;

public class NoteController {
  private RoomController roomController;

  public void onCloseClick() throws IOException {
    roomController.getClueContainer().setVisible(false);
    roomController.getRoomContainer().setVisible(true);
  }

  public void setRoomController(RoomController roomController) {
    this.roomController = roomController;
  }
}
