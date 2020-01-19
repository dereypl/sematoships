package com.semato.ships.client.ui.playerinfo;


import com.jfoenix.controls.JFXTextField;
import com.semato.ships.client.ui.boards.BoardsController;
import com.semato.ships.client.ui.wrapper.WrapperController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerInfoController {

    @FXML
    private JFXTextField input;

    @FXML
    private Label errorLabel;

    private static PlayerInfoController instance;

    public PlayerInfoController() {
        instance = this;
    }

    public static PlayerInfoController getInstance() {
        return instance;
    }

    @FXML
    private void handleStartGameAction() {

        if (input.getText().trim().isEmpty()) {
            errorLabel.setText("Nie można rozpocząć gry, bez ustalonego pseudonimu!");
        } else {
            WrapperController.getInstance().changeContentToBoards();
            BoardsController.getInstance().playerNameLabel.setText(input.getText());
        }
    }
}
