package edu.ulatina.controllers;

import edu.ulatina.serviceTO.ServiceNonWorkingDayTO;
import edu.ulatina.transfereObjects.ColaboratorTO;
import edu.ulatina.serviceTO.ServiceColaboratorTO;
import edu.ulatina.transfereObjects.NonWorkingDayTO;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.application.*;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.PrimeFaces;

/*
 * @author Nwitlyck
 */
@ManagedBean(name = "daysOffController")
@SessionScoped
public class DaysOffController implements Serializable {

    private NonWorkingDayTO selectedNonWorkingDayTO;

    private ServiceNonWorkingDayTO serviceNonWorkingDayTO;

    private boolean newNonWorkingDay;

    private List<NonWorkingDayTO> listNonWorkingDayTO;

    public NonWorkingDayTO getSelectedNonWorkingDayTO() {
        return selectedNonWorkingDayTO;
    }

    public void setSelectedNonWorkingDayTO(NonWorkingDayTO selectedNonWorkingDayTO) {
        this.selectedNonWorkingDayTO = selectedNonWorkingDayTO;
    }

    public ServiceNonWorkingDayTO getServiceNonWorkingDayTO() {
        return serviceNonWorkingDayTO;
    }

    public void setServiceNonWorkingDayTO(ServiceNonWorkingDayTO serviceNonWorkingDayTO) {
        this.serviceNonWorkingDayTO = serviceNonWorkingDayTO;
    }

    public List<NonWorkingDayTO> getListNonWorkingDayTO() {
        return listNonWorkingDayTO;
    }

    public void setListNonWorkingDayTO(List<NonWorkingDayTO> listNonWorkingDayTO) {
        this.listNonWorkingDayTO = listNonWorkingDayTO;
    }

    public java.util.Date getCalendarInitialDate() {
        return (java.util.Date) this.selectedNonWorkingDayTO.getInitialDate();
    }

    public void setCalendarInitialDate(java.util.Date initialDate) {
        if (initialDate != null) {
            this.selectedNonWorkingDayTO.setInitialDate(new java.sql.Date(initialDate.getTime()));
        }
    }

    public java.util.Date getCalendarFinalDate() {
        return (java.util.Date) this.selectedNonWorkingDayTO.getFinalDate();
    }

    public void setCalendarFinalDate(java.util.Date finalDate) {
        if (finalDate != null) {
            this.selectedNonWorkingDayTO.setFinalDate(new java.sql.Date(finalDate.getTime()));
        }
    }

    public boolean isNewNonWorkingDay() {
        return newNonWorkingDay;
    }

    public void setNewNonWorkingDay(boolean newNonWorkingDay) {
        this.newNonWorkingDay = newNonWorkingDay;
    }

    @PostConstruct
    public void initialize() {
        serviceNonWorkingDayTO = new ServiceNonWorkingDayTO();
        selectedNonWorkingDayTO = new NonWorkingDayTO(0, 0, 0, null, null, 0, 0);
        fillListNonWorkingDayTO();

    }

    public void fillListNonWorkingDayTO() {
        try {
            listNonWorkingDayTO = serviceNonWorkingDayTO.select();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "There was a problem with the connection unable to get data"));
            listNonWorkingDayTO = new ArrayList<NonWorkingDayTO>();
        }
    }

    public void saveNonWorkingDayTOAsVacation() {
        if (!nullVerification() || !colaboratorExist() || !dateIsFuture()) {
            return;

        }
        selectedNonWorkingDayTO.setType(2);
        try {
            serviceNonWorkingDayTO.insert(selectedNonWorkingDayTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to add non working day data"));

        }

        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Done", "Vacation requested"));
        fillListNonWorkingDayTO();

    }

    public void saveNonWorkingDayTOAsTimeOff() {
        if (!nullVerification() || !colaboratorExist() || !dateIsFuture()) {
            return;

        }
        selectedNonWorkingDayTO.setType(3);
        try {
            serviceNonWorkingDayTO.insert(selectedNonWorkingDayTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to add non working day data"));
        }

        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Done", "Time off requested"));
        fillListNonWorkingDayTO();
    }

    public void updateNonWorkingDayTO() {

    }

    public void desableNonWorkingDayTO() {

    }

    public void deleteNonWorkingDayTO() {

    }

    public void openNew() {
        selectedNonWorkingDayTO = new NonWorkingDayTO(0, 0, 0, null, null, 0, 0);
        newNonWorkingDay = true;
    }

    public void closeNew() {
        newNonWorkingDay = false;
    }

    public boolean nullVerification() {

        if (selectedNonWorkingDayTO.getIdColaborator() == 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "The id colaborator is null"));
            return false;

        }

        if (selectedNonWorkingDayTO.getInitialDate() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "The initial date is null"));
            return false;

        }

        if (selectedNonWorkingDayTO.getFinalDate() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Null value", "The final date is null"));
            return false;

        }
        return true;
    }

    public boolean colaboratorExist() {

        try {
            if (new ServiceColaboratorTO().selectByPk(new ColaboratorTO(selectedNonWorkingDayTO.getIdColaborator(), 0, null, null, "", 0)) == null) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The colaborator wasnt found"));
                return false;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to search for a colaborator data"));
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean dateIsFuture() {
        if (selectedNonWorkingDayTO.getInitialDate().before(new Date(System.currentTimeMillis()))) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The initial date is in the past"));
            return false;

        }
        if (selectedNonWorkingDayTO.getFinalDate().before(selectedNonWorkingDayTO.getInitialDate())) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The final date is before initial date"));
            return false;

        }
        return true;
    }

}
