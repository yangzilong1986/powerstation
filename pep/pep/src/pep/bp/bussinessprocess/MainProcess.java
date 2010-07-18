/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.bussinessprocess;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.processor.*;
import pep.mina.common.PepCommunicatorInterface;

/**
 *
 * @author Thinkpad
 */
public class MainProcess {

    private static int rtTaskProcessorMaxNumber = 2;
    private static int pollingProcessorMaxNumber = 1;
    private LeakPointProcessor lpProcessor;
    private SMSNoticeProcessor SMSProcessor;
    private RealTimeTaskProcessor RTaskProcessor;
    private PollingProcessor pollingProcessor;
    private final static Logger log = LoggerFactory .getLogger(MainProcess.class);
    private PepCommunicatorInterface pepCommunicator;//通信代理器

    private void runRealTimeTaskProcessor(){
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 1; i <= rtTaskProcessorMaxNumber; i++) {
            try {
                String task = "启动实时交互任务处理器 " + i;
                log.info(task);
                threadPool.execute(new RealTimeTaskProcessor(this.pepCommunicator));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void runPollingProcessor(){
        try {
            if (this.pollingProcessor == null) {
                this.pollingProcessor = new PollingProcessor();
            }
            pollingProcessor.run();
            log.info("启动主动轮召任务处理器 ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public MainProcess(PepCommunicatorInterface pepCommunicator) {
        this.pepCommunicator = pepCommunicator;
        lpProcessor = new LeakPointProcessor();
        pollingProcessor = new PollingProcessor();
        RTaskProcessor = new RealTimeTaskProcessor(pepCommunicator);
    }

    public void run() {
        runRealTimeTaskProcessor();
        //runPollingProcessor();
    }

}
