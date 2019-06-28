package employee;

import backend.ControlledScreen;
import backend.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class EmployeeMainController implements ControlledScreen {
    ScreensController myController;

    @FXML
    private Label welcom_name;

    @FXML
    private Label shift;

    @FXML
    private TextField txt_product;

    @FXML
    private TextField txt_qty;

    @FXML
    private TextField txt_price;

    @FXML
    private Label product_err;

    @FXML
    private Label price_err;

    @FXML
    private Label qty_err;

    @FXML
    private ListView<?> cart_display;

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void checkout(ActionEvent event) {

    }

    @FXML
    void complaint(ActionEvent event) {

    }

    @FXML
    void refund(ActionEvent event) {

    }

    @FXML
    void showTables(ActionEvent event) {

    }

    @FXML
    void soldOut(ActionEvent event) {

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
