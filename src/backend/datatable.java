package sample;


import javafx.scene.control.Alert;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;

import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

public class datatable {
    private static SecretKeySpec secretKey;
    final String secret = "ssshhhhhhhhhhh!!!!";
    private static byte[] key;


    public  boolean add(Person person) {
        int rows =0;
        try{
        Connection connection=ConnectionSingleton.getConnection();


        String generatedPassword=hash(person.getPassword(),secret);

        String addQueryPre = "INSERT INTO datatable (USERNAME, FIRST_NAME,LAST_NAME,PASSWORD, ADDRESS, PHONE_NUMBER,DOB, DESIGNATION) "

                + " VALUES (?,?, ?, ?, ?,?,?,?)";


            //step 1

            PreparedStatement pst = connection.prepareStatement(addQueryPre);

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
    public  void forgot(Person person) {
        int rows =0;
        try{
            Connection connection=ConnectionSingleton.getConnection();


            String generatedPassword=hash(person.getPassword(),secret);

            String updateQueryPre = "UPDATE  datatable SET PASSWORD=?"

                    + " WHERE USERNAME=?";


            //step 1

            PreparedStatement pst = connection.prepareStatement(updateQueryPre);

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





    public  void match(Person person,String field_password) {

        String database_password="";

        String updateQueryPre = "SELECT PASSWORD FROM datatable WHERE USERNAME=?";

        try {
            //step 1
            Connection con=ConnectionSingleton.getConnection();
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

        if(database_password.equals(hash(field_password,secret))){
            Alert a=new Alert(Alert.AlertType.INFORMATION,"Login Successful");
            a.show();
            return;}
        else{
            Alert a=new Alert(Alert.AlertType.INFORMATION,"Incorrect Password");
            a.show();
            return ;}

    }

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String hash(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }



}
