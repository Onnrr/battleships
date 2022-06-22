package models;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;

public class ReadyWait implements Runnable {
    Player player;
    Text text;
    private Thread t;
    ActionEvent event;

    public ReadyWait(Player p, Text txt, ActionEvent event) {
        player = p;
        text = txt;
        this.event = event;
    }

    @Override
    public void run() {
        String ready = "";
        try {
            ready = player.getMesssage();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (ready.equals("READY")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        AppManager.changeScene(getClass().getResource("/views/gamePlay.fxml"), event, player);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void start() {
        System.out.println("Starting Wait");
        if (t == null) {
            t = new Thread(this, "");
            t.start();
        }
    }

}
