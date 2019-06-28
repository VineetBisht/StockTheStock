/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.Date;

/**
 *
 * @author Shivam
 */
public class ComplaintEmp {
    int empid;
    Date dt;
    String subject;
    String desc;
    String status;

    public ComplaintEmp() {
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public ComplaintEmp(int empid, Date dt, String subject, String desc, String status) {
        this.empid = empid;
        this.dt = dt;
        this.subject = subject;
        this.desc = desc;
        this.status = status;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEmpid() {
        return empid;
    }

    public Date getDt() {
        return dt;
    }

    public String getSubject() {
        return subject;
    }

    public String getDesc() {
        return desc;
    }

    public String getStatus() {
        return status;
    }
    
    
}
