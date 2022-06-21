package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    Button startButton;

    public void initData() {
        hostBox.setSelected(true);
        joinBox.setSelected(false);
        codeField.setDisable(true);
    }

    public void startGame(ActionEvent e) throws IOException {
        Player p;
        if (hostBox.isSelected()) {
            p = new Player(nameField.getText(), true);
        } else {
            p = new Player(nameField.getText(), false);
            p.setGameID(Integer.parseInt(codeField.getText()));
        }
        AppManager.changeScene(getClass().getResource("/views/setup.fxml"), e, p);
    }

    public void nameAction(ActionEvent e) {
        if (nameField.getText().equals("")) {
            startButton.setDisable(true);
        } else {
            if (joinBox.isSelected() && !codeField.getText().equals("")) {
                startButton.setDisable(false);
            } else if (hostBox.isSelected()) {
                startButton.setDisable(false);
            }
        }
    }

    public void hostSelect(ActionEvent e) {
        if (hostBox.isSelected()) {
            hostBox.setSelected(true);
            joinBox.setSelected(false);
            codeField.setDisable(true);
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
