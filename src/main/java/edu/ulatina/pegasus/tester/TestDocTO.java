package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.DocTO;
import edu.ulatina.pegasus.serviceTO.ServiceDocsTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestDocTO implements ITester<DocTO> {

    @Override
    public List<DocTO> testSelect() {
        
        List<DocTO> objectTOList;
        
        try {
            
            objectTOList = new ServiceDocsTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<DocTO>();
        }
        
        return objectTOList;
    }

    @Override
    public DocTO testSelectByPk(DocTO objectTO) {
        
        try {
            
            return new ServiceDocsTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new DocTO();
        }
        
    }

    @Override
    public void testInsert(DocTO objectTO) {
        
        try {
            
            new ServiceDocsTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(DocTO objectTO) {
        
        try {
            
            new ServiceDocsTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(DocTO objectTO) {
        
        try {
            
            new ServiceDocsTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
