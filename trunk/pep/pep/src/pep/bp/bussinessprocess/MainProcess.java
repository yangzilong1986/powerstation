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
    private static int rtResponseDealerMaxNumber = 1;
    private static int pollingProcessorMaxNumber = 1;
    private LeakPointProcessor lpProcessor;
    private SMSNoticeProcessor sMSProcessor;
    private PollingProcessor pollingProcessor;
    private UpLoadProcessor upLoadProcessor;
    private PlanManager planManager;
    private final static Logger log = LoggerFactory.getLogger(MainProcess.class);
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private ThreadPoolExecutor threadPool;

    private void runRealTimeTaskSender() {
        for (int i = 1; i <= rtTaskSenderMaxNumber; i++) {
            try {
                String task = "启动实时交互任务发送器 " + i;
                log.info(task);
                threadPool.execute(new RealTimeSender(this.pepCommunicator));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void runResponseDealer() {
        for (int i = 1; i <= rtResponseDealerMaxNumber; i++) {
            try {
                String task = "启动主站下发返回处理器 " + i;
                log.info(task);
                threadPool.execute(new ResponseDealer(this.pepCommunicator));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void runPollingProcessor() {
        try {
            if (this.pollingProcessor == null) {
                this.pollingProcessor = new PollingProcessor(this.pepCommunicator);
            }
            pollingProcessor.run();
            log.info("启动主动轮召任务处理器 ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void runPlanManager() {
        try {
            if (this.planManager == null) {
                this.planManager = new PlanManager(this.pepCommunicator);
            }
            planManager.run();
            log.info("启动漏保试跳计划管理器 ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void runupLoadProcessor() {
        if (this.upLoadProcessor == null) {
            this.upLoadProcessor = new UpLoadProcessor(this.pepCommunicator);
        }
        log.info("启动主动上报任务处理器 ");
        upLoadProcessor.run();

    }

    public MainProcess(PepCommunicatorInterface pepCommunicator) {
        this.threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        this.pepCommunicator = pepCommunicator;
        lpProcessor = new LeakPointProcessor();
        pollingProcessor = new PollingProcessor(this.pepCommunicator);

    }

    public void run() {
        runRealTimeTaskSender();
        runResponseDealer();
        runPollingProcessor();
        runPlanManager();
        runupLoadProcessor();
        
    }
}
