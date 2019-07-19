package employee;

import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, ControlledScreen {

    ScreensController myController;
    Person p = null;

    @FXML
    private TextField username;


    @FXML
    private PasswordField password;

    private boolean Validate() {
        boolean b = true;
        if (username.getText().isEmpty()) {

            b = false;
        }

        if (password.getText().isEmpty()) {
            b = false;
        }
        if (b == false)
            return false;
        else
            return true;
    }

    private void Reset() {
        password.setText("");
    }

    private Alert createAlert(Alert.AlertType type, String message) {
        return new Alert(type, message);
    }

    @FXML
    private void login() {
        if (!Validate()) {
            createAlert(Alert.AlertType.WARNING, "Please input valid information").show();
            return;
        }
        Datatable d = new Datatable();
        p = new Person();
        p.setUser_name(username.getText());
        if (d.match(p, password.getText()) == null) {
            createAlert(Alert.AlertType.WARNING, "Invalid Credentials!");
        } else if (d.match(p, password.getText()).equals("manager")) {
             myController.setScreen(Files.managerMain);
        } else if (d.match(p, password.getText()).equals("employee")) {
            d.timesheet(p);
            myController.setScreen(Files.employeeMain);
        }
        Reset();
    }


    @FXML
    private void signUp() {
        myController.setScreen(Files.SignUp);
    }

    @FXML
    private void forget() {
        Datatable d = new Datatable();
        if (!Validate()) {
            createAlert(Alert.AlertType.WARNING, "Please input valid information").show();
            return;
        }
        Person p = new Person();
        p.setUser_name(username.getText());
        p.setPassword(password.getText());
        d.forgot(p);

        Reset();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void close() {
        System.exit(0);
    }
}
