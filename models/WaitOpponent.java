package models;

import java.io.IOException;

public class WaitOpponent implements Runnable {
    private Thread t;
    Player player;

    public WaitOpponent(Player p) {
        player = p;
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

        if (player.hit(row, column)) {
            player.sendMessage("HIT");

        } else {
            player.sendMessage("MISS");
        }
    }

    public void start() {
        System.out.println("Starting " + "wait");
        if (t == null) {
            t = new Thread(this, "waitOpponent");
            t.start();
        }
    }
}
