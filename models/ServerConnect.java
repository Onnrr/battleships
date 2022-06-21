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
