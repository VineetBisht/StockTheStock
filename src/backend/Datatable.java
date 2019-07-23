package backend;


import javafx.scene.control.Alert;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.security.MessageDigest;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;


public class Datatable {
    private static SecretKeySpec secretKey;
    final String secret = "ssshhhhhhhhhhh!!!!";
    private static byte[] key;
    private LocalTime starttime, endtime;
    String username = "hr";
    String password = "oracle";
    String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
    Connection con;

    public Datatable() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean add(Person person) {
        try {

            String generatedPassword = hash(person.getPassword(), secret);

            String addQueryPre = "INSERT INTO datatable (USERNAME, FIRST_NAME,LAST_NAME,PASSWORD, ADDRESS, PHONE_NUMBER,DOB, DESIGNATION, EMAIL) "

                    + " VALUES (?,?, ?, ?, ?,?,?,?,?)";

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
            pst.setString(9, person.getEmail());

            //step 3
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean forgot(Person person) {
        int rows = 0;
        try {
            String generatedPassword = hash(person.getPassword(), secret);

            String updateQueryPre = "UPDATE  datatable SET PASSWORD=?"

                    + " WHERE USERNAME=?";
            //step 1

            PreparedStatement pst = con.prepareStatement(updateQueryPre);
            //step 2

            pst.setString(1, generatedPassword);
            pst.setString(2, person.getUser_name());

            //step 3
            rows = pst.executeUpdate();
            if (rows != 0) {
                return true;
            }
        } catch (Exception s) {
            s.printStackTrace();
            return false;
        }
        return false;
    }


    public String match(Person person, String field_password) {

        String database_password = "";

        String updateQueryPre = "SELECT * FROM datatable WHERE USERNAME=?";
        ResultSet rs = null;

        try {
            //step 1
            PreparedStatement pst = con.prepareStatement(updateQueryPre);

            //step 2
            pst.setString(1, person.getUser_name());

            //step 3
            rs = pst.executeQuery();
            if (rs.next()) {
                database_password = rs.getString("PASSWORD");
                if (database_password.equals(hash(field_password, secret))) {
                    return rs.getString("designation");
                }
            }
        } catch (Exception s) {
            s.printStackTrace();
        }
        return null;
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

        try {

            DateTimeFormatter tm = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate now = LocalDate.now();
            String date = now.format(DateTimeFormatter.ofPattern("dd-MM-yy"));

            LocalTime starttime = LocalTime.now();

            String start = "SELECT * from timesheet WHERE username=? AND dt=?";
            PreparedStatement pst1 = con.prepareStatement(start);
            pst1.setString(1, person.getUser_name());
            pst1.setString(2, date);
            int row = pst1.executeUpdate();

            String listQuerry = "SELECT * from datatable WHERE username=?";
            PreparedStatement preparedStatement = con.prepareStatement(listQuerry);
            preparedStatement.setString(1, person.getUser_name());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int rate = resultSet.getInt("SALARY");
            int emp_id = resultSet.getInt("EMP_ID");

            String status = "INSERT INTO EMP_ONLINE(username) "
                    + " VALUES(?)";
            PreparedStatement onp = con.prepareStatement(status);
            onp.setString(1, person.getUser_name());
            onp.executeUpdate();


            if (row != 0) {
                String time = starttime.format(tm);
                String addQueryPre = "UPDATE  timesheet SET dt=?, starttime=?,endtime=?,hrs=?,salary=?, emp_id=? WHERE username=?";

                //step 1
                PreparedStatement pst = con.prepareStatement(addQueryPre);

                //step 2
                pst.setString(7, person.getUser_name());
                pst.setString(1, date);
                pst.setString(2, time);
                pst.setString(3, "");
                pst.setDouble(4, 0d);
                pst.setDouble(5, 0d);
                pst.setInt(6, emp_id);

                //step 3

                pst.executeUpdate();
            } else {
                String time = starttime.format(tm);

                String addQueryPre = "INSERT INTO timesheet (username, dt,starttime,endtime, hrs, salary,emp_id) "

                        + " VALUES (?,?, ?, ?, ?,?,?)";


                //step 1

                PreparedStatement pst = con.prepareStatement(addQueryPre);

                //step 2

                pst.setString(1, person.getUser_name());
                pst.setString(2, date);
                pst.setString(3, time);
                pst.setString(4, "");
                pst.setDouble(5, 0d);
                pst.setDouble(6, 0d);
                pst.setInt(7, emp_id);

                //step 3
                pst.executeUpdate();
            }
        } catch (Exception s) {
            s.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean endshift() {
        int rows = 0;
        try {
            String user = "";
            String online = "SELECT * from EMP_ONLINE";
            PreparedStatement onp = con.prepareStatement(online);
            ResultSet rs1 = onp.executeQuery();
            rs1.next();
            user = rs1.getString("USERNAME");

            String listQuerry = "SELECT * from datatable WHERE username=?";
            PreparedStatement preparedStatement = con.prepareStatement(listQuerry);
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int rate = resultSet.getInt("SALARY");

            String a = "";
            String msg = "";
            String dt = "";

            DateTimeFormatter tm = DateTimeFormatter.ofPattern("HH:mm");

            String start = "SELECT * from timesheet WHERE username=?";
            PreparedStatement pst1 = con.prepareStatement(start);
            pst1.setString(1, user);

            ResultSet rs = pst1.executeQuery();

            while (rs.next()) {
                a = rs.getString("STARTTIME");
                dt = rs.getString("DT");
                starttime = LocalTime.parse(a);
            }

            endtime = LocalTime.now();
            double hrs = (ChronoUnit.MINUTES.between(endtime, starttime)) / 60.0;
            String time = endtime.format(tm);
            double sal = hrs * rate;

            String addQueryPre = "UPDATE  timesheet SET endtime=?,hrs=?,salary=? WHERE USERNAME=?";

            //step 1

            PreparedStatement pst = con.prepareStatement(addQueryPre);

            //step 2
            pst.setString(1, time);
            pst.setDouble(2, hrs);
            pst.setDouble(3, sal);
            pst.setString(4, user);
            //pst.setDouble(2, hrs);

            //step 3

            pst.executeUpdate();
            msg = "Username: " + user + "\nDate: " + dt + "\nStart Time: " + a + "\nEnd Time: " + time + "\nHours: " +
                    hrs;
            sendMail(resultSet.getString("email"), msg);

            PreparedStatement ps = con.prepareStatement("DELETE FROM EMP_ONLINE WHERE USERNAME=?");
            ps.setString(1, user);
            ps.executeUpdate();

        } catch (Exception s) {
            System.out.println(s);
            return false;
        }
        return true;
    }

    public static void sendMail(String reciever, String msg) throws Exception {
        System.out.println("Message prepare");
        Properties properties = new Properties();

        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        String fromUser = "javatest35@gmail.com";
        String fromUserEmailPassword = "javathere";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUser, fromUserEmailPassword);
            }
        });
        Message message = prepareMessage(session, fromUser, reciever, msg);
        Transport.send(message);
        System.out.println("Message sent");
    }

    private static Message prepareMessage(Session session, String sender, String reciever, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
            message.setSubject("(Stock The Stock) Shift Details:");
            message.setText(msg);
            return message;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public String getUserName() {
        try {
            String online = "SELECT * from EMP_ONLINE";
            PreparedStatement onp = con.prepareStatement(online);
            ResultSet rs1 = onp.executeQuery();
            rs1.next();
            String user = rs1.getString("USERNAME");

            String online2 = "SELECT * from datatable where username=?";
            PreparedStatement onp2 = con.prepareStatement(online2);
            onp2.setString(1,user);
            ResultSet rs2 = onp2.executeQuery();
            rs2.next();
            return rs2.getString("first_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}