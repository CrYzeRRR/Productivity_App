import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.*;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    private int menuIndex = 0;

    @FXML
    private Button menuButton;
    @FXML
    private Button homeButton;
    @FXML
    private Pane menuPane;

    @FXML
    private Button tasksBtn,goalsBtn,toDoBtn,scheduleBtn,habitsBtn,progressBtn,quotesBtn;

    /*
       TODO: Splash screen ( with tips )
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void displayMenu(ActionEvent event) {
        if (menuIndex % 2 == 0) {
            menuPane.setVisible(true);
            menuPane.setDisable(false);
        }
        else {
            menuPane.setVisible(false);
            menuPane.setDisable(true);
        }
        menuIndex++;
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {

        Stage stage;
        Parent root;

        if(event.getSource()==tasksBtn){
            stage = (Stage) tasksBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("TasksMenu.fxml"));
        }
        else {
            stage = (Stage) tasksBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("TasksMenu.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
