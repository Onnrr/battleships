package controllers;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXSnackbarLayout;

import com.jfoenix.controls.JFXSnackbar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import models.Cell;
import models.Player;
import models.ReadyWait;
import models.SceneInitialise;
import models.ServerConnect;

public class SetupController implements SceneInitialise {
    final int TABLE_SIZE = 10;
    int length;
    boolean isVertical;
    Cell[][] buttons;
    Player player;

    @FXML
    GridPane background;

    @FXML
    Button readyButton;

    @FXML
    ComboBox<String> shipBox;

    @FXML
    ComboBox<String> directionBox;

    @FXML
    ImageView image;

    @FXML
    Text gameIDText;

    @FXML
    Text waitText;

    @FXML
    Pane messagePane;

    GridPane table;

    boolean noAction;

    @Override
    public void initData(Player p) {
        if (p.isHost()) {
            ServerConnect connect = new ServerConnect("server connection", p, waitText);
            connect.start();
        } else {
            waitText.setVisible(false);
        }

        shipBox.getItems().addAll("1 Block", "1 Block", "1 Block", "2 Blocks", "2 Blocks", "2 Blocks",
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
        readyButton.setDisable(true);

        setUpGrid(p);
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                buttons[i][j].setOccupied(false);
            }
        }
        player = p;

        setGameID(p.getGameID());
        noAction = false;

        messagePane.setVisible(false);
        messagePane.setDisable(true);
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

                button.setOnMouseClicked(e -> {
                    handleClick(e);
                });

                button.setOnMouseEntered((event) -> {
                    if (!setHoverEffect(event)) {
                        disabledHoverEffect(event);
                    }
                });

                button.setOnMouseExited((event) -> {
                    removeHoverEffect(event);
                    noAction = false;
                });

                button.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());

                table.add(button, x, y);
                buttons[x][y] = button;
            }
        }

    }

    public void shipSelect(ActionEvent e) {
        File file;
        if (shipBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
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
        if (noAction) {
            return;
        }
        if (directionBox.getSelectionModel().getSelectedItem().equals("Vertical")) {
            int start = ((Cell) e.getSource()).getColumn();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE) {
                    break;
                }
                buttons[((Cell) e.getSource()).getRow()][i].setDisable(true);
                buttons[((Cell) e.getSource()).getRow()][i].setOccupied(true);
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().remove("hovered");
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().remove("outOfBounds");
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().add("occupied");
            }
        } else {
            int start = ((Cell) e.getSource()).getRow();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE) {
                    break;
                }
                buttons[i][((Cell) e.getSource()).getColumn()].setDisable(true);
                buttons[i][((Cell) e.getSource()).getColumn()].setOccupied(true);
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().remove("hovered");
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().remove("outOfBounds");
                buttons[i][((Cell) e.getSource()).getColumn()].getStyleClass().add("occupied");
            }
        }

        shipBox.getItems().remove(shipBox.getSelectionModel().getSelectedItem());
        if (shipBox.getItems().isEmpty()) {
            shipBox.setDisable(true);
            readyButton.setDisable(false);
            length = 0;
        } else {
            shipBox.getSelectionModel().selectFirst();
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

    }

    private boolean setHoverEffect(MouseEvent e) {
        if (directionBox.getSelectionModel().getSelectedItem().equals("Vertical")) {
            int start = ((Cell) e.getSource()).getColumn();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE || buttons[((Cell) e.getSource()).getRow()][i].isOccupied()) {
                    return false;
                }
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().add("hovered");
            }
        } else {
            int start = ((Cell) e.getSource()).getRow();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE || buttons[i][((Cell) e.getSource()).getColumn()].isOccupied()) {
                    return false;
                }
                buttons[i][((Cell) e.getSource()).getColumn()].getStyleClass().add("hovered");
            }
        }
        return true;
    }

    private boolean removeHoverEffect(MouseEvent e) {
        if (directionBox.getSelectionModel().getSelectedItem().equals("Vertical")) {
            int start = ((Cell) e.getSource()).getColumn();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE) {
                    return false;
                }
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().remove("hovered");
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().remove("outOfBounds");
            }
        } else {
            int start = ((Cell) e.getSource()).getRow();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE) {
                    return false;
                }
                buttons[i][((Cell) e.getSource()).getColumn()].getStyleClass().remove("hovered");
                buttons[i][((Cell) e.getSource()).getColumn()].getStyleClass().remove("outOfBounds");
            }
        }
        return true;
    }

    private void disabledHoverEffect(MouseEvent e) {
        noAction = true;
        if (directionBox.getSelectionModel().getSelectedItem().equals("Vertical")) {
            int start = ((Cell) e.getSource()).getColumn();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE) {
                    break;
                }
                buttons[((Cell) e.getSource()).getRow()][i].getStyleClass().add("outOfBounds");
            }
        } else {
            int start = ((Cell) e.getSource()).getRow();
            for (int i = start; i < start + length; i++) {
                if (i >= TABLE_SIZE) {
                    break;
                }
                buttons[i][((Cell) e.getSource()).getColumn()].getStyleClass().add("outOfBounds");
            }
        }
    }

    public void reset(ActionEvent e) {
        shipBox.getItems().clear();

        directionBox.getItems().clear();
        initData(player);
    }

    public void ready(ActionEvent e) throws IOException {
        if (!player.isOpponentConnected()) {
            System.out.println("not connected");
            displayMessage(messagePane, "Other player has not joined yet", true);
            return;
        }
        System.out.println("connected");
        readyButton.setDisable(true);
        waitText.setVisible(true);
        waitText.setText("Waiting for Opponent");
        player.sendMessage("READY");
        for (int x = 0; x < TABLE_SIZE; x++) {
            for (int y = 0; y < TABLE_SIZE; y++) {
                if (buttons[y][x].isOccupied()) {
                    player.getMyTable()[y][x] = 1;
                } else {
                    player.getMyTable()[y][x] = 0;
                }
            }
        }
        ReadyWait wait = new ReadyWait(player, waitText, e);
        wait.start();
    }

    public void setGameID(int id) {
        gameIDText.setText("Game ID = " + id);
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
