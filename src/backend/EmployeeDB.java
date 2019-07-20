package backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;


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


    public ObservableList<Complaint> list() {
        String listQuerry = "SELECT * FROM customercomplaint";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(listQuerry);
            ResultSet rs = preparedStatement.executeQuery();
            ObservableList<Complaint> obList = FXCollections.observableArrayList();

            while(rs.next()){
                obList.add(new Complaint(rs.getInt("complaintid"), rs.getString("prodid"),
                        rs.getString("customername"), new Date(rs.getDate("complaintdate").getTime()),
                        rs.getString("description"), rs.getDouble("phone")));
            return obList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int update(Object list[]) {
        int rows = 0;
        String updateQuerry = "UPDATE stock SET volume = (SELECT volume FROM stock WHERE product_id=?)-? where product_id=?";
        String counter = "UPDATE product_counter SET counter = (SELECT counter FROM product_counter WHERE product_id=?)+? where product_id=?";
        for (int i = 0; i < list.length; i++) {
            Cart item=(Cart)list[i];
            System.out.println(1);
            try {
                PreparedStatement pst = con.prepareStatement(updateQuerry);
                pst.setString(1, item.getProduct_id());
                pst.setInt(2, item.getVolume());
                pst.setString(3, item.getProduct_id());
                pst.executeUpdate();

                PreparedStatement count = con.prepareStatement(counter);
                count.setString(1, item.getProduct_id());
                count.setInt(2, item.getVolume());
                count.setString(3, item.getProduct_id());
                count.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(4);
            }
        }
        return rows;
    }

    public Cart findObject(String id,int qty) {
        String findQuerry = "SELECT * FROM stock WHERE product_id = ?";
        try {
            PreparedStatement pST = con.prepareStatement(findQuerry);
            pST.setString(1, id);

            ResultSet rs = pST.executeQuery();
            if (rs.next()) {
                return new Cart(rs.getString("product_id"), rs.getString("name"),
                        rs.getInt("price"), qty);
            } else {
                return null;
            }

        } catch (SQLException era) {
            System.err.println(era);
            return null;
        }
    }

    public int findPrice(String id) {
        String findQuerry = "SELECT PRICE FROM stock WHERE product_id = ?";
        try {
            PreparedStatement pST = con.prepareStatement(findQuerry);
            pST.setString(1, id);

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

    public void deleteCompl(Complaint selectedItem) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM customercomplaint WHERE complaintid=?");
            ps.setInt(1, selectedItem.getComplaintID());
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


