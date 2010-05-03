/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.bussinessprocess;

import pep.bp.processor.*;

/**
 *
 * @author Thinkpad
 */
public class MainProcess {
    private static int produceTaskSleepTime = 2;
    private static int consumeTaskSleepTime = 2000;
    private static int produceTaskMaxNumber = 10;


    private UpLoadProcessor uploadProcessor;
    private RealTimeTaskProcessor rtTaskProcessor;
    private LeakPointProcessor lpProcessor;
    private SMSNoticeProcessor SMSProcessor;

    public MainProcess(){
        uploadProcessor = new UpLoadProcessor();
        rtTaskProcessor = new RealTimeTaskProcessor();
        lpProcessor = new LeakPointProcessor();
        SMSProcessor = new SMSNoticeProcessor();
    }
}
