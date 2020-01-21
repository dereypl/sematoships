package com.semato.ships.client.ui.playerinfo;


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
            errorLabel.setText("Oczekiwanie na połączenie...");

            ClientTcp.getInstance().startConnection("localhost", 5000);
            Task<StartGameResponse> startConnectionTask = new Task<StartGameResponse>() {
                @Override
                public StartGameResponse call() throws IOException, ClassNotFoundException {
                    return ClientTcp.getInstance().sendStartGameRequest(input.getText());
                }
            };

            Task<BoardResponse> sendEmptyRequestTask = new Task<BoardResponse>() {
                @Override
                public BoardResponse call() throws IOException, ClassNotFoundException {
                    return ClientTcp.getInstance().sendEmptyRequest();
                }
            };


            sendEmptyRequestTask.setOnSucceeded(e -> {
                System.out.println(" board success");
                BoardResponse response = sendEmptyRequestTask.getValue();
                Context.getInstance().setMyBoard(response.getMyBoard());

            });

            startConnectionTask.setOnSucceeded(e -> {
                System.out.println("success");
                StartGameResponse response = startConnectionTask.getValue();
                Context.getInstance().setEnemyBoard(response.getEnemyBoard());
                Context.getInstance().setEnemyTurn(response.isEnemyTurn());
                Context.getInstance().setEnemyNick(response.getEnemyNick());
                Context.getInstance().setPlayerNick(input.getText());

                if(response.isEnemyTurn()){
                    new Thread(sendEmptyRequestTask).start();
                }

                WrapperController.getInstance().changeContentToBoards();
            });

            new Thread(startConnectionTask).start();
        }
    }
}
