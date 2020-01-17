package com.semato.ships.client.ui.boards;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BoardsController {

    private static BoardsController instance;

    public BoardsController() {
        instance = this;
    }

    public static BoardsController getInstance() {
        return instance;
    }

    @FXML
    private void handleStartGameAction(ActionEvent event) {
//        WrapperController.getInstance().changeContentToRepertoire();
    }
}
