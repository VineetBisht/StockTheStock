package employee;

import backend.ControlledScreen;
import backend.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class LoginController implements ControlledScreen {

        ScreensController myController;

        @FXML
        private MenuItem about;

        @FXML
        private TextField username;

        @FXML
        private TextField password;

        @FXML
        void close(ActionEvent event) {

        }

        @FXML
        void forgot(ActionEvent event) {

        }

        @FXML
        void login(ActionEvent event) {

        }

        @FXML
        void signup(ActionEvent event) {

        }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController=screenParent;
    }
}


