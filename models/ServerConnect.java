package models;

import java.io.IOException;

import javafx.scene.text.Text;

public class ServerConnect implements Runnable {
    private Thread t;
    private String threadName;
    Player player;
    Text text;

    public ServerConnect(String name, Player p, Text text) {
        player = p;
        threadName = name;
        this.text = text;
    }

    @Override
    public void run() {
        System.out.println("Running connect");
        if (player.isHost()) {
            try {
                player.connect(text);
                System.out.println("host connected");
            } catch (IOException e) {
                System.out.println("error");
            }
        } else {

        }
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
