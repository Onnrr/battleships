package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import models.Player;
import models.SceneInitialise;

public class gamePlayController implements SceneInitialise {
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

    GridPane opponentTable;
    GridPane myTable;

    public void initData(Player p) {
        turnText.setText("Opponent's Turn");
        remShipsText.setText("Remaining Ships : " + p.getRemaining());
        oppShipsText.setText("Opponent's remaining ships : " + p.getOppRemaining());
        setUpGrid(p);
    }

    public void setUpGrid(Player p) {
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
                Button button = new Button();
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                // button.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
                // button.getStyleClass().add("gridButton");
                button.setOnMouseClicked(e -> {
                    handleClick(e);
                });
                opponentTable.add(button, x, y);

                Button b = new Button();
                b.setMaxWidth(Double.MAX_VALUE);
                b.setMaxHeight(Double.MAX_VALUE);
                b.setDisable(true);
                b.setMaxWidth(Double.MAX_VALUE);
                b.setMaxHeight(Double.MAX_VALUE);
                if (p.getMyTable()[x][y] == 0) {

                } else if (p.getMyTable()[x][y] == 1) {

                }
                myTable.add(b, x, y);

            }
        }

    }

    private void handleClick(MouseEvent e) {
    }
}
