package com.semato.ships.client.ui.home;


import com.jfoenix.controls.JFXButton;
import com.semato.ships.client.ui.wrapper.WrapperController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private static HomeController instance;

    public HomeController() {
        instance = this;
    }

    public static HomeController getInstance() {
        return instance;
    }

    @FXML
    private void handleStartGameAction(ActionEvent event) {
        WrapperController.getInstance().changeContentToPlayerInfo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
