package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.AppManager;
import models.Player;

public class StartController {
    @FXML
    CheckBox hostBox;

    @FXML
    CheckBox joinBox;

    @FXML
    TextField nameField;

    @FXML
    TextField codeField;

    public void initData() {

    }

    public void startGame(ActionEvent e) throws IOException {
        Player p;
        if (hostBox.isSelected()) {
            p = new Player(true);
        } else {
            p = new Player(false);
        }
        AppManager.changeScene(getClass().getResource("/views/setup.fxml"), e, p);
    }

    Player player = new Player(false);
}
