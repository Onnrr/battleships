package models;

import java.io.IOException;

public class ServerConnect implements Runnable {
    private Thread t;
    private String threadName;
    Player player;

    public ServerConnect(String name, Player p) {
        player = p;
        threadName = name;
    }

    @Override
    public void run() {
        System.out.println("Running connect");
        if (player.isHost()) {
            try {
                player.connect();
            } catch (IOException e) {
                System.out.println("error");
            }
        } else {
            try {
                player.connect(6868);
            } catch (IOException e) {
                System.out.println("error");
            }
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
