package employee;

import backend.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TablesController implements ControlledScreen, Initializable {
    ScreensController myController;

    @FXML
    private Label welcom_name;

    @FXML
    private Label shift;

    @FXML
    private ComboBox<String> tables_combo;

    @FXML
    private TableView<ManageStock> viewTable;
    @FXML
    private TableColumn<ManageStock, Integer> v_idColumn;
    @FXML
    private TableColumn<ManageStock, String> v_nameColumn;
    @FXML
    private TableColumn<ManageStock, Integer> v_volumeColumn;
    @FXML
    private TableColumn<ManageStock, Double> v_priceColumn;
    @FXML
    private TableColumn<ManageStock, String> v_addedOn;
    @FXML
    private TableColumn<ManageStock, String> v_expiryDate;
    @FXML
    private TableColumn<ManageStock, String> v_distributorColumn;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    void about(ActionEvent event) {
    }

    @FXML
    void close(ActionEvent event) {
    myController.getStage().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate(v_idColumn, v_nameColumn, v_priceColumn, v_volumeColumn, v_addedOn, v_expiryDate, v_distributorColumn);
        ManageStockDB sdb = new ManageStockDB();
        ObservableList<ManageStock> obj = sdb.list();
        if(obj != null){
            viewTable.setItems(obj);
        }
        else{
            Alert alert2=new Alert(Alert.AlertType.INFORMATION,"Stocks Empty!");
            alert2.showAndWait();
        }
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
