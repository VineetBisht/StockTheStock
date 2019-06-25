package sample;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.*;

public class DataTable {

    private Connection con;

    public DataTable() throws Exception {

        String username = "n01324490";
        String password = "oracle";
        String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";

        Class.forName("oracle.jdbc.driver.OracleDriver");

        //create connection
        this.con = DriverManager.getConnection(url, username, password);

    }

    public int add(Person person) {
        int rows =0;

        String generatedPassword=hash(person.getPassword());

        String addQueryPre = "INSERT INTO DataTable (user_name, fname,lname,password, address, phn_no,birth_date, designation) "

                + " VALUES (?,?, ?, ?, ?,?,?,?)";

        try {
            //step 1

            PreparedStatement pst = con.prepareStatement(addQueryPre);

            //step 2


            pst.setString(1, person.getUser_name());

            pst.setString(2, person.getFname());

            pst.setString(3, person.getLname());

            pst.setString(4, generatedPassword);

            pst.setString(5, person.getAddress());

            pst.setString(6, person.getPhn_no());

            pst.setDate(7, person.getBirth_date());

            pst.setString(8, person.getDesignation());

            //step 3

            rows = pst.executeUpdate();
        }catch(SQLException s){
            System.out.println(s);
        }

        return rows;
    }



    public int delete(Person person) {
        int rows =0;

        String updateQueryPre = "DELETE FROM DataTable WHERE user_name=?";

        try {
            //step 1

            PreparedStatement pst = con.prepareStatement(updateQueryPre);

            //step 2

            pst.setString(1 ,person.getUser_name());

            //step 3

            rows = pst.executeUpdate();
        }catch(SQLException s){
            System.out.println(s);
        }

        return rows;
    }

    public Boolean match(Person person,String field_password) {

       String database_password="";

        String updateQueryPre = "SELECT password FROM DataTable WHERE user_name=?";

        try {
            //step 1

            PreparedStatement pst = con.prepareStatement(updateQueryPre);

            //step 2

            pst.setString(1, person.getUser_name());

            //step 3

            ResultSet rs = pst.executeQuery();
            database_password=rs.getString("password");

        }catch(SQLException s){
            System.out.println(s);
        }

        if(database_password==hash(field_password))
            return true;
        else
            return false;

    }

    public String hash(String password){

        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
