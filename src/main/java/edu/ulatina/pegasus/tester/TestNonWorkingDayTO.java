package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.interfaces.ITester;
import edu.ulatina.pegasus.transfereObjects.NonWorkingDayTO;
import edu.ulatina.pegasus.serviceTO.ServiceNonWorkingDayTO;
import java.util.*;

/**
 * @author PegasusTeam
 */
public class TestNonWorkingDayTO implements ITester<NonWorkingDayTO> {

    @Override
    public List<NonWorkingDayTO> testSelect() {

        List<NonWorkingDayTO> objectTOList;

        try {

            objectTOList = new ServiceNonWorkingDayTO().select();

        } catch (Exception e) {
            e.printStackTrace();
            objectTOList = new ArrayList<NonWorkingDayTO>();
        }

        return objectTOList;
    }

    @Override
    public NonWorkingDayTO testSelectByPk(NonWorkingDayTO objectTO) {
        
        try {

            return new ServiceNonWorkingDayTO().selectByPk(objectTO);

        } catch (Exception e) {
            e.printStackTrace();
            return new NonWorkingDayTO();
        } 
    }

    @Override
    public void testInsert(NonWorkingDayTO objectTO) {

        try {

            new ServiceNonWorkingDayTO().insert(objectTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testUpdate(NonWorkingDayTO objectTO) {

        try {

            new ServiceNonWorkingDayTO().update(objectTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testDelete(NonWorkingDayTO objectTO) {

        try {

            new ServiceNonWorkingDayTO().delete(objectTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
