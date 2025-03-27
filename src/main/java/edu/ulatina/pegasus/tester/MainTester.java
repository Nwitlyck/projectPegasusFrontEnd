package edu.ulatina.pegasus.tester;

import edu.ulatina.pegasus.transfereObjects.*;

/**
 * @author PegasusTeam
 */
public class MainTester {

    public static void main(String[] args) {
        testColaboratorTO();
        //testMasterTO();
        //testDetailTO();
        //testNonWorkingDayTO();
        //testDocTO();
        //testFeedbackTO();
        //testMeetingHasColaboratorAndFeedbackTO();
        //testMeetingTO();
        //testColaboratorHasProjectTO();
        //testProjectTO();
        //testPersonalDataTO();
        //testProjectHasFeedbackTO();
    }

    public static void testColaboratorTO() {

        TestColaboratorTO test = new TestColaboratorTO();

        //test.testInsert(new ColaboratorTO(0, 0, new java.sql.Date(System.currentTimeMillis()), null, "Colaborator", 1)); // insert test
        //test.testUpdate(new ColaboratorTO(2, 1, new java.sql.Date(System.currentTimeMillis()), null, "Manger", 1)); // update test
        //test.testDelete(new ColaboratorTO(4,0, null, null, "", 0)); // delete test
        for (ColaboratorTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Access Level: " + objectTO.getAcceslevel()
                    + "| Hire Date: " + objectTO.getHireDate()
                    + "| Fire Date: " + objectTO.getFireDate()
                    + "| Password: " + objectTO.getPassword()
            );
        }
    }

    public static void testMasterTO() {

        TestMasterTO test = new TestMasterTO();
        DetailTO obj = new DetailTO();
        obj.setId(2);

        //test.testInsert(new MasterTO(0, "Documents", "Doc")); // insert test
        //test.testUpdate(new MasterTO(4, "Animals", "An")); // update test
        //test.testDelete(obj)); // delete test
        for (MasterTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Name: " + objectTO.getName()
                    + "| General Code: " + objectTO.getGeneralCode()
            );
        }
    }

    public static void testDetailTO() {

        TestDetailTO test = new TestDetailTO();
        DetailTO obj = new DetailTO();
        obj.setId(2);

        //test.testInsert(new DetailTO(0, "Cat", "Cat", 1)); // insert test
        //test.testUpdate(new DetailTO(1, "Curriculum Vitae", "CV", 1)); // update test
        //test.testDelete(obj); // delete test
        for (DetailTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Id Master: " + objectTO.getIdMaster()
                    + "| Name: " + objectTO.getName()
                    + "| General Code: " + objectTO.getGeneralCode()
            );
        }
    }

    public static void testNonWorkingDayTO() {

        TestNonWorkingDayTO test = new TestNonWorkingDayTO();
        NonWorkingDayTO obj = new NonWorkingDayTO();
        obj.setId(2);

        //test.testInsert(new NonWorkingDayTO(0, 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), 1)); // insert test
        //test.testUpdate(new NonWorkingDayTO(1, 1, 1, test.testSelectByPk(obj).getInitialDate(), new Timestamp(System.currentTimeMillis()), 1)); // update test
        //test.testDelete(obj); // delete test
        for (NonWorkingDayTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Id colaborador: " + objectTO.getIdColaborator()
                    + "| Type: " + objectTO.getType()
                    + "| Initial Date: " + objectTO.getInitialDate()
                    + "| Final Date: " + objectTO.getFinalDate()
            );
        }
    }

    public static void testDocTO() {

        TestDocTO test = new TestDocTO();
        DocTO obj = new DocTO();
        obj.setId(1);

        //test.testInsert(new DocTO(0, "Curriculum", new java.sql.Date(System.currentTimeMillis()), 1, 1, 1)); // insert test
        //test.testUpdate(new DocTO(1, "Curriculum Vitae", test.testSelectByPk(obj).getDate(), 1, 1, 1)); // update test
        //test.testDelete(obj); // delete test
        for (DocTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Name: " + objectTO.getDocLocation()
                    + "| Type: " + objectTO.getType()
                    + "| Colaborator id: " + objectTO.getColaboratorId()
            );
        }
    }

    public static void testFeedbackTO() {

        TestFeedbackTO test = new TestFeedbackTO();
        FeedbackTO obj = new FeedbackTO();
        obj.setId(1);

        //test.testInsert(new FeedbackTO(0, 1, "Its bad", 1)); // insert test
        //test.testUpdate(new FeedbackTO(1, 1, "Its good", 1)); // update test
        //test.testDelete(obj); // delete test
        for (FeedbackTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Author: " + objectTO.getAuthor()
                    + "| Opinion: " + objectTO.getFeedback()
            );
        }
    }

    public static void testMeetingHasColaboratorAndFeedbackTO() {

        TestMeetingHasColaboratorAndFeedbackTO test = new TestMeetingHasColaboratorAndFeedbackTO();
        MeetingHasColaboratorAndFeedbackTO obj = new MeetingHasColaboratorAndFeedbackTO();
        obj.setColaboratorId(1);
        obj.setMeetingId(1);
        obj.setFeedbackId(2);

        //test.testInsert(new MeetingHasColaboratorAndFeedbackTO(1, 1, 2, 1)); // insert test
        //test.testUpdate(new MeetingHasColaboratorAndFeedbackTO(1, 1, 2, 0)); // update test
        //test.testDelete(obj); // delete test
        for (MeetingHasColaboratorAndFeedbackTO objectTO : test.testSelect()) {
            System.out.println("Colaborator id: " + objectTO.getColaboratorId()
                    + "| Meeting id: " + objectTO.getMeetingId()
                    + "| Feedback id: " + objectTO.getFeedbackId()
            );
        }
    }

    public static void testColaboratorHasProjectTO() {

        TestColaboratorHasProjectTO test = new TestColaboratorHasProjectTO();
        ColaboratorHasProjectTO obj = new ColaboratorHasProjectTO();
        obj.setIdColaborator(1);
        obj.setIdProject(1);

        //test.testInsert(new ColaboratorHasProjectTO(1, 1, 100, new java.sql.Date(System.currentTimeMillis()), null, 1)); // insert test
        //test.testUpdate(new ColaboratorHasProjectTO(1, 1, 100, test.testSelectByPk(obj).getInitialDate() , new java.sql.Date(System.currentTimeMillis()), 1)); // update test
        //test.testDelete(obj); // delete test
        for (ColaboratorHasProjectTO objectTO : test.testSelect()) {
            System.out.println("Colaborador id: " + objectTO.getIdColaborator()
                    + "| Project id: " + objectTO.getIdProject()
                    + "| Hours: " + objectTO.getTotalTime()
                    + "| Initial date: " + objectTO.getInitialDate()
                    + "| Final date: " + objectTO.getFinalDate()
            );
        }
    }

    public static void testProjectTO() {

        TestProjectTO test = new TestProjectTO();
        ProjectsTO obj = new ProjectsTO();
        obj.setId(1);

        //test.testInsert(new ProjectsTO(0, "Pegasus", new java.sql.Date(System.currentTimeMillis()), null, 1)); // insert test
        //test.testUpdate(new ProjectsTO(1, "Project Pegasus", test.testSelectByPk(obj).getInitialdate(), null, 1)); // update test
        //test.testDelete(obj); // delete test
        for (ProjectsTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Name: " + objectTO.getName()
                    + "| Initial date: " + objectTO.getInitialdate()
                    + "| Final date: " + objectTO.getFinaldate()
            );
        }
    }

    public static void testPersonalDataTO() {

        TestPersonalDataTO test = new TestPersonalDataTO();
        PersonalDataTO obj = new PersonalDataTO();
        obj.setId(1);

        //test.testInsert(new PersonalDataTO(0, 1, "Admin", new java.sql.Date(System.currentTimeMillis()), 87399898, 1)); // insert test
        //test.testUpdate(new PersonalDataTO(1, 1, "Admin", test.testSelectByPk(obj).getBirthdate(), 0, 1)); // update test
        //test.testDelete(obj); // delete test
        for (PersonalDataTO objectTO : test.testSelect()) {
            System.out.println("Id: " + objectTO.getId()
                    + "| Name: " + objectTO.getName()
                    + "| Birthdate: " + objectTO.getBirthdate()
                    + "| Emergency Contact: " + objectTO.getEmergencycontact()
            );
        }
    }

    public static void testProjectHasFeedbackTO() {

        TestProjectHasFeedbackTO test = new TestProjectHasFeedbackTO();
        ProjectHasFeedbackTO obj = new ProjectHasFeedbackTO();
        obj.setProjectId(1);
        obj.setFeedbackId(2);

        //test.testInsert(new ProjectHasFeedbackTO(1, 2, "Work", 0, 1)); // insert test
        //test.testUpdate(new ProjectHasFeedbackTO(1, 2, "Work", 1, 1)); // update test
        //test.testDelete(obj); // delete test
        for (ProjectHasFeedbackTO objectTO : test.testSelect()) {
            System.out.println("Project id: " + objectTO.getProjectId()
                    + "| Feedback id: " + objectTO.getFeedbackId()
                    + "| Action plan: " + objectTO.getActionPlan()
                    + "| Action plan done: " + objectTO.getActionPlanDone()
            );
        }
    }

}

//una tonteria
