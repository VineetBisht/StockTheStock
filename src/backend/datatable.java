package sample;


import javafx.scene.control.Alert;
import oracle.sql.DATE;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;

import static java.time.temporal.ChronoUnit.MINUTES;

public class datatable {
    private static SecretKeySpec secretKey;
    final String secret = "ssshhhhhhhhhhh!!!!";
    private static byte[] key;
    private LocalTime starttime,endtime;

    public boolean add(Person person) {
        int rows = 0;
        try {
            Connection connection = ConnectionSingleton.getConnection();


            String generatedPassword = hash(person.getPassword(), secret);

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
            Alert a = new Alert(Alert.AlertType.WARNING, "Username Already Exits");
            a.show();
            return false;
        } catch (Exception s) {
            System.out.println(s);
            return false;
        }

        return true;
    }

    public void forgot(Person person) {
        int rows = 0;
        try {
            Connection connection = ConnectionSingleton.getConnection();


            String generatedPassword = hash(person.getPassword(), secret);

            String updateQueryPre = "UPDATE  datatable SET PASSWORD=?"

                    + " WHERE USERNAME=?";


            //step 1

            PreparedStatement pst = connection.prepareStatement(updateQueryPre);

            //step 2


            pst.setString(1, generatedPassword);

            pst.setString(2, person.getUser_name());


            //step 3

            rows = pst.executeUpdate();
            if (rows == 0) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Username not found");
                a.show();
                return;
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Password Changed");
                a.show();
                return;
            }


        } catch (Exception s) {
            System.out.println(s);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Task Unsuccessful");
            a.show();
            return;

        }


    }


    public boolean match(Person person, String field_password) {

        String database_password = "";

        String updateQueryPre = "SELECT PASSWORD FROM datatable WHERE USERNAME=?";

        try {
            //step 1
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement pst = con.prepareStatement(updateQueryPre);

            //step 2

            pst.setString(1, person.getUser_name());

            //step 3

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                database_password = rs.getString("PASSWORD");

            } else {

                Alert a = new Alert(Alert.AlertType.INFORMATION, "Username not found");
                a.show();
                return false;
            }


        } catch (Exception s) {
            System.out.println(s);
        }

        if (database_password.equals(hash(field_password, secret))) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Successful");
            a.show();
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Failed");
            a.show();
            return false;
        }

    }

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String hash(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public boolean timesheet(Person person) {
        int rows = 0;
        try {
            Connection connection = ConnectionSingleton.getConnection();



            DateTimeFormatter tm = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate now = LocalDate.now();
            String date = now.format(DateTimeFormatter.ofPattern("dd-MM-yy"));


            LocalTime starttime = LocalTime.now();

            String start = "SELECT * from timesheet WHERE username=? AND dt=?";
            PreparedStatement pst1 = connection.prepareStatement(start);
            pst1.setString(1, person.getUser_name());
            pst1.setString(2, date );

            int row = pst1.executeUpdate();

            if(row!=0) {
                String time = starttime.format(tm);

                String addQueryPre = "UPDATE  timesheet SET dt=?, starttime=?,endtime=?,hrs=?,salary=?,hourly_rate=? WHERE username=?";


                //step 1

                PreparedStatement pst = connection.prepareStatement(addQueryPre);

                //step 2


                pst.setString(7, person.getUser_name());

                pst.setString(1, date);

                pst.setString(2, time);

                pst.setString(3, "");

                pst.setDouble(4, 0d);

                pst.setDouble(5, 0d);

                pst.setDouble(6, 20d);


                //step 3

                rows = pst.executeUpdate();
            }
            else {
                String time = starttime.format(tm);

                String addQueryPre =  "INSERT INTO timesheet (username, dt,starttime,endtime, hrs, salary,HOURLY_RATE) "

                        + " VALUES (?,?, ?, ?, ?,?,?)";


                //step 1

                PreparedStatement pst = connection.prepareStatement(addQueryPre);

                //step 2


                pst.setString(1, person.getUser_name());

                pst.setString(2, date);

                pst.setString(3, time);

                pst.setString(4, "");

                pst.setDouble(5, 0d);

                pst.setDouble(6, 0d);

                pst.setDouble(7, 20d);


                //step 3

                rows = pst.executeUpdate();
            }
        }  catch (Exception s) {
            System.out.println(s);
            return false;
        }
        return true;

    }
    public boolean endshift(Person person) {
        int rows = 0;
        try {
            String a;
            double rate = 0d;
            Connection connection = ConnectionSingleton.getConnection();



            DateTimeFormatter tm = DateTimeFormatter.ofPattern("HH:mm");

            String start = "SELECT * from timesheet WHERE username=?";
            PreparedStatement pst1 = connection.prepareStatement(start);
            pst1.setString(1, person.getUser_name());

            ResultSet rs = pst1.executeQuery();

            while (rs.next()){
                a=rs.getString("STARTTIME");
                rate=rs.getDouble("HOURLY_RATE");
                starttime=LocalTime.parse(a);
            }

            endtime=LocalTime.now();

           double hrs=(ChronoUnit.MINUTES.between(starttime, endtime))/60.0;



            String time = endtime.format(tm);

            double sal=hrs*rate;

            String addQueryPre = "UPDATE  timesheet SET endtime=?,hrs=?,salary=?";


            //step 1

            PreparedStatement pst = connection.prepareStatement(addQueryPre);

            //step 2





            pst.setString(1, time);
            pst.setDouble(2, hrs);
            pst.setDouble(3, sal);
            //pst.setDouble(2, hrs);




            //step 3

            rows = pst.executeUpdate();
        }  catch (Exception s) {
            System.out.println(s);
            return false;
        }
        return true;

    }
}