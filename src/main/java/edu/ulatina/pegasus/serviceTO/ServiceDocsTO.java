package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.DocTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PegasusTeam
 */
public class ServiceDocsTO extends Service implements ICrud<DocTO> {

    @Override
    public void insert(DocTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO docs VALUES (null, ?, ?, ?)");
        ps.setInt(1, objectTO.getColaboratorId());
        ps.setInt(2, objectTO.getType());
        ps.setString(3, objectTO.getDocLocation());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(DocTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE docs SET calaborator_id = ?, type = ?, doclocation = ? WHERE (id = ?)");

        ps.setInt(1, objectTO.getColaboratorId());
        ps.setInt(2, objectTO.getType());
        ps.setString(3, objectTO.getDocLocation());
        ps.setInt(6, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(DocTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM docs WHERE (id = ?)");

        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<DocTO> select() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DocTO> objectTOList = new ArrayList<DocTO>();

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, type, doclocation FROM docs");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int id_colaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            String docLocation = rs.getString("doclocation");

            DocTO objectTO = new DocTO(id, id_colaborator, type, docLocation);

            objectTOList.add(objectTO);
        }
        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public DocTO selectByPk(DocTO objectTO) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        DocTO docsTO = null;

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, type, doclocation FROM docs WHERE id = ? ");
        ps.setInt(1, objectTO.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int id_colaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            String docLocation = rs.getString("doclocation");

            docsTO = new DocTO(id, id_colaborator, type, docLocation);

        }

        close(rs);
        close(ps);
        close(conn);

        return docsTO;
    }
    
    public List<DocTO> selectByColaboratorId(int byColaboratorId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DocTO> objectTOList = new ArrayList<DocTO>();

        ps = getConnection().prepareStatement("SELECT id, calaborator_id, type, doclocation FROM docs Where calaborator_id = ?");
        ps.setInt(1, byColaboratorId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int id_colaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            String docLocation = rs.getString("doclocation");

            DocTO objectTO = new DocTO(id, id_colaborator, type, docLocation);

            objectTOList.add(objectTO);
        }
        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
    public List<DocTO> selectByColaboratorManagerId(int byColaboratorManagerId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DocTO> objectTOList = new ArrayList<DocTO>();

        ps = getConnection().prepareStatement("SELECT d.id, d.calaborator_id, d.type, d.doclocation FROM docs d, colaborators c Where d.calaborator_id = c.id AND c.manager_id = ?;");
        ps.setInt(1, byColaboratorManagerId);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int id_colaborator = rs.getInt("calaborator_id");
            int type = rs.getInt("type");
            String docLocation = rs.getString("doclocation");

            DocTO objectTO = new DocTO(id, id_colaborator, type, docLocation);

            objectTOList.add(objectTO);
        }
        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
}
