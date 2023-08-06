/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.controllers;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import edu.ulatina.transfereObjects.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import edu.ulatina.serviceTO.*;
import java.util.ArrayList;
/**
 *
 * @author Usuario
 */
@ManagedBean(name = "ProjectColaboratorController")
@SessionScoped
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
    
    @PostConstruct
    public void initialize(){
        fillList();
    }
    
    public void fillList(){
        try {
            listColaboratorHasProjects = new ServiceColaboratorHasProjectTO().selectByColaboratorId(loginController.getLogColaboratorTO().getId());
        } catch (Exception e) {
            listColaboratorHasProjects = new ArrayList<>();
        }
    }
    
    
}
