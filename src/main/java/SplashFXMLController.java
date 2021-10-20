import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashFXMLController implements Initializable {

    @FXML
    AnchorPane mainAnchorPane;

    @FXML
    public static ProgressBar splashProgressBar;

//    LoadingTime loadingTime = new LoadingTime();
//    Thread splash = new Thread(loadingTime);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        splash.start();
    }
}

//class LoadingTime implements Runnable {
//
//    private int index = 0;
//    private boolean flag = false;
//    SplashFXMLController object = new SplashFXMLController();
//    @FXML
//    AnchorPane mainAnchorPane = object.mainAnchorPane;
//    @FXML
//    ProgressBar progressBar = object.progressBar;
//
//    @Override
//    public void run() {
//        try {
//            if(index == 3) {
//                mainAnchorPane.setVisible(false);
//                mainAnchorPane.setDisable(true);
//            }
//            Thread.sleep(1000);
//            index++;
//            progressBar.setProgress(index * 33);
//        }
//        catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
