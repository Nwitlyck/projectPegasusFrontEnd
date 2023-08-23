/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.controllers;

import java.util.List;
import javax.faces.bean.ManagedBean;
import edu.ulatina.transfereObjects.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import edu.ulatina.serviceTO.*;
import java.util.ArrayList;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "ProjectColaboratorController")
@ViewScoped
public class ProjectColaboratorController {

    private List<ColaboratorHasProjectTO> listColaboratorHasProjects;

    @ManagedProperty(value = "#{loginController}")
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
