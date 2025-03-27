package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.ProjectsTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PegasusTeam
 */
public class ServiceProjectsTO extends Service implements ICrud<ProjectsTO> {

    @Override
    public void insert(ProjectsTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO projects VALUES (null, ?, ?, ?, ?, ?)");
        ps.setString(1, objectTO.getName());
        ps.setDate(2, objectTO.getInitialdate());
        ps.setDate(3, objectTO.getFinaldate());
        ps.setInt(4, objectTO.getState());
        ps.setInt(5, objectTO.getCompleted());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    @Override
    public void update(ProjectsTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE projects SET name = ?, initial_date = ?, final_date = ?, state = ?, completed = ? WHERE (id = ?)");

        ps.setString(1, objectTO.getName());
        ps.setDate(2, objectTO.getInitialdate());
        ps.setDate(3, objectTO.getFinaldate());
        ps.setInt(4, objectTO.getState());
        ps.setInt(5, objectTO.getCompleted());
        ps.setInt(6, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    @Override
    public void delete(ProjectsTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM projects WHERE (id = ?)");

        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    @Override
    public List<ProjectsTO> select() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProjectsTO> objectTOList = new ArrayList<ProjectsTO>();

        ps = getConnection().prepareStatement("SELECT id, name, initial_date, final_date, state, completed FROM projects");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Date initialdate = rs.getDate("initial_date");
            Date finaldate = rs.getDate("final_date");
            int state = rs.getInt("state");
            int complete = rs.getInt("completed");

            ProjectsTO objectTO = new ProjectsTO(id, name, initialdate, finaldate, state, complete);

            objectTOList.add(objectTO);
        }
        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public ProjectsTO selectByPk(ProjectsTO objectTO) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProjectsTO projectTO = null;

        ps = getConnection().prepareStatement("SELECT id, name, initial_date, final_date, state, completed FROM projects WHERE id = ? AND state = 1");
        ps.setInt(1, objectTO.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Date initialdate = rs.getDate("initial_date");
            Date finaldate = rs.getDate("final_date");
            int state = rs.getInt("state");
            int complete = rs.getInt("completed");

            projectTO = new ProjectsTO(id, name, initialdate, finaldate, state, complete);

        }

        close(rs);
        close(ps);
        close(conn);

        return projectTO;
    }
    
    public Map<String, Integer> selectUsingMap() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Integer> map = new HashMap<>();

        ps = getConnection().prepareStatement("SELECT id, name FROM projects");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            map.put(name, id);
        }

        close(rs);
        close(ps);
        close(conn);

        return map;
    }
    
    public Map<String, Integer> selectUsingMapByIdColaborator(int byIdColaborator) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Integer> map = new HashMap<>();

        ps = getConnection().prepareStatement("SELECT p.id, p.name FROM projects p, colaboratosr_has_projects chp WHERE p.id = chp.id_project AND id_colaborador = ?");
        ps.setInt(1, byIdColaborator);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            map.put(name, id);
        }

        close(rs);
        close(ps);
        close(conn);

        return map;
    }

}
