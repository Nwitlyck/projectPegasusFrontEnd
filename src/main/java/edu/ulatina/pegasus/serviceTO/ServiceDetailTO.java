package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.DetailTO;
import java.sql.*;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class ServiceDetailTO extends Service implements ICrud<DetailTO> {

    @Override
    public void insert(DetailTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO details VALUES (null, ?, ?, ?)");
        ps.setInt(1, objectTO.getIdMaster());
        ps.setString(2, objectTO.getName());
        ps.setString(3, objectTO.getGeneralCode());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(DetailTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE details SET name = ?, general_code = ?, masteres_id = ? WHERE (id = ?)");
        ps.setString(1, objectTO.getName());
        ps.setString(2, objectTO.getGeneralCode());
        ps.setInt(3, objectTO.getIdMaster());
        ps.setInt(4, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(DetailTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM details WHERE (id = ?)");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<DetailTO> select() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DetailTO> objectTOList = new ArrayList<DetailTO>();

        ps = getConnection().prepareStatement("SELECT id, masteres_id, name, general_code FROM details");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int idMaster = rs.getInt("masteres_id");
            String name = rs.getString("name");
            String generalcode = rs.getString("general_code");

            DetailTO objectTO = new DetailTO(id, name, generalcode, idMaster);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public DetailTO selectByPk(DetailTO objectTO) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        DetailTO detailTO = null;

        ps = getConnection().prepareStatement("SELECT id, masteres_id, name, general_code FROM details WHERE id = ?");
        ps.setInt(1, objectTO.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            int idMaster = rs.getInt("masteres_id");
            String name = rs.getString("name");
            String generalcode = rs.getString("general_code");


            detailTO = new DetailTO(id, name, generalcode, idMaster);

        } else {
            detailTO = new DetailTO();
        }

        close(rs);
        close(ps);
        close(conn);

        return detailTO;
    }
    
    public Map<String, Integer> selectByMasterId(int byMasterId) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Integer> map = new HashMap<>();

        ps = getConnection().prepareStatement("SELECT id, name FROM details WHERE masteres_id = ?");
        ps.setInt(1, byMasterId);
        rs = ps.executeQuery();

        while(rs.next()) {
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
