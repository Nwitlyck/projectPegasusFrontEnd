package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.PersonalDataTO;
import edu.ulatina.pegasus.serviceTO.ServicePersonalDataTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestPersonalDataTO implements ITester<PersonalDataTO> {

    @Override
    public List<PersonalDataTO> testSelect() {
        
        List<PersonalDataTO> objectTOList;
        
        try {
            
            objectTOList = new ServicePersonalDataTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<PersonalDataTO>();
        }
        
        return objectTOList;
    }

    @Override
    public PersonalDataTO testSelectByPk(PersonalDataTO objectTO) {
        
        try {
            
            return new ServicePersonalDataTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new PersonalDataTO();
        }
        
    }

    @Override
    public void testInsert(PersonalDataTO objectTO) {
        
        try {
            
            new ServicePersonalDataTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(PersonalDataTO objectTO) {
        
        try {
            
            new ServicePersonalDataTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(PersonalDataTO objectTO) {
        
        try {
            
            new ServicePersonalDataTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
}
