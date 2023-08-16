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
 * @author isalozano
 */
@ManagedBean(name = "ProjectTimeManagementController")
public class ProjectTimeManagementController {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    private InvestedTimeTO investedTimeTO;

    private List<InvestedTimeTO> listInvestedTimeTO;

    private ServiceInvestedTime serviceInvestedTime;

    private int idDetailSelected;

    private Map<String, Integer> mapTask;
    
    

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

    @PostConstruct
    public void initianizate() {
        serviceInvestedTime = new ServiceInvestedTime();
        investedTimeTO = new InvestedTimeTO();
        fillList();
        fillMapTask();
    }

    public void fillList() {
        try {
            listInvestedTimeTO = serviceInvestedTime.selectById(1);//se escribe un 1  mientras  se crean mas proyectos
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

    public void save() {
        if (idDetailSelected == 0) {
            FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Null value", "Please pick a task"));
            return;//si no hay algo seleccionado se manda un mensaje de error
        }

        if (investedTimeTO.getInvestedITime() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Null value", "Please pick a time whitch is more than 0"));
            return;//si no hay un tiempo mayopr o igual a 0 se manda un mensaje de error
        }

        try {
            investedTimeTO.setColaboratorAndProjectId(1);//se escribe un 1  mientras  se crean mas proyectos
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
