package com.semato.ships.client.ui;



import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.ArrayList;


public abstract class MainController extends Application implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

//    @FXML
//    void handleCloseButtonAction(MouseEvent event) throws IOException, ClassNotFoundException {
//
//        Message terminate = Client.getInstance().sendMessage(new Message("terminate",new ArrayList<>()));
//        System.out.println(terminate.getType());
//
//        Client.getInstance().stopConnection();
//
//        System.exit(0);
//    }


    public void MovingStage(Parent root, Stage stage) {

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }


    public void loadPage(String path) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL.APPLICATION_MODAL);
            stage.setTitle("SEMATO SHIPS");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            setPosition(stage);
            MovingStage(root, stage);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void setPosition(Stage stage){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

}
