
package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;

/**
 * @author PegasusTeam
 */
public class ProjectHasFeedbackTO implements Serializable{
    private int projectId;
    private int FeedbackId;
    private String actionPlan;
    private int actionPlanDone;
    private int state;

    public ProjectHasFeedbackTO() {
    }

    public ProjectHasFeedbackTO(int projectId, int FeedbackId, String actionPlan, int actionPlanDone, int state) {
        this.projectId = projectId;
        this.FeedbackId = FeedbackId;
        this.actionPlan = actionPlan;
        this.actionPlanDone = actionPlanDone;
        this.state = state;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(int FeedbackId) {
        this.FeedbackId = FeedbackId;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public int getActionPlanDone() {
        return actionPlanDone;
    }

    public void setActionPlanDone(int actionPlanDone) {
        this.actionPlanDone = actionPlanDone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
}
