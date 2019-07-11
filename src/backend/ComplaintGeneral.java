/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.ComplaintCust;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author Shivam
 */
public class ComplaintGeneral {
        String usrnm="N01324490";
        //String usrnm="N01328150";
        String passwd="oracle";
        String url="jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
       
    public ComplaintGeneral() throws ClassNotFoundException {
        
    try{
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con=DriverManager.getConnection(url, usrnm,passwd);
        DatabaseMetaData dm=con.getMetaData();
        ResultSet rs=dm.getTables(null,null,"customercomplaint", null);
        if(!rs.next())
        {
           String query="Create table customercomplaint(complaintid integer  GENERATED BY DEFAULT AS IDENTITY START WITH 1 primary key,prodid integer references stock(product_id),customername varchar(20),complaintdate date,description varchar(400))";
            //String query="Create table customercomplaint(complaintid integer  GENERATED BY DEFAULT AS IDENTITY START WITH 1 primary key,prodid integer,customername varchar(20),complaintdate date,description varchar(400))";
            PreparedStatement stm=con.prepareStatement(query);
            stm.executeUpdate();
       
        }
        }
        catch(SQLException ex)
        {}

    }

        public void addComplain(ComplaintCust comp) throws SQLException, ClassNotFoundException
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection(url, usrnm,passwd);
            String query="Insert into customercomplaint(prodid,customername,complaintdate,description) values(?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
           
            java.sql.Date sqldt=new Date(comp.dt.getTime());
             
            ps.setInt(1,comp.getProdId());
            
            ps.setString(2,comp.getCustomerNm());
            ps.setDate(3,sqldt);
            
            ps.setString(4,comp.getDesc());
            
            ps.executeQuery();
            
        }
        
        public void deleteComplaint(int compId) throws ClassNotFoundException, SQLException
        {
         Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection(url, usrnm,passwd);
            String query="delete from customercomplaint where complaintid=?";
            PreparedStatement ps=con.prepareStatement(query);
           
            ps.setInt(1,compId);
            
            ps.executeQuery();
            
        }
        public static void main(String[] args) throws ClassNotFoundException, SQLException {
        java.util.Date dt = new java.util.Date();
        ComplaintCust cust=new ComplaintCust(1,dt,"good","shivam");
        
        ComplaintGeneral gen=new ComplaintGeneral();
        gen.addComplain(cust);
        
    }
    }