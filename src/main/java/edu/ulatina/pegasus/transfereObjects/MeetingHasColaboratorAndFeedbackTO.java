package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;

/**
 * @author PegasusTeam
 */
public class MeetingHasColaboratorAndFeedbackTO implements Serializable{
    private int ColaboratorId;
    private int MeetingId;
    private int FeedbackId;
    private int state;

    public MeetingHasColaboratorAndFeedbackTO() {
    }

    public MeetingHasColaboratorAndFeedbackTO(int ColaboratorId, int MeetingId, int FeedbackId, int state) {
        this.ColaboratorId = ColaboratorId;
        this.MeetingId = MeetingId;
        this.FeedbackId = FeedbackId;
        this.state = state;
    }

    public int getColaboratorId() {
        return ColaboratorId;
    }

    public void setColaboratorId(int ColaboratorId) {
        this.ColaboratorId = ColaboratorId;
    }

    public int getMeetingId() {
        return MeetingId;
    }

    public void setMeetingId(int MeetingId) {
        this.MeetingId = MeetingId;
    }

    public int getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(int FeedbackId) {
        this.FeedbackId = FeedbackId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    
    
}
