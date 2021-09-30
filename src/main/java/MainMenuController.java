import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    private int menuIndex = 0;

    @FXML
    private Button menuButton;
    @FXML
    private Button homeButton;
    @FXML
    /*
       TODO: Swap anchor panes with panes
       TODO: Just one anchor pane as root
       TODO: Splash screen ( with tips )
     */
    private AnchorPane menuAnchorPane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void displayMenu(ActionEvent event) {
        if (menuIndex % 2 == 0) {
            menuAnchorPane.setVisible(true);
            menuAnchorPane.setDisable(false);
        }
        else {
            menuAnchorPane.setVisible(false);
            menuAnchorPane.setDisable(true);
        }
        menuIndex++;
    }


}
