package employee;

import backend.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController implements Initializable, ControlledScreen {
    ScreensController myController;
    String otpc="";

    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField cpassword;
    @FXML
    private TextField phone_number;
    @FXML
    private TextField email;
    @FXML
    private TextArea address;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton rb_m;
    @FXML
    private RadioButton rb_e;

    @FXML
    private Button signup;

    @FXML
    private Button otpButton;

    @FXML
    private Label l_phn;
    @FXML
    private Label l_bd;
    @FXML
    private Label email_label;
    @FXML
    private Label l_fnm;
    @FXML
    private Label l_ln;
    @FXML
    private Label l_ad;
    @FXML
    private Label l_cp;
    @FXML
    private Label l_p;
    @FXML
    private Label l_u;
    @FXML
    private Label e_phn;
    @FXML
    private Label e_dob;

    @FXML
    private Label otpLabel;

    @FXML
    private TextField otp;

    private boolean Validate() {
        boolean b = true;
        if (username.getText().isEmpty()) {
            l_u.setTextFill(Color.web("red"));
            b = false;
        } else
            l_u.setTextFill(Color.web("black"));

        if (fname.getText().isEmpty()) {
            l_fnm.setTextFill(Color.web("red"));
            b = false;
        } else
            l_fnm.setTextFill(Color.web("black"));

        if (lname.getText().isEmpty()) {
            l_ln.setTextFill(Color.web("red"));
            b = false;
        } else
            l_ln.setTextFill(Color.web("black"));

        if (email.getText().isEmpty()) {
            email_label.setTextFill(Color.web("red"));
            b = false;
        } else
            email_label.setTextFill(Color.web("black"));


        if (password.getText().isEmpty()) {
            l_p.setTextFill(Color.web("red"));
            b = false;
        } else
            l_p.setTextFill(Color.web("black"));

        if (cpassword.getText().isEmpty()) {
            l_cp.setTextFill(Color.web("red"));
            b = false;
        } else
            l_cp.setTextFill(Color.web("black"));

        if (phone_number.getText().isEmpty()) {
            l_phn.setTextFill(Color.web("red"));
            b = false;
        } else
            l_phn.setTextFill(Color.web("black"));

        if (address.getText().isEmpty()) {
            l_ad.setTextFill(Color.web("red"));
            b = false;
        } else
            l_ad.setTextFill(Color.web("black"));

        if (dob.getValue() == null) {
            l_bd.setTextFill(Color.web("red"));

            b = false;
        } else if (dob.getValue() != null) {
            LocalDate now = LocalDate.now();
            Period period= Period.between(dob.getValue(),now);
            int gap=period.getYears();
            if (now.isBefore(dob.getValue()) ) {
                l_bd.setTextFill(Color.web("red"));
                e_dob.setText("Date of Birth should be less than current date");
                b = false;
            }
            else if(gap<18 ){
                l_bd.setTextFill(Color.web("red"));
                e_dob.setText("User can't be less than 18 years old");
                b=false;
            }else {

                e_dob.setText("");
                l_bd.setTextFill(Color.web("black"));
            }
        }


        Pattern p = Pattern.compile("[0-9]{10}");
        Matcher m = p.matcher(phone_number.getText());
        if (!(m.find() && m.group().equals(phone_number.getText()))) {
            l_phn.setTextFill(Color.web("red"));
            e_phn.setText("Phone number should be 10 digits");
            b = false;
        } else {
            l_phn.setTextFill(Color.web("black"));
            e_phn.setText("");
        }

        if (!password.getText().equals(cpassword.getText())) {
            createAlert(AlertType.WARNING, "Password does not match.").show();
            PassReset();
            b = false;
        }

        if (b == false)
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
        email.setText("");
        dob.setValue(null);
        username.setText("");

    }

    private Alert createAlert(AlertType type, String message) {
        return new Alert(type, message);
    }

    @FXML
    private void close() {
        System.exit(0);
    }

        @FXML
    private void add() {
        if (!Validate()) {
            createAlert(AlertType.WARNING, "Please input valid information").show();
            return;
        }

        otpc=SignUpController.OTP();

        try{
            Datatable.sendMail(email.getText(),"Your OTP :"+ otpc);
        }catch (Exception e){
            e.printStackTrace();
        }

        fname.setDisable(true);
        lname.setDisable(true);
        password.setDisable(true);
        cpassword.setDisable(true);
        phone_number.setDisable(true);
        address.setDisable(true);
        username.setDisable(true);
        dob.setDisable(true);
        rb_e.setDisable(true);
        rb_m.setDisable(true);
        signup.setDisable(true);
        otp.setVisible(true);
        otpButton.setVisible(true);
        otpLabel.setVisible(true);
        email.setDisable(true);
        createAlert(AlertType.INFORMATION, "Confirmation OTP has been generated. Please confirm your email.").show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    private void back() {
        myController.setScreen(Files.login);
    }

        @FXML
    private void confirmOTP() {

        if(otp.getText().isEmpty()){
            createAlert(AlertType.INFORMATION, "Enter the OTP").show();
            return;
        }

        if(!otpc.equals(otp.getText())){
            createAlert(AlertType.INFORMATION, "Wrong OTP!").show();
            return;
        }

        Datatable d = new Datatable();

        String des;
        if (rb_e.isSelected())
            des = "employee";
        else
            des = "manager";

        Person data = new Person(username.getText(), fname.getText(), lname.getText(), password.getText(), address.getText()
                , Double.parseDouble(phone_number.getText()), (Date.valueOf(dob.getValue())), des, email.getText());

        boolean isAdded = d.add(data);

        if (isAdded) {
            createAlert(AlertType.INFORMATION, "Record is added successfully").show();
            Reset();
            myController.setScreen(Files.login);
        } else {
            createAlert(AlertType.ERROR, "Task Unsuccessful").show();
        }

    }

    static String OTP()
    {
        int len=6;
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");

        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        String otpt = "";

        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otpt +=
                    numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        System.out.println(otpt);
        return otpt;
    }
}
