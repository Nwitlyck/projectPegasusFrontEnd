package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.ProjectsTO;
import edu.ulatina.pegasus.serviceTO.ServiceProjectsTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestProjectTO implements ITester<ProjectsTO> {

    @Override
    public List<ProjectsTO> testSelect() {
        
        List<ProjectsTO> objectTOList;
        
        try {
            
            objectTOList = new ServiceProjectsTO().select();
            
        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<ProjectsTO>();
        }
        
        return objectTOList;
    }

    @Override
    public ProjectsTO testSelectByPk(ProjectsTO objectTO) {
        
        try {
            
            return new ServiceProjectsTO().selectByPk(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ProjectsTO();
        }
        
    }

    @Override
    public void testInsert(ProjectsTO objectTO) {
        
        try {
            
            new ServiceProjectsTO().insert(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(ProjectsTO objectTO) {
        
        try {
            
            new ServiceProjectsTO().update(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(ProjectsTO objectTO) {
        
        try {
            
            new ServiceProjectsTO().delete(objectTO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
}
