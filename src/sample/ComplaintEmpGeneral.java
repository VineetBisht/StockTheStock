/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Shivam
 */
public class ComplaintEmpGeneral {
    
        String usrnm="N01324490";
        //String usrnm="N01328150";
        String passwd="oracle";
        String url="jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
       
    public ComplaintEmpGeneral() throws ClassNotFoundException {
        
    try{
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con=DriverManager.getConnection(url, usrnm,passwd);
        DatabaseMetaData dm=con.getMetaData();
        ResultSet rs=dm.getTables(null,null,"employeecomplaint", null);
        if(!rs.next())
        {
            System.out.println("kkl");
            String query="Create table employeecomplaint(complaintid integer GENERATED BY DEFAULT AS IDENTITY START WITH 1 primary key,empid integer references Employee(emp_id),complaintdate date ,subject varchar(40),description varchar(400) ,status varchar(20))";
            //String query="Create table employeecomplaint(complaintid integer GENERATED BY DEFAULT AS IDENTITY START WITH 1 primary key,empid integer ,complaintdate date ,subject varchar(40),description varchar(400) ,status varchar(20))";
            Statement stm=con.createStatement();
            stm.executeQuery(query);
       
        }
        }
        catch(SQLException ex)
        {}

    }

        public void addComplain(ComplaintEmp emp) throws SQLException, ClassNotFoundException
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection(url, usrnm,passwd);
            String query="Insert into employeecomplaint(empid,complaintdate,subject,description,status) values(?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
           
            java.sql.Date sqldt=new Date(emp.dt.getTime());
             
            ps.setInt(1,emp.getEmpid());
            
            ps.setDate(2,sqldt);
            
            ps.setString(3,emp.getSubject());
            
            ps.setString(4,emp.getDesc());
            
            ps.setString(5,emp.getStatus());
            
            ps.executeQuery();
            
        }
        
        public void updateComplainStatus(ComplaintEmp emp) throws SQLException, ClassNotFoundException
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection(url, usrnm,passwd);
            String query="update employeecomplaint set status=? where empid=?";
            PreparedStatement ps=con.prepareStatement(query);
                       
            ps.setString(1,"Handled");
           
            ps.setInt(5,emp.getEmpid());
            
            ps.executeQuery();
            
        }
        
        public void deleteComplain(int compId) throws SQLException, ClassNotFoundException
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection(url, usrnm,passwd);
            String query="delete from employeecomplaint where complaintid=?";
            PreparedStatement ps=con.prepareStatement(query);
           
            ps.setInt(1,compId);
            
            ps.executeQuery();
            
        }
        public static void main(String[] args) throws ClassNotFoundException, SQLException {
            java.util.Date dt = new java.util.Date();
        ComplaintEmp em=new ComplaintEmp(1,dt , "hh", "lk", "Pending");
            ComplaintEmpGeneral ge=new ComplaintEmpGeneral();
            ge.addComplain(em);
    }
    
}
