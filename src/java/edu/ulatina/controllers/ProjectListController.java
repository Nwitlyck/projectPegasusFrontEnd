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
public class ProjectListController {

    private ProjectsTO projectsTO;

    private List<ProjectsTO> listProjectsTO;

    private ServiceProjectsTO  serviceProjectsTO;


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
    
    public String getState(ProjectsTO pto){
        if (pto.getState()==1 && pto.getCompleted()==0)
            return "Active";
        if (pto.getState()==1 && pto.getCompleted()==1)
            return "Completed";
        
        return "Dropped";
        
        
    }
            
            
      @PostConstruct
    public void initianizate() {
        serviceProjectsTO = new ServiceProjectsTO();
        projectsTO = new ProjectsTO();
        fillList();
        
    }
    
    public void fillList() {
        try {
            listProjectsTO = serviceProjectsTO.select();//se escribe un 1  mientras  se crean mas proyectos
            // sale de datos colab_has_project en bd
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to save data"));
            listProjectsTO = new ArrayList<ProjectsTO>();
        }
    }
   
    public void save() {
        projectsTO.setInitialdate(new java.sql.Date(System.currentTimeMillis()));
        projectsTO.setState(1);

        try {
            serviceProjectsTO.insert(projectsTO);
            PrimeFaces.current().executeInitScript("PF('manageProjectsDialog').hide();");
            projectsTO = new ProjectsTO();


        } catch (Exception e) {
            e.printStackTrace();
        }
        fillList();
    }
    
   
}