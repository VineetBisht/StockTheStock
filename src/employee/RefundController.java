package employee;

import backend.ControlledScreen;
import backend.EmployeeDB;
import backend.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RefundController implements Initializable, ControlledScreen {
    String username = "n01324490";
    String password = "oracle";
    String ur = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    EmployeeDB edb;
    ScreensController myController;

    private Connection con;

    @FXML
    private TextField name;

    @FXML
    private TextField quantity;

    @FXML
    private Label err_1;

    @FXML
    private Label err_2;

    @FXML
    private Label err_3;

    @FXML
    private TextArea issue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection( ur, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        edb=new EmployeeDB();
     }

    @FXML
    void about(ActionEvent event) {
    }

    @FXML
    void close(ActionEvent event) {
        myController.getStage().hide();
    }

        @FXML
    void refund(ActionEvent event) {
        if(name.getText().isEmpty() || quantity.getText().isEmpty() || issue.getText().isEmpty()){
        showErrors();
        return;
        }else{
            hideErrors();
        }

        int rows = 0;
        String updateQuerry = "UPDATE stock SET volume = (SELECT volume FROM stock WHERE product_id=?)+?  where product_id=?";
            try {
                PreparedStatement pst = con.prepareStatement(updateQuerry);
                pst.setInt(1, Integer.parseInt(name.getText()));
                pst.setInt(2, Integer.parseInt(quantity.getText()));
                pst.setInt(3, Integer.parseInt(name.getText()));
                rows = pst.executeUpdate();

               if(rows==0) {
                   new Alert(Alert.AlertType.WARNING,"Stock ID not found").showAndWait();
               }else{
                   name.setText("");
                   quantity.setText("");
                   issue.setText("");
                   new Alert(Alert.AlertType.INFORMATION,"Stock Re-Added. Return Price:$" +(edb.findPrice(name.getText())*Integer.parseInt(quantity.getText()))).showAndWait();
               }
            } catch (SQLException e) {
                System.err.println(e);
        }
    }

    void showErrors(){
        err_1.setVisible(true);
        err_2.setVisible(true);
        err_3.setVisible(true);
    }

    void hideErrors(){
        err_1.setVisible(false);
        err_2.setVisible(false);
        err_3.setVisible(false);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
