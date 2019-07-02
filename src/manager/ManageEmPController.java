/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.ControlledScreen;
import backend.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Shivam
 */
public class ManageEmPController implements Initializable, ControlledScreen {
    ScreensController myController;
   
    @FXML private TableView<ComplaintEmp> mytable;
    @FXML private TableView<ComplaintCust> mytable2;
    @FXML private TabPane tp;
    @FXML private Tab tab1;
    @FXML private Tab tab5;
    @FXML private Tab tab2;
    @FXML private Tab tab3;
    @FXML private Tab tab4;
       @FXML private TableColumn<ComplaintEmp,Integer> compId;
        
       @FXML private TableColumn<ComplaintEmp,Integer> empId;
        
       @FXML private TableColumn<ComplaintEmp,String> comSubject;
        
       @FXML private TableColumn<ComplaintEmp,String> comDesc;
        
       @FXML private TableColumn<ComplaintEmp,String> status;
        
       @FXML private TableColumn<ComplaintEmp,Date> date;
       
       @FXML TableColumn<ComplaintCust,Integer> compempId;
        
       @FXML TableColumn<ComplaintCust,Integer> prodId;
        
       @FXML TableColumn<ComplaintCust,String> custName;
        
       @FXML TableColumn<ComplaintCust,String> comempDesc;
        
       @FXML TableColumn<ComplaintCust,Date> comdate;
       
    @FXML
    private void handleButtonAction(ActionEvent event) {
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            compId.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,Integer>("compId"));
            
            empId.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,Integer>("empid"));
            
            comSubject.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("subject"));
            
            comDesc.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("desc"));
            
            status.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("status"));
            
            date.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,Date>("dt"));
            
            compempId.setCellValueFactory(new PropertyValueFactory<ComplaintCust,Integer>("compId"));
            
            prodId.setCellValueFactory(new PropertyValueFactory<ComplaintCust,Integer>("prodId"));
            
            custName.setCellValueFactory(new PropertyValueFactory<ComplaintCust,String>("customerNm"));
            
            comempDesc.setCellValueFactory(new PropertyValueFactory<ComplaintCust,String>("desc"));
            
            comdate.setCellValueFactory(new PropertyValueFactory<ComplaintCust,Date>("dt"));
            
        String usrnm="N01324490";
//        String usrnm="N01328150";
    String passwd="oracle";
    String urld="jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    
        
        
        ObservableList<ComplaintEmp> obscom=FXCollections.observableArrayList();;
            try {
                 Connection con=DriverManager.getConnection(urld, usrnm,passwd);
            String query="Select * from employeecomplaint";
       
            
            ResultSet rs=con.createStatement().executeQuery(query);
            
                ComplaintEmp comp=null;
                
            while(rs.next())
            {
                comp=new ComplaintEmp();
                comp.setCompId(rs.getInt("complaintid"));
                comp.setEmpid(rs.getInt("empid"));
                comp.setDt(rs.getDate("complaintdate"));
                comp.setSubject(rs.getString("subject"));
                comp.setDesc(rs.getString("description"));
                comp.setStatus(rs.getString("status"));
                System.out.println(""+comp.getCompId()+comp.getEmpid()+comp.getDesc()+comp.getStatus());
                obscom.add(comp);
            }
    }
            catch(Exception ex)
            {
            }
            
            mytable.setItems(obscom);
    
             try {
                 Connection con=DriverManager.getConnection(urld, usrnm,passwd);
            String query="Select * from customercomplaint";
       
            PreparedStatement ps=con.prepareStatement(query);
            
            ResultSet rs=ps.executeQuery();
            
            ComplaintCust comp2;
                
            ObservableList<ComplaintCust> obscom2=FXCollections.observableArrayList();
                
            while(rs.next())
            {
                comp2=new ComplaintCust();
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

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
