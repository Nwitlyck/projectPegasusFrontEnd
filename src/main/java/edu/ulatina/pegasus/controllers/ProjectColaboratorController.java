/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.pegasus.controllers;

import java.io.Serializable;
import java.util.List;

import edu.ulatina.pegasus.serviceTO.ServiceColaboratorHasProjectTO;
import edu.ulatina.pegasus.serviceTO.ServiceProjectsTO;
import edu.ulatina.pegasus.transfereObjects.ColaboratorHasProjectTO;
import edu.ulatina.pegasus.transfereObjects.ProjectsTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
@Named("ProjectColaboratorController")
@ViewScoped
public class ProjectColaboratorController implements Serializable {

    private List<ColaboratorHasProjectTO> listColaboratorHasProjects;

    @Inject
    private LoginController loginController;

    public List<ColaboratorHasProjectTO> getListColaboratorHasProjects() {
        return listColaboratorHasProjects;
    }

    public void setListColaboratorHasProjects(List<ColaboratorHasProjectTO> listColaboratorHasProjects) {
        this.listColaboratorHasProjects = listColaboratorHasProjects;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
    //get/set special

    public String getNameForId(ColaboratorHasProjectTO chpto) {

        ProjectsTO pTO = new ProjectsTO();
        pTO.setId(chpto.getIdProject());

        try {
            return new ServiceProjectsTO().selectByPk(pTO).getName();
        } catch (Exception e) {
        }

        return "Not found";
    }

    public String getStateForColaboratorHasProjectTO(ColaboratorHasProjectTO chpto) {
        if (chpto.getState() == 1) {
            return "Active";
        }

        return "Unactive";
    }

    //metods
    @PostConstruct
    public void initialize() {
        fillList();
    }

    public void fillList() {
        try {
            listColaboratorHasProjects = new ServiceColaboratorHasProjectTO().selectByColaboratorId(loginController.getLogColaboratorTO().getId());
        } catch (Exception e) {
            listColaboratorHasProjects = new ArrayList<>();
        }
    }

    public boolean canBeDisplay(ColaboratorHasProjectTO chpto) {

        if (chpto == null) {
            return false;
        }

        if (chpto.getState()== 1) {
            return true;
        }

        return false;
    }
}
