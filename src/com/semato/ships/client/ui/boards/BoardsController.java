package com.semato.ships.client.ui.boards;

import com.semato.ships.client.Context;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.semato.ships.global.Board.*;

public class BoardsController implements Initializable {

    private Image ship = new Image("com/semato/ships/client/resources/assets/your_ship.png");
    private Image sunk = new Image("com/semato/ships/client/resources/assets/ship_sunk.png");
    private Image water = new Image("com/semato/ships/client/resources/assets/water.png");
    private Image enemy_water = new Image("com/semato/ships/client/resources/assets/enemy_water.png");
    private Image player_miss = new Image("com/semato/ships/client/resources/assets/shoot_missed.png");
    private Image player_hit = new Image("com/semato/ships/client/resources/assets/your_ship_hit.png");
    private Image enemy_miss = new Image("com/semato/ships/client/resources/assets/shoot_missed.png");
    private Image enemy_hit = new Image("com/semato/ships/client/resources/assets/enemy_hit.png");

    @FXML
    private GridPane playerBoard;

    @FXML
    private GridPane enemyBoard;

    @FXML
    private Text gameStatusInfo;

    @FXML
    public Text playerNameLabel;

    private static BoardsController instance;

    public BoardsController() {
        instance = this;
    }

    public static BoardsController getInstance() {
        return instance;
    }

    public void showEnemyMoveText(){
        gameStatusInfo.setText("Oczekiwanie na ruch przeciwnika...");
        gameStatusInfo.setFill(Color.rgb(236, 28, 45));
    }

    public void showPlayerMoveText(){
        gameStatusInfo.setText("Twoja kolej!");
        gameStatusInfo.setFill(Color.rgb(0, 240, 255));
    }

    private void fillBoard(GridPane board) {

        int fieldStatus;
        boolean isPlayerBoard = (board == playerBoard);

        for (int y = 0; y < DIMENSION; y++) {
            for (int x = 0; x < DIMENSION; x++) {

                if (isPlayerBoard) fieldStatus = Context.getInstance().getMyBoard().getStatus(x, y);
                else fieldStatus = Context.getInstance().getEnemyBoard().getStatusForEnemy(x, y);

                switch (fieldStatus) {
                    case STATUS_WATER:
                        if (isPlayerBoard) board.add(new ImageView(water), x, y);
                        else {
                            board.add(new ImageView(enemy_water), x, y);
                            int finalX = x;
                            int finalY = y;
                            board.getChildren().get(DIMENSION * y + x).setOnMousePressed(e -> System.out.println("X: " + finalX + " Y: " + finalY)); //TODO: shoot request to server (send enemy board)
                        }
                        break;

                    case STATUS_SHIP:
                        board.add(new ImageView(ship), x, y);
                        break;

                    case STATUS_MISS:
                        if (isPlayerBoard) board.add(new ImageView(player_miss), x, y);
                        else board.add(new ImageView(enemy_miss), x, y);
                        break;

                    case STATUS_HIT:
                        if (isPlayerBoard) board.add(new ImageView(player_hit), x, y);
                        else board.add(new ImageView(enemy_hit), x, y);
                        break;

                    case STATUS_SUNK:
                        board.add(new ImageView(sunk), x, y);
                        break;
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillBoard(playerBoard);
        fillBoard(enemyBoard);
        showEnemyMoveText();
    }
}
