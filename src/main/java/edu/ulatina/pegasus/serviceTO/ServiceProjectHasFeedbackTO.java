
package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.ProjectHasFeedbackTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PegasusTeam
 */
public class ServiceProjectHasFeedbackTO extends Service implements ICrud<ProjectHasFeedbackTO> {

    @Override
    public void insert(ProjectHasFeedbackTO objectTO) throws Exception {
        
        PreparedStatement ps = null; 
        
        ps = getConnection().prepareStatement("INSERT INTO projects_has_feedbacks VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, objectTO.getProjectId());
        ps.setInt(2, objectTO.getFeedbackId());
        ps.setString(3, objectTO.getActionPlan());
        ps.setInt(4, objectTO.getActionPlanDone());
        ps.setInt(5, objectTO.getState());
        ps.executeUpdate();
        
        close(ps);
        close(conn);
    }

    @Override
    public void update(ProjectHasFeedbackTO objectTO) throws Exception {
        
        PreparedStatement ps = null;
        
        ps = getConnection().prepareStatement("UPDATE projects_has_feedbacks SET action_plan = ?, action_plane_done = ?, state = ? WHERE (projects_id = ? AND feedbacks_id = ?)");
        
        ps.setString(1, objectTO.getActionPlan());
        ps.setInt(2, objectTO.getActionPlanDone());
        ps.setInt(3, objectTO.getState());
        ps.setInt(4, objectTO.getProjectId());
        ps.setInt(5, objectTO.getFeedbackId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(ProjectHasFeedbackTO objectTO) throws Exception {
        
        PreparedStatement ps = null;
        
        ps = getConnection().prepareStatement("DELETE FROM projects_has_feedbacks WHERE (projects_id = ? AND feedbacks_id = ?)");

        ps.setInt(1, objectTO.getProjectId());
        ps.setInt(2, objectTO.getFeedbackId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<ProjectHasFeedbackTO> select() throws Exception {
         PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProjectHasFeedbackTO> objectTOList = new ArrayList<ProjectHasFeedbackTO>();
        
        ps = getConnection().prepareStatement("SELECT projects_id, feedbacks_id, action_plan, action_plane_done, state FROM projects_has_feedbacks WHERE state = 1");
        rs = ps.executeQuery();
        
        while (rs.next()) {
            int projectId= rs.getInt("projects_id");
            int FeedbackId = rs.getInt("feedbacks_id");
            String actionPlane = rs.getString("action_plan");
            int actionPlaneDone = rs.getInt("action_plane_done");
            int state = rs.getInt("state");
            
            objectTOList.add(new ProjectHasFeedbackTO(projectId, FeedbackId, actionPlane, actionPlaneDone, state));           
    }
        close( rs);
        close(ps);
        close(conn);

        return objectTOList; 
    }

    @Override
    public ProjectHasFeedbackTO selectByPk(ProjectHasFeedbackTO objectTO) throws Exception {
         PreparedStatement ps = null;
        ResultSet rs = null;
        ProjectHasFeedbackTO projectHasFeedbackTO = null;
    
        ps = getConnection().prepareStatement("SELECT projects_id, feedbacks_id, action_plan, action_plane_done, state FROM projects_has_feedbacks WHERE projects_id = ? AND feedbacks_id = ? AND state = 1");
        ps.setInt(1, objectTO.getProjectId());
        ps.setInt(2, objectTO.getFeedbackId());
        rs = ps.executeQuery();
        
        if (rs.next()){
            int projectId= rs.getInt("projects_id");
            int FeedbackId = rs.getInt("feedbacks_id");
            String actionPlane = rs.getString("action_plan");
            int actionPlaneDone = rs.getInt("action_plane_done");
            int state = rs.getInt("state");
            
            
            
            projectHasFeedbackTO  = new ProjectHasFeedbackTO(projectId, FeedbackId, actionPlane, actionPlaneDone, state);
                     
        }
        
        close(rs);
        close(ps);
        close(conn);

        return projectHasFeedbackTO;
    }
    
    public List<ProjectHasFeedbackTO> selectByProject(int byProject) throws Exception {
         PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProjectHasFeedbackTO> objectTOList = new ArrayList<ProjectHasFeedbackTO>();
    
        ps = getConnection().prepareStatement("SELECT projects_id, feedbacks_id, action_plan, action_plane_done, state FROM projects_has_feedbacks WHERE projects_id = ?");
        ps.setInt(1, byProject);
        rs = ps.executeQuery();
        
        while (rs.next()){
            int projectId= rs.getInt("projects_id");
            int FeedbackId = rs.getInt("feedbacks_id");
            String actionPlane = rs.getString("action_plan");
            int actionPlaneDone = rs.getInt("action_plane_done");
            int state = rs.getInt("state");
            
            
            
            objectTOList.add(new ProjectHasFeedbackTO(projectId, FeedbackId, actionPlane, actionPlaneDone, state));
                     
        }
        
        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
}
