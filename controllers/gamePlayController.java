package controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXSnackbarLayout;

import com.jfoenix.controls.JFXSnackbar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import models.AppManager;
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
    AnchorPane winPane;

    @FXML
    AnchorPane losePane;

    @FXML
    Text turnText;

    @FXML
    Text oppShipsText;

    @FXML
    Text remShipsText;

    @FXML
    GridPane darkPane;

    @FXML
    Pane messagePane;

    GridPane opponentTable;
    GridPane myTable;
    Cell[][] buttons;
    Cell[][] myButtons;
    Player player;

    public void initData(Player p) {
        winPane.setVisible(false);
        winPane.setDisable(true);

        messagePane.setVisible(false);
        messagePane.setDisable(true);

        losePane.setVisible(false);
        losePane.setDisable(true);

        remShipsText.setText("Remaining Ships : " + p.getRemaining());
        oppShipsText.setText("Opponent's remaining ships : " + p.getOppRemaining());
        buttons = new Cell[TABLE_SIZE][TABLE_SIZE];
        myButtons = new Cell[TABLE_SIZE][TABLE_SIZE];
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

                Cell b = new Cell(x, y);
                b.setMaxWidth(Double.MAX_VALUE);
                b.setMaxHeight(Double.MAX_VALUE);
                b.setDisable(true);

                if (p.getMyTable()[x][y] == 1) {
                    b.getStyleClass().add("occupied");
                }
                myTable.add(b, x, y);
                myButtons[x][y] = b;
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
            displayMessage(messagePane, "You hit one of your opponent's ships!", false);
            player.incrementCorrectGuess();
            oppShipsText.setText("Opponent's remaining ships : " + player.getOppRemaining());
            if (player.getOppRemaining() == 0) {
                System.out.println("winn");
                winPane.setVisible(true);
                winPane.setDisable(false);
                darkPane.setVisible(true);
                darkPane.setDisable(false);

            }

        } else if (result.equals("MISS")) {
            displayMessage(messagePane, "You Missed", true);
            ((Cell) e.getSource()).setDisable(true);
        }

        opponentsTurn();
    }

    public void opponentsTurn() throws IOException {
        turnText.setText("Opponent's Turn");
        darkPane.setVisible(true);
        darkPane.setDisable(false);
        WaitOpponent wait = new WaitOpponent(player, turnText, darkPane, remShipsText, losePane, messagePane,
                myButtons);
        wait.start();
    }

    public void setTurnText(String string) {
        turnText.setText(string);
    }

    public void exit(ActionEvent e) throws IOException {
        AppManager.changeScene(getClass().getResource("/views/start.fxml"), e, player);
    }

    private void displayMessage(Pane pane, String errorMessage, boolean error) {
        System.out.println(errorMessage);
        pane.setVisible(true);
        pane.setDisable(false);
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        if (error) {
            snackbar.getStylesheets().add("/stylesheets/errorMessage.css");
        } else {
            snackbar.getStylesheets().add("/stylesheets/message.css");
        }

        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(errorMessage)));
        pane.setDisable(true);
    }

}
