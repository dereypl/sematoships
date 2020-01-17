package com.semato.ships.client.ui.playerinfo;


import com.jfoenix.controls.JFXButton;
import com.semato.ships.client.ui.wrapper.WrapperController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerInfoController {

    private static PlayerInfoController instance;

    public PlayerInfoController() {
        instance = this;
    }

    public static PlayerInfoController getInstance() {
        return instance;
    }

    @FXML
    private void handleStartGameAction() {
        WrapperController.getInstance().changeContentToBoards();
    }
}
