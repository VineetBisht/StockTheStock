package employee;

import manager.ManageStock;

import java.sql.*;

public class EmployeeDB {
    String username = "n01324490";
    String password = "oracle";
    String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    private Connection con;

    public EmployeeDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void list() {
        String listQuerry = "SELECT * FROM stock";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(listQuerry);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int update(ManageStock i[], int quantity[]) {
        int rows = 0;
        String updateQuerry = "UPDATE stock SET volume = ?"
                + "WHERE name = ?";
        for (int j = 0; j < i.length; j++) {
            try {
                PreparedStatement pst = con.prepareStatement(updateQuerry);
                pst.setInt(1, quantity[j]);
                pst.setString(2, i[j].getName());
                rows = pst.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return rows;
    }

    public String find(ManageStock i) {
        String findQuerry = "SELECT * FROM stock WHERE product_id = ?";
        int flag = 0;
        try {
            PreparedStatement pst = con.prepareStatement(findQuerry);
            pst.setInt(1, i.getProduct_id());
            ResultSet rs2 = pst.executeQuery();
            if (rs2.next()) {
                return rs2.getString("volume");
            } else {
                System.out.println("\nThere is not such record with the provided Product ID");
            }
            return null;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

}


