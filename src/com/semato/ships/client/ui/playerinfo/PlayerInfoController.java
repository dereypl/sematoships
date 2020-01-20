package com.semato.ships.client.ui.playerinfo;


import com.jfoenix.controls.JFXTextField;
import com.semato.ships.client.Context;
import com.semato.ships.client.connector.ClientTcp;
import com.semato.ships.client.ui.boards.BoardsController;
import com.semato.ships.client.ui.wrapper.WrapperController;
import com.semato.ships.global.payload.StartGameRequest;
import com.semato.ships.global.payload.StartGameResponse;
import com.sun.security.ntlm.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

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
            ClientTcp.getInstance().startConnection("localhost", 5000);
            StartGameResponse startGameResponse = startGame(input.getText());
            Context.getInstance().setEnemyBoard(startGameResponse.getEnemyBoard());
            WrapperController.getInstance().changeContentToBoards();
            BoardsController.getInstance().playerNameLabel.setText(input.getText());
            BoardsController.getInstance().enemyNameLabel.setText(startGameResponse.getEnemyNick());
        }
    }

    private StartGameResponse startGame(String nick){
        try {
            ClientTcp.getInstance().getOutObj().writeObject(new StartGameRequest(nick, Context.getInstance().getMyBoard()));
            return (StartGameResponse) ClientTcp.getInstance().getInObj().readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
