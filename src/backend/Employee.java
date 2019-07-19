/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import javafx.scene.control.Button;

/**
 *
 * @author Shivam
 */
public class Employee {
    private int empno;

    private String empName;
    Button btn;
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    private String lname;
    private String fname;

    public String getEmpName() {
        return empName;
    }

    public Employee(int empno, String empName, Button btn) {
        this.empno = empno;
        this.empName = empName;
        this.btn = btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Button getBtn() {
        return btn;
    }

    public int getEmpno() {
        return empno;
    }

    public Employee(int empno, String lname, String fname, Button btn) {
        this.empno = empno;
        this.lname = lname;
        this.fname = fname;
        this.btn = btn;
    }

    public Employee(int empno, String lname, String fname) {
        this.empno = empno;
        this.lname = lname;
        this.fname = fname;
    }

    public Employee(int empno, String empName, Button btn, String lname, String fname) {
        this.empno = empno;
        this.empName = empName;
        this.btn = btn;
        this.lname = lname;
        this.fname = fname;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }
    
}
