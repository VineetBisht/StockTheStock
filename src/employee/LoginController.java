package employee;

import backend.ControlledScreen;
import backend.Person;
import backend.ScreensController;
import backend.datatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class LoginController implements Initializable, ControlledScreen {

    ScreensController myController;
    @FXML
    private TextField username;


    @FXML private PasswordField password;

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    private boolean Validate() {
        boolean b = true;
        if (username.getText().isEmpty()) {

            b = false;
        }


        if (password.getText().isEmpty()) {

            b = false;
        }
        if(b==false)
            return false;
        else
            return true;
    }
    private void Reset() {
        password.setText("");

        username.setText("");

    }
    private Alert createAlert(Alert.AlertType type, String message) {
        return new Alert(type, message);
    }

    @FXML
    private void login() {
        if(!Validate()){
            createAlert(Alert.AlertType.WARNING, "Please input valid information").show();
            return;
        }
        datatable d = new datatable();
        Person p=new Person();
        p.setUser_name(username.getText());
        if(d.match(p,password.getText()))
        d.timesheet(p);
        Reset();

    }
    @FXML
    private void logout() {
        if(!Validate()){
            createAlert(Alert.AlertType.WARNING, "Please input valid information").show();
            return;
        }
        datatable d = new datatable();
        Person p=new Person();
        p.setUser_name(username.getText());
        if(d.match(p,password.getText()))
        d.endshift(p);
        Reset();

    }
    @FXML
    private void forget() {
        datatable d = new datatable();
        if(!Validate()){
            createAlert(Alert.AlertType.WARNING, "Please input valid information").show();
            return;
        }
        Person p=new Person();
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
        myController=screenPage;
    }
}
