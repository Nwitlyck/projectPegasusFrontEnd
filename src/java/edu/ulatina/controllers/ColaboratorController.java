package edu.ulatina.controllers;

import edu.ulatina.serviceTO.ServiceColaboratorTO;
import edu.ulatina.transfereObjects.ColaboratorTO;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.application.*;
import org.primefaces.PrimeFaces;

/*
 * @author Pegasus
 */
@ManagedBean(name = "colaboratorController")
@SessionScoped
public class ColaboratorController implements Serializable {

    private List<ColaboratorTO> listColaboratorTO;

    private ColaboratorTO selectedColaboratorTO;

    private ServiceColaboratorTO serviceColaboratorTO;

    private boolean newColaboratorTO;

    private String sizeListColaboratorTO;

    public List<ColaboratorTO> getListColaboratorTO() {
        return listColaboratorTO;
    }

    public void setListColaboratorTO(List<ColaboratorTO> listColaboratorTO) {
        this.listColaboratorTO = listColaboratorTO;
    }

    public ColaboratorTO getSelectedColaboratorTO() {
        return selectedColaboratorTO;
    }

    public void setSelectedColaboratorTO(ColaboratorTO selectedColaboratorTO) {
        this.selectedColaboratorTO = selectedColaboratorTO;
    }

    public ServiceColaboratorTO getServiceColaboratorTO() {
        return serviceColaboratorTO;
    }

    public void setServiceColaboratorTO(ServiceColaboratorTO serviceColaboratorTO) {
        this.serviceColaboratorTO = serviceColaboratorTO;
    }

    public boolean isNewColaboratorTO() {
        return newColaboratorTO;
    }

    public void setNewColaboratorTO(boolean newColaboratorTO) {
        this.newColaboratorTO = newColaboratorTO;
    }

    public String getSizeListColaboratorTO() {
        return sizeListColaboratorTO;
    }

    public void setSizeListColaboratorTO(String sizeListColaboratorTO) {
        this.sizeListColaboratorTO = sizeListColaboratorTO;
    }

    public java.util.Date getCalendarHireDate() {
        return (java.util.Date) this.selectedColaboratorTO.getHireDate();
    }

    public void setCalendarHireDate(java.util.Date hireDate) {
        this.selectedColaboratorTO.setHireDate(new java.sql.Date(hireDate.getTime()));
    }

    public java.util.Date getCalendarFireDate() {
        return (java.util.Date) this.selectedColaboratorTO.getFireDate();
    }

    public void setCalendarFireDate(java.util.Date fireDate) {
        if (fireDate != null) {
            this.selectedColaboratorTO.setFireDate(new java.sql.Date(fireDate.getTime()));
        }
    }

    //mettods
    @PostConstruct
    public void initianizate() {
        serviceColaboratorTO = new ServiceColaboratorTO();
        selectedColaboratorTO = new ColaboratorTO(0, 0, new java.sql.Date(System.currentTimeMillis()), null, "", 1);
        fillListColaboratorTO();
        sizeListColaboratorTO = listColaboratorTO.size() + "";
    }

    public void fillListColaboratorTO() {
        try {
            listColaboratorTO = serviceColaboratorTO.select();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to get data"));
            listColaboratorTO = new ArrayList<ColaboratorTO>();
        }
    }

    public void saveColaboratorTO() {
        if(selectedColaboratorTONotNullOrEmipy()||selectedColaboratorDateIsFuture()){
            return;
        }
        
        try {
            serviceColaboratorTO.insert(this.selectedColaboratorTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to add colaborator data"));
        }
        initianizate();
    }

    public void updateColaboratorTO() {
        
        if(selectedColaboratorTONotNullOrEmipy()||selectedColaboratorDateIsFuture()){
            return;
        }

        try {
            serviceColaboratorTO.update(selectedColaboratorTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to update calaborator data"));
        }
        initianizate();
    }

    public void desableColaboratorTO() {
        selectedColaboratorTO.setState(0);
        try {
            serviceColaboratorTO.update(selectedColaboratorTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to desable calaborator data"));
        }
        initianizate();
    }
    
    public void deleteColaboratorTO(){
        try {
            serviceColaboratorTO.delete(selectedColaboratorTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to delete calaborator data"));
        }
        initianizate();
    }

    public void openNew() {
        selectedColaboratorTO = new ColaboratorTO(0, 0, new java.sql.Date(System.currentTimeMillis()), null, "", 1);
        newColaboratorTO = true;
    }

    public void closeNew() {
        newColaboratorTO = false;
    }

    public boolean selectedColaboratorTONotNullOrEmipy() {
        if (selectedColaboratorTO == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null", "The Colaborator is null"));
            return true;
        }
        if (selectedColaboratorTO.getAcceslevel() < 0 || selectedColaboratorTO.getAcceslevel() > 2) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator acesslevel is not on range"));
            return true;
        }
        if (selectedColaboratorTO.getHireDate() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator hire date is not fill"));
            return true;
        }
        if (selectedColaboratorTO.getPassword().isEmpty() || selectedColaboratorTO.getPassword() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator password is not fill"));
            return true;
        }

        return false;
    }

    public boolean selectedColaboratorDateIsFuture() {

        if (selectedColaboratorTO.getHireDate().after(new Date(System.currentTimeMillis()))) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Null value", "The Colaborator hire date in the future!");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return true;
        }

        return false;
    }

}

//
