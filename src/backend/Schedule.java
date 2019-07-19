/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CreateDatabase;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author Shivam
 */
public class Schedule {

    private String monTime = "";
    private String tuesTime = "";
    private String wedTime = "";
    private String thursTime = "";
    private String friTime = "";
    private String satTime = "";

    public void setTotHours(double totHours) {
        this.totHours = totHours;
    }
    private String sunTime = "";

    public double getTotHours() {
        return totHours;
    }
    private double totHours;

    public String getMonTime() {
        return monTime;
    }

    public void setMonTime(String monTime) {
        this.monTime = monTime;
    }

    public void setTuesTime(String tuesTime) {
        this.tuesTime = tuesTime;
    }

    public void setWedTime(String wedTime) {
        this.wedTime = wedTime;
    }

    public void setThursTime(String thursTime) {
        this.thursTime = thursTime;
    }

    public void setFriTime(String friTime) {
        this.friTime = friTime;
    }

    public void setSatTime(String satTime) {
        this.satTime = satTime;
    }

    public void setSunTime(String sunTime) {
        this.sunTime = sunTime;
    }

    public String getTuesTime() {
        return tuesTime;
    }

    public String getWedTime() {
        return wedTime;
    }

    public String getThursTime() {
        return thursTime;
    }

    public String getFriTime() {
        return friTime;
    }

    public String getSatTime() {
        return satTime;
    }

    public String getSunTime() {
        return sunTime;
    }

    public void calculateTotalHours() {
        String[][] timings = new String[7][6];
        double hours = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {

                timings[i][j] = new String();
            }
        }
        if (!sunTime.equals("")) {                          //splitting the time as to get starttime and end time seperately
            timings[0] = sunTime.split(" ");
        }
        else{
            timings[0][0]="";
        }

        if (!monTime.equals("")) {
            timings[1] = monTime.split(" ");
        }
        else{
            timings[1][0]="";                           //because of the statement at line 151, if i dont put anything in the string if the schedule is 
        }                                               //not set, then it remains empty and when i check my condition in if statement that is inside the
                                                        //for loop, it returns me null pointer exception, so just to remove that exception and do that 
                                                        //validation i have added else in every timings setting.
        
        if (!tuesTime.equals("")) {
            timings[2] = tuesTime.split(" ");
        }
        else{
            timings[2][0]="";
        }
        
        if (!wedTime.equals("")) {
            timings[3] = wedTime.split(" ");
        }
        else{
            timings[3][0]="";
        }
        
        if (!thursTime.equals("")) {
            timings[4] = thursTime.split(" ");
        }
        else{
            timings[4][0]="";
        }
        
        if (!friTime.equals("")) {
            timings[5] = friTime.split(" ");
        }
        else{
            timings[5][0]="";
        }
        
        if (!friTime.equals("")) {
            timings[6] = satTime.split(" ");
        }
        else{
            timings[6][0]="";
        }
        
        for (int i = 0; i <= 6; i++) {

            if (!timings[i][0].equals("")) {                            //checking if the string for a particular day is not empty
                if (timings[i][1].equals(timings[i][4])) {              //if both the start time and end time are in AM/morning

                    double time1 = Double.parseDouble(timings[i][0]);   //getting the values from the splitted string
                    double time2 = Double.parseDouble(timings[i][3]);

                    if (time1 == 12) {                                  //total working hours for the employee
                        hours += time2;

                    } else {
                        hours += (time2 - time1);                       //if he started at some other time in the morning, the total hours he worked would
                        //be the endtime-starttime as the endtime will be bigger unit that the start time.
                    }
                    System.out.println("" + hours);
                } else {
//                if(timings[i][1].equals("AM") && timings[i][5].equals("PM"))
//                {

                    double time1 = Double.parseDouble(timings[i][0]);
                    double time2 = Double.parseDouble(timings[i][3]);
                    if (time1 == 12) {                                  //IF:if the start time is 12AM 
                        if (time2 == 12) {                              //if the start time is 12AM and end time 12 PM eg: 12AM to 12PM
                            hours += time1;                             //total hours worked would be 12 hours ie we can assign either time 1 or time2 
                                                                        // the values for both is 12
                        } else {                                       
                                                                        //if start time is 12 but end time is something else than 12 eg 12AM to 2PM
                            hours += time1 + time2;                     //the unit of hours worked would be the previous whole morning and the after noon
                                                                        // hours worked.
                        }
                    } else {                                            //ELSE:time1 is not 12AM                         
                        if(time2==12)                                   //if the start time is not 12 but the end time is 12 that could be only one
                                                                        //case in which you start in the morning lets say from 5AM to 12PM
                        {
                            hours +=time2-time1;                        //so easy counting 12-5 would give you 7 hours(above comment example)
                        }
                        else                                            //else ending time is not 12 then that means both start time and end time are not 12
                        {                                               // for eg 3AM to 2PM
                        hours += (12 - time1) + time2;                  //this statement means us (12AM-3AM)+2PM ie(12-3+2)=11 hours
                    
                        }
                    }
                    System.out.println("" + (time2 + time1));
                    //   }
                    System.out.println("" + hours);
                }
            }
        }

    this.totHours  = hours; //assigning hours
    }
     
}

