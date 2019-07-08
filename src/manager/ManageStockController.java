package manager;

import backend.ManageStock;
import backend.ManageStockDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManageStockController implements Initializable {

    //---------------------------Controller for ManageStock Page(variables + onAction methods)--------------------------
    @FXML
    private TextField a_idField, d_idField, f_idField, u_idField;
    @FXML
    private TextField a_nameField, u_nameField;
    @FXML
    private TextField a_priceField, u_priceField;
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
    private TableColumn<ManageStock, String> f_addedOn, v_addedOn;
    @FXML
    private TableColumn<ManageStock, String> f_expiryDate, v_expiryDate;
    @FXML
    private TableColumn<ManageStock, String> f_distributorColumn, v_distributorColumn;
    @FXML
    private DatePicker a_addedOn, a_expiryDate, u_addedOn, u_expiryDate;
    @FXML
    private Tab viewTab;
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
//        }else if(a_addedOn.getValue() == null){
//            alert2.setContentText("Please provide item total volume");
//            alert2.showAndWait();
//        }else if(a_expiryDate.getValue() == null){
//            alert2.setContentText("Please provide item total volume");
//            alert2.showAndWait();
        }else if(a_distributor_idField.getText() == null || a_distributor_idField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide distributor name for item");
            alert2.showAndWait();
        }else{
            s.setProduct_id(Integer.parseInt(a_idField.getText()));
            s.setName(a_nameField.getText());
            s.setPrice(Double.parseDouble(a_priceField.getText()));
            s.setVolume(Integer.parseInt(a_volumeField.getText()));
            s.setDistributor_id(a_distributor_idField.getText());
            s.setAdded_on(a_addedOn.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));
            s.setExpiry_date(a_expiryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));

            result = sdb.addItem(s);
            if(result != 0){
                alert1.setContentText("Details for item " + a_nameField.getText() + " added successfully");
                reset(actionEvent);
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
        if(d_idField.getText() == null || d_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else{
            s.setProduct_id(Integer.parseInt(d_idField.getText()));
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
        if(f_idField.getText() == null || f_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else{
            s.setProduct_id(Integer.parseInt(f_idField.getText()));
            s = sdb.fDelete(s);
            if(s != null){
                ObservableList<ManageStock> obj = sdb.find(s);
                if(obj != null){
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
        if(u_idField.getText() == null || u_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else if(u_nameField.getText() == null || u_nameField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item Name");
            alert2.showAndWait();
        }else if(u_priceField.getText() == null || u_priceField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item price");
            alert2.showAndWait();
        }else if(u_volumeField.getText() == null || u_volumeField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide item total volume");
            alert2.showAndWait();
        }else if(u_distributor_idField.getText() == null || u_distributor_idField.getText().trim().isEmpty()){
            alert2.setContentText("Please provide distributor name for item");
            alert2.showAndWait();
        } else{
            s.setProduct_id(Integer.parseInt(u_idField.getText()));
            s = sdb.fDelete(s);
            if(s != null){
                s.setProduct_id(Integer.parseInt(u_idField.getText()));
                s.setName(u_nameField.getText());
                s.setPrice(Double.parseDouble(u_priceField.getText()));
                s.setVolume(Integer.parseInt(u_volumeField.getText()));
                s.setDistributor_id(u_distributor_idField.getText());
                s.setAdded_on(u_addedOn.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                s.setExpiry_date(u_expiryDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                result = sdb.update(s);
                if(result != 0){
                    alert1.setContentText("Record for Item with Item Id: " + u_idField.getText() + " updated successfully");
                    alert1.showAndWait();
                }
                else{
                    alert2.setContentText("Something unexpected error occurred");
                    alert2.showAndWait();
                }
            }
            else{
                alert2.setContentText("There is no Item with Item Id: " + u_idField.getText());
                alert2.showAndWait();
            }
        }
    }


    @FXML
    public void list(Event ev) {
        setAlert();
        if (viewTab.isSelected()) {
            System.out.println("Tab is Selected");
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populate(v_idColumn, v_nameColumn, v_priceColumn, v_volumeColumn, v_addedOn, v_expiryDate, v_distributorColumn);

        populate(f_idColumn, f_nameColumn, f_priceColumn, f_volumeColumn, f_addedOn, f_expiryDate, f_distributorColumn);


    }

    private void populate(TableColumn<ManageStock, Integer> idColumn
            , TableColumn<ManageStock, String> nameColumn
            , TableColumn<ManageStock, Double> priceColumn
            , TableColumn<ManageStock, Integer> volumeColumn
            , TableColumn<ManageStock, String> addedOn
            , TableColumn<ManageStock, String> expiryDate
            , TableColumn<ManageStock, String> distributorColumn) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
        addedOn.setCellValueFactory(new PropertyValueFactory<>("added_on"));
        expiryDate.setCellValueFactory(new PropertyValueFactory<>("expiry_date"));
        distributorColumn.setCellValueFactory(new PropertyValueFactory<>("distributor_id"));
    }


}
