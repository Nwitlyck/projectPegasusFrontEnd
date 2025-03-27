package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.PersonalDataTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PegasusTeam
 */
public class ServicePersonalDataTO extends Service implements ICrud<PersonalDataTO> {

    @Override
    public void insert(PersonalDataTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO personaldata VALUES (null, ?, ?, ?, ?, ?)");
        ps.setInt(1, objectTO.getId_colaborator());
        ps.setString(2, objectTO.getName());
        ps.setDate(3, objectTO.getBirthdate());
        ps.setInt(4, objectTO.getEmergencycontact());
        ps.setInt(5, objectTO.getState());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(PersonalDataTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE personaldata SET calaborator_id = ?, name = ?, birthdate = ?, emergency_contact = ?, state = ? WHERE (id = ?)");

        ps.setInt(1, objectTO.getId_colaborator());
        ps.setString(2, objectTO.getName());
        ps.setDate(3, objectTO.getBirthdate());
        ps.setInt(4, objectTO.getEmergencycontact());
        ps.setInt(5, objectTO.getState());
        ps.setInt(6, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(PersonalDataTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM personaldata WHERE (id = ?)");

        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<PersonalDataTO> select() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PersonalDataTO> objectTOList = new ArrayList<PersonalDataTO>();

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, name, birthdate, emergency_contact, state FROM personaldata WHERE state = 1");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int calaboratorId = rs.getInt("calaborator_id");
            String name = rs.getString("name");
            Date birthdate = rs.getDate("birthdate");
            int emergencyContact = rs.getInt("emergency_contact");
            int state = rs.getInt("state");

            PersonalDataTO objectTO = new PersonalDataTO(id, calaboratorId, name, birthdate, emergencyContact, state);

            objectTOList.add(objectTO);
        }
        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public PersonalDataTO selectByPk(PersonalDataTO objectTO) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PersonalDataTO personalDataTO = null;

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, name, birthdate, emergency_contact, state FROM personaldata WHERE id = ? AND state = 1");
        ps.setInt(1, objectTO.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int calaboratorId = rs.getInt("calaborator_id");
            String name = rs.getString("name");
            Date birthdate = rs.getDate("birthdate");
            int emergencyContact = rs.getInt("emergency_contact");
            int state = rs.getInt("state");

            personalDataTO = new PersonalDataTO(id, calaboratorId, name, birthdate, emergencyContact, state);

        }

        close(rs);
        close(ps);
        close(conn);

        return personalDataTO;
    }

    public PersonalDataTO selectByColaboratorId(int byColaboratorId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        PersonalDataTO personalDataTO = null;

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, name, birthdate, emergency_contact, state FROM personaldata WHERE calaborator_id = ?");
        ps.setInt(1, byColaboratorId);
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int calaboratorId = rs.getInt("calaborator_id");
            String name = rs.getString("name");
            Date birthdate = rs.getDate("birthdate");
            int emergencyContact = rs.getInt("emergency_contact");
            int state = rs.getInt("state");

            personalDataTO = new PersonalDataTO(id, calaboratorId, name, birthdate, emergencyContact, state);

        }

        close(rs);
        close(ps);
        close(conn);

        return personalDataTO;
    }

    public Map<String, Integer> selectUsingMap() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Integer> map = new HashMap<>();

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, name, birthdate, emergency_contact, state FROM personaldata WHERE state = 1");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("calaborator_id");
            String name = rs.getString("name");

            map.put(name, id);
        }

        close(rs);
        close(ps);
        close(conn);

        return map;
    }

}
