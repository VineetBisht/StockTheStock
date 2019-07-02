package employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RefundController {
    String username = "n01324490";
    String password = "oracle";
    String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    private Connection con;

    public RefundController() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Label err_1;

    @FXML
    private Label err_2;

    @FXML
    private Label err_3;

    @FXML
    private Label name;

    @FXML
    private Label quantity;

    @FXML
    private TextArea issue;

    @FXML
    void refund(ActionEvent event) {
        if(name.getText().isEmpty() | quantity.getText().isEmpty() | issue.getText().isEmpty()){
        showErrors();
        return;
        }else{
            hideErrors();
        }

        int rows = 0;
        String updateQuerry = "UPDATE stock SET volume = ?"
                + "WHERE name = ?";
            try {
                PreparedStatement pst = con.prepareStatement(updateQuerry);
                pst.setInt(1, Integer.parseInt(quantity.getText()));
                pst.setString(2, name.getText());
                rows = pst.executeUpdate();
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

}
