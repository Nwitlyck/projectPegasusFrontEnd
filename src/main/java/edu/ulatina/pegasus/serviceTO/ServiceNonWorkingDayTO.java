package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.NonWorkingDayTO;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author PegasusTeam
 */
public class ServiceNonWorkingDayTO extends Service implements ICrud<NonWorkingDayTO> {

    @Override
    public void insert(NonWorkingDayTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO nonworkingdays VALUES (null, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, objectTO.getIdColaborator());
        ps.setInt(2, objectTO.getType());
        ps.setDate(3, objectTO.getInitialDate());
        ps.setDate(4, objectTO.getFinalDate());
        ps.setString(5, objectTO.getFeedback());
        ps.setInt(6, objectTO.getState());
        ps.setInt(7, objectTO.getReview());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(NonWorkingDayTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE nonworkingdays SET calaborator_id = ?, type = ?, initial_date = ?, final_date = ?, feedback = ?, state = ?, review = ?  WHERE (id = ?)");
        ps.setInt(1, objectTO.getIdColaborator());
        ps.setInt(2, objectTO.getType());
        ps.setDate(3, objectTO.getInitialDate());
        ps.setDate(4, objectTO.getFinalDate());
        ps.setString(5, objectTO.getFeedback());
        ps.setInt(6, objectTO.getState());
        ps.setInt(7, objectTO.getReview());
        ps.setInt(8, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(NonWorkingDayTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM nonworkingdays WHERE (id = ?)");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<NonWorkingDayTO> select() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<NonWorkingDayTO> objectTOList = new ArrayList<NonWorkingDayTO>();

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, type, initial_date, final_date, feedback, state, review FROM nonworkingdays");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idColaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            Date initialDate = rs.getDate("initial_date");
            Date finalDate = rs.getDate("final_date");
            String feedback = rs.getString("feedback");
            int state = rs.getInt("state");
            int review = rs.getInt("review");

            NonWorkingDayTO objectTO = new NonWorkingDayTO(id, type, idColaborator, initialDate, finalDate, feedback, state, review);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public NonWorkingDayTO selectByPk(NonWorkingDayTO objectTO) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        NonWorkingDayTO nonWorkingDayTO = null;

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, type, initial_date, final_date, feedback, state, review FROM nonworkingdays WHERE id = ?");
        ps.setInt(1, objectTO.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int idColaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            Date initialDate = rs.getDate("initial_date");
            Date finalDate = rs.getDate("final_date");
            String feedback = rs.getString("feedback");
            int state = rs.getInt("state");
            int review = rs.getInt("review");

            nonWorkingDayTO = new NonWorkingDayTO(id, type, idColaborator, initialDate, finalDate, feedback, state, review);

        }

        close(rs);
        close(ps);
        close(conn);

        return nonWorkingDayTO;
    }
    
    public List<NonWorkingDayTO> selectByColaboratorId(int byColaboratorId) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<NonWorkingDayTO> objectTOList = new ArrayList<NonWorkingDayTO>();

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, type, initial_date, final_date, feedback, state, review FROM nonworkingdays WHERE calaborator_id = ? ");
        ps.setInt(1, byColaboratorId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idColaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            Date initialDate = rs.getDate("initial_date");
            Date finalDate = rs.getDate("final_date");
            String feedback = rs.getString("feedback");
            int state = rs.getInt("state");
            int review = rs.getInt("review");

            NonWorkingDayTO objectTO = new NonWorkingDayTO(id, type, idColaborator, initialDate, finalDate, feedback, state, review);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
    public List<NonWorkingDayTO> selectByReview(int byRewiew) throws Exception {
 
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<NonWorkingDayTO> objectTOList = new ArrayList<NonWorkingDayTO>();

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, type, initial_date, final_date, feedback, state, review FROM nonworkingdays WHERE review = ?");
        ps.setInt(1, byRewiew);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idColaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            Date initialDate = rs.getDate("initial_date");
            Date finalDate = rs.getDate("final_date");
            String feedback = rs.getString("feedback");
            int state = rs.getInt("state");
            int review = rs.getInt("review");

            NonWorkingDayTO objectTO = new NonWorkingDayTO(id, type, idColaborator, initialDate, finalDate, feedback, state, review);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
    public List<NonWorkingDayTO> selectByReviewAndColaboratorManagerId(int byRewiew, int byColaboratorManagerId) throws Exception {
 
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<NonWorkingDayTO> objectTOList = new ArrayList<NonWorkingDayTO>();

        ps = getConnection().prepareStatement("SELECT n.id, n.calaborator_id, n.type, n.initial_date, n.final_date, n.feedback, n.state, n.review FROM nonworkingdays n, colaborators c WHERE c.id = n.calaborator_id AND n.review = ?  AND c.manager_id = ?");
        ps.setInt(1, byRewiew);
        ps.setInt(2, byColaboratorManagerId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idColaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            Date initialDate = rs.getDate("initial_date");
            Date finalDate = rs.getDate("final_date");
            String feedback = rs.getString("feedback");
            int state = rs.getInt("state");
            int review = rs.getInt("review");

            NonWorkingDayTO objectTO = new NonWorkingDayTO(id, type, idColaborator, initialDate, finalDate, feedback, state, review);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
}
