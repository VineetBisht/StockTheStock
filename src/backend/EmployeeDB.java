package backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;

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

    public int update(Object list[]) {
        int rows = 0;
        String updateQuerry = "UPDATE stock SET volume = (SELECT volume FROM stock WHERE product_id=?)-? where product_id=?";
        for (int i = 0; i < list.length; i++) {
            Cart item=(Cart)list[i];
            System.out.println(1);
            try {
                PreparedStatement pst = con.prepareStatement(updateQuerry);
                pst.setInt(1, item.getProduct_id());
                pst.setInt(2, item.getVolume());
                pst.setInt(3, item.getProduct_id());
                System.out.println(2);
                rows = pst.executeUpdate();
                System.out.println(3);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(4);
            }
        }
        return rows;
    }

    public Cart findObject(int id,int qty) {
        String findQuerry = "SELECT * FROM stock WHERE product_id = ?";
        try {
            PreparedStatement pST = con.prepareStatement(findQuerry);
            pST.setInt(1, id);

            ResultSet rs = pST.executeQuery();
            if (rs.next()) {
                return new Cart(rs.getInt("product_id"), rs.getString("name"),
                        rs.getInt("price"), qty);
            } else {
                return null;
            }

        } catch (SQLException era) {
            System.err.println(era);
            return null;
        }
    }

    public int findPrice(int id) {
        String findQuerry = "SELECT PRICE FROM stock WHERE product_id = ?";
        try {
            PreparedStatement pST = con.prepareStatement(findQuerry);
            pST.setInt(1, id);

            ResultSet rs = pST.executeQuery();
            if (rs.next()) {
                return rs.getInt("price");
            } else {
                return 0;
            }
        } catch (SQLException era) {
            era.printStackTrace();
        }
        return 0;
    }
}

