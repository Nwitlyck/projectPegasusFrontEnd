package edu.ulatina.controllers;

import edu.ulatina.serviceTO.*;
import edu.ulatina.transfereObjects.*;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.application.*;

/*
 * @author Pegasus
 */
@ManagedBean(name = "colaboratorController")
@SessionScoped
public class ColaboratorController implements Serializable {

    private List<ColaboratorTO> listColaboratorTO;

    private ColaboratorTO selectedColaboratorTO;

    private ServiceColaboratorTO serviceColaboratorTO;

    private boolean newColaboratorTO;

    private String sizeListColaboratorTO;

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    private PersonalDataTO selectedPersonalData;

    private ServicePersonalDataTO servicePersonalDataTO;

    private ColaboratorTO logColaborator;

    private boolean enable;

    public ServicePersonalDataTO getServicePersonalDataTO() {
        return servicePersonalDataTO;
    }

    public void setServicePersonalDataTO(ServicePersonalDataTO servicePersonalDataTO) {
        this.servicePersonalDataTO = servicePersonalDataTO;
    }

    public PersonalDataTO getSelectedPersonalData() {
        return selectedPersonalData;
    }

    public void setSelectedPersonalData(PersonalDataTO selectedPersonalData) {
        this.selectedPersonalData = selectedPersonalData;
    }

    public List<ColaboratorTO> getListColaboratorTO() {
        return listColaboratorTO;
    }

    public void setListColaboratorTO(List<ColaboratorTO> listColaboratorTO) {
        this.listColaboratorTO = listColaboratorTO;
    }

    public ColaboratorTO getSelectedColaboratorTO() {
        return selectedColaboratorTO;
    }

    public void setSelectedColaboratorTO(ColaboratorTO selectedColaboratorTO) {
        this.selectedColaboratorTO = selectedColaboratorTO;
        if (!isNewColaboratorTO()) {
            personalDateFromColaborator();
        }
    }

    public ServiceColaboratorTO getServiceColaboratorTO() {
        return serviceColaboratorTO;
    }

    public void setServiceColaboratorTO(ServiceColaboratorTO serviceColaboratorTO) {
        this.serviceColaboratorTO = serviceColaboratorTO;
    }

    public boolean isNewColaboratorTO() {
        return newColaboratorTO;
    }

    public void setNewColaboratorTO(boolean newColaboratorTO) {
        this.newColaboratorTO = newColaboratorTO;
    }

    public String getSizeListColaboratorTO() {
        return sizeListColaboratorTO;
    }

