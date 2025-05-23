package edu.ulatina.pegasus.controllers;

import edu.ulatina.pegasus.serviceTO.ServiceColaboratorHasProjectTO;
import edu.ulatina.pegasus.serviceTO.ServicePersonalDataTO;
import edu.ulatina.pegasus.serviceTO.ServiceProjectsTO;
import edu.ulatina.pegasus.transfereObjects.ColaboratorHasProjectTO;

import java.io.Serializable;
import java.util.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Nwitlyck
 */
@Named("ProjectMembersListController")
@ViewScoped
public class ProjectMembersListController implements Serializable {

    private Map<String, Integer> mapColaboratos;

    private Map<String, Integer> mapProjects;

    private ColaboratorHasProjectTO colaboratorHasProjectTO;

    private List<ColaboratorHasProjectTO> listColaboratorHasProjectTO;

    private ServiceColaboratorHasProjectTO serviceColaboratorHasProjectTO;

    public Map<String, Integer> getMapColaboratos() {
        return mapColaboratos;
    }

    public void setMapColaboratos(Map<String, Integer> mapColaboratos) {
        this.mapColaboratos = mapColaboratos;
    }

    public Map<String, Integer> getMapProjects() {
        return mapProjects;
    }

    public void setMapProjects(Map<String, Integer> mapProjects) {
        this.mapProjects = mapProjects;
    }

    public ColaboratorHasProjectTO getColaboratorHasProjectTO() {
        return colaboratorHasProjectTO;
    }

    public void setColaboratorHasProjectTO(ColaboratorHasProjectTO colaboratorHasProjectTO) {
        this.colaboratorHasProjectTO = colaboratorHasProjectTO;
    }

    public List<ColaboratorHasProjectTO> getListColaboratorHasProjectTO() {
        return listColaboratorHasProjectTO;
    }

    public void setListColaboratorHasProjectTO(List<ColaboratorHasProjectTO> listColaboratorHasProjectTO) {
        this.listColaboratorHasProjectTO = listColaboratorHasProjectTO;
    }

    public ServiceColaboratorHasProjectTO getServiceColaboratorHasProjectTO() {
        return serviceColaboratorHasProjectTO;
    }

    public void setServiceColaboratorHasProjectTO(ServiceColaboratorHasProjectTO serviceColaboratorHasProjectTO) {
        this.serviceColaboratorHasProjectTO = serviceColaboratorHasProjectTO;
    }

    //Special get/set
    public String getNameWithId(ColaboratorHasProjectTO chpto) {

        try {
            return new ServicePersonalDataTO().selectByColaboratorId(chpto.getIdColaborator()).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "not found";
        }
    }

    public String getStateForColaboratorHasProject(ColaboratorHasProjectTO chpto) {
        if (chpto.getState() == 1) {
            return "Active";
        }

        return "Unactive";
    }

    //metods
    @PostConstruct
    public void initianizate() {
        serviceColaboratorHasProjectTO = new ServiceColaboratorHasProjectTO();
        colaboratorHasProjectTO = new ColaboratorHasProjectTO();
        fillList();
        fillListMapColaboratos();
        fillListMapProject();
    }

    public void fillListMapProject() {
        try {
            mapProjects = new ServiceProjectsTO().selectUsingMap();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            mapProjects = new HashMap<>();
        }
    }

    public void fillListMapColaboratos() {
        try {
            mapColaboratos = new ServicePersonalDataTO().selectUsingMap();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            mapColaboratos = new HashMap<>();
        }
    }

    public void fillList() {

        if (colaboratorHasProjectTO.getIdProject() == 0) {
            listColaboratorHasProjectTO = new ArrayList<ColaboratorHasProjectTO>();
            return;
        }

        try {
            listColaboratorHasProjectTO = new ServiceColaboratorHasProjectTO().selectByProjectId(colaboratorHasProjectTO.getIdProject());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            listColaboratorHasProjectTO = new ArrayList<ColaboratorHasProjectTO>();
            e.printStackTrace();
        }
    }

    public void saveCoplaboratorHasProject() {

        if (colaboratorHasProjectTO.getIdColaborator() == 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Please pick a colaborator for the project"));
            return;
        }

        colaboratorHasProjectTO.setInitialDate(new java.sql.Date(System.currentTimeMillis()));
        colaboratorHasProjectTO.setState(1);

        try {
            new ServiceColaboratorHasProjectTO().insert(colaboratorHasProjectTO);
            PrimeFaces.current().executeInitScript("PF('manageProjectsMembersDialog').hide();");
            colaboratorHasProjectTO = new ColaboratorHasProjectTO();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to save data"));
            e.printStackTrace();
        }
        fillList();
    }

    public void enable() {
        if (colaboratorHasProjectTO.getState() == 1) {
            return;
        }

        colaboratorHasProjectTO.setState(1);
        colaboratorHasProjectTO.setFinalDate(null);

        try {
            serviceColaboratorHasProjectTO.update(colaboratorHasProjectTO);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The request could not be approved"));
        }
        fillList();
    }

    public void disable() {

        if (colaboratorHasProjectTO.getState() == 0) {
            return;
        }
        colaboratorHasProjectTO.setState(0);
        colaboratorHasProjectTO.setFinalDate(new java.sql.Date(System.currentTimeMillis()));

        try {
            serviceColaboratorHasProjectTO.update(colaboratorHasProjectTO);

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The request could not be approved"));
        }
        fillList();
    }
    
    public boolean boolOfState(ColaboratorHasProjectTO chpto) {
        if (chpto.getState() == 1) {
            return true;
        }

        return false;
    }

}
