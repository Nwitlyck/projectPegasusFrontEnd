package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.ColaboratorTO;
import edu.ulatina.pegasus.serviceTO.ServiceColaboratorTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestColaboratorTO implements ITester<ColaboratorTO>{

    @Override
    public List<ColaboratorTO> testSelect() {
        
        List<ColaboratorTO> objectTOList;
        
        try {

            var x = new ServiceColaboratorTO().selectByEmail("felipe@admin.com");
            
            objectTOList = new ArrayList<>();
            objectTOList.add(x);

        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<ColaboratorTO>();
        }
        
        return objectTOList;
    }

    @Override
    public ColaboratorTO testSelectByPk(ColaboratorTO objectTO) {
        
        try {
            
            return new ServiceColaboratorTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ColaboratorTO();
        }
        
    }

    @Override
    public void testInsert(ColaboratorTO objectTO) {
        
        try {
            
            new ServiceColaboratorTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(ColaboratorTO objectTO) {
        
        try {
            
            new ServiceColaboratorTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(ColaboratorTO objectTO) {
        
        try {
            
            new ServiceColaboratorTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
