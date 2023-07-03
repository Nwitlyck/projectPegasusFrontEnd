
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

        this.redirect("/faces/Main.xhtml");
    }

    public boolean isIdEmpity() {
        if (this.id== 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "The id is empity or it's 0"));
            return true;
        }
        return false;
    }

    public boolean isPasswordEmpity() {
        if (this.password.isEmpty() || this.password== null) {
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
}
