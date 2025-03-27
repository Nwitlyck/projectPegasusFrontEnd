package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author PegasusTeam
 */
public class ProjectsTO implements Serializable{

    private int id;
    private String name;
    private Date initialdate;
    private Date finaldate;
    private int state;
    private int completed;

    public ProjectsTO() {
    }

    public ProjectsTO(int id, String name, Date initialdate, Date finaldate, int state, int completed) {
        this.id = id;
        this.name = name;
        this.initialdate = initialdate;
        this.finaldate = finaldate;
        this.state = state;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInitialdate() {
        return initialdate;
    }

    public void setInitialdate(Date initialdate) {
        this.initialdate = initialdate;
    }

    public Date getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(Date finaldate) {
        this.finaldate = finaldate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
    
}
