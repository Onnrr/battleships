package controllers;

import java.io.IOException;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import models.Cell;
import models.Player;
import models.SceneInitialise;
import models.WaitOpponent;

public class GamePlayController implements SceneInitialise {
    final int TABLE_SIZE = 10;

    @FXML
    GridPane rightGrid;

    @FXML
    AnchorPane anchor;

    @FXML
    AnchorPane anchor2;

    @FXML
    Text turnText;

    @FXML
    Text oppShipsText;

    @FXML
    Text remShipsText;

    @FXML
    GridPane darkPane;

    GridPane opponentTable;
    GridPane myTable;
    Cell[][] buttons;
    Player player;

    public void initData(Player p) {

        remShipsText.setText("Remaining Ships : " + p.getRemaining());
        oppShipsText.setText("Opponent's remaining ships : " + p.getOppRemaining());
        buttons = new Cell[TABLE_SIZE][TABLE_SIZE];
        setUpGrid(p);
        if (!p.isHost()) {
            darkPane.setVisible(true);
            darkPane.setDisable(false);
            turnText.setText("Opponent's Turn");
            try {
                opponentsTurn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            darkPane.setVisible(false);
            darkPane.setDisable(true);
            turnText.setText("Your Turn");
        }
    }

    public void setUpGrid(Player p) {
        player = p;
        opponentTable = new GridPane();
        opponentTable.setMaxHeight(Double.MAX_VALUE);
        opponentTable.setMaxWidth(Double.MAX_VALUE);

        myTable = new GridPane();
        myTable.setMaxHeight(Double.MAX_VALUE);
        myTable.setMaxWidth(Double.MAX_VALUE);

        AnchorPane.setRightAnchor(opponentTable, 10.0);
        AnchorPane.setLeftAnchor(opponentTable, 30.0);
        AnchorPane.setBottomAnchor(opponentTable, 10.0);
        AnchorPane.setTopAnchor(opponentTable, 10.0);
        anchor.getChildren().add(opponentTable);

        rightGrid.add(myTable, 1, 1);

        for (int i = 0; i < TABLE_SIZE; i++) {
            ColumnConstraints col = new ColumnConstraints();
            ColumnConstraints col2 = new ColumnConstraints();
            col.setPercentWidth(100 / TABLE_SIZE);
            col2.setPercentWidth(100 / TABLE_SIZE);
            opponentTable.getColumnConstraints().add(col);
            myTable.getColumnConstraints().add(col2);
            RowConstraints row = new RowConstraints();
            RowConstraints row2 = new RowConstraints();
            row.setPercentHeight(100 / TABLE_SIZE);
            row2.setPercentHeight(100 / TABLE_SIZE);
            opponentTable.getRowConstraints().add(row);
            myTable.getRowConstraints().add(row2);
        }

        for (int x = 0; x < TABLE_SIZE; x++) {
            for (int y = 0; y < TABLE_SIZE; y++) {
                Cell button = new Cell(x, y);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);

                button.setOnMouseClicked(e -> {
                    try {
                        handleClick(e);
                    } catch (IOException e1) {
                        System.out.println("mouseclick IO error");
                    }
                });
                opponentTable.add(button, x, y);
                buttons[x][y] = button;

                Button b = new Button();
                b.setMaxWidth(Double.MAX_VALUE);
                b.setMaxHeight(Double.MAX_VALUE);
                b.setDisable(true);

                if (p.getMyTable()[y][x] == 1) {
                    b.getStyleClass().add("occupied");
                }
                myTable.add(b, y, x);

            }
        }

    }

    private void handleClick(MouseEvent e) throws IOException {
        int row = ((Cell) e.getSource()).getRow();
        int column = ((Cell) e.getSource()).getColumn();

        String guess = String.valueOf(row);
        guess += column;
        player.sendMessage(guess);

        String result = player.getMesssage();

        if (result.equals("HIT")) {
            ((Cell) e.getSource()).setDisable(true);
            ((Cell) e.getSource()).getStyleClass().add("hit");
            player.incrementCorrectGuess();
            oppShipsText.setText("Opponent's remaining ships : " + player.getOppRemaining());

        } else if (result.equals("MISS")) {
            ((Cell) e.getSource()).setDisable(true);
        }

        opponentsTurn();
    }

    public void opponentsTurn() throws IOException {
        turnText.setText("Opponent's Turn");
        darkPane.setVisible(true);
        darkPane.setDisable(false);
        WaitOpponent wait = new WaitOpponent(player, turnText, darkPane, remShipsText);
        wait.start();

    }

    public void setTurnText(String string) {
        turnText.setText(string);
    }

}
