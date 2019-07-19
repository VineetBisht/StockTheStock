/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CreateDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ManageEmployeeClass {
     Connection con;
    String usrnm;
    String passwd;
    String url;
    
    ManageEmployeeClass() throws ClassNotFoundException, SQLException
    {
        usrnm="N01328150";
        passwd="oracle";
       url ="jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
       
        Class.forName("oracle.jdbc.driver.OracleDriver");
            con =DriverManager.getConnection(url, usrnm,passwd);
    }
    
    int addEmp(Employee emp) throws SQLException
    {
        
        String query="insert into employees values (?,?,?)";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1, emp.getEmpno());
        ps.setString(2, emp.getLname());
        ps.setString(3, emp.getFname());
        
        int x=ps.executeUpdate();
        return x;
    }
    
    void diplayEmp() throws SQLException
    {
       String query="select * from employees";
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(query);
        
        while(rs.next())
        {
            System.out.println("Employee No:"+rs.getInt("emp_no")+"\nEmployee Name:"+rs.getString("fname")+" "+rs.getString("lname"));
            System.out.println("");
        }
        
    }
    int updateEmp(Employee emp,String lNm) throws SQLException
    {
        String query="Update employees set lname=? where emp_no=?";
        
        PreparedStatement pm=con.prepareStatement(query);
        pm.setString(1, lNm);
        pm.setInt(2, emp.getEmpno());
        int x=pm.executeUpdate();
        return x;
    }
    
    int deleteEMp(int id) throws SQLException
    {
        String query="Delete from employees where emp_no=?";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1, id);
        int x=ps.executeUpdate();
        
        return x;
    }
    
    int findEMp(int id) throws SQLException
    {
        int x=0;
        String query="select * from employees where emp_no=?";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs=ps.executeQuery();
        
        while(rs.next())
        {
            System.out.println("Employee Found.");
            System.out.println("Employee No:"+rs.getInt("emp_no")+"\nEmployee Name:"+rs.getString("fname")+" "+rs.getString("lname"));
            x=1;
            System.out.println("");
        }
        
        return x;
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Employee emp=new Employee(5, "Sahni", "Simranjeet");
        
        ManageEmployeeClass obj=new ManageEmployeeClass();
        
        int x=obj.addEmp(emp);
        if(x>0)
        {
            System.out.println("Employee Inserted.");
        }
        else
        {
            System.out.println("Some error occured while inserting employee.");
        }
        
        x=obj.updateEmp(emp, "R. Ghai");
        
        if(x>0)
        {
            System.out.println("Employee Updated.");
        }
        else
        {
            System.out.println("Some error occured while updating employee.");
        }
        
        x=obj.findEMp(5);
         if(x!=1)
        {
            System.out.println("Employee not found.");
        }
         
         obj.diplayEmp();
         
         x=obj.deleteEMp(5);
         
          if(x>0)
        {
            System.out.println("Employee Deleted.");
        }
        else
        {
            System.out.println("Some error occured while deleting employee.");
        }
    }
    
}
