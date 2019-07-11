/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Date;
import java.sql.Types;
/**
 *
 * @author Shivam
 */
public class ComplaintCust {
    int compId;
    int prodId;
    Date dt;

    public void setCompId(int compId) {
        this.compId = compId;
    }
    String desc;
    String customerNm;

    public String getCustomerNm() {
        return customerNm;
    }

    public ComplaintCust() {
    }

    public ComplaintCust(int prodId, Date dt, String desc, String customerNm) {
        this.prodId = prodId;
        this.dt = dt;
        this.desc = desc;
        this.customerNm = customerNm;
    }

    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }
    
    public void setProdId(int prodId) {
        this.prodId = prodId;
    }


    public void setDt(Date dt) {
        this.dt = dt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getProdId() {
        return prodId;
    }

    public Date getDt() {
        return dt;
    }

    public String getDesc() {
        return desc;
    }
    
    
    public static void main(String[] args)  { 
        
    }
}
