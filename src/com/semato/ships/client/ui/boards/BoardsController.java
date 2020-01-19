package com.semato.ships.client.ui.boards;

import com.semato.ships.client.Context;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.semato.ships.global.Board.*;

public class BoardsController implements Initializable {

    Image ship = new Image("com/semato/ships/client/resources/assets/your_ship.png");
    Image sunk = new Image("com/semato/ships/client/resources/assets/ship_sunk.png");
    Image water = new Image("com/semato/ships/client/resources/assets/water.png");
    Image enemy_water = new Image("com/semato/ships/client/resources/assets/enemy_water.png");
    Image player_miss = new Image("com/semato/ships/client/resources/assets/shoot_missed.png");
    Image player_hit = new Image("com/semato/ships/client/resources/assets/your_ship_hit.png");
    Image enemy_miss = new Image("com/semato/ships/client/resources/assets/shoot_missed.png");
    Image enemy_hit = new Image("com/semato/ships/client/resources/assets/enemy_hit.png");

    @FXML
    private GridPane playerBoard;

    @FXML
    public Text playerNameLabel;

    private static BoardsController instance;

    public BoardsController() {
        instance = this;
    }

    public static BoardsController getInstance() {
        return instance;
    }

    private void fillBoard(GridPane board) {

        int fieldStatus;

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {

                if (board == playerBoard) fieldStatus = Context.getInstance().getMyBoard().getStatus(x, y);
                else fieldStatus = Context.getInstance().getEnemyBoard().getStatus(x, y);

                switch (fieldStatus) {
                    case STATUS_WATER:
                        if (board == playerBoard) board.add(new ImageView(water), y, x);
                        else board.add(new ImageView(enemy_water), y, x);
                        break;

                    case STATUS_SHIP:
                        board.add(new ImageView(ship), y, x);
                        break;

                    case STATUS_MISS:
                        if (board == playerBoard) board.add(new ImageView(player_miss), y, x);
                        else board.add(new ImageView(enemy_miss), y, x);
                        break;

                    case STATUS_HIT:
                        if (board == playerBoard) board.add(new ImageView(player_hit), y, x);
                        else board.add(new ImageView(enemy_hit), y, x);
                        break;

                    case STATUS_SUNK:
                        board.add(new ImageView(sunk), y, x);
                        break;
                }

            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillBoard(playerBoard);
    }
}
