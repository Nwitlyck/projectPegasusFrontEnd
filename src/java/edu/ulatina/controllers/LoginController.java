package edu.ulatina.controllers;

import edu.ulatina.serviceTO.ServiceColaboratorTO;
import edu.ulatina.transfereObjects.ColaboratorTO;
import java.io.Serializable;
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

    private int id;

    private String password;

    private ColaboratorTO logColaboratorTO; 

    public LoginController() {
    }

    public LoginController(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    //metods
    public void logIn() {
        if (isIdEmpity()) {
            return;
        }

        if (isPasswordEmpity()) {
            return;
        }

        if (!verifyUser()) {
            return;
        }
        
        try {
            this.logColaboratorTO = new ServiceColaboratorTO().selectByPk(new ColaboratorTO(id, 0, null, null, "", 0));

        } catch (Exception e) {
        }

        this.redirect("/faces/Main.xhtml");
    }

    public boolean isIdEmpity() {
        if (this.id == 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The id is empity or it's 0"));
            return true;
        }
        return false;
    }

    public boolean isPasswordEmpity() {
        if (this.password.isEmpty() || this.password == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The password is empity"));
            return true;
        }
        return false;
    }

    public boolean verifyUser() {
        ColaboratorTO colaboratorTO;
        try {
            colaboratorTO = new ServiceColaboratorTO().selectByPk(new ColaboratorTO(id, 0, null, null, "", 0));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Error at the time to connect with data base"));
            e.printStackTrace();
            return false;
        }

        if (colaboratorTO == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The id or password aren't correct"));
            return false;
        }

        if (!colaboratorTO.getPassword().equals(this.password)) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The id or password aren't correct"));
            return false;
        }

        return true;
    }

    public void redirect(String ruta) {
        HttpServletRequest request = null;
        try {

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            request.setAttribute("idLog", id);
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
