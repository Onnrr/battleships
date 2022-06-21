import controllers.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/start.fxml"));
        loader.load();

        StartController c = loader.getController();
        c.initData();

        Parent p = loader.getRoot();
        Scene scene = new Scene(p);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Battle Ships");
        primaryStage.setFullScreen(false);
        primaryStage.setHeight(700);
        primaryStage.setWidth(1100);
        // primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
