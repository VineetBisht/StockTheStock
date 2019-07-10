package sample;

import com.sun.javafx.scene.layout.region.Margins;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class datatablecontroller implements Initializable{

    @FXML private TextField fname;
    @FXML private TextField lname;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField cpassword;
    @FXML private TextField phone_number;
    @FXML private TextArea address;
    @FXML private DatePicker dob;
    @FXML private RadioButton rb_m;
    @FXML private RadioButton rb_e;
    @FXML private Button signup;

    @FXML private Label l_phn;
    @FXML private Label l_bd;
    @FXML private Label l_fnm;
    @FXML private Label l_ln;
    @FXML private Label l_ad;
    @FXML private Label l_cp;
    @FXML private Label l_p;
    @FXML private Label l_u;
    @FXML private Label e_phn;
    @FXML private Label e_dob;




    private boolean Validate() {
        boolean b=true;
        if(username.getText().isEmpty()) {
            l_u.setTextFill(Color.web("red"));
            b=false;
        }
        else
            l_u.setTextFill(Color.web("black"));

        if(fname.getText().isEmpty()) {
            l_fnm.setTextFill(Color.web("red"));
            b=false;
        }
        else
            l_fnm.setTextFill(Color.web("black"));

        if(lname.getText().isEmpty()) {
            l_ln.setTextFill(Color.web("red"));
            b=false;
        }
        else
            l_ln.setTextFill(Color.web("black"));

        if(password.getText().isEmpty()) {
            l_p.setTextFill(Color.web("red"));
            b=false;
        }
        else
            l_p.setTextFill(Color.web("black"));

        if(cpassword.getText().isEmpty()) {
            l_cp.setTextFill(Color.web("red"));
            b=false;
        }
        else
            l_cp.setTextFill(Color.web("black"));

        if(phone_number.getText().isEmpty()) {
            l_phn.setTextFill(Color.web("red"));
            b=false;
        }
        else
            l_phn.setTextFill(Color.web("black"));

        if(address.getText().isEmpty()) {
            l_ad.setTextFill(Color.web("red"));
            b=false;
        }
        else
            l_ad.setTextFill(Color.web("black"));

        if(dob.getValue()==null) {
            l_bd.setTextFill(Color.web("red"));

            b=false;
        }
        else if(dob.getValue()!=null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
            LocalDate now = LocalDate.now();
            if (now.isBefore(dob.getValue())){
                l_bd.setTextFill(Color.web("red"));
                e_dob.setText("Date of Birth should be less than current date");
                b=false;
            }



        else{

            e_dob.setText("");
            l_bd.setTextFill(Color.web("black"));}}


        Pattern p = Pattern.compile("[0-9]{9}");
        Matcher m = p.matcher(phone_number.getText());
        if (!(m.find() && m.group().equals(phone_number.getText()))){
            l_phn.setTextFill(Color.web("red"));
            e_phn.setText("Phone number should be 9 digits");
            b=false;}
        else{
            l_phn.setTextFill(Color.web("black"));
            e_phn.setText("");}

        if(!password.getText().equals(cpassword.getText())) {
            createAlert(AlertType.WARNING, "Password does not match.").show();
            PassReset();
            b=false;
        }

        if(b==false)
            return false;
        else
        return true;
    }
    private void PassReset() {
        password.setText("");
        cpassword.setText("");


    }
    private void Reset() {
        password.setText("");
        cpassword.setText("");
        phone_number.setText("");
        fname.setText("");
        lname.setText("");
        address.setText("");
        phone_number.setText("");

        dob.setValue(null);
        username.setText("");

    }
    private Alert createAlert(AlertType type, String message) {
        return new Alert(type, message);
    }

    @FXML
    private void add() {
        datatable d=new datatable();
        if(!Validate()){
           createAlert(AlertType.WARNING, "Please input valid information").show();
            return;
       }



        String des;
        if(rb_e.isSelected())
            des="employee";
        else
            des="manager";

        Person data = new Person(username.getText(), fname.getText(), lname.getText(), password.getText(), address.getText()
                ,Double.parseDouble(phone_number.getText()),(Date.valueOf(dob.getValue())),des);

        boolean isAdded = d.add(data);

        if(isAdded) {
            createAlert(AlertType.INFORMATION, "Record is added successfully").show();



            Reset();

        }else {
            createAlert(AlertType.INFORMATION, "Task Unsuccessful").show();
        }
    }









    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
