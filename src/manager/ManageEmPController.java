package manager;

import backend.*;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Shivam
 */
public class ManageEmPController implements Initializable, ControlledScreen {
    Button []buttonSchedule=new Button[100];
    Connection con;
    ScreensController myController;
    @FXML private TextField txtid;
    @FXML private TableView<Schedule> mytableSchedule;
    @FXML private TableColumn<Schedule,String> sun;
    @FXML private TableColumn<Schedule,String> mon;
    @FXML private TableColumn<Schedule,String> tue;
    @FXML private TableColumn<Schedule,String> wed;
    @FXML private TableColumn<Schedule,String> thurs;
    @FXML private TableColumn<Schedule,String> fri;
    @FXML private TableColumn<Schedule,String> sat;
    @FXML private TableColumn<Schedule,Double> hours;
    
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    
    int empCounter=0;//for kepping track of the buttons being built dynamically in the tableview
    
    @FXML
    private TextField txt1start;
    
    @FXML
    private TextField txt2end;
    
    @FXML
    private TableView<ComplaintEmp> mytable;
    @FXML
    private TableView<ComplaintCust> mytable2;
    @FXML
    private TableView<Employee> myTableEmp;
    @FXML
    private TabPane tp;
    @FXML
    private Tab tab1;
    @FXML
    private Tab tab5;
    @FXML
    private Tab tab2;
    @FXML
    private Tab tab3;
    @FXML
    private Tab tab4;
    @FXML private ComboBox cb1;
    @FXML private ComboBox cb2;
    @FXML private ComboBox cb3;
    String []days={"Week","Sunday","Monday","Tuesday","Wednessday","Thursday","Friday","Saturday"};
    
    int empScheduleId;//the id for which the schedule is being inserted
    
    //tablecolumns for schedule employee tab
    @FXML
    private TableColumn<Employee, Integer> employeeId;

    @FXML
    private TableColumn<Employee, String> employeeName;
    
    @FXML
    private TableColumn<Employee, Button> employeebtn;
    
    //tableColumns for employee complaint tab
    @FXML
    private TableColumn<ComplaintEmp, Integer> compId;

    @FXML
    private TableColumn<ComplaintEmp, Integer> empId;

    @FXML
    private TableColumn<ComplaintEmp, String> comSubject;

    @FXML
    private TableColumn<ComplaintEmp, String> comDesc;

    @FXML
    private TableColumn<ComplaintEmp, String> status;

    @FXML
    private TableColumn<ComplaintEmp, Date> date;

    //tableColumns for customers complaint tab
    @FXML
    TableColumn<ComplaintCust, Integer> compempId;

    @FXML
    TableColumn<ComplaintCust, Integer> prodId;

    @FXML
    TableColumn<ComplaintCust, String> custName;

    @FXML
    TableColumn<ComplaintCust, String> comempDesc;
    
    @FXML Button btnScheduleInsert;
    
    @FXML TableColumn<ComplaintCust, Date> comdate;
    
    @FXML Button btnDisplaysch;
    
    int counterDateEmp = 0;//increasing the counter as we find records with the empid in the table for the current week
    //String usrnm="N01324490";
    String usrnm = "N01328150";
    
    String passwd = "oracle";
    
