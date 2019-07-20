package backend;

import java.util.Date;

/*
 * @author Shivam
*/
public class SetSchedule {
  Date dt;
  int empId;
  

    public Date getDt() {
        return dt;
    }

    public SetSchedule() {
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public SetSchedule(Date dt, int empId, String start_time, String end_time) {
        this.dt = dt;
        this.empId = empId;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getEmpId() {
        return empId;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }
  String start_time;
  String end_time;
  
}
