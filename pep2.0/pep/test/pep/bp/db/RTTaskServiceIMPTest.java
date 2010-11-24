/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import static org.junit.Assert.*;
import pep.bp.model.RealTimeTaskDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class RTTaskServiceIMPTest {
    private static RTTaskService taskService;
    public RTTaskServiceIMPTest() {

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ApplicationContext cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
        taskService = (RTTaskService)cxt.getBean(SystemConst.REALTIMETASK_BEAN);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


   


    /**
     * Test of insertTask method, of class RTTaskServiceIMP.
     */
    @Test
    public void testInsertTask() {
        RealTimeTaskDAO task = new RealTimeTaskDAO();
        task.setSequencecode(1);
        task.setSendmsg("test");
        taskService.insertTask(task);
    }

    /**
     * Test of insertTasks method, of class RTTaskServiceIMP.
     */
    @Test
    public void testInsertTasks() {
        List<RealTimeTaskDAO> Tasks = new ArrayList(5);
        for(int i=1;i<=5;i++){
            RealTimeTaskDAO task = new RealTimeTaskDAO();
            task.setSequencecode(i);
            task.setSendmsg("test"+i);
            Tasks.add(task);
        }
        taskService.insertTasks(Tasks);
    }

    /**
     * Test of insertRecvMsg method, of class RTTaskServiceIMP.
     */
    @Test
    public void testInsertRecvMsg() {
        System.out.println("insertRecvMsg");
        long sequnceCode = 0L;
        String recvMsg = "";
        RTTaskServiceIMP instance = new RTTaskServiceIMP();
        instance.insertRecvMsg(sequnceCode, "",recvMsg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

     /**
     * Test of getTasks method, of class RTTaskServiceIMP.
     */
    @Test
    public void testGetTasks() {
        List<RealTimeTaskDAO> tasks = taskService.getTasks();
        assertTrue(tasks.size()>0);

    }

}