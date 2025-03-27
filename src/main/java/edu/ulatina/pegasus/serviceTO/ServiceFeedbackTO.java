package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.FeedbackTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PegasusTeam
 */
public class ServiceFeedbackTO extends Service implements ICrud<FeedbackTO> {

    @Override
    public void insert(FeedbackTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO feedbacks VALUES (null, ?, ?, ?, ?)");
        ps.setInt(1, objectTO.getAuthor());
        ps.setString(2, objectTO.getFeedback());
        ps.setDate(3, objectTO.getDateOfFeedback());
        ps.setInt(4, objectTO.getState());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(FeedbackTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE feedbacks SET author = ?, feedback = ?, date_of_feedback = ?, state= ? WHERE (id = ?)");

        ps.setInt(1, objectTO.getAuthor());
        ps.setString(2, objectTO.getFeedback());
        ps.setDate(3, objectTO.getDateOfFeedback());
        ps.setInt(4, objectTO.getState());
        ps.setInt(5, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(FeedbackTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM feedbacks WHERE (id = ?)");

        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<FeedbackTO> select() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FeedbackTO> objectTOList = new ArrayList<FeedbackTO>();

        ps = getConnection().prepareStatement("SELECT id, author, feedback, date_of_feedback, state FROM feedbacks");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int author = rs.getInt("author");
            String feedback = rs.getString("feedback");
            Date dateOfFeedback = rs.getDate("date_of_feedback");
            int state = rs.getInt("state");

            FeedbackTO objectTO = new FeedbackTO(id, author, feedback, dateOfFeedback, state);

            objectTOList.add(objectTO);
        }
        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public FeedbackTO selectByPk(FeedbackTO objectTO) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        FeedbackTO feedbackTO = null;

        ps = getConnection().prepareStatement("SELECT id, author, feedback, date_of_feedback, state FROM feedbacks WHERE id = ?");
        ps.setInt(1, objectTO.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int author = rs.getInt("author");
            String feedback = rs.getString("feedback");
            Date dateOfFeedback = rs.getDate("date_of_feedback");
            int state = rs.getInt("state");

            feedbackTO = new FeedbackTO(id, author, feedback, dateOfFeedback, state);

        }

        close(rs);
        close(ps);
        close(conn);

        return feedbackTO;
    }

    
    public FeedbackTO selectByAll(FeedbackTO objectTO) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        FeedbackTO feedbackTO = null;

        ps = getConnection().prepareStatement("SELECT id, author, feedback, date_of_feedback, state FROM feedbacks WHERE author = ? AND feedback = ? AND date_of_feedback = ? AND state = ?");
        ps.setInt(1, objectTO.getAuthor());
        ps.setString(2, objectTO.getFeedback());
        ps.setDate(3, objectTO.getDateOfFeedback());
        ps.setInt(4, objectTO.getState());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int author = rs.getInt("author");
            String feedback = rs.getString("feedback");
            Date dateOfFeedback = rs.getDate("date_of_feedback");
            int state = rs.getInt("state");

            feedbackTO = new FeedbackTO(id, author, feedback, dateOfFeedback, state);

        }

        close(rs);
        close(ps);
        close(conn);

        return feedbackTO;
    }
}
