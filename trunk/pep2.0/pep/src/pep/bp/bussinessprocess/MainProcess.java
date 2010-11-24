/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.bussinessprocess;

import pep.bp.processor.polling.PollingProcessor;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.processor.*;
import pep.bp.processor.planManager.PlanManager;
import pep.mina.common.PepCommunicatorInterface;

/**
 *
 * @author Thinkpad
 */
public class MainProcess {

    private static int rtTaskSenderMaxNumber = 1;
    private static int pollingProcessorMaxNumber = 1;

    private PollingProcessor pollingProcessor;
    private PlanManager planManager;

    private final static Logger log = LoggerFactory.getLogger(MainProcess.class);
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private ThreadPoolExecutor threadPool;

    private void runProcessor(int maxCount, String title, Runnable bp) {
        for (int i = 1; i <= maxCount; i++) {
            try {
                String task = title + i;
                log.info(task);
                threadPool.execute(bp);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }



    private void runPollingProcessor() {
        try {
            if (this.pollingProcessor == null) {
                this.pollingProcessor = new PollingProcessor(this.pepCommunicator);
            }
            pollingProcessor.run();
            log.info("启动主动轮召任务调度器 ");
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private void runPlanManager() {
        try {
            if (this.planManager == null) {
                this.planManager = new PlanManager(this.pepCommunicator);
            }
            planManager.run();
            log.info("启动漏保试跳计划调度器 ");
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }

    public MainProcess(PepCommunicatorInterface pepCommunicator) {
        this.threadPool = new ThreadPoolExecutor(4, 5, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        this.pepCommunicator = pepCommunicator;
        planManager = new PlanManager(this.pepCommunicator);
        pollingProcessor = new PollingProcessor(this.pepCommunicator);

    }

    public void run() {
        runProcessor(rtTaskSenderMaxNumber, "启动实时交互任务发送器 ", new RealTimeSender(this.pepCommunicator));
        runProcessor(rtTaskSenderMaxNumber, "启动主站下发返回处理器 ", new ResponseDealer(this.pepCommunicator));
        runPlanManager();
        runPollingProcessor();
        runProcessor(rtTaskSenderMaxNumber, "启动短信回复检查处理器 ", new SMSCheckProcessor());
        runProcessor(rtTaskSenderMaxNumber, "启动主动上报任务处理器 ", new UpLoadProcessor(this.pepCommunicator));      
    }
}
