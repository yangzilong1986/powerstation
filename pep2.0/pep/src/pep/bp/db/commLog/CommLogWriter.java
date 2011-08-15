/**
 * @Description:
 * @author lijun
 * @date 2011-6-30 7:47:53
 *
 * Expression tags is undefined on line 6, column 5 in Templates/Classes/Class.java.
 */
package pep.bp.db.commLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.bp.model.CommLogDAO;
import pep.system.SystemConst;

public class CommLogWriter extends TimerTask {

    private final static Logger log = LoggerFactory.getLogger(CommLogWriter.class);
    protected ApplicationContext cxt;
    public static final int maxCacheTime = 10 * 1000;//最大缓存时间
    public static final int maxCacheSize = 100;//最大缓存报文条数
    private static List<CommLogDAO> commLogList = new ArrayList<CommLogDAO>();
    private static final CommLogWriter commLogWriterInstance = new CommLogWriter();
    private CommLogService commLogService;

    private CommLogWriter() {
        super();
        cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
        commLogService = (CommLogService) cxt.getBean(SystemConst.COMMLOG_BEAN);
        log.info("启动通信日志记录器....");
    }

    private void insertLogs() {
        synchronized (commLogList) {
            try {
                commLogService.insertLogs(commLogList);
            } finally {
                CommLogWriter.commLogList.clear();
            }
        }
    }

    public static CommLogWriter getInstance() {
        return commLogWriterInstance;
    }

    public  void insertLog(String rtua, String message, String direction) {
        synchronized(commLogList){
            CommLogDAO commLog = new CommLogDAO(rtua, message, new Date(), direction);
            commLogList.add(commLog);
//        if (commLogList.size() >= maxCacheSize) {
//            insertLogs();
//        }
        }
    }

    @Override
    public void run() {
        insertLogs();
    }
}
