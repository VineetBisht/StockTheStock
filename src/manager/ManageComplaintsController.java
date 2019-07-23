package manager;

import backend.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageComplaintsController implements ControlledScreen, Initializable {
    ScreensController myController;

    @FXML
    private Label welcom_name;

    @FXML
    private Label shift;

    @FXML
    private ComboBox<String> tables_combo;

    @FXML
    private TableView<Complaint> viewTable;
    @FXML
    private TableColumn<Complaint, Integer> v_ID;
    @FXML
    private TableColumn<Complaint, String> v_pid;
    @FXML
    private TableColumn<Complaint, Integer> v_cName;
    @FXML
    private TableColumn<Complaint, Date> v_cDate;
    @FXML
    private TableColumn<Complaint, String> v_des;
    @FXML
    private TableColumn<Complaint, Double> v_ph;

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
        populate();
        EmployeeDB sdb = new EmployeeDB();
        ObservableList<Complaint> obj = sdb.list();
        if(obj != null){
            viewTable.setItems(obj);
        }
        else{
            Alert alert2=new Alert(Alert.AlertType.INFORMATION,"No Complaints to show");
            alert2.showAndWait();
        }

        viewTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(viewTable.getSelectionModel().getSelectedItem() != null){
                    new Alert(Alert.AlertType.INFORMATION,"Product ID:"+ viewTable.getSelectionModel().getSelectedItem().getProdid() +
                            "\nDate: "+viewTable.getSelectionModel().getSelectedItem().getComplaintDate()+"\nDescription:" +
                            " "+viewTable.getSelectionModel().getSelectedItem().getDescription()).showAndWait();
                    sdb.deleteCompl(viewTable.getSelectionModel().getSelectedItem());
                    viewTable.getItems().remove(viewTable.getSelectionModel().getSelectedItem());
                }
            }
        });
    }


    private void populate() {
//        v_ID.setCellValueFactory(new PropertyValueFactory<>("complaintID"));
        v_pid.setCellValueFactory(new PropertyValueFactory<>("prodid"));
        v_cName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        v_cDate.setCellValueFactory(new PropertyValueFactory<>("complaintDate"));
        v_des.setCellValueFactory(new PropertyValueFactory<>("description"));
        v_ph.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
}
