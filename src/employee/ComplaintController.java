package employee;

import backend.ControlledScreen;
import backend.Files;
import backend.ScreensController;
import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComplaintController implements ControlledScreen {

    ScreensController myController;
    @FXML
    private Label welcom_name;

    @FXML
    private Label shift;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_mob;

    @FXML
    private TextField txt_prod;

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
        if (txt_name.getText().isEmpty() || txt_prod.getText().isEmpty() || txt_mob.getText().isEmpty() || issuebox.getText().isEmpty()) {
            showErrors();
            return;
        } else {
            hideErrors();
        }
        String username = "n01324490";
        String password = "oracle";
        String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
        Connection con = null;
        int rows = 0;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String addQuerry = "INSERT INTO CustomerComplaint (PRODID, CUSTOMERNAME, DESCRIPTION, PHONE) VALUES (?, ?, ?, ?)";

        try {
            //step1
            PreparedStatement pst = con.prepareStatement(addQuerry);
            //step 2
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_prod.getText());
            pst.setString(3, issuebox.getText());
            pst.setDouble(4, Double.parseDouble(txt_mob.getText()));

            //step 3
            rows = pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully filed.");
            alert.showAndWait();
            txt_mob.setText("");
            txt_prod.setText("");
            txt_name.setText("");
            issuebox.setText("");

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

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

    void showErrors() {
        qty_err.setVisible(true);
        price_err.setVisible(true);
        product_err.setVisible(true);
    }

    void hideErrors() {
        qty_err.setVisible(false);
        price_err.setVisible(false);
        product_err.setVisible(false);
    }


}

