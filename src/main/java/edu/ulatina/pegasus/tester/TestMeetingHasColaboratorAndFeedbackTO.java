package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.MeetingHasColaboratorAndFeedbackTO;
import edu.ulatina.pegasus.serviceTO.ServiceMeetingHasColaboratorAndFeedbackTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestMeetingHasColaboratorAndFeedbackTO implements ITester<MeetingHasColaboratorAndFeedbackTO> {

    @Override
    public List<MeetingHasColaboratorAndFeedbackTO> testSelect() {
        
        List<MeetingHasColaboratorAndFeedbackTO> objectTOList;
        
        try {
            
            objectTOList = new ServiceMeetingHasColaboratorAndFeedbackTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<MeetingHasColaboratorAndFeedbackTO>();
        }
        
        return objectTOList;
    }

    @Override
    public MeetingHasColaboratorAndFeedbackTO testSelectByPk(MeetingHasColaboratorAndFeedbackTO objectTO) {
        
        try {
            
            return new ServiceMeetingHasColaboratorAndFeedbackTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new MeetingHasColaboratorAndFeedbackTO();
        }
        
    }

    @Override
    public void testInsert(MeetingHasColaboratorAndFeedbackTO objectTO) {
        
        try {
            
            new ServiceMeetingHasColaboratorAndFeedbackTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(MeetingHasColaboratorAndFeedbackTO objectTO) {
        
        try {
            
            new ServiceMeetingHasColaboratorAndFeedbackTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(MeetingHasColaboratorAndFeedbackTO objectTO) {
        
        try {
            
            new ServiceMeetingHasColaboratorAndFeedbackTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
