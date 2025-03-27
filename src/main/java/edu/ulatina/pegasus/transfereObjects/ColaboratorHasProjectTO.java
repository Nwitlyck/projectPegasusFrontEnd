package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author PegasusTeam
 */
public class ColaboratorHasProjectTO implements Serializable{

    private int id;
    private int idColaborator;
    private int idProject;
    private int totalTime;
    private Date initialDate;
    private Date finalDate;
    private int state;

    public ColaboratorHasProjectTO() {
    }
    
    public ColaboratorHasProjectTO(int id, int idColaborator, int idProject, int totalTime, Date initialDate, Date finalDate, int state) {
        this.id = id;
        this.idColaborator = idColaborator;
        this.idProject = idProject;
        this.totalTime = totalTime;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdColaborator() {
        return idColaborator;
    }

    public void setIdColaborator(int idColaborator) {
        this.idColaborator = idColaborator;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    

}
