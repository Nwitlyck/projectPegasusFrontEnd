package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author PegasusTeam
 */
public class NonWorkingDayTO implements Serializable{

    private int id;
    private int type;
    private int idColaborator;
    private Date initialDate;
    private Date finalDate;
    private String feedback;
    private int state;
    private int review;

    public NonWorkingDayTO() {
    }

    public NonWorkingDayTO(int id, int type, int idColaborator, Date initialDate, Date finalDate, String feedback, int state, int review) {
        this.id = id;
        this.type = type;
        this.idColaborator = idColaborator;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.feedback = feedback;
        this.state = state;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdColaborator() {
        return idColaborator;
    }

    public void setIdColaborator(int idColaborator) {
        this.idColaborator = idColaborator;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }
    
}
