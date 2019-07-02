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
    private ObservableList<String> list;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
    }
    
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
        


col2.setCellValueFactory(new PropertyValueFactory<>("surname"));

        // TODO
       
    }    
    
}
