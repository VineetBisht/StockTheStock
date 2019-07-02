package employee;


import javafx.scene.control.Alert;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Datatable {

    private Connection con;

    Datatable() {
        String username = "n01324490";
        String password = "oracle";
        String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(url, username, password);
            // check if "datatable" table is there
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "datatable", null);
            if (!tables.next()) {
                PreparedStatement pStmt = null;
                String create = "CREATE TABLE datatable(USERNAME VARCHAR2(30)," +
                        "FIRST_NAME VARCHAR2(30) NOT NULL," +
                        "LAST_NAME VARCHAR2(30) NOT NULL," +
                        "PASS VARCHAR2(30) NOT NULL," +
                        "ADDRESS VARCHAR2(250)," +
                        "PHONE_NUMBER DOUBLE NOT NULL," +
                        "DOB DATE," +
                        "DESIGNATION VARCHAR2(30) NOT NULL," +
                        "PRIMARY KEY (USERNAME))";
                pStmt = con.prepareStatement(create);
                pStmt.executeUpdate();
                System.out.println("Created new 'datatable' table successfully ");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public  boolean add(sample.Person person) {
        int rows =0;
        try{
         String generatedPassword=hash(person.getPassword());

        String addQueryPre = "INSERT INTO datatable (USERNAME, FIRST_NAME,LAST_NAME,PASSWORD, ADDRESS, PHONE_NUMBER,DOB, DESIGNATION) "

                + " VALUES (?,?, ?, ?, ?,?,?,?)";


            //step 1

            PreparedStatement pst = con.prepareStatement(addQueryPre);

            //step 2


            pst.setString(1, person.getUser_name());

            pst.setString(2, person.getFname());

            pst.setString(3, person.getLname());

            pst.setString(4, generatedPassword);

            pst.setString(5, person.getAddress());

            pst.setDouble(6, person.getPhn_no());

            pst.setDate(7, person.getBirth_date());

            pst.setString(8, person.getDesignation());

            //step 3

            rows = pst.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            Alert a=new Alert(Alert.AlertType.WARNING,"Username Already Exits");
            a.show();
            return false;
        }catch(Exception s){
            System.out.println(s);
            return false;
        }

        return true;
    }
    public  void forgot(sample.Person person) {
        int rows =0;
        try{
              String generatedPassword=hash(person.getPassword());

            String updateQueryPre = "UPDATE  datatable SET PASSWORD=?"

                    + " WHERE USERNAME=?";


            //step 1

            PreparedStatement pst = con.prepareStatement(updateQueryPre);

            //step 2


            pst.setString(1, generatedPassword);

            pst.setString(2, person.getUser_name());



            //step 3

            rows=pst.executeUpdate();
            if (rows==0 ){
                Alert a=new Alert(Alert.AlertType.WARNING,"Username not found");
                a.show();
                return ;
            }
            else
            {
                Alert a=new Alert(Alert.AlertType.INFORMATION, "Password Changed");
                a.show();
                return ;
            }


        }catch(Exception s){
            System.out.println(s);
            Alert a=new Alert(Alert.AlertType.INFORMATION, "Task Unsuccessful");
            a.show();
            return ;

        }


    }





    public  void match(sample.Person person, String field_password) {

        String database_password="";

        String updateQueryPre = "SELECT PASSWORD FROM datatable WHERE USERNAME=?";

        try {
            //step 1
            PreparedStatement pst = con.prepareStatement(updateQueryPre);

            //step 2

            pst.setString(1, person.getUser_name());

            //step 3

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                database_password=rs.getString("PASSWORD");

            }
            else {

            Alert a=new Alert(Alert.AlertType.INFORMATION,"Username not found");
            a.show();
                return ;}


        }catch(Exception s){
            System.out.println(s);
        }

        if(database_password.equals(hash(field_password))){
            Alert a=new Alert(Alert.AlertType.INFORMATION,"Login Successful");
            a.show();
            return;}
        else{
            Alert a=new Alert(Alert.AlertType.INFORMATION,"Incorrect Password");
            a.show();
            return ;}

    }

    public  String hash(String password){


        String hashtext="";
        try {

            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return hashtext;
    }

}
