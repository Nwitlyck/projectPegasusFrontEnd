package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.DetailTO;
import edu.ulatina.pegasus.serviceTO.ServiceDetailTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestDetailTO implements ITester<DetailTO> {

    @Override
    public List<DetailTO> testSelect() {
        
        List<DetailTO> objectTOList;
        
        try {
            
            objectTOList = new ServiceDetailTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<DetailTO>();
        }
        
        return objectTOList;
    }

    @Override
    public DetailTO testSelectByPk(DetailTO objectTO) {
        
        try {
            
            return new ServiceDetailTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new DetailTO();
        }
    }

    @Override
    public void testInsert(DetailTO objectTO) {
        
        try {
            
            new ServiceDetailTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(DetailTO objectTO) {
        
        try {
            
            new ServiceDetailTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(DetailTO objectTO) {
        
        try {
            
            new ServiceDetailTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
