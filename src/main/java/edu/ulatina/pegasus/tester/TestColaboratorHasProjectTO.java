
package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.ColaboratorHasProjectTO;
import edu.ulatina.pegasus.serviceTO.ServiceColaboratorHasProjectTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestColaboratorHasProjectTO implements ITester<ColaboratorHasProjectTO> {

    @Override
    public List<ColaboratorHasProjectTO> testSelect() {
        
        List<ColaboratorHasProjectTO> objectTOList;
        
        try {
            
            objectTOList = new ServiceColaboratorHasProjectTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<ColaboratorHasProjectTO>();
        }
        
        return objectTOList;
    }

    @Override
    public ColaboratorHasProjectTO testSelectByPk(ColaboratorHasProjectTO objectTO) {
        
        try {
            
            return new ServiceColaboratorHasProjectTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ColaboratorHasProjectTO();
        }
    }

    @Override
    public void testInsert(ColaboratorHasProjectTO objectTO) {
        
        try {
            
            new ServiceColaboratorHasProjectTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(ColaboratorHasProjectTO objectTO) {
        
        try {
            
            new ServiceColaboratorHasProjectTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(ColaboratorHasProjectTO objectTO) {
        
        try {
            
            new ServiceColaboratorHasProjectTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
