/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;

/**
 *
 * @author isalozano
 */
public class InvestedTimeTO implements Serializable{
    
    private int id;
    private int colaboratorAndProjectId;
    private int task;
    private int investedITime;
    
    public InvestedTimeTO() {
    }
    

    public InvestedTimeTO(int id, int colaboratorAndProjectId, int task, int investedITime) {
        this.id = id;
        this.colaboratorAndProjectId = colaboratorAndProjectId;
        this.task = task;
        this.investedITime = investedITime;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColaboratorAndProjectId() {
        return colaboratorAndProjectId;
    }

    public void setColaboratorAndProjectId(int colaboratorAndProjectId) {
        this.colaboratorAndProjectId = colaboratorAndProjectId;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public int getInvestedITime() {
        return investedITime;
    }

    public void setInvestedITime(int investedITime) {
        this.investedITime = investedITime;
    }

}