    String urld = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    @FXML private void displaySchedule2() throws SQLException
    {
        int id=Integer.parseInt(txtid.getText());
        Calendar cal=Calendar.getInstance();
        int weekNoSchedule=cal.get(Calendar.WEEK_OF_YEAR);

        mon.setCellValueFactory(new PropertyValueFactory<Schedule,String>("monTime"));
        tue.setCellValueFactory(new PropertyValueFactory<Schedule, String>("tuesTime"));
        wed.setCellValueFactory(new PropertyValueFactory<Schedule, String>("wedTime"));
        thurs.setCellValueFactory(new PropertyValueFactory<Schedule,String>("thursTime"));
        fri.setCellValueFactory(new PropertyValueFactory<Schedule, String>("friTime"));
        sat.setCellValueFactory(new PropertyValueFactory<Schedule, String>("satTime"));
        sun.setCellValueFactory(new PropertyValueFactory<Schedule, String>("sunTime"));
        hours.setCellValueFactory(new PropertyValueFactory<Schedule,Double>("totHours"));

        ObservableList<Schedule> obscom = FXCollections.observableArrayList();;
        
            String query = "Select * from Schedule where emp_id=?";

            con = DriverManager.getConnection(urld, usrnm, passwd);

            PreparedStatement prep=con.prepareStatement(query);
            prep.setInt(1,id);
            
            ResultSet rs=prep.executeQuery();

            Schedule scd =new Schedule();
            Calendar cal2=Calendar.getInstance();
            Calendar calTemp=Calendar.getInstance();
            int f=calTemp.get(Calendar.DAY_OF_WEEK);
            while (rs.next()) {
                Date dt=rs.getDate("dt");
                cal2.setTime(dt);
                int weekNoSchedule2=cal2.get(Calendar.WEEK_OF_YEAR);
                if(weekNoSchedule2==weekNoSchedule)
                {
                    int dayNo=cal2.get(Calendar.DAY_OF_WEEK);
                    switch(dayNo)
                    {
                        case 1:
                        {
                            String timing=rs.getString("start_time")+" - "+rs.getString("end_time");
                            scd.setSunTime(timing);
                            
                        break;
                        }
                        case 2:
                        {
                            String timing=rs.getString("start_time")+" - "+rs.getString("end_time");
                            scd.setMonTime(timing);
                            
                            
                        break;
                        }
                        case 3:
                        {
                        String timing=rs.getString("start_time")+" - "+rs.getString("end_time");
                            scd.setTuesTime(timing);
                            
                            
                        break;
                        }
                        case 4:
                        {
                            String timing=rs.getString("start_time")+" - "+rs.getString("end_time");
                            scd.setWedTime(timing);
                            
                        break;
                        }
                        case 5:
                        {
                            String timing=rs.getString("start_time")+" - "+rs.getString("end_time");
                            scd.setThursTime(timing);
                            
                        break;
                        }
                        case 6:
                        {
                            String timing=rs.getString("start_time")+" - "+rs.getString("end_time");
                            scd.setFriTime(timing);
                            
                        break;
                        }
                        case 7:
                        {
                            String timing=rs.getString("start_time")+" - "+rs.getString("end_time");
                            scd.setSatTime(timing);
                            
                        break;
                        }
                    }
            
                }
                
            }
            scd.calculateTotalHours();
            obscom.add(scd);
        mytableSchedule.setItems(obscom);
    
    } 

    @FXML private AnchorPane ap;
    
    @FXML
    private Tab tab11;
    
    @FXML
    Label lblweek;
    
    @FXML Label lbldate;
    
    void empComplaintSetValue() {

        compId.setCellValueFactory(new PropertyValueFactory<ComplaintEmp, Integer>("compId"));

        empId.setCellValueFactory(new PropertyValueFactory<ComplaintEmp, Integer>("empid"));

        comSubject.setCellValueFactory(new PropertyValueFactory<ComplaintEmp, String>("subject"));

        comDesc.setCellValueFactory(new PropertyValueFactory<ComplaintEmp, String>("desc"));

        status.setCellValueFactory(new PropertyValueFactory<ComplaintEmp, String>("status"));

        date.setCellValueFactory(new PropertyValueFactory<ComplaintEmp, Date>("dt"));

        ObservableList<ComplaintEmp> obscom = FXCollections.observableArrayList();;
        
        try {
            
            String query = "Select * from employeecomplaint";

            con = DriverManager.getConnection(urld, usrnm, passwd);

            ResultSet rs = con.createStatement().executeQuery(query);

            ComplaintEmp comp = null;

            while (rs.next()) {
                comp = new ComplaintEmp();
                comp.setCompId(rs.getInt("complaintid"));
                comp.setEmpid(rs.getInt("empid"));
                comp.setDt(rs.getDate("complaintdate"));
                comp.setSubject(rs.getString("subject"));
                comp.setDesc(rs.getString("description"));
                comp.setStatus(rs.getString("status"));
                obscom.add(comp);
            }
        } catch (Exception ex) {
        }

        mytable.setItems(obscom);
        
    }
    