    public void setSizeListColaboratorTO(String sizeListColaboratorTO) {
        this.sizeListColaboratorTO = sizeListColaboratorTO;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public java.util.Date getCalendarHireDate() {
        return (java.util.Date) this.selectedColaboratorTO.getHireDate();
    }

    public void setCalendarHireDate(java.util.Date hireDate) {
        if (hireDate != null) {
            this.selectedColaboratorTO.setHireDate(new java.sql.Date(hireDate.getTime()));
        }
    }

    public java.util.Date getCalendarFireDate() {
        return (java.util.Date) this.selectedColaboratorTO.getFireDate();
    }

    public void setCalendarFireDate(java.util.Date fireDate) {
        if (fireDate != null) {
            this.selectedColaboratorTO.setFireDate(new java.sql.Date(fireDate.getTime()));
        }
    }

    public java.util.Date getCalendarBirthDate() {
        return (java.util.Date) this.selectedPersonalData.getBirthdate();
    }

    public void setCalendarBirthDate(java.util.Date birthDate) {
        if (birthDate != null) {
            this.selectedPersonalData.setBirthdate(new java.sql.Date(birthDate.getTime()));
        }
    }

    public ColaboratorTO getLogColaborator() {
        return logColaborator;
    }

    public void setLogColaborator(ColaboratorTO logColaborator) {
        this.logColaborator = logColaborator;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    //mettods
    @PostConstruct
    public void initianizate() {
        logColaborator = loginController.logColaborator();
        serviceColaboratorTO = new ServiceColaboratorTO();
        servicePersonalDataTO = new ServicePersonalDataTO();
        selectedColaboratorTO = new ColaboratorTO();
        selectedPersonalData = new PersonalDataTO();
        fillListColaboratorTO();
        sizeListColaboratorTO = listColaboratorTO.size() + "";
    }

    public void fillListColaboratorTO() {
        try {
            if (loginController.logColaborator().getAcceslevel() == 2) {
                fillAsAdmin();
            } else {
                fillAsManager();
            }
        } catch (Exception e) {
            listColaboratorTO = new ArrayList<ColaboratorTO>();
        }
    }

    public void fillAsAdmin() throws Exception {
        if (!this.enable) {
            listColaboratorTO = serviceColaboratorTO.selectByState(1);
        } else {

            listColaboratorTO = serviceColaboratorTO.selectByState(0);
        }
    }

    public void fillAsManager() throws Exception {
        listColaboratorTO = serviceColaboratorTO.selectByManagerId(loginController.logColaborator().getId());
    }

    public void saveColaboratorTO() {
        if (verifyNull() || !IsAValidEmail() || !IsEmailNew() || selectedColaboratorDateIsFuture()) {
            return;
        }

        try {
            serviceColaboratorTO.insert(selectedColaboratorTO);
            selectedPersonalData.setId_colaborator(serviceColaboratorTO.selectByEmail(selectedColaboratorTO.getEmail()).getId());
            servicePersonalDataTO.insert(this.selectedPersonalData);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to add colaborator data"));
        }
        initianizate();
    }

    public void updateColaboratorTO() {

        if (verifyNull() || !IsAValidEmail() || !IsEmailNew() || selectedColaboratorDateIsFuture()) {
            return;
        }

        try {
            serviceColaboratorTO.update(selectedColaboratorTO);
            servicePersonalDataTO.update(selectedPersonalData);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to update calaborator data"));
        }
        initianizate();
    }

    public void desableColaboratorTO() {
        selectedColaboratorTO.setState(0);
        selectedPersonalData.setState(0);
        try {
            serviceColaboratorTO.update(selectedColaboratorTO);
            servicePersonalDataTO.update(selectedPersonalData);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to desable calaborator data"));
        }
        initianizate();
    }
    
    public void enableColaboratorTO() { 
        selectedColaboratorTO.setState(1);
        selectedPersonalData.setState(1);
        try {
            serviceColaboratorTO.update(selectedColaboratorTO);
            servicePersonalDataTO.update(selectedPersonalData);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to desable calaborator data"));
        }
        initianizate();
    } 

    public void deleteColaboratorTO() { 
        try {
            servicePersonalDataTO.delete(selectedPersonalData);
            serviceColaboratorTO.delete(selectedColaboratorTO); 
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to delete calaborator data"));
        }
        initianizate();
    }

    public void openNew() {
        selectedColaboratorTO = new ColaboratorTO(0, logColaborator.getId(), "", 0, new java.sql.Date(System.currentTimeMillis()), null, "", 0, 1);
        newColaboratorTO = true;
        selectedPersonalData = new PersonalDataTO(0, 0, "", null, 0, 1);
    }

    public void closeNew() {
        newColaboratorTO = false;
    }

    public void personalDateFromColaborator() {

        try {
            selectedPersonalData = servicePersonalDataTO.selectByColaboratorId(selectedColaboratorTO.getId());
        } catch (Exception e) {
            selectedPersonalData = new PersonalDataTO(0, selectedColaboratorTO.getId(), "", null, 0, 1);
        }
    }

    public boolean verifyNull() {
        if (selectedColaboratorTO == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null", "The Colaborator is null"));
            return true;
        }
        if (selectedColaboratorTO.getEmail().isEmpty() || selectedColaboratorTO.getEmail() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator email is not fill"));
            return true;
        }
        if (selectedColaboratorTO.getAcceslevel() < 0 || selectedColaboratorTO.getAcceslevel() > 2) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator acesslevel is not on range"));
            return true;
        }
        if (selectedColaboratorTO.getHireDate() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator hire date is not fill"));
            return true;
        }
        if (selectedColaboratorTO.getPassword().isEmpty() || selectedColaboratorTO.getPassword() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator password is not fill"));
            return true;
        }
        if (selectedPersonalData.getName().isEmpty() || selectedPersonalData.getName() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Name is not filled"));
            return true;
        }

        if (selectedPersonalData.getBirthdate() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator birthdate is not fill"));
            return true;
        }
        if (selectedPersonalData.getEmergencycontact() <= 10000000 || selectedPersonalData.getEmergencycontact() > 1000000000) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator emergency contact is not on range"));
            return true;
        }

        return false;
    }

    public boolean IsAValidEmail() {

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (!Pattern.compile(regexPattern).matcher(this.selectedColaboratorTO.getEmail()).matches()) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid", "The Email is invalid"));
            return false;
        }

        return true;
    }

    public boolean IsEmailNew() {

        try {
            if (serviceColaboratorTO.selectByEmail(selectedColaboratorTO.getEmail()) != null && serviceColaboratorTO.selectByEmail(selectedColaboratorTO.getEmail()).getId() != selectedColaboratorTO.getId()) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid", "The email is repeded"));
                return false;
            }
        } catch (Exception e) {
        }

        return true;
    }

    public boolean selectedColaboratorDateIsFuture() {

        if (selectedColaboratorTO.getHireDate().after(new Date(System.currentTimeMillis()))) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid", "The Colaborator hire date in the future!"));
            return true;
        }

        return false;
    }

    public String managerEmailById(ColaboratorTO colaboratorTO) {

        ColaboratorTO manager = new ColaboratorTO();
        manager.setId(colaboratorTO.getManagerId());

        try {
            return serviceColaboratorTO.selectByPk(manager).getEmail();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String nameByAcessLevel(ColaboratorTO colaboratorTO) {

        switch(colaboratorTO.getAcceslevel()){
            case 0:
                return "Colaborator";
            case 1:
                return "Manager";
            case 2:
                return "Admin";
        }
        return "";
    }

    public void showDisable() {
        enable = true;
        fillListColaboratorTO();
    }

    public void showEnable() {
        enable = false;
        fillListColaboratorTO();
    }

}
