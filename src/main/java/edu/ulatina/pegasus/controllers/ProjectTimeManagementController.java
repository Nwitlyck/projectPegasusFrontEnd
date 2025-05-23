package edu.ulatina.pegasus.controllers;

import edu.ulatina.pegasus.serviceTO.ServiceColaboratorHasProjectTO;
import edu.ulatina.pegasus.serviceTO.ServiceDetailTO;
import edu.ulatina.pegasus.serviceTO.ServiceInvestedTime;
import edu.ulatina.pegasus.serviceTO.ServiceProjectsTO;
import edu.ulatina.pegasus.transfereObjects.ColaboratorHasProjectTO;
import edu.ulatina.pegasus.transfereObjects.DetailTO;
import edu.ulatina.pegasus.transfereObjects.InvestedTimeTO;

import java.io.Serializable;
import java.util.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author isalozano
 */
@Named("ProjectTimeManagementController")
@ViewScoped
public class ProjectTimeManagementController implements Serializable {

    @Inject
    private LoginController loginController;

    private InvestedTimeTO investedTimeTO;

    private Map<String, Integer> mapProjects;

    private List<InvestedTimeTO> listInvestedTimeTO;

    private ServiceInvestedTime serviceInvestedTime;

    private int idDetailSelected;

    private Map<String, Integer> mapTask;

    private int idProject;

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public Map<String, Integer> getMapProjects() {
        return mapProjects;
    }

    public void setMapProjects(Map<String, Integer> mapProjects) {
        this.mapProjects = mapProjects;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public InvestedTimeTO getInvestedTimeTO() {
        return investedTimeTO;
    }

    public void setInvestedTimeTO(InvestedTimeTO investedTimeTO) {
        this.investedTimeTO = investedTimeTO;
    }

    public ServiceInvestedTime getServiceInvestedTime() {
        return serviceInvestedTime;
    }

    public void setServiceInvestedTime(ServiceInvestedTime serviceInvestedTime) {
        this.serviceInvestedTime = serviceInvestedTime;
    }

    public int getIdDetailSelected() {
        return idDetailSelected;
    }

    public void setIdDetailSelected(int idDetailSelected) {
        this.idDetailSelected = idDetailSelected;
    }

    public Map<String, Integer> getMapTask() {
        return mapTask;
    }

    public void setMapTask(Map<String, Integer> mapTask) {
        this.mapTask = mapTask;
    }

    public List<InvestedTimeTO> getListInvestedTimeTO() {
        return listInvestedTimeTO;
    }

    public void setListInvestedTimeTO(List<InvestedTimeTO> listInvestedTimeTO) {
        this.listInvestedTimeTO = listInvestedTimeTO;
    }

    public int getProjectHasColaborator() {
        ColaboratorHasProjectTO colaboratorHasProjectTO = new ColaboratorHasProjectTO();
        colaboratorHasProjectTO.setIdProject(idProject);
        colaboratorHasProjectTO.setIdColaborator(loginController.logColaborator().getId());

        try {
            return new ServiceColaboratorHasProjectTO().selectByPk(colaboratorHasProjectTO).getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @PostConstruct
    public void initianizate() {
        serviceInvestedTime = new ServiceInvestedTime();
        investedTimeTO = new InvestedTimeTO();
        fillListMapProject();
        fillList();
        fillMapTask();
    }

    public void fillList() {
        if (idProject == 0) {
            return;
        }

        try {
            listInvestedTimeTO = serviceInvestedTime.selectById(getProjectHasColaborator());//se llava auna funcion que obtiene el id colaborator has project
            // sale de datos colab_has_project en bd           
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to save data"));
            listInvestedTimeTO = new ArrayList<InvestedTimeTO>();
        }
    }

    public void fillMapTask() {
        int idMasterForTask = 4;

        try {
            mapTask = new ServiceDetailTO().selectByMasterId(idMasterForTask);

        } catch (Exception e) {
            mapTask = new HashMap<>();

        }
    }

    public void fillListMapProject() {
        try {
            mapProjects = new ServiceProjectsTO().selectUsingMapByIdColaborator(loginController.logColaborator().getId());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem with the connection unable to get data"));
            mapProjects = new HashMap<>();
        }
    }

    public void save() {

        if (idProject == 0) {
            return;
        }

        if (idDetailSelected == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "Please pick a task"));
            return;//si no hay algo seleccionado se manda un mensaje de error
        }

        if (investedTimeTO.getInvestedITime() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "Please pick a time whitch is more than 0"));
            return;//si no hay un tiempo mayopr o igual a 0 se manda un mensaje de error
        }

        try {
            investedTimeTO.setColaboratorAndProjectId(getProjectHasColaborator());//se escribe un 1  mientras  se crean mas proyectos
            // sale de datos colab_has_project en bd
            investedTimeTO.setTask(idDetailSelected);
            serviceInvestedTime.insert(investedTimeTO);

            PrimeFaces.current().executeInitScript("PF('manageTaskDialog').hide();");
            investedTimeTO = new InvestedTimeTO();

        } catch (Exception e) {
        }
        fillList();
    }

    public String nameByAcessLevel(int idDetail) {

        DetailTO detailTO = new DetailTO();
        detailTO.setId(idDetail);
        try {
            return new ServiceDetailTO().selectByPk(detailTO).getName();

        } catch (Exception e) {
            return "";
        }
    }

}
