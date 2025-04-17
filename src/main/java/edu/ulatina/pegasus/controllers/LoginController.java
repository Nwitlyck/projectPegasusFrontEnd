package edu.ulatina.pegasus.controllers;

import at.favre.lib.crypto.bcrypt.*;
import edu.ulatina.pegasus.serviceTO.ServiceColaboratorTO;
import edu.ulatina.pegasus.serviceTO.ServicePersonalDataTO;
import edu.ulatina.pegasus.transfereObjects.ColaboratorTO;
import edu.ulatina.pegasus.transfereObjects.PersonalDataTO;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.*;
import jakarta.faces.context.*;
import jakarta.faces.application.*;
import jakarta.servlet.http.HttpServletRequest;

/*
 * @author Nwitlyck
 */
@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

    private String email;

    private String password;

    private ColaboratorTO logColaboratorTO;
    
    private PersonalDataTO logPersonalDataTO;
    
    public LoginController() {
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ColaboratorTO getLogColaboratorTO() {
        return logColaboratorTO;
    }

    public void setLogColaboratorTO(ColaboratorTO logColaboratorTO) {
        this.logColaboratorTO = logColaboratorTO;
    }

    public PersonalDataTO getLogPersonalDataTO() {
        return logPersonalDataTO;
    }

    public void setLogPersonalDataTO(PersonalDataTO logPersonalDataTO) {
        this.logPersonalDataTO = logPersonalDataTO;
    }

    private final int ACCESS_STATUS_DENIED = 1;

    private final int ACCESS_STATUS_APPROVED = 0;

    public void logIn() {

        if (!verifyNulls()) {
            boolean logInResult = true;

            if (!verifyAccess()) {
                return;
            }
            if (!verifyUser()) {
                logInResult = false;
            }
            try {
                new ServiceColaboratorTO().InsertAccessControlRecord(this.email, logInResult);
                if (logInResult) {
                    this.logColaboratorTO = new ServiceColaboratorTO().selectByEmail(this.email);
                    this.logPersonalDataTO = new ServicePersonalDataTO().selectByColaboratorId(this.logColaboratorTO.getId());
                    this.email = "";
                    this.password = "";

                    this.redirect("/faces/Main.xhtml");
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean verifyAccess() {
        ColaboratorTO colaboratorTO = null;
        int access = 0;
        try {
            access = new ServiceColaboratorTO().checkAccess(this.email);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Error connecting with data base"));
            e.printStackTrace();

        }
        if (access == ACCESS_STATUS_APPROVED) {
            return true;

        } else if (access == ACCESS_STATUS_DENIED) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Too many bad attempts"));
        }
        return false;
    }

    public boolean verifyNulls() {
        if (this.email.isEmpty() || this.email == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email is empty"));
            return true;
        }
        
        if (this.password.isEmpty() || this.password == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The password is empty"));
            return true;
        }
        return false;
    }

    public boolean verifyUser() {
        ColaboratorTO colaboratorTO = null;
        try {
            colaboratorTO = new ServiceColaboratorTO().selectByEmail(this.email);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Error connecting with data base"));
            e.printStackTrace();
            return false;
        }

        if (colaboratorTO == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email or password are incorrect"));
            return false;

        } else {
            BCrypt.Result result = BCrypt.verifyer().verify(this.password.toCharArray(), colaboratorTO.getPassword());
            if (result.verified){
                return true;

            } else {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email or password are incorrect"));
                return false;
            }
        }
    }

    public void redirect(String ruta) {
        HttpServletRequest request = null;
        try {

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public boolean isLog() {
        if (logColaboratorTO == null) {
            redirect("/faces/index.xhtml");
            return false;
        }
        return true;
    }

    public ColaboratorTO logColaborator() {

        if (!isLog()) {
            return this.logColaboratorTO;
        }

        return this.logColaboratorTO;
    }

    public boolean isManager() {

        if (!isLog()) {
            return false;
        }

        if (logColaboratorTO.getAcceslevel() == 5 || logColaboratorTO.getAcceslevel() == 6) {
            return true;
        }

        return false;
    }

    public boolean isAdmin() {
        if (!isLog()) {
            return false;
        }

        if (logColaboratorTO.getAcceslevel() == 6) {
            return true;
        }

        return false;
    }
 
}
