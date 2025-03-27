package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.FeedbackTO;
import edu.ulatina.pegasus.serviceTO.ServiceFeedbackTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestFeedbackTO implements ITester<FeedbackTO> {

    @Override
    public List<FeedbackTO> testSelect() {
        
        List<FeedbackTO> objectTOList;
        
        try {
            
            objectTOList = new ServiceFeedbackTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<FeedbackTO>();
        }
        
        return objectTOList;
    }

    @Override
    public FeedbackTO testSelectByPk(FeedbackTO objectTO) {
        
        try {
            
            return new ServiceFeedbackTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new FeedbackTO();
        }
        
    }

    @Override
    public void testInsert(FeedbackTO objectTO) {
        
        try {
            
            new ServiceFeedbackTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(FeedbackTO objectTO) {
        
        try {
            
            new ServiceFeedbackTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(FeedbackTO objectTO) {
        
        try {
            
            new ServiceFeedbackTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
