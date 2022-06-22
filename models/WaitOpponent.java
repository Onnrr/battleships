package models;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class WaitOpponent implements Runnable {
    private Thread t;
    Player player;
    Text turnText;
    Text remainingText;
    GridPane pane;
    AnchorPane lose;

    public WaitOpponent(Player p, Text txt, GridPane grid, Text remainingText, AnchorPane losePane) {
        player = p;
        turnText = txt;
        pane = grid;
        this.remainingText = remainingText;
        lose = losePane;
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

}
