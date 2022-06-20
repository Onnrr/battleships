import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Player;
import models.SceneInitialise;

public class Main extends Application {
    // aasd
    @Override
    public void start(Stage primaryStage) throws Exception {

        Player player = new Player(false);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/gamePlay.fxml"));
        loader.load();

        SceneInitialise c2 = loader.getController();
        c2.initData(player);

        Parent p = loader.getRoot();
        Scene scene = new Scene(p);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Battle Ships");
        primaryStage.setFullScreen(false);
        primaryStage.setHeight(700);
        primaryStage.setWidth(1100);
        primaryStage.setResizable(false);
        primaryStage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
