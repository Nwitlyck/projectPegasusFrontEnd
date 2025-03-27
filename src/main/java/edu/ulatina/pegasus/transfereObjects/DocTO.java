package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;

/**
 * @author PegasusTeam
 */
public class DocTO implements Serializable{

    private int id;
    private int colaboratorId;
    private int type;
    private String docLocation;

    public DocTO() {

    }

    public DocTO(int id, int colaboratorId, int type, String docLocation) {
        if (id != 0) {
            this.id = id;
        }
        this.colaboratorId = colaboratorId;
        this.type = type;
        this.docLocation = docLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColaboratorId() {
        return colaboratorId;
    }

    public void setColaboratorId(int colaboratorId) {
        this.colaboratorId = colaboratorId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDocLocation() {
        return docLocation;
    }

    public void setDocLocation(String docLocation) {
        this.docLocation = docLocation;
    }
    
}
