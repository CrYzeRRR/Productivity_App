import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
// OKHttp sau JKSON for APIS


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("Life Tracky");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Photos/icon.png")));
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

