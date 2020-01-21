package com.semato.ships.client.ui.playerinfo;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.semato.ships.client.Context;
import com.semato.ships.client.connector.ClientTcp;
import com.semato.ships.client.ui.boards.BoardsController;
import com.semato.ships.client.ui.wrapper.WrapperController;
import com.semato.ships.global.payload.BoardResponse;
import com.semato.ships.global.payload.StartGameRequest;
import com.semato.ships.global.payload.StartGameResponse;
import com.sun.security.ntlm.Client;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerInfoController {

    @FXML
    private JFXTextField input;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXButton StartGameButton;

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
            StartGameButton.setDisable(true);
            errorLabel.setText("Oczekiwanie na połączenie...");

            ClientTcp.getInstance().startConnection("localhost", 5000);
            Task<StartGameResponse> startGameTask = new Task<StartGameResponse>() {
                @Override
                public StartGameResponse call() {
                    return ClientTcp.getInstance().sendStartGameRequest(input.getText());
                }
            };

            Task<BoardResponse> sendEmptyRequestTask = new Task<BoardResponse>() {
                @Override
                public BoardResponse call() {
                    return ClientTcp.getInstance().sendEmptyRequest();
                }
            };

            sendEmptyRequestTask.setOnSucceeded(e -> {
                BoardResponse response = sendEmptyRequestTask.getValue();
                Context.getInstance().setEnemyTurn(false);
                Context.getInstance().setMyBoard(response.getMyBoard());
            });

            startGameTask.setOnSucceeded(e -> {
                StartGameResponse response = startGameTask.getValue();
                Context.getInstance().setEnemyBoard(response.getEnemyBoard());
                Context.getInstance().setEnemyTurn(response.isEnemyTurn());
                Context.getInstance().setEnemyNick(response.getEnemyNick());
                Context.getInstance().setPlayerNick(input.getText());

                if(response.isEnemyTurn()){
                    new Thread(sendEmptyRequestTask).start();
                }

                WrapperController.getInstance().changeContentToBoards();
            });

            new Thread(startGameTask).start();
        }
    }
}
