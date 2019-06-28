package employee;

import backend.ControlledScreen;
import backend.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ComplaintController implements ControlledScreen {

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
    private Label product_err;

    @FXML
    private Label price_err;

    @FXML
    private Label qty_err;


    @FXML
    private TextArea issuebox;

    @FXML
    void complaint(ActionEvent event) {

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}

