import javafx.application.Preloader;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class MyPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    public MyPreloader() {

    }

    @Override
    public void init() throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        scene = new Scene(root1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            SplashFXMLController.splashProgressBar.setProgress(((ProgressNotification) info).getProgress());
        }
    }


    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                System.out.println("BEFORE_START");
                preloaderStage.hide();
                break;
        }

    }

}
