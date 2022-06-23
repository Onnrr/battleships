package models;

import java.io.IOException;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class WaitOpponent implements Runnable {
    private Thread t;
    Player player;
    Text turnText;
    Text remainingText;
    GridPane pane;
    AnchorPane lose;
    Pane messagePane;
    Cell[][] buttons;

    public WaitOpponent(Player p, Text txt, GridPane grid, Text remainingText, AnchorPane losePane, Pane message,
            Cell[][] btns) {
        player = p;
        turnText = txt;
        pane = grid;
        this.remainingText = remainingText;
        lose = losePane;
        messagePane = message;
        buttons = btns;
    }

    @Override
    public void run() {
        String guess = "00";
        try {
            guess = player.getMesssage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int row = Character.getNumericValue(guess.charAt(0));
        int column = Character.getNumericValue(guess.charAt(1));

        if (player.hit(column, row)) {
            player.sendMessage("HIT");
            buttons[row][column].getStyleClass().add("hit");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    displayMessage(messagePane, "Your ship has sunk", true);
                }
            });
            player.decrRemaining();
            remainingText.setText("Remaining ships : " + player.getRemaining());
            if (player.getRemaining() == 0) {
                lose.setVisible(true);
                lose.setDisable(false);
                pane.setVisible(true);
                pane.setDisable(false);
            }
        } else {
            player.sendMessage("MISS");
            buttons[row][column].getStyleClass().add("miss");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    displayMessage(messagePane, "Your Opponent Missed", false);
                }
            });

        }
        turnText.setText("Your Turn");
        pane.setVisible(false);
        pane.setDisable(true);
    }

    public void start() {
        System.out.println("Starting " + "wait");
        if (t == null) {
            t = new Thread(this, "waitOpponent");
            t.start();
        }
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
