package employee;

import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.ButtonType;

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
    
    Date d=new Date();
    
    @FXML
    private void login() throws SQLException {
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
            
                d.timesheet(p);
            
            int flag=checkLoginTimings(username.getText());
            flag=1;
            if(flag==1)
            {
                
                myController.setScreen(Files.managerMain);
            }
            else
            {
                 Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry "+username.getText()+" , You have not been assigned a shift at this time.", ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> res = alert.showAndWait();

            }
            
        } else if (d.match(p, password.getText()).equals("employee")) {
            d.timesheet(p);
            
        System.out.println("lll");
            int flag=checkLoginTimings(username.getText());
            if(flag==1)
            {
                myController.setScreen(Files.employeeMain);
            }
            else
            {
                 Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry "+username.getText()+" , You have not been assigned a shift at this time.", ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> res = alert.showAndWait();

            }
        }
        Reset();
    }
String usrnm = "hr";
    //String usrnm = "N01328150";

    String passwd = "oracle";

    String urld = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    
    Connection con;
    
    int checkLoginTimings(String usernm) throws SQLException
    {
        int flag=0;
    
        String query = "Select emp_id from Datatable where username='"+usernm+"'";

        con = DriverManager.getConnection(urld, usrnm, passwd);

        Statement prep = con.createStatement();
       
        ResultSet rs=prep.executeQuery(query);
        
        int id=0;
        
        while(rs.next())
        {
           id=rs.getInt("emp_id");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String valDate = sdf.format(d).toString();
        String query2= "select * from schedule where emp_id=? and dt=?";

        System.out.println("lll");

        PreparedStatement prep2 = con.prepareStatement(query2);
        prep2.setInt(1, id);
        prep2.setString(2, valDate);
        
        ResultSet rs2=prep2.executeQuery();
        String startTime="";
        String endTime="";
        
        System.out.println("lll");
        while(rs2.next())
        {
            startTime=rs2.getString("start_time");
            endTime=rs2.getString("end_time");
                   
        }
        if(!startTime.equals(""))
        {
        String []timeValue=startTime.split(" ");
        
        double startTimeValue=Double.parseDouble(timeValue[0]);
        
        timeValue=endTime.split(" ");
        double endTimeValue=Double.parseDouble(timeValue[0]);
        
        int currentHourValue=d.getHours();
        if(currentHourValue<12)
        {
            if(currentHourValue>startTimeValue)
            {
                flag=1;
            }
        }else
        {
            if((currentHourValue-12)<endTimeValue)
            {
                flag=1;
            }
        }
              
        }
        return flag;
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
