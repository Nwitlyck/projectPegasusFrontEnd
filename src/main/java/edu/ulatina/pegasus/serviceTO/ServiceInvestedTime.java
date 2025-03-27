/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.pegasus.serviceTO;

import edu.ulatina.pegasus.interfaces.ICrud;
import edu.ulatina.pegasus.transfereObjects.InvestedTimeTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isalozano
 */
public class ServiceInvestedTime extends Service implements ICrud<InvestedTimeTO> {

    @Override
    public void insert(InvestedTimeTO objectTO) throws Exception {
        
        PreparedStatement ps = null; 
        
        ps = getConnection().prepareStatement("INSERT INTO investedtime VALUES (null, ?, ?, ?)");
        ps.setInt(1, objectTO.getColaboratorAndProjectId());
        ps.setInt(2, objectTO.getTask());
        ps.setInt(3, objectTO.getInvestedITime());
        ps.executeUpdate();
        
        close(ps);
        close(conn);
    }

    @Override
    public void update(InvestedTimeTO objectTO) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(InvestedTimeTO objectTO) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<InvestedTimeTO> select() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
       
    @Override
    public InvestedTimeTO selectByPk(InvestedTimeTO objectTO) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<InvestedTimeTO> selectById( int byColaboratorAndProjectId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InvestedTimeTO> objectTOList = new ArrayList<InvestedTimeTO>();
        
        ps = getConnection().prepareStatement("SELECT id, colaborator_and_project_id, task, times FROM investedtime WHERE colaborator_and_project_id = ?");
        ps.setInt(1, byColaboratorAndProjectId);
        rs = ps.executeQuery();
        
        while(rs.next()){
            int id = rs.getInt("id");
            int colaboratorAndProjectId = rs.getInt("colaborator_and_project_id");
            int task = rs.getInt("task");
            int investedTime  = rs.getInt("times");       
            
            objectTOList.add(new InvestedTimeTO(id, colaboratorAndProjectId, task, investedTime));
                       
    }
        close (rs);
        close(ps);
        close(conn);
        
        return objectTOList;
    }  
}
