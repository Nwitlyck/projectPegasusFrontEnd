package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author PegasusTeam
 */
public class ColaboratorTO implements Serializable{

    private int id;
    private int managerId;
    private String email;
    private int acceslevel;
    private Date hireDate;
    private Date fireDate;
    private String password;
    private int vacationDays;
    private int state;

    public ColaboratorTO() {
    }

    public ColaboratorTO(int id, int managerId, String email, int acceslevel, Date hireDate, Date fireDate, String password, int vacationDays, int state) {
        if (id != 0) {
            this.id = id;
        }
        this.managerId = managerId;
        this.email = email;
        this.acceslevel = acceslevel;
        this.hireDate = hireDate;
        this.fireDate = fireDate;
        this.password = password;
        this.vacationDays = vacationDays;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAcceslevel() {
        return acceslevel;
    }

    public void setAcceslevel(int acceslevel) {
        this.acceslevel = acceslevel;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getFireDate() {
        return fireDate;
    }

    public void setFireDate(Date fireDate) {
        this.fireDate = fireDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
