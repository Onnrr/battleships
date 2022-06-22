package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.AppManager;
import models.Player;
import models.SceneInitialise;

public class StartController implements SceneInitialise {
    @FXML
    CheckBox hostBox;

    @FXML
    CheckBox joinBox;

    @FXML
    TextField codeField;

    @FXML
    Button startButton;

    public void initData() {
        hostBox.setSelected(true);
        joinBox.setSelected(false);
        codeField.setDisable(true);
    }

    @Override
    public void initData(Player p) {
        hostBox.setSelected(true);
        joinBox.setSelected(false);
        codeField.setDisable(true);
    }

    public void startGame(ActionEvent e) throws IOException {
        Player p;
        if (hostBox.isSelected()) {
            p = new Player(true);
        } else {
            p = new Player(false);
            p.setGameID(Integer.parseInt(codeField.getText()));
        }
        if (!p.isHost()) {
            // Wait screen
            try {
                p.connect(p.getGameID());
                System.out.println("client connected");
                AppManager.changeScene(getClass().getResource("/views/setup.fxml"), e, p);
            } catch (IOException IOE) {
                System.out.println("error");
            }
        } else {
            AppManager.changeScene(getClass().getResource("/views/setup.fxml"), e, p);
        }

    }

    public void hostSelect(ActionEvent e) {
        if (hostBox.isSelected()) {
            hostBox.setSelected(true);
            joinBox.setSelected(false);
            codeField.setDisable(true);
            codeField.setText("");
        } else {
            hostBox.setSelected(false);
            joinBox.setSelected(true);
            codeField.setDisable(false);
        }
    }

    public void joinSelect(ActionEvent e) {
        if (joinBox.isSelected()) {
            joinBox.setSelected(true);
            hostBox.setSelected(false);
            codeField.setDisable(false);
        } else {
            joinBox.setSelected(false);
            hostBox.setSelected(true);
            codeField.setDisable(true);
        }
    }

}
