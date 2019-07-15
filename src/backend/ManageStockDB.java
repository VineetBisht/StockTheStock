import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ManageStockDB {
    private Connection con;
    int rows;
    int count =0;


    public ManageStockDB() {
        String username = "n01305083";
        String password = "oracle";
        String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(url, username, password);
            // check if "employee" table is there
//            DatabaseMetaData dbm = con.getMetaData();
//            ResultSet tables = dbm.getTables(null, null, "stock", null);
//            if (!tables.next()) {
//                PreparedStatement pStmt;
//                String create = "CREATE TABLE stock(product_id NUMBER GENERATED BY DEFAULT AS IDENTITY START WITH 320 PRIMARY KEY," +
//                        "name VARCHAR2( 255 ) NOT NULL," +
//                        "price NUMBER( 8, 2 )," +
//                        "volume INTEGER, added_on DATE, expiry_date DATE" +
//                        "distributor_id VARCHAR2( 255 ), image BLOB)";
//                int x=con.createStatement().executeUpdate(create);
//                System.out.println("Created new 'Stock' table successfully ");
//            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int addItem(ManageStock i){
        String addQuerry = "INSERT INTO stock2 (product_id, name, price, volume, added_on, expiry_date, distributor_id, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String added, expiry;
        long long_added, long_expiry;
        java.util.Date util_added, util_expiry;
        java.sql.Date sql_added, sql_expiry;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        try{
            //step1
            PreparedStatement pst = con.prepareStatement(addQuerry);
            //step 2
            pst.setInt(1, i.getProduct_id());
            pst.setString(2, i.getName());
            pst.setDouble(3, i.getPrice());
            pst.setInt(4, i.getVolume());

//            added = i.getAdded_on();
//            util_added = sdf.parse(added);
//            long_added = util_added.getTime();
//            sql_added = new java.sql.Date(long_added);
//
//            expiry = i.getExpiry_date();
//            util_expiry = sdf.parse(expiry);
//            long_expiry = util_expiry.getTime();
//            sql_expiry = new java.sql.Date(long_expiry);

            pst.setString(5, i.getAdded_on());
            pst.setString(6, i.getExpiry_date());
            pst.setString(7, i.getDistributor_id());

            FileInputStream fis = new FileInputStream(i.getFile());

            pst.setBinaryStream(8, (InputStream)fis, (int)i.getFile().length()); //length of file 3rd argument


            //step 3
            rows = pst.executeUpdate();
            return rows;

        }catch(SQLException e){
            System.err.println(e);
            return 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
//        catch (ParseException e) {
//            e.printStackTrace();
//            return 0;
//        }

    }

    public int delete(ManageStock i){
        String deleteQuerry = "DELETE FROM stock WHERE product_id = ?";

        try{
            PreparedStatement pst = con.prepareStatement(deleteQuerry);
            pst.setInt(1, i.getProduct_id());
            rows = pst.executeUpdate();
            return  rows;
        }catch(SQLException e){
            System.err.println(e);
            return 0;
        }
    }

    public ManageStock fDelete(ManageStock i){
        String q = "SELECT * FROM stock2 WHERE product_id = ?";
        try{
            PreparedStatement pST = con.prepareStatement(q);
            pST.setInt(1, i.getProduct_id());

            ResultSet rs = pST.executeQuery();
            ManageStock stockItem = new ManageStock();
            if (rs.next()) {
                stockItem.setProduct_id(rs.getInt("product_id"));
                stockItem.setName(rs.getString("name"));
                stockItem.setPrice(rs.getDouble("price"));
                stockItem.setVolume(rs.getInt("volume"));
                stockItem.setAdded_on(rs.getString("added_on"));
                stockItem.setExpiry_date((rs.getString("expiry_date")));
                stockItem.setDistributor_id(rs.getString("distributor_id"));

                return stockItem;
            }
            else
                return null;
        }catch(SQLException era){
            System.err.println(era);
            return null;
        }
    }

    public int update(ManageStock i){
        String added, expiry;
        long long_added, long_expiry;
        java.util.Date util_added, util_expiry;
        java.sql.Date sql_added, sql_expiry;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String updateQuerry = "UPDATE stock SET name = ?, price = ?, volume = ?, added_on = ?, " +
                "expiry_date = ?, distributor_id = ?, image = ?"
                + "WHERE product_id = ?";
        try{
            PreparedStatement pst = con.prepareStatement(updateQuerry);
            pst.setString(1, i.getName());
            pst.setDouble(2, i.getPrice());
            pst.setInt(3, i.getVolume());


            pst.setString(4, i.getAdded_on());
            pst.setString(5, i.getExpiry_date());
            pst.setString(6, i.getDistributor_id());

            FileInputStream fis = new FileInputStream(i.getFile());
            pst.setBinaryStream(7, (InputStream)fis, (int)i.getFile().length()); //length of file 3rd argument

            pst.setInt(8, i.getProduct_id());

            rows = pst.executeUpdate();
            return  rows;
        }catch(SQLException e){
            System.err.println(e);
            return 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ObservableList<ManageStock> list(){
        String listQuerry = "SELECT * FROM stock2 WHERE TO_DATE(expiry_date, 'DD-MON-YYYY') > current_date";
        ObservableList<ManageStock> obList = FXCollections.observableArrayList();
        String query2 = "WHERE TO_DATE(expiry_date, 'DD-MON-YYYY') - current_date <= 7 AND TO_DATE(expiry_date, 'DD-MON-YYYY') - current_date > 0";

        try{
            PreparedStatement pST = con.prepareStatement(listQuerry);
            PreparedStatement pST2 = con.prepareStatement(query2);
            ResultSet rs = pST.executeQuery();
            while(rs.next()){

                obList.add(new ManageStock(rs.getInt("product_id"), rs.getString("name"),
                        rs.getInt("price"), rs.getInt("volume"),
                        rs.getString("distributor_id"), rs.getString("added_on"), rs.getString("expiry_date")));

            }

            return obList;

        }catch(SQLException e){
            System.err.println(e);
            return null;
        }
    }

    public ObservableList<ManageStock> expiredList(){
        String listQuerry = "SELECT * FROM stock2 WHERE TO_DATE(expiry_date, 'DD-MON-YYYY') < current_date";
        ObservableList<ManageStock> obList = FXCollections.observableArrayList();

        try{
            PreparedStatement pST = con.prepareStatement(listQuerry);
            ResultSet rs = pST.executeQuery();
            while(rs.next()){

                obList.add(new ManageStock(rs.getInt("product_id"), rs.getString("name"),
                        rs.getInt("price"), rs.getInt("volume"),
                        rs.getString("distributor_id"), rs.getString("added_on"), rs.getString("expiry_date")));

            }

            return obList;

        }catch(SQLException e){
            System.err.println(e);
            return null;
        }
    }

    public ObservableList<ManageStock> find(ManageStock i) {
        String findQuerry = "SELECT * FROM stock2 WHERE product_id = ?";
        ObservableList<ManageStock> obList = FXCollections.observableArrayList();
        try {
            PreparedStatement pST = con.prepareStatement(findQuerry);
            pST.setInt(1, i.getProduct_id());

            ResultSet rs = pST.executeQuery();
            if (rs.next()) {

                obList.add(new ManageStock(rs.getInt("product_id"), rs.getString("name"),
                        rs.getInt("price"), rs.getInt("volume"),
                        rs.getString("distributor_id"), rs.getString("added_on"), rs.getString("expiry_date")));
                return obList;
            } else {
                return null;
            }

        } catch (SQLException era) {
            System.err.println(era);
            return null;
        }
    }


    public InputStream findImage(ManageStock i ){
        String findQuerry = "SELECT * FROM stock2 WHERE product_id = ?";
        InputStream is = null;
        try {
            PreparedStatement pST = con.prepareStatement(findQuerry);
            pST.setInt(1, i.getProduct_id());

            ResultSet rs = pST.executeQuery();
            if (rs.next()) {
                is = rs.getBinaryStream("image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return is;

    }
}

