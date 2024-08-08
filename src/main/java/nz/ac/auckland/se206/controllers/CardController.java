package nz.ac.auckland.se206.controllers;

public class CardController {
  private RoomController roomController;

  public void onCloseClick() {
    roomController.getClueContainer().setVisible(false);
  }

  public void setRoomController(RoomController roomController) {
    this.roomController = roomController;
  }
}
