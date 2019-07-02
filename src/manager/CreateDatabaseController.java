package manager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author Shivam
 */
public class CreateDatabaseController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
    }
    
    String usrnm="N01324490";
        //String usrnm="N01328150";
    String passwd="oracle";
    String urld="jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    
    @FXML
        TableView tblview;
    
    @FXML
    TableColumn col1;
    
    @FXML
    TableColumn col2;
    
    @FXML
    private void handleBtnmAction(ActionEvent event) {
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn comId=new TableColumn("Complaint Id");
        
        TableColumn empId=new TableColumn("Employee Id");
        
        TableColumn comSubject=new TableColumn("Complaint Subject");
        
        TableColumn comDesc=new TableColumn("Complaint Description");
        
        TableColumn status=new TableColumn("Status");
        
        TableColumn date=new TableColumn("Date");
        
        tblview.getColumns().addAll(comId,empId,date,comSubject,comDesc,status);
        
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
        }
    
    
    }
    
}
