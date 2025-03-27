
package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author PegasusTeam
 */
public class FeedbackTO implements Serializable{
    private int id;
    private int author;
    private String feedback;
    private Date dateOfFeedback;
    private int state;

    public FeedbackTO() {
    }

    public FeedbackTO(int id, int author, String feedback, Date dateOfFeedback, int state) {
        this.id = id;
        this.author = author;
        this.feedback = feedback;
        this.dateOfFeedback = dateOfFeedback;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getDateOfFeedback() {
        return dateOfFeedback;
    }

    public void setDateOfFeedback(Date dateOfFeedback) {
        this.dateOfFeedback = dateOfFeedback;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
}