    @FXML
    void insertSchedule(ActionEvent event)
    {
        int flag=0;
        int timeFlag=0;
        Date newDate=null;
        String dayValue="";
        try{
            dayValue=cb1.getValue().toString();
        }catch(Exception ex)
        {
            flag=1;
        }
        if(flag==0)
        {
        Calendar cal=Calendar.getInstance();
        int todayDate=cal.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < 8; i++) {
            if(dayValue.equals(days[i])) //checking selected day from combobox and comparing it with the string stored for the day above 
            {
                if(todayDate<i) 
                {
                    int difference=i-todayDate; //to get the date  of the day that is being selected inn the combobox, we take the difference 
                                                //between the two day and then add the differnce of days  it to the current day    
                    cal.add(Calendar.DAY_OF_MONTH, difference); //adding the difference in the current date 
                    newDate=cal.getTime();
                }
                else
                {
                    newDate=cal.getTime();
                }
            }
        }
            
        }
        String startDateValue="";
        String endDateValue="";
        try{
            double startTimeValue=Double.parseDouble(txt1start.getText());
            double endTimeValue=Double.parseDouble(txt2end.getText());
            String AmPm=cb2.getValue().toString();
            startDateValue =txt1start.getText()+" "+AmPm;
            AmPm=cb3.getValue().toString();
            endDateValue=txt2end.getText()+" "+AmPm;
            
            if(cb2.getValue().equals(cb3.getValue()))
            {
                if((startTimeValue<endTimeValue || (startTimeValue==12 && endTimeValue<startTimeValue))  && endTimeValue<12 && startTimeValue>0)
                {
                     timeFlag=0;
                }
                else
                {
                    Alert alert=new Alert(Alert.AlertType.WARNING,"Time is not set properly.",ButtonType.OK,ButtonType.CANCEL);
                    Optional<ButtonType> res=alert.showAndWait();
                    timeFlag=1;
                }
            }else
            {
                 if((cb3.getValue().equals("AM") && endTimeValue<12))
                {
                    timeFlag=0;
                }
                 else
                 {
                    timeFlag=1;
                 }
                 if(cb2.getValue().equals("PM"))
                 {
                     if(cb3.getValue().equals("AM"))
                     {
                        timeFlag=1;
                     }
                     else 
                     {
                        timeFlag=0;
                     }
                 }
                 if(cb2.getValue().equals("AM"))
                 {
                     if(cb3.getValue().equals("PM"))
                     {
                        timeFlag=0;
                     }
                     else 
                     {
                        timeFlag=1;
                     }
                 }
                 if(timeFlag==1)
                 {
                     
                    Alert alert=new Alert(Alert.AlertType.WARNING,"Time is not set properly.",ButtonType.OK,ButtonType.CANCEL);
                    Optional<ButtonType> res=alert.showAndWait();
                 }
            }
            
        }
        catch(Exception ex)
        {
            flag=1;
        }
        if(timeFlag==0)
        {
           if(flag==1|| txt1start.getText().equals("") || txt2end.getText().equals("") || cb1.getValue().equals("") || cb2.getValue().equals("") || cb3.getValue().equals(""))
           {
               Alert alert=new Alert(Alert.AlertType.WARNING,"No fields or combobox can be left empty and proper value should be inputed.",ButtonType.OK,ButtonType.CANCEL);
            Optional<ButtonType> res=alert.showAndWait();
           }
           else{
           try {      
                java.sql.Date sqldt=new java.sql.Date(newDate.getTime());
                String query = "insert into schedule values(?,?,?,?)";

                con = DriverManager.getConnection(urld, usrnm, passwd);

                PreparedStatement prep=con.prepareStatement(query);

                prep.setInt(1, empScheduleId);
                prep.setDate(2, sqldt);
                prep.setString(3, startDateValue);
                prep.setString(4, endDateValue);

                int x=prep.executeUpdate();
                
                if(x==1)
                {
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"The schedule has been set.",ButtonType.OK,ButtonType.CANCEL);
                    Optional<ButtonType> res=alert.showAndWait();
                    btnScheduleInsert.setDisable(true);
                    txt1start.setText("");
                    txt2end.setText("");
                    cb1.getItems().clear();                    
                    cb2.getItems().clear();
                    cb3.getItems().clear();
                    
                }
            } catch (Exception ex) {
            }
        }
        }
    }
    
    void custComplaintSetValue() {

        compempId.setCellValueFactory(new PropertyValueFactory<ComplaintCust, Integer>("compId"));

        prodId.setCellValueFactory(new PropertyValueFactory<ComplaintCust, Integer>("prodId"));

        custName.setCellValueFactory(new PropertyValueFactory<ComplaintCust, String>("customerNm"));

        comempDesc.setCellValueFactory(new PropertyValueFactory<ComplaintCust, String>("desc"));

        comdate.setCellValueFactory(new PropertyValueFactory<ComplaintCust, Date>("dt"));

        try {
            con = DriverManager.getConnection(urld, usrnm, passwd);
            String query = "Select * from customercomplaint";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            ComplaintCust comp2;

            ObservableList<ComplaintCust> obscom2 = FXCollections.observableArrayList();

            while (rs.next()) {
                comp2 = new ComplaintCust();
                comp2.setCompId(rs.getInt(1));
                comp2.setProdId(rs.getInt(2));
                comp2.setCustomerNm(rs.getString(3));
                comp2.setDt(rs.getDate(4));
                comp2.setDesc(rs.getString(5));

                obscom2.add(comp2);

            }
            mytable2.setItems(obscom2);
        } catch (SQLException ex) {
            Logger.getLogger(ManageEmPController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    int idValue;
    
    void getEmployee() {
        
//create table schedule (emp_id number references Employees(emp_no),dt Date,start_time varchar2(20),end_time varchar2(20),Primary Key (emp_id,dt));
        employeeId.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("empno"));
            
        employeeName.setCellValueFactory(new PropertyValueFactory<Employee,String>("empName"));
            
        employeebtn.setCellValueFactory(new PropertyValueFactory<Employee,Button>("btn"));
          
        Date datelbl=new Date();
        
        Calendar cal = Calendar.getInstance();
        
        lbldate.setText(datelbl.toString());
        
        int weekNo = cal.get(Calendar.WEEK_OF_YEAR);//getting the current week number

        String query = "select * from employees";

        try {
            Date dt = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy"); 

            String valDate = sdf.format(dt).toString();//changing the format of the date
            
            lblweek.setText("Date:"+weekNo);

            con = DriverManager.getConnection(urld, usrnm, passwd);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            ObservableList<Employee> empObs=FXCollections.observableArrayList();
            
            while (rs.next()) {             //getting each and every employee record from the employee table and futher checking their schedule
                int tempEmpId = rs.getInt("emp_no");

                String query2 = "select * from schedule where emp_id=" + tempEmpId;

                Statement stmt2 = con.createStatement();
                
                ResultSet rs2 = stmt2.executeQuery(query2);
                
                int minDays=7;//total days
                
                while (rs2.next()) {                                     //checking whether the current employee has been scheduled for all the days or not
                    
                    Date d = rs2.getDate("dt");                          //if the employee is scheduled for all the days in the week his name will not visible in the 
                                                                         //tableview mytableemp
                    cal.setTime(d);
                    
                    int storedWeekNo=cal.get(Calendar.WEEK_OF_YEAR);    //getting the data from the table schedule and comparing the date that we recieved 
                   
                   
                    if(storedWeekNo==weekNo)                            //from the current record with the current week record.
                    {
                         if(minDays>cal.get(Calendar.DAY_OF_WEEK))           //for getting the smallest day of the week for which schedule is set
                        {
                            minDays=cal.get(Calendar.DAY_OF_WEEK);         //getting min day value
                        }
                        counterDateEmp++;                      //increasing the counter as we find records with the empid in the table for the current week
                    }
                    
                }
                
                Employee emp;
                int diff=7-minDays;                             //counting the days left after the min day value,ie if the least day stored is tuesday
                                                                  //then the remaining days should be 7-3=4              
                if(counterDateEmp<6 && counterDateEmp<=diff)                //if the counter is less than 6 that means schedule for some days for this empid is not set
                {                                                           //and if the counteremp is greater than diff, that means from this day the
                                                                            //current date the employee schedule has been set for all the days.
                    buttonSchedule[empCounter]=new Button("Set Schedule");                              //setting a button in listview
                    
                    buttonSchedule[empCounter].setOnAction(this::handleButtonAction);                  //setting a handling button for every event
                    
                    emp=new Employee(rs.getInt("emp_no"),(rs.getString("fname")+" "+rs.getString("lname")),buttonSchedule[empCounter]);
                    empObs.add(emp);
                    
                    empCounter++;//for kepping track of the buttons being built dynamically in the tableview
                    
                }
                counterDateEmp=0;
            }
            myTableEmp.setItems(empObs);//setting the observable list
        } catch (SQLException ex) {
            Logger.getLogger(ManageEmPController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cb1.setPromptText("Choose a Day");

    }
    
    @FXML
    private void insertDays() throws SQLException                            //inserting week days into combobox for which the schedule has not been set
    {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

        String valDate = sdf.format(dt).toString();
            
        int weekNo = cal.get(Calendar.WEEK_OF_YEAR);
            
        con = DriverManager.getConnection(urld, usrnm, passwd);
            
        String query2 = "select * from schedule where emp_id=" +empScheduleId;

        Statement stmt2 = con.createStatement();
            
        ResultSet rs2 = stmt2.executeQuery(query2);

        Date datelbl=new Date();
            
        int []daysCheckingArr=new int[8];
            
        for (int i = 0; i < 8; i++) {
            daysCheckingArr[i]=0;
        }
                
            while (rs2.next()) {
                Date d = rs2.getDate("dt");
                cal.setTime(d);
                
                daysCheckingArr[0]=1;
                int storedWeekNo=cal.get(Calendar.WEEK_OF_YEAR);             //extracting weekno from the date to check whether its the current week or not
                if(storedWeekNo==weekNo)//if theere is already a record in the database for any date of this week we dont want to insert schedule for that date
                    {
                        int x=cal.get(Calendar.DAY_OF_WEEK);
                        daysCheckingArr[x]=1;//setting status as 1
                    }                                
                }    
            
            cal.setTime(dt);
                
                int todayDay=cal.get(Calendar.DAY_OF_WEEK);
                
                
                for (int i = todayDay; i < 8; i++) {
                    if(daysCheckingArr[i]==0)                                            //checking status to insert days in cpombobox
                    {
                        cb1.getItems().add(days[i]);
                    }
                }
                
                 cb2.getItems().add("AM");
                 cb2.getItems().add("PM");
                 
                 cb3.getItems().add("AM");
                 cb3.getItems().add("PM");
                 
    }
    
    private void handleButtonAction(ActionEvent event) 
    {
        txt1start.setText("");
        txt2end.setText("");
        cb1.getItems().clear();                    
        cb2.getItems().clear();
        cb3.getItems().clear();                    
        btnScheduleInsert.setDisable(false);
        for (int i = 0; i < 100; i++) {
            if(event.getSource()==buttonSchedule[i])
            {
                empScheduleId= Integer.parseInt(myTableEmp.getColumns().get(0).getCellObservableValue(i).getValue().toString());
                try {
                    insertDays();
                    
                    return;
                } catch (SQLException ex) {
                    Logger.getLogger(ManageEmPController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empComplaintSetValue();

        custComplaintSetValue();
        
        getEmployee();
        
        btnScheduleInsert.setDisable(true);

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
