package edu.ulatina.controllers;

import edu.ulatina.serviceTO.ServiceNonWorkingDayTO;
import edu.ulatina.transfereObjects.ColaboratorTO;
import edu.ulatina.serviceTO.ServiceColaboratorTO;
import edu.ulatina.serviceTO.ServiceDetailTO;
import edu.ulatina.transfereObjects.NonWorkingDayTO;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.application.*;
import org.omg.CORBA.ServiceDetail;

/*
 * @author Nwitlyck
 */
@ManagedBean(name = "daysOffController")
@SessionScoped
public class DaysOffController implements Serializable {

    private NonWorkingDayTO selectedNonWorkingDayTO;

    private ServiceNonWorkingDayTO serviceNonWorkingDayTO;

    private boolean newNonWorkingDay;

    private List<NonWorkingDayTO> listNonWorkingDayTO;

    private boolean showRewied;

    private NonWorkingDayTO currentNonWorkingDayTO;
    
    private Map<String,Integer> map;
    
    private List<NonWorkingDayTO> listDaysOff;
    

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    public LoginController getLoginController() {
        return loginController;
    }

    public List<NonWorkingDayTO> getListDaysOff() {
        return listDaysOff;
    }

    public void setListDaysOff(List<NonWorkingDayTO> listDaysOff) {
        this.listDaysOff = listDaysOff;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
    

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public NonWorkingDayTO getSelectedNonWorkingDayTO() {
        return selectedNonWorkingDayTO;
    }

    public void setSelectedNonWorkingDayTO(NonWorkingDayTO selectedNonWorkingDayTO) {
        this.selectedNonWorkingDayTO = selectedNonWorkingDayTO;
    }

    public ServiceNonWorkingDayTO getServiceNonWorkingDayTO() {
        return serviceNonWorkingDayTO;
    }

    public void setServiceNonWorkingDayTO(ServiceNonWorkingDayTO serviceNonWorkingDayTO) {
        this.serviceNonWorkingDayTO = serviceNonWorkingDayTO;
    }

    public List<NonWorkingDayTO> getListNonWorkingDayTO() {
        return listNonWorkingDayTO;
    }

    public void setListNonWorkingDayTO(List<NonWorkingDayTO> listNonWorkingDayTO) {
        this.listNonWorkingDayTO = listNonWorkingDayTO;
    }

    public boolean isShowRewied() {
        return showRewied;
    }

    public void setShowRewied(boolean showRewied) {
        this.showRewied = showRewied;
    }

    public java.util.Date getCalendarInitialDate() {
        return (java.util.Date) this.selectedNonWorkingDayTO.getInitialDate();
    }

    public void setCalendarInitialDate(java.util.Date initialDate) {
        if (initialDate != null) {
            this.selectedNonWorkingDayTO.setInitialDate(new java.sql.Date(initialDate.getTime()));
        }
    }

    public java.util.Date getCalendarFinalDate() {
        return (java.util.Date) this.selectedNonWorkingDayTO.getFinalDate();
    }

    public void setCalendarFinalDate(java.util.Date finalDate) {
        if (finalDate != null) {
            this.selectedNonWorkingDayTO.setFinalDate(new java.sql.Date(finalDate.getTime()));
        }
    }

    public boolean isNewNonWorkingDay() {
        return newNonWorkingDay;
    }

    public void setNewNonWorkingDay(boolean newNonWorkingDay) {
        this.newNonWorkingDay = newNonWorkingDay;
    }

    public NonWorkingDayTO getCurrentNonWorkingDayTO() {
        return currentNonWorkingDayTO;
    }

    public void setCurrentNonWorkingDayTO(NonWorkingDayTO currentNonWorkingDayTO) {
        this.currentNonWorkingDayTO = currentNonWorkingDayTO;
    }

    //Metods
    @PostConstruct
    public void initialize() {
        this.showRewied = false;
        serviceNonWorkingDayTO = new ServiceNonWorkingDayTO();
        selectedNonWorkingDayTO = new NonWorkingDayTO();
        fillListNonWorkingDayTO();
        fillMap();
        fillDaysOff();

    }
    
    public void fillDaysOff(){
         try {
            listDaysOff = serviceNonWorkingDayTO.selectByColaboratorId(loginController.logColaborator().getId());
            
        } catch (Exception e) {
            listDaysOff = new ArrayList<>();
        }
    }
    
    public void fillMap(){
        try {
            map = new ServiceDetailTO().selectByMasterId(2);
            
        } catch (Exception e) {
        }
    }

    public void fillListNonWorkingDayTO() {
        if (loginController.getLogColaboratorTO().getAcceslevel() == 6) {
            fillAsAdmin();
        } else {
            fillAsManager();
        }
    }

    public void fillAsAdmin() {

        try {
            if (!this.showRewied) {
                listNonWorkingDayTO = serviceNonWorkingDayTO.selectByReview(0);
            } else {
                listNonWorkingDayTO = serviceNonWorkingDayTO.selectByReview(1);
            }
        } catch (Exception e) {
            listNonWorkingDayTO = new ArrayList<NonWorkingDayTO>();
        }
    }

    public void fillAsManager() {
        try {
            if (!this.showRewied) {
                listNonWorkingDayTO = serviceNonWorkingDayTO.selectByReviewAndColaboratorManagerId(0, loginController.logColaborator().getId());
            } else {
                listNonWorkingDayTO = serviceNonWorkingDayTO.selectByReviewAndColaboratorManagerId(1, loginController.logColaborator().getId());
            }

        } catch (Exception e) {
            listNonWorkingDayTO = new ArrayList<NonWorkingDayTO>();
        }
    }
    
    public void save(){
        
        selectedNonWorkingDayTO.setIdColaborator(loginController.logColaborator().getId());
        if (!nullVerification() || !colaboratorExist() || !dateIsFuture()) {
            return;

        }
        
        try {
            serviceNonWorkingDayTO.insert(selectedNonWorkingDayTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to add non working day data"));
        }

        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Done", "Time off requested"));
        fillListNonWorkingDayTO();
        
    }

    public void loadRewied() {
        this.showRewied = false;
        fillListNonWorkingDayTO();
    }

    public void loadNotRewied() {
        this.showRewied = true;
        fillListNonWorkingDayTO();
    }

    public void openNew() {
        selectedNonWorkingDayTO = new NonWorkingDayTO();
        newNonWorkingDay = true;
    }

    public void closeNew() {
        newNonWorkingDay = false;
    }

    public boolean nullVerification() {

        if (selectedNonWorkingDayTO.getIdColaborator() == 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "The id colaborator is null"));
            return false;

        }

        if (selectedNonWorkingDayTO.getInitialDate() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "The initial date is null"));
            return false;

        }

        if (selectedNonWorkingDayTO.getFinalDate() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "The final date is null"));
            return false;

        }
        return true;
    }

    public boolean colaboratorExist() {

        try {
            ColaboratorTO colaboratorTO = new ColaboratorTO();
            colaboratorTO.setId(selectedNonWorkingDayTO.getIdColaborator());
            if (new ServiceColaboratorTO().selectByPk(colaboratorTO) == null) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The colaborator wasnt found"));
                return false;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to search for a colaborator data"));
            return false;
        }

        return true;
    }

    public boolean dateIsFuture() {
        if (selectedNonWorkingDayTO.getInitialDate().before(new Date(System.currentTimeMillis()))) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The initial date is in the past"));
            return false;

        }
        if (selectedNonWorkingDayTO.getFinalDate().before(selectedNonWorkingDayTO.getInitialDate())) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The final date is before initial date"));
            return false;

        }
        return true;
    }

    public void approved() {
        selectedNonWorkingDayTO.setState(1);
        selectedNonWorkingDayTO.setReview(1);
        selectedNonWorkingDayTO.setFeedback("Enjoy!");
        try {
            serviceNonWorkingDayTO.update(selectedNonWorkingDayTO);

        } catch (Exception e) { 
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The request could not be approved"));
        }
        fillListNonWorkingDayTO();
        fillDaysOff();
    }

    public void disapproved() {
        selectedNonWorkingDayTO.setState(0);
        selectedNonWorkingDayTO.setReview(1);
        try {
            serviceNonWorkingDayTO.update(selectedNonWorkingDayTO);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The request could not be disapproved"));
        }
        fillListNonWorkingDayTO();
        fillDaysOff();
    }

    public String managerEmailById(NonWorkingDayTO nonWorkingDayTO) {

        ColaboratorTO manager = new ColaboratorTO();
        manager.setId(nonWorkingDayTO.getIdColaborator());

        try {
            return new ServiceColaboratorTO().selectByPk(manager).getEmail();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getStateInString(NonWorkingDayTO nonWorkingDayTO){
        
        if(nonWorkingDayTO.getReview()==0){
            return "Not Reviewed";
        }
        if(nonWorkingDayTO.getState()==0){
            return "Denied";
        }
        return "Approved";
    }
}
