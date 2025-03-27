
package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.MeetingHasColaboratorAndFeedbackTO;
import java.sql.*;
import java.util.*;
/**
 * @author PegasusTeam
 */
public class ServiceMeetingHasColaboratorAndFeedbackTO extends Service implements ICrud<MeetingHasColaboratorAndFeedbackTO>  {

    @Override
    public void insert(MeetingHasColaboratorAndFeedbackTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO meetings_has_colaborators_and_feedback VALUES (null, ?, ?, ?)");
        ps.setInt(1, objectTO.getColaboratorId());
        ps.setInt(2, objectTO.getFeedbackId());
        ps.setInt(3, objectTO.getState());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(MeetingHasColaboratorAndFeedbackTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE meetings_has_colaborators_and_feedback SET colaborator_id = ?, feedbacks_id = ?, state = ? WHERE meetings_id = ? ");

        ps.setInt(1, objectTO.getColaboratorId());
        ps.setInt(2, objectTO.getFeedbackId());
        ps.setInt(3, objectTO.getState());
        ps.setInt(4, objectTO.getMeetingId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(MeetingHasColaboratorAndFeedbackTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM meetings_has_colaborators_and_feedback WHERE meetings_id = ? ");
        ps.setInt(1, objectTO.getMeetingId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<MeetingHasColaboratorAndFeedbackTO> select() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MeetingHasColaboratorAndFeedbackTO> objectTOList = new ArrayList<MeetingHasColaboratorAndFeedbackTO>();

        ps = getConnection().prepareStatement("SELECT meetings_id, colaborator_id, feedbacks_id, state FROM meetings_has_colaborators_and_feedback");
        rs = ps.executeQuery();

        while (rs.next()) {
            int meetings_id = rs.getInt("meetings_id");
            int colaborator_id = rs.getInt("colaborator_id");
            int feedbacks_id = rs.getInt("feedbacks_id");
            int state = rs.getInt("state");

            MeetingHasColaboratorAndFeedbackTO objectTO = new MeetingHasColaboratorAndFeedbackTO(colaborator_id, meetings_id, feedbacks_id, state);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public MeetingHasColaboratorAndFeedbackTO selectByPk(MeetingHasColaboratorAndFeedbackTO objectTO) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        MeetingHasColaboratorAndFeedbackTO meetingHasColaboratorAndFeedbackTO = null;

        ps = getConnection().prepareStatement("SELECT meetings_id, colaborator_id, feedbacks_id, state FROM meetings_has_colaborators_and_feedback WHERE meetings_id");
        ps.setInt(1, objectTO.getMeetingId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int meetings_id = rs.getInt("meetings_id");
            int colaborator_id = rs.getInt("colaborator_id");
            int feedbacks_id = rs.getInt("feedbacks_id");
            int state = rs.getInt("state");

            meetingHasColaboratorAndFeedbackTO = new MeetingHasColaboratorAndFeedbackTO(colaborator_id, meetings_id, feedbacks_id, state);

        }

        close(rs);
        close(ps);
        close(conn);

        return meetingHasColaboratorAndFeedbackTO;
    }
    
    
    public List<MeetingHasColaboratorAndFeedbackTO> selectByColaboratorId(int byColaboratorId) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MeetingHasColaboratorAndFeedbackTO> objectTOList = new ArrayList<MeetingHasColaboratorAndFeedbackTO>();

        ps = getConnection().prepareStatement("SELECT meetings_id, colaborator_id, feedbacks_id, state FROM meetings_has_colaborators_and_feedback WHERE colaborator_id = ?");
        ps.setInt(1, byColaboratorId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int meetings_id = rs.getInt("meetings_id");
            int colaborator_id = rs.getInt("colaborator_id");
            int feedbacks_id = rs.getInt("feedbacks_id");
            int state = rs.getInt("state");

            MeetingHasColaboratorAndFeedbackTO objectTO = new MeetingHasColaboratorAndFeedbackTO(colaborator_id, meetings_id, feedbacks_id, state);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
    public List<MeetingHasColaboratorAndFeedbackTO> selectByAuthorId(int byAuthorId) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MeetingHasColaboratorAndFeedbackTO> objectTOList = new ArrayList<MeetingHasColaboratorAndFeedbackTO>();

        ps = getConnection().prepareStatement("SELECT mcf.meetings_id, mcf.colaborator_id, mcf.feedbacks_id, mcf.state FROM meetings_has_colaborators_and_feedback mcf, feedbacks f WHERE mcf.feedbacks_id = f.id AND f.author = ?");
        ps.setInt(1, byAuthorId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int meetings_id = rs.getInt("meetings_id");
            int colaborator_id = rs.getInt("colaborator_id");
            int feedbacks_id = rs.getInt("feedbacks_id");
            int state = rs.getInt("state");

            MeetingHasColaboratorAndFeedbackTO objectTO = new MeetingHasColaboratorAndFeedbackTO(colaborator_id, meetings_id, feedbacks_id, state);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
}
