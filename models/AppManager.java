package models;

import java.io.IOException;
import java.net.URL;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppManager {
    public static void changeScene(URL fxmlfile, ActionEvent event, Player player) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlfile);
        loader.load();
        SceneInitialise c2 = loader.getController();
        c2.initData(player);
        Parent p = loader.getRoot();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(p, ((Node) event.getSource()).getScene().getWidth(),
                ((Node) event.getSource()).getScene().getHeight());
        // scene.getStylesheets().add(userSession.getStyleSheet());
        stage.setScene(scene);
        stage.show();
    }
}
