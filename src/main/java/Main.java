import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// OKHttp sau JKSON for APIS


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SplashFXMLController splash =  new SplashFXMLController();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(root, 1200, 800);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        for (int i = 0; i < 100; i++) {
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(i));
        }
    }


    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);
    }
}

