package edu.ulatina.pegasus.controllers;

import java.io.Serializable;
import java.util.List;

import edu.ulatina.pegasus.serviceTO.ServiceFeedbackTO;
import edu.ulatina.pegasus.serviceTO.ServicePersonalDataTO;
import edu.ulatina.pegasus.serviceTO.ServiceProjectHasFeedbackTO;
import edu.ulatina.pegasus.serviceTO.ServiceProjectsTO;
import edu.ulatina.pegasus.transfereObjects.FeedbackTO;
import edu.ulatina.pegasus.transfereObjects.ProjectHasFeedbackTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/*
 * @author Nwitlyck
 */
@Named("ProjectMeetingController")
@ViewScoped
public class ProjectMeetingController implements Serializable {

    private Map<String, Integer> mapProject;

    private List<ProjectHasFeedbackTO> listProjectHasFeedbackTO;

    private ProjectHasFeedbackTO projectHasFeedbackTO;

    private ServiceProjectHasFeedbackTO serviceProjectHasFeedbackTO;

    private FeedbackTO feedbackTO;

    @Inject
    private LoginController loginController;

    public Map<String, Integer> getMapProject() {
        return mapProject;
    }

    public void setMapProject(Map<String, Integer> mapProject) {
        this.mapProject = mapProject;
    }

    public List<ProjectHasFeedbackTO> getListProjectHasFeedbackTO() {
        return listProjectHasFeedbackTO;
    }

    public void setListProjectHasFeedbackTO(List<ProjectHasFeedbackTO> listProjectHasFeedbackTO) {
        this.listProjectHasFeedbackTO = listProjectHasFeedbackTO;
    }

    public ProjectHasFeedbackTO getProjectHasFeedbackTO() {
        return projectHasFeedbackTO;
    }

    public void setProjectHasFeedbackTO(ProjectHasFeedbackTO projectHasFeedbackTO) {
        this.projectHasFeedbackTO = projectHasFeedbackTO;
    }

    public ServiceProjectHasFeedbackTO getServiceProjectHasFeedbackTO() {
        return serviceProjectHasFeedbackTO;
    }

    public void setServiceProjectHasFeedbackTO(ServiceProjectHasFeedbackTO serviceProjectHasFeedbackTO) {
        this.serviceProjectHasFeedbackTO = serviceProjectHasFeedbackTO;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public FeedbackTO getFeedbackTO() {
        return feedbackTO;
    }

    public void setFeedbackTO(FeedbackTO feedbackTO) {
        this.feedbackTO = feedbackTO;
    }

    //Special gets/sets
    public String getAuthor(ProjectHasFeedbackTO phfto) {

        FeedbackTO feedbackTO = new FeedbackTO();
        feedbackTO.setId(phfto.getFeedbackId());
        try {
            return new ServicePersonalDataTO().selectByColaboratorId(new ServiceFeedbackTO().selectByPk(feedbackTO).getAuthor()).getName();
        } catch (Exception e) {
            return "Not Found";
        }
    }

    public String getDescription(ProjectHasFeedbackTO phfto) {

        FeedbackTO feedbackTO = new FeedbackTO();
        feedbackTO.setId(phfto.getFeedbackId());
        try {
            return new ServiceFeedbackTO().selectByPk(feedbackTO).getFeedback();
        } catch (Exception e) {
            return "Not Found";
        }
    }

    public java.sql.Date getDateFeedback(ProjectHasFeedbackTO phfto) {

        FeedbackTO feedbackTO = new FeedbackTO();
        feedbackTO.setId(phfto.getFeedbackId());
        try {
            return new ServiceFeedbackTO().selectByPk(feedbackTO).getDateOfFeedback();
        } catch (Exception e) {
            return new java.sql.Date(0);
        }
    }

    public String getActionPlan(ProjectHasFeedbackTO phfto) {

        if (phfto.getActionPlanDone() == 0) {
            return "Not Done";
        }

        return "Done";
    }

    public java.util.Date getDateOfFeedBack() {
        return (java.util.Date) this.feedbackTO.getDateOfFeedback();
    }

    public void setDateOfFeedBack(java.util.Date dateOfFeedBack) {
        if (dateOfFeedBack != null) {
            this.feedbackTO.setDateOfFeedback(new java.sql.Date(dateOfFeedBack.getTime()));
        }
    }

    //Metods
    @PostConstruct
    public void initialize() {

        serviceProjectHasFeedbackTO = new ServiceProjectHasFeedbackTO();
        projectHasFeedbackTO = new ProjectHasFeedbackTO();
        feedbackTO = new FeedbackTO();
        fillMapProjects();
        fillList();

    }

    public void fillMapProjects() {
        try {
            mapProject = new ServiceProjectsTO().selectUsingMapByIdColaborator(loginController.logColaborator().getId());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            mapProject = new HashMap<>();
        }
    }

    public void fillList() {
        if (projectHasFeedbackTO.getProjectId() == 0) {
            return;
        }
        try {
            listProjectHasFeedbackTO = serviceProjectHasFeedbackTO.selectByProject(projectHasFeedbackTO.getProjectId());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            listProjectHasFeedbackTO = new ArrayList<ProjectHasFeedbackTO>();
        }
    }

    public void openNew() {
        feedbackTO = new FeedbackTO();
        projectHasFeedbackTO = new ProjectHasFeedbackTO();
    }

    public void save() {
        if (!nullVerification()) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was empity value"));
            return;
        }
        if (!notFutureDate()) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Date is in the future"));
            return;
        }

        feedbackTO.setAuthor(loginController.logColaborator().getId());
        feedbackTO.setState(1);
        projectHasFeedbackTO.setActionPlanDone(0);
        projectHasFeedbackTO.setState(1);

        try {
            new ServiceFeedbackTO().insert(feedbackTO);
            projectHasFeedbackTO.setFeedbackId(new ServiceFeedbackTO().selectByAll(feedbackTO).getId());
            serviceProjectHasFeedbackTO.insert(projectHasFeedbackTO);
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Feedback was save"));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to save data"));
        }
    }

    public boolean nullVerification() {

        if (projectHasFeedbackTO.getProjectId() == 0) {
            return false;
        }
        if (feedbackTO.getFeedback().isEmpty() || feedbackTO.getFeedback() == "") {
            return false;
        }
        if (feedbackTO.getDateOfFeedback() == null) {
            return false;
        }
        if (projectHasFeedbackTO.getActionPlan().isEmpty() || projectHasFeedbackTO.getActionPlan() == "") {
            return false;
        }

        return true;
    }

    public boolean notFutureDate() {

        if (feedbackTO.getDateOfFeedback().after(new java.sql.Date(System.currentTimeMillis()))) {
            return false;
        }

        return true;
    }

    public void completeActionPlan() {

        if (projectHasFeedbackTO.getActionPlanDone() == 1) {
            return;
        }

        projectHasFeedbackTO.setActionPlanDone(1);

        try {
            serviceProjectHasFeedbackTO.update(projectHasFeedbackTO);
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Action plan completed"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was an error"));
        }
        fillList();
    }

    public boolean canBeDone(ProjectHasFeedbackTO phfto) {
        if (phfto.getActionPlanDone() == 1) {
            return false;
        }
        return true;
    }

}
