package controllers;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import models.Cell;
import models.Player;
import models.SceneInitialise;

public class SetupController implements SceneInitialise {
    final int TABLE_SIZE = 10;
    int length;
    boolean isVertical;
    Cell[][] buttons;

    @FXML
    GridPane background;

    @FXML
    ComboBox<String> shipBox;

    @FXML
    ComboBox<String> directionBox;

    @FXML
    ImageView image;

    GridPane table;

    @Override
    public void initData(Player p) {
        shipBox.getItems().addAll("1 Block", "1 Block", "1 Block", "1 Block", "2 Blocks", "2 Blocks", "2 Blocks",
                "3 Blocks", "3 Blocks", "5 Blocks");
        directionBox.getItems().addAll("Vertical", "Horizontal");

        shipBox.getSelectionModel().selectFirst();
        directionBox.getSelectionModel().selectFirst();

        isVertical = true;
        length = 1;

        File file = new File("images/1Block.jpg");
        Image img = new Image(file.toURI().toString());
        image.setImage(img);

        buttons = new Cell[TABLE_SIZE][TABLE_SIZE];

        setUpGrid(p);
    }

    public void setUpGrid(Player p) {
        table = new GridPane();
        table.setMaxHeight(Double.MAX_VALUE);
        table.setMaxWidth(Double.MAX_VALUE);

        background.add(table, 1, 1);

        for (int i = 0; i < TABLE_SIZE; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100 / TABLE_SIZE);
            table.getColumnConstraints().add(col);
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100 / TABLE_SIZE);
            table.getRowConstraints().add(row);
        }

        for (int x = 0; x < TABLE_SIZE; x++) {
            for (int y = 0; y < TABLE_SIZE; y++) {
                Cell button = new Cell(x, y);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                // button.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
                // button.getStyleClass().add("gridButton");
                button.setOnMouseClicked(e -> {
                    handleClick(e);
                });
                table.add(button, x, y);
                buttons[x][y] = button;
            }
        }
    }

    public void shipSelect(ActionEvent e) {
        File file;
        if (shipBox.getSelectionModel().getSelectedItem().equals("1 Block")) {
            file = new File("images/1Block.jpg");
        } else if (shipBox.getSelectionModel().getSelectedItem().equals("2 Blocks")) {
            file = new File("images/2Block.jpg");
        } else if (shipBox.getSelectionModel().getSelectedItem().equals("3 Blocks")) {
            file = new File("images/3Block.jpg");
        } else {
            file = new File("images/5Block.jpg");
        }
        length = Character.getNumericValue(shipBox.getSelectionModel().getSelectedItem().charAt(0));
        Image img = new Image(file.toURI().toString());
        image.setImage(img);
    }

    private void handleClick(MouseEvent e) {
        int start = ((Cell) e.getSource()).getRow();
        for (int i = start; i < start + length; i++) {
            buttons[i][((Cell) e.getSource()).getColumn()].setDisable(true);
        }

    }
}
