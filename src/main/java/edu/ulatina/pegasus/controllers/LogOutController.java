package edu.ulatina.pegasus.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

/*
 * @author Nwitlyck
 */
@Named("logOutController")
@SessionScoped
public class LogOutController implements Serializable {

    public void logOut() {
        HttpServletRequest request = null;
        try {

            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + "/faces/index.xhtml");
        } catch (Exception e) {

        }
    }

}
