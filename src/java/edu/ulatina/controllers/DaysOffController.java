package edu.ulatina.controllers;

import edu.ulatina.serviceTO.ServiceNonWorkingDayTO;
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

    }

    public void saveNonWorkingDayTOAsVacation() {
        if (!nullVerification() || !dateIsFuture()) {
            return;

        }
        selectedNonWorkingDayTO.setType(2);
        try {
            serviceNonWorkingDayTO.insert(selectedNonWorkingDayTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to add non working day data"));
            e.printStackTrace();
        }

    }

    public void saveNonWorkingDayTOAsTimeOff() {
        if (!nullVerification() || !dateIsFuture()) {
            return;

        }
        selectedNonWorkingDayTO.setType(3);
        try {
            serviceNonWorkingDayTO.insert(selectedNonWorkingDayTO);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There was a problem with the connection unable to add non working day data"));
        }

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
