package employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML private PasswordField password;

    private boolean validate() {
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
    private void reset() {
        password.setText("");

        username.setText("");

    }
    private Alert createAlert(Alert.AlertType type, String message) {
        return new Alert(type, message);
    }

    @FXML
    private void login() {
        if(!validate()){
            createAlert(Alert.AlertType.WARNING, "Please input valid information").show();
            return;
        }
        Datatable d = new Datatable();
        sample.Person p=new sample.Person();
        p.setUser_name(username.getText());
        d.match(p,password.getText());
        reset();

    }
    @FXML
    private void forget() {
        Datatable d = new Datatable();
        if(!validate()){
            createAlert(Alert.AlertType.WARNING, "Please input valid information").show();
            return;
        }
        sample.Person p=new sample.Person();
        p.setUser_name(username.getText());
        p.setPassword(password.getText());
        d.forgot(p);
        reset();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
