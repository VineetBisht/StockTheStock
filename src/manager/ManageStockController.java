package manager;

import backend.ControlledScreen;
import backend.ScreensController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageStockController implements ControlledScreen, Initializable {

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }


    //---------------------------Controller for ManageStock Page(variables + onAction methods)--------------------------
    @FXML
    private TextField a_idField, d_idField, f_idField, u_idField;
    @FXML
    private TextField a_nameField, u_nameField;
    @FXML
    private TextField a_priceField, u_pricefield;
    @FXML
    private TextField a_volumeField, u_volumeField;
    @FXML
    private TextField a_distributor_idField, u_distributor_idField;
    @FXML
    private TableView<ManageStock> viewTable, findTable;
    @FXML
    private TableColumn<ManageStock, Integer> f_idColumn, v_idColumn;
    @FXML
    private TableColumn<ManageStock, String> f_nameColumn, v_nameColumn;
    @FXML
    private TableColumn<ManageStock, Integer> f_volumeColumn, v_volumeColumn;
    @FXML
    private TableColumn<ManageStock, Double> f_priceColumn, v_priceColumn;
    @FXML
    private TableColumn<ManageStock, Integer> f_distributorColumn, v_distributorColumn;
    int result = 0;
    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
    Alert alert2 = new Alert(Alert.AlertType.ERROR);

    public void setAlert(){
        alert1.setTitle("Stock Records");
        alert1.setHeaderText(null);
        alert2.setTitle("Stock Records");
        alert2.setHeaderText(null);
    }

    public void add(ActionEvent actionEvent) {
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;
        if(a_idField.getText() == null || a_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else if(a_nameField.getText() == null || a_nameField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item Name");
            alert2.showAndWait();
        }else if(a_priceField.getText() == null || a_priceField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item price");
            alert2.showAndWait();
        }else if(a_volumeField.getText() == null || a_volumeField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item total volume");
            alert2.showAndWait();
        }else if(a_distributor_idField.getText() == null || a_distributor_idField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide distributor name for item");
            alert2.showAndWait();
        }else{
            s.setProduct_id(Integer.parseInt(a_idField.getText()));
            s.setName(a_nameField.getText());
            s.setPrice(Double.parseDouble(a_priceField.getText()));
            s.setVolume(Integer.parseInt(a_volumeField.getText()));
            s.setDistributor_id(Integer.parseInt(a_distributor_idField.getText()));

            result = sdb.addItem(s);
            if(result != 0){
                alert1.setContentText("Details for item " + a_nameField.getText() + " added successfully");
                alert1.showAndWait();
            }
            else{
                alert2.setContentText("Something unexpected error occurred");
                alert2.showAndWait();
            }
        }
    }

    public void reset(ActionEvent actionEvent) {
        a_idField.setText("");
        a_nameField.setText("");
        a_priceField.setText("");
        a_volumeField.setText("");
        a_distributor_idField.setText("");
    }

    public void delete(ActionEvent actionEvent) {
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;
        if(a_idField.getText() == null || a_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else{
            s.setProduct_id(Integer.parseInt(a_idField.getText()));
            s = sdb.fDelete(s);
            if(s != null){
                String name = s.getName();
                result = sdb.delete(s);
                if(result != 0){
                    alert1.setContentText("Record for item " + name + " is deleted successfully");
                    alert1.showAndWait();
                }
                else{
                    alert2.setContentText("Something unexpected error occurred");
                    alert2.showAndWait();
                }
            }else{
                alert2.setContentText("There is no Item with Item Id: " + a_idField.getText());
                alert2.showAndWait();
            }
        }
    }

    public void find(ActionEvent actionEvent) {
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;
        if(a_idField.getText() == null || a_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else{
            s.setProduct_id(Integer.parseInt(a_idField.getText()));
            s = sdb.fDelete(s);
            if(s != null){
                ObservableList<ManageStock> obj = sdb.find(s);
                if(obj != null){
                    f_idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
                    f_nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    f_priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                    f_volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
                    f_distributorColumn.setCellValueFactory(new PropertyValueFactory<>("distributor_id"));
                    findTable.setItems(obj);
                }
                else{
                    alert2.setContentText("Something unexpected error occurred");
                    alert2.showAndWait();
                }
            }else{
                alert2.setContentText("There is no Item with Item Id: " + a_idField.getText());
                alert2.showAndWait();
            }
        }
    }

    public void update(ActionEvent actionEvent) {
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;
        if(a_idField.getText() == null || a_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else if(a_nameField.getText() == null || a_nameField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item Name");
            alert2.showAndWait();
        }else if(a_priceField.getText() == null || a_priceField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item price");
            alert2.showAndWait();
        }else if(a_volumeField.getText() == null || a_volumeField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item total volume");
            alert2.showAndWait();
        }else if(a_distributor_idField.getText() == null || a_distributor_idField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide distributor name for item");
            alert2.showAndWait();
        } else{
            s.setProduct_id(Integer.parseInt(a_idField.getText()));
            s = sdb.fDelete(s);
            if(s != null){
                s.setProduct_id(Integer.parseInt(a_idField.getText()));
                s.setName(a_nameField.getText());
                s.setPrice(Double.parseDouble(a_priceField.getText()));
                s.setVolume(Integer.parseInt(a_volumeField.getText()));
                s.setDistributor_id(Integer.parseInt(a_distributor_idField.getText()));
                result = sdb.update(s);
                if(result != 0){
                    alert1.setContentText("Record for Item with Item Id: " + a_idField.getText() + " updated successfully");
                    alert1.showAndWait();
                }
                else{
                    alert2.setContentText("Something unexpected error occurred");
                    alert2.showAndWait();
                }
            }
            else{
                alert2.setContentText("There is no Item with Item Id: " + a_idField.getText());
                alert2.showAndWait();
            }
        }
    }



    public void list(ActionEvent actionEvent) {
        setAlert();
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        ObservableList<ManageStock> obj = sdb.list();
        if(obj != null){
            viewTable.setItems(obj);
        }
        else{
            alert2.setContentText("There is no record in the database");
            alert2.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        v_idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        v_nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        v_priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        v_volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
        v_distributorColumn.setCellValueFactory(new PropertyValueFactory<>("distributor_id"));


    }






}
