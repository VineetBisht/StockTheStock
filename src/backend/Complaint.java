package backend;

import java.util.Date;

public class Complaint {
    private int complaintID;
    private String prodid;
    private String customerName;
    private Date complaintDate;
    private String description;
    private double phone;

    public Complaint(int complaintid, String prodid, String customername, Date complaintdate, String description, double phone) {
        this.complaintID=complaintid;
        this.complaintDate=complaintdate;
        this.customerName=customername;
        this.description=description;
        this.prodid=prodid;
        this.phone=phone;
    }

    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPhone() {
        return phone;
    }

    public void setPhone(double phone) {
        this.phone = phone;
    }
}
