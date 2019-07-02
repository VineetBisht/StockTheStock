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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ManageEmPController implements Initializable {
    String usrnm="N01324490";
        //String usrnm="N01328150";
    String passwd="oracle";
    String urld="jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    @FXML private TableView mytableview1;
    
    @FXML private Label label;
    
    @FXML
    private Tab empcumtab;
    
    @FXML
    private TabPane mytabpane;
    @FXML
    private TableView mytableview2;
   
    @FXML
    private void databaseName(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn comId=new TableColumn("Complaint Id");
        
        TableColumn empId=new TableColumn("Employee Id");
        
        TableColumn comSubject=new TableColumn("Complaint Subject");
        
        TableColumn comDesc=new TableColumn("Complaint Description");
        
        TableColumn status=new TableColumn("Status");
        
        TableColumn date=new TableColumn("Date");
        
        
        TableColumn compempId=new TableColumn("Complaint Id");
        
        TableColumn prodId=new TableColumn("Product Id");
        
        TableColumn custName=new TableColumn("Customer Name");
        
        TableColumn comempDesc=new TableColumn("Complaint Description");
        
        TableColumn comdate=new TableColumn("Date");
        
        mytableview2.getColumns().addAll(comId,empId,date,comSubject,comDesc,status);
        
         mytableview1.getColumns().addAll(compempId,prodId,custName,comdate,comempDesc);
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageEmPController.class.getName()).log(Level.SEVERE, null, ex);
        }
            try {
                 Connection con=DriverManager.getConnection(urld, usrnm,passwd);
            String query="Select * from employeecomplaint";
       
            PreparedStatement ps=con.prepareStatement(query);
            
            ResultSet rs=ps.executeQuery();
            
                ComplaintEmp comp=new ComplaintEmp();
                
                ObservableList<ComplaintEmp> obscom=FXCollections.observableArrayList();
            while(rs.next())
            {
                comp.setCompId(rs.getInt(1));
                comp.setEmpid(rs.getInt(2));
                comp.setDt(rs.getDate(3));
                comp.setSubject(rs.getString(4));
                comp.setDesc(rs.getString(5));
                comp.setStatus(rs.getString(6));
                
                obscom.add(comp);
                
            }
            
            comId.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("Complaint Id"));
            
            empId.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("Employee Id"));
            
            comSubject.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("Complaint Subject"));
            
            comDesc.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("Complaint Description"));
            
            status.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("Complaint Status"));
            
            date.setCellValueFactory(new PropertyValueFactory<ComplaintEmp,String>("Date"));
            
            mytableview2.setItems(obscom);
        } catch (SQLException ex) {
            Logger.getLogger(ManageEmPController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
             try {
                 Connection con=DriverManager.getConnection(urld, usrnm,passwd);
            String query="Select * from customercomplaint";
       
            PreparedStatement ps=con.prepareStatement(query);
            
            ResultSet rs=ps.executeQuery();
            
            ComplaintCust comp=new ComplaintCust();
                
            ObservableList<ComplaintCust> obscom=FXCollections.observableArrayList();
                
            while(rs.next())
            {
                comp.setCompId(rs.getInt(1));
                comp.setProdId(rs.getInt(2));
                comp.setCustomerNm(rs.getString(3));
                comp.setDt(rs.getDate(4));
                comp.setDesc(rs.getString(5));
                
                obscom.add(comp);
                
            }
            
            compempId.setCellValueFactory(new PropertyValueFactory<ComplaintCust,String>("Complaint Id"));
            
            
            prodId.setCellValueFactory(new PropertyValueFactory<ComplaintCust,String>("Product Id"));
            
            custName.setCellValueFactory(new PropertyValueFactory<ComplaintCust,String>("Customer Name"));
            
            comDesc.setCellValueFactory(new PropertyValueFactory<ComplaintCust,String>("Complaint Description"));
            
            comdate.setCellValueFactory(new PropertyValueFactory<ComplaintCust,String>("Date"));
            
            mytableview1.setItems(obscom);
        } catch (SQLException ex) {
            Logger.getLogger(ManageEmPController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }    
    
}
