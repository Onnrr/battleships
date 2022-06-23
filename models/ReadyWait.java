package models;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketPermission;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ReadyWait implements Runnable {
    Player player;
    Text text;
    private Thread t;
    ActionEvent event;
    Pane messagePane;

    public ReadyWait(Player p, Text txt, ActionEvent event, Pane msg) {
        player = p;
        text = txt;
        this.event = event;
        messagePane = msg;
    }

    @Override
    public void run() {
        String ready = "";
        try {
            ready = player.getMesssage();
        } catch (IOException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    displayMessage(messagePane, "Other player disconnected", true);
                }
            });
        }

        if (ready.equals("READY")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        AppManager.changeScene(getClass().getResource("/views/gamePlay.fxml"), event, player);
                    } catch (IOException e) {
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
