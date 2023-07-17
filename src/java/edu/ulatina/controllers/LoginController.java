package edu.ulatina.controllers;

import edu.ulatina.serviceTO.*;
import edu.ulatina.transfereObjects.*;
import java.io.*;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.application.*;
import javax.servlet.http.HttpServletRequest;

/*
 * @author Nwitlyck
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private String email;

    private String password;

    private ColaboratorTO logColaboratorTO;

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
    
    public void logIn() {
        if (verifyNulls()) {
            return;
        }

        if (!verifyUser()) {
            return;
        }

        try {
            this.logColaboratorTO = new ServiceColaboratorTO().selectByEmail(this.email);
            this.email = "";
            this.password = "";

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.redirect("/faces/Main.xhtml");
    }

    public boolean verifyNulls() {
        if (this.email.isEmpty() || this.email == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email is empity"));
            return true;
        }
        
        if (this.password.isEmpty() || this.password == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The password is empity"));
            return true;
        }
        return false;
    }

    public boolean verifyUser() {
        ColaboratorTO colaboratorTO;
        try {
            colaboratorTO = new ServiceColaboratorTO().selectByEmail(this.email);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Error at the time to connect with data base"));
            return false;
        }

        if (colaboratorTO == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email or password are incorrect"));
            return false;
        }

        if (!colaboratorTO.getPassword().equals(this.password)) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The email or password are incorrect"));
            return false;
        }

        return true;
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

        if (logColaboratorTO.getAcceslevel() == 0) {
            return false;
        }

        return true;
    }

    public boolean isAdmin() {
        if (!isLog()) {
            return false;
        }

        if (logColaboratorTO.getAcceslevel() != 2) {
            return false;
        }

        return true;
    }
 
}
