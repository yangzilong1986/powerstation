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

/**
 *
 * @author Thinkpad
 */
public class MainProcess {

    private static int rtTaskProcessorMaxNumber = 2;
    private LeakPointProcessor lpProcessor;
    private SMSNoticeProcessor SMSProcessor;
    private final static Logger log = LoggerFactory .getLogger(MainProcess.class);

    private void runRealTimeTaskProcessor(){
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 1; i <= rtTaskProcessorMaxNumber; i++) {
            try {
                String task = "启动实时交互任务处理器 " + i;
                log.info(task);
                threadPool.execute(new RealTimeTaskProcessor());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MainProcess() {
        lpProcessor = new LeakPointProcessor();
        SMSProcessor = new SMSNoticeProcessor();
    }

    public void run() {
        runRealTimeTaskProcessor();
        lpProcessor.run();
        SMSProcessor.run();
    }
}
