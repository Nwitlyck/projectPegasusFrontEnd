package edu.ulatina.pegasus.controllers;

import edu.ulatina.pegasus.serviceTO.ServiceFeedbackTO;
import edu.ulatina.pegasus.serviceTO.ServiceMeetingHasColaboratorAndFeedbackTO;
import edu.ulatina.pegasus.serviceTO.ServicePersonalDataTO;
import edu.ulatina.pegasus.transfereObjects.FeedbackTO;
import edu.ulatina.pegasus.transfereObjects.MeetingHasColaboratorAndFeedbackTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/*
 * @author Nwitlyck
 */
@Named("PersonalMeetingsController")
@ViewScoped
public class PersonalMeetingsController implements Serializable {

    private List<MeetingHasColaboratorAndFeedbackTO> listMeetingHasColaboratorAndFeedbackTO;

    private boolean flag;

    private ServiceMeetingHasColaboratorAndFeedbackTO serviceMeetingHasColaboratorAndFeedbackTO;

    @Inject
    private LoginController loginController;

    private MeetingHasColaboratorAndFeedbackTO meetingHasColaboratorAndFeedbackTO;

    private FeedbackTO feedbackTO;

    public List<MeetingHasColaboratorAndFeedbackTO> getListMeetingHasColaboratorAndFeedbackTO() {
        return listMeetingHasColaboratorAndFeedbackTO;
    }

    public void setListMeetingHasColaboratorAndFeedbackTO(List<MeetingHasColaboratorAndFeedbackTO> listMeetingHasColaboratorAndFeedbackTO) {
        this.listMeetingHasColaboratorAndFeedbackTO = listMeetingHasColaboratorAndFeedbackTO;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ServiceMeetingHasColaboratorAndFeedbackTO getServiceMeetingHasColaboratorAndFeedbackTO() {
        return serviceMeetingHasColaboratorAndFeedbackTO;
    }

    public void setServiceMeetingHasColaboratorAndFeedbackTO(ServiceMeetingHasColaboratorAndFeedbackTO serviceMeetingHasColaboratorAndFeedbackTO) {
        this.serviceMeetingHasColaboratorAndFeedbackTO = serviceMeetingHasColaboratorAndFeedbackTO;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public MeetingHasColaboratorAndFeedbackTO getMeetingHasColaboratorAndFeedbackTO() {
        return meetingHasColaboratorAndFeedbackTO;
    }

    public void setMeetingHasColaboratorAndFeedbackTO(MeetingHasColaboratorAndFeedbackTO meetingHasColaboratorAndFeedbackTO) {
        this.meetingHasColaboratorAndFeedbackTO = meetingHasColaboratorAndFeedbackTO;
    }

    public FeedbackTO getFeedbackTO() {
        return feedbackTO;
    }

    public void setFeedbackTO(FeedbackTO feedbackTO) {
        this.feedbackTO = feedbackTO;
    }

    //Special Gets/sets
    public String getColaborator(MeetingHasColaboratorAndFeedbackTO mhcafto) {

        try {
            return new ServicePersonalDataTO().selectByColaboratorId(mhcafto.getColaboratorId()).getName();
        } catch (Exception e) {
            return "Not Found";
        }
    }

    public String getAuthor(MeetingHasColaboratorAndFeedbackTO mhcafto) {

        FeedbackTO feedbackTO = new FeedbackTO();
        feedbackTO.setId(mhcafto.getFeedbackId());
        try {
            return new ServicePersonalDataTO().selectByColaboratorId(new ServiceFeedbackTO().selectByPk(feedbackTO).getAuthor()).getName();
        } catch (Exception e) {
            return "Not Found";
        }
    }

    public String getDescription(MeetingHasColaboratorAndFeedbackTO mhcafto) {

        FeedbackTO feedbackTO = new FeedbackTO();
        feedbackTO.setId(mhcafto.getFeedbackId());
        try {
            return new ServiceFeedbackTO().selectByPk(feedbackTO).getFeedback();
        } catch (Exception e) {
            return "Not Found";
        }
    }

    public java.sql.Date getDateFeedback(MeetingHasColaboratorAndFeedbackTO mhcafto) {

        FeedbackTO feedbackTO = new FeedbackTO();
        feedbackTO.setId(mhcafto.getFeedbackId());
        try {
            return new ServiceFeedbackTO().selectByPk(feedbackTO).getDateOfFeedback();
        } catch (Exception e) {
            return new java.sql.Date(0);
        }
    }

    public java.util.Date getDateOfFeedBack() {
        return (java.util.Date) this.feedbackTO.getDateOfFeedback();
    }

    public void setDateOfFeedBack(java.util.Date dateOfFeedBack) {
        if (dateOfFeedBack != null) {
            this.feedbackTO.setDateOfFeedback(new java.sql.Date(dateOfFeedBack.getTime()));
        }
    }

    //metods
    @PostConstruct
    public void initialize() {

        serviceMeetingHasColaboratorAndFeedbackTO = new ServiceMeetingHasColaboratorAndFeedbackTO();
        feedbackTO = new FeedbackTO();
        meetingHasColaboratorAndFeedbackTO = new MeetingHasColaboratorAndFeedbackTO();

        fillList();

    }

    public void fillList() {
        if (!flag) {
            fillByColaborator();
        } else {
            fillByAuthor();
        }
    }

    public void fillByColaborator() {
        try {
            listMeetingHasColaboratorAndFeedbackTO = serviceMeetingHasColaboratorAndFeedbackTO.selectByColaboratorId(loginController.logColaborator().getId());
        } catch (Exception e) {
            listMeetingHasColaboratorAndFeedbackTO = new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
        }
    }

    public void fillByAuthor() {
        try {
            listMeetingHasColaboratorAndFeedbackTO = serviceMeetingHasColaboratorAndFeedbackTO.selectByAuthorId(loginController.logColaborator().getId());
        } catch (Exception e) {
            listMeetingHasColaboratorAndFeedbackTO = new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
        }
    }

    public void trueFlag() {
        flag = true;
        fillList();
    }

    public void falseFlag() {
        flag = false;
        fillList();
    }

    public void openNew() {
        feedbackTO = new FeedbackTO();
        meetingHasColaboratorAndFeedbackTO = new MeetingHasColaboratorAndFeedbackTO();
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
        meetingHasColaboratorAndFeedbackTO.setState(1);

        try {
            new ServiceFeedbackTO().insert(feedbackTO);
            meetingHasColaboratorAndFeedbackTO.setFeedbackId(new ServiceFeedbackTO().selectByAll(feedbackTO).getId());
            serviceMeetingHasColaboratorAndFeedbackTO.insert(meetingHasColaboratorAndFeedbackTO);
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Feedback was save"));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to save data"));
        }
    }

    public boolean nullVerification() {

        if (meetingHasColaboratorAndFeedbackTO.getColaboratorId() == 0) {
            return false;
        }
        if (feedbackTO.getFeedback().isEmpty() || feedbackTO.getFeedback() == "") {
            return false;
        }
        if (feedbackTO.getDateOfFeedback() == null) {
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

}
