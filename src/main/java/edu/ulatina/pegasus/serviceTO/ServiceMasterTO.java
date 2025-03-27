package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.MasterTO;
import java.sql.*;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class ServiceMasterTO extends Service implements ICrud<MasterTO> {

    @Override
    public void insert(MasterTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO masters VALUES (null, ?, ?)");
        ps.setString(1, objectTO.getName());
        ps.setString(2, objectTO.getGeneralCode());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(MasterTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE masters SET name = ?, general_code = ? WHERE (id = ?)");
        ps.setString(1, objectTO.getName());
        ps.setString(2, objectTO.getGeneralCode());
        ps.setInt(3, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(MasterTO objectTO) throws Exception {

        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("DELETE FROM masters WHERE (id = ?)");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<MasterTO> select() throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MasterTO> objectTOList = new ArrayList<MasterTO>();

        ps = getConnection().prepareStatement("SELECT id, name, general_code FROM masters");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String generalCode = rs.getString("general_code");

            MasterTO objectTO = new MasterTO(id, name, generalCode);

            objectTOList.add(objectTO);
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public MasterTO selectByPk(MasterTO objectTO) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        MasterTO masterTO = null;

        ps = getConnection().prepareStatement("SELECT id, name, generalcode FROM masters WHERE id = ?");
        ps.setInt(1, objectTO.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String generalcode = rs.getString("generalcode");

            masterTO = new MasterTO(id, name, generalcode);

        }

        close(rs);
        close(ps);
        close(conn);

        return masterTO;
    }

}
