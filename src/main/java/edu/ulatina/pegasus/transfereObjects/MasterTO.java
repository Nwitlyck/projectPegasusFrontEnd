package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;

/**
 * @author PegasusTeam
 */
public class MasterTO implements Serializable{

    private int id;
    private String name;
    private String generalCode;

    public MasterTO() {
    }

    public MasterTO(int id, String name, String generalCode) {
        if (id != 0) {
            this.id = id;
        }
        this.name = name;
        this.generalCode = generalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneralCode() {
        return generalCode;
    }

    public void setGeneralCode(String generalCode) {
        this.generalCode = generalCode;
    }

}
