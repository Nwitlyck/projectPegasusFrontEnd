package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.ProjectHasFeedbackTO;
import edu.ulatina.pegasus.serviceTO.ServiceProjectHasFeedbackTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestProjectHasFeedbackTO implements ITester<ProjectHasFeedbackTO> {

    @Override
    public List<ProjectHasFeedbackTO> testSelect() {
        
        List<ProjectHasFeedbackTO> objectTOList;
        
        try {
            
            objectTOList = new ServiceProjectHasFeedbackTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<ProjectHasFeedbackTO>();
        }
        
        return objectTOList;
    }

    @Override
    public ProjectHasFeedbackTO testSelectByPk(ProjectHasFeedbackTO objectTO) {
        
        try {
            
            return new ServiceProjectHasFeedbackTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ProjectHasFeedbackTO();
        }
        
    }

    @Override
    public void testInsert(ProjectHasFeedbackTO objectTO) {
        
        try {
            
            new ServiceProjectHasFeedbackTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(ProjectHasFeedbackTO objectTO) {
        
        try {
            
            new ServiceProjectHasFeedbackTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(ProjectHasFeedbackTO objectTO) {
        
        try {
            
            new ServiceProjectHasFeedbackTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
}
