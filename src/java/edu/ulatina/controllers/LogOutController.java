package edu.ulatina.controllers;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/*
 * @author Nwitlyck
 */
@ManagedBean(name = "logOutController")
@SessionScoped
public class LogOutController {

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
