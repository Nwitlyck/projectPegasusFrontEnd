package edu.ulatina.controllers;

import javax.faces.bean.*;
import edu.ulatina.serviceTO.*;
import edu.ulatina.transfereObjects.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;

/**
 *
 * @author esterojas
 */
@ManagedBean(name = "ProjectListController")
@SessionScoped
public class ProjectListController {

    private ProjectsTO projectsTO;

    private List<ProjectsTO> listProjectsTO;

    private List<ColaboratorHasProjectTO> listColaboratorHasProjectTO;

    private ServiceProjectsTO serviceProjectsTO;

    private Map<String, Integer> mapColaboratos;

    private ColaboratorHasProjectTO colaboratorHasProjectTO;
    
    private ProjectsTO slectedPTO;

    public ProjectsTO getProjectsTO() {
        return projectsTO;
    }

    public void setProjectsTO(ProjectsTO projectsTO) {
        this.projectsTO = projectsTO;
    }

    public List<ProjectsTO> getListProjectsTO() {
        return listProjectsTO;
    }

    public void setListProjectsTO(List<ProjectsTO> listProjectsTO) {
        this.listProjectsTO = listProjectsTO;
    }

    public ServiceProjectsTO getServiceProjectsTO() {
        return serviceProjectsTO;
    }

    public void setServiceProjectsTO(ServiceProjectsTO serviceProjectsTO) {
        this.serviceProjectsTO = serviceProjectsTO;
    }

    public List<ColaboratorHasProjectTO> getListColaboratorHasProjectTO() {
        return listColaboratorHasProjectTO;
    }

    public void setListColaboratorHasProjectTO(List<ColaboratorHasProjectTO> listColaboratorHasProjectTO) {
        this.listColaboratorHasProjectTO = listColaboratorHasProjectTO;
    }

    public Map<String, Integer> getMapColaboratos() {
        return mapColaboratos;
    }

    public void setMapColaboratos(Map<String, Integer> mapColaboratos) {
        this.mapColaboratos = mapColaboratos;
    }

    public ColaboratorHasProjectTO getColaboratorHasProjectTO() {
        return colaboratorHasProjectTO;
    }

    public void setColaboratorHasProjectTO(ColaboratorHasProjectTO colaboratorHasProjectTO) {
        this.colaboratorHasProjectTO = colaboratorHasProjectTO;
    }

    //special get/set metods
    public java.util.Date getCalendarInitialDate() {
        return (java.util.Date) this.projectsTO.getInitialdate();
    }

    public void setCalendarInitialDate(java.util.Date initialDate) {
        if (initialDate != null) {
            this.projectsTO.setInitialdate(new java.sql.Date(initialDate.getTime()));
        }
    }

    public java.util.Date getCalendarFinallDate() {
        return (java.util.Date) this.projectsTO.getFinaldate();
    }

    public void setCalendarFinalDate(java.util.Date finalDate) {
        if (finalDate != null) {
            this.projectsTO.setFinaldate(new java.sql.Date(finalDate.getTime()));
        }
    }

    public String getStateForProject(ProjectsTO pto) {
        if (pto.getState() == 1 && pto.getCompleted() == 0) {
            return "Active";
        }
        if (pto.getState() == 1 && pto.getCompleted() == 1) {
            return "Completed";
        }

        return "Dropped";
    }

    public String getNameWithId(ColaboratorHasProjectTO chpto) {

        try {
            return new ServicePersonalDataTO().selectByColaboratorId(chpto.getId()).getName();
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
        serviceProjectsTO = new ServiceProjectsTO();
        colaboratorHasProjectTO = new ColaboratorHasProjectTO();
        projectsTO = new ProjectsTO();
        fillList();
        fillListMapColaboratos();
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
        try {
            listProjectsTO = serviceProjectsTO.select();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            listProjectsTO = new ArrayList<ProjectsTO>();
        }
    }

    public void fillListOfMemebers(ProjectsTO pTO) {
        
        slectedPTO = pTO;

        try {
            listColaboratorHasProjectTO = new ServiceColaboratorHasProjectTO().selectByProjectId(slectedPTO.getId());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            listProjectsTO = new ArrayList<ProjectsTO>();
            e.printStackTrace();
        }
    }

    public void save() {

        if (projectsTO.getName().isEmpty() || projectsTO.getName() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Please write a name for the project"));
            return;
        }

        projectsTO.setInitialdate(new java.sql.Date(System.currentTimeMillis()));
        projectsTO.setState(1);

        try {
            serviceProjectsTO.insert(projectsTO);
            PrimeFaces.current().executeInitScript("PF('manageProjectsDialog').hide();");
            projectsTO = new ProjectsTO();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to save data"));
            e.printStackTrace();
        }
        fillList();
    }

    public void saveCoplaboratorHasProject() {

        if (colaboratorHasProjectTO.getIdColaborator() == 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Please pick a colaborator for the project"));
            return;
        }

        colaboratorHasProjectTO.setInitialDate(new java.sql.Date(System.currentTimeMillis()));
        colaboratorHasProjectTO.setState(1);
        colaboratorHasProjectTO.setIdProject(slectedPTO.getId());

        try {
            new ServiceColaboratorHasProjectTO().insert(colaboratorHasProjectTO);
            PrimeFaces.current().executeInitScript("PF('manageProjectsMembersDialog').hide();");
            colaboratorHasProjectTO = new ColaboratorHasProjectTO();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to save data"));
            e.printStackTrace();
        }
        fillListOfMemebers(slectedPTO);
    }

}
