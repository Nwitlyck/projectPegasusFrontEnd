
package edu.ulatina.pegasus.serviceTO;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.ColaboratorHasProjectTO;

/**
 * @author PegasusTeam
 */
public class ServiceColaboratorHasProjectTO extends Service implements ICrud <ColaboratorHasProjectTO> {

    @Override
    public void insert(ColaboratorHasProjectTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO colaboratosr_has_projects VALUES (null, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, objectTO.getIdColaborator());
        ps.setInt(2, objectTO.getIdProject());
        ps.setInt(3, objectTO.getTotalTime());
        ps.setDate(4, objectTO.getInitialDate());
        ps.setDate(5, objectTO.getFinalDate());
        ps.setInt(6, objectTO.getState());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(ColaboratorHasProjectTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE colaboratosr_has_projects SET id_colaborador = ?, id_project = ?, totalTime = ?, initial_date = ?, final_date = ? , state = ? WHERE (id = ?)");
        ps.setInt(1, objectTO.getIdColaborator());
        ps.setInt(2, objectTO.getIdProject());
        ps.setInt(3, objectTO.getTotalTime());
        ps.setDate(4, objectTO.getInitialDate());
        ps.setDate(5, objectTO.getFinalDate());
        ps.setInt(6, objectTO.getState());
        ps.setInt(7, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(ColaboratorHasProjectTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM colaboratosr_has_projects WHERE (id = ?)");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<ColaboratorHasProjectTO> select() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ColaboratorHasProjectTO> objectTOList = new ArrayList<ColaboratorHasProjectTO>();

        ps = getConnection().prepareStatement("SELECT id, id_colaborador, id_project, totalTime, initial_date, final_date, state FROM colaboratosr_has_projects WHERE state = 1");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idColab = rs.getInt("id_colaborador");
            int idProject = rs.getInt("id_project");
            int totalTime = rs.getInt("totalTime");
            Date initial_date = rs.getDate("initial_date");
            Date final_date = rs.getDate("final_date");
            int state = rs.getInt("state");
          
            ColaboratorHasProjectTO objectTO = new ColaboratorHasProjectTO(id, idColab, idProject, totalTime, initial_date, final_date, state);
            
            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public ColaboratorHasProjectTO selectByPk(ColaboratorHasProjectTO objectTO) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ColaboratorHasProjectTO colaboratorHasProjectTO = null;

        ps = getConnection().prepareStatement("SELECT id, id_colaborador, id_project, totalTime, initial_date, final_date, state FROM colaboratosr_has_projects WHERE id_colaborador = ? AND id_project = ? AND state = 1");
        ps.setInt(1, objectTO.getIdColaborator());
        ps.setInt(2, objectTO.getIdProject());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int idColab = rs.getInt("id_colaborador");
            int idProject = rs.getInt("id_project");
            int totalTime = rs.getInt("totalTime");
            Date initial_date = rs.getDate("initial_date");
            Date final_date = rs.getDate("final_date");
            int state = rs.getInt("state");

            colaboratorHasProjectTO = new ColaboratorHasProjectTO(id, idColab, idProject, totalTime, initial_date, final_date, state);
        }

        close(rs);
        close(ps);
        close(conn);

        return colaboratorHasProjectTO;
    }
    
    
    public List<ColaboratorHasProjectTO> selectByProjectId(int byProjectId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ColaboratorHasProjectTO> objectTOList = new ArrayList<ColaboratorHasProjectTO>();

        ps = getConnection().prepareStatement("SELECT id, id_colaborador, id_project, totalTime, initial_date, final_date, state FROM colaboratosr_has_projects WHERE id_project = ?");
        ps.setInt(1, byProjectId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idColab = rs.getInt("id_colaborador");
            int idProject = rs.getInt("id_project");
            int totalTime = rs.getInt("totalTime");
            Date initial_date = rs.getDate("initial_date");
            Date final_date = rs.getDate("final_date");
            int state = rs.getInt("state");
          
            ColaboratorHasProjectTO objectTO = new ColaboratorHasProjectTO(id, idColab, idProject, totalTime, initial_date, final_date, state);
            
            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
    public List<ColaboratorHasProjectTO> selectByColaboratorId(int byColaboratorId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ColaboratorHasProjectTO> objectTOList = new ArrayList<ColaboratorHasProjectTO>();

        ps = getConnection().prepareStatement("SELECT id, id_colaborador, id_project, totalTime, initial_date, final_date, state FROM colaboratosr_has_projects WHERE id_colaborador = ?");
        ps.setInt(1, byColaboratorId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idColab = rs.getInt("id_colaborador");
            int idProject = rs.getInt("id_project");
            int totalTime = rs.getInt("totalTime");
            Date initial_date = rs.getDate("initial_date");
            Date final_date = rs.getDate("final_date");
            int state = rs.getInt("state");
          
            ColaboratorHasProjectTO objectTO = new ColaboratorHasProjectTO(id, idColab, idProject, totalTime, initial_date, final_date, state);
            
            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
}
