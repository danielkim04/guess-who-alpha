package nz.ac.auckland.se206.controllers;

public class CameraController {
  private RoomController roomController;

  public void onCloseClick() {
    roomController.getClueContainer().setVisible(false);
    roomController.getRoomContainer().setVisible(true);
  }

  public void setRoomController(RoomController roomController) {
    this.roomController = roomController;
  }
}
