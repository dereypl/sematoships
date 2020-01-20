package com.semato.ships.client.ui.wrapper;


import com.semato.ships.client.ui.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WrapperController extends MainController {

    @FXML
    private ImageView CloseButton;

    @FXML
    AnchorPane contentPane;

    private static WrapperController instance;

    public WrapperController() {
        instance = this;
    }

    public static WrapperController getInstance() {
        return instance;
    }

    private void closeStage() {
        ((Stage) CloseButton.getScene().getWindow()).close();
    }

   @FXML
    void handleCloseButtonAction(MouseEvent event) {
        System.exit(0);
    } //TODO: end connection here!!



    private void setNode(Node node){
        contentPane.getChildren().clear();
        contentPane.getChildren().add((Node) node);
    }
    public void setContentPage(AnchorPane Content, String loc )
    {
        try {
            Content = FXMLLoader.load(getClass().getResource(loc));
            setNode(Content);
        } catch(IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadPage("/com/semato/ships/client/ui/wrapper/wrapper.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setContentPage(contentPane, "/com/semato/ships/client/ui/home/home.fxml");
    }

    @FXML
    public void changeContentToHome() {
        setContentPage(contentPane, "/com/semato/ships/client/ui/home/home.fxml");
    }

    @FXML
    public void changeContentToPlayerInfo() {
        setContentPage(contentPane, "/com/semato/ships/client/ui/playerinfo/playerInfo.fxml");
    }

    @FXML
    public void changeContentToBoards() {
        setContentPage(contentPane, "/com/semato/ships/client/ui/boards/boards.fxml");
    }
}
