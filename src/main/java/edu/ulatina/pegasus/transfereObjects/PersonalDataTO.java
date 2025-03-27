package edu.ulatina.pegasus.transfereObjects;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author PegasusTeam
 */
public class PersonalDataTO implements Serializable{

    private int id;
    private int id_colaborator;
    private String name;
    private Date birthdate;
    private int emergencycontact;
    private int state;

    public PersonalDataTO() {
    }

    public PersonalDataTO(int id, int id_colaborator, String name, Date birthdate, int emergencycontact, int state) {
        if (id != 0) {
            this.id = id;
        }
        this.id_colaborator = id_colaborator;
        this.name = name;
        this.birthdate = birthdate;
        this.emergencycontact = emergencycontact;
        this.state = state;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getEmergencycontact() {
        return emergencycontact;
    }

    public void setEmergencycontact(int emergencycontact) {
        this.emergencycontact = emergencycontact;
    }

    public int getId_colaborator() {
        return id_colaborator;
    }

    public void setId_colaborator(int id_colaborator) {
        this.id_colaborator = id_colaborator;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
