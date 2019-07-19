package backend;



import java.sql.Date;

public class Person {

    private String user_name;
    private String fname;
    private String lname;
    private String password;
    private String address;
    private double phn_no;
    private Date birth_date;
    private String designation;
    private String email;

    public Person(String username, String fname, String lname,String password, String address, double phn_no, Date birth_date, String designation, String email) {
        this.user_name = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.address=address;
        this.phn_no=phn_no;
        this.birth_date=birth_date;
        this.designation=designation;
        this.email=email;
    }

    public Person(){}

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPhn_no() {
        return phn_no;
    }

    public void setPhn_no(double phn_no) {
        this.phn_no = phn_no;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}



