/**
 * @Description:
 * @author lijun
 * @date 2011-6-30 7:47:53
 *
 * Expression tags is undefined on line 6, column 5 in Templates/Classes/Class.java.
 */
package pep.bp.db.commLog;

import java.sql.Time;
import java.util.ArrayList;
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
    private final List<CommLogDAO> commLogList;
    private static final CommLogWriter commLogWriterInstance = new CommLogWriter();
    private CommLogService commLogService;

    private CommLogWriter() {
        super();
        commLogList = new ArrayList<CommLogDAO>();
        cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
        commLogService = (CommLogService) cxt.getBean(SystemConst.COMMLOG_BEAN);
        log.info("启动通信日志记录器....");
    }

    private void insertLogs() {
        List<CommLogDAO> worklist = new ArrayList<CommLogDAO>();
        synchronized (this.commLogList) {
            try {
                worklist.addAll(commLogList);
            } finally {
                commLogList.clear();
            }
        }
        commLogService.insertLogs(worklist);
    }

    public static CommLogWriter getInstance() {
        return commLogWriterInstance;
    }

    public void insertLog(String rtua, String message, String direction) {
        synchronized (this.commLogList) {
            CommLogDAO commLog = new CommLogDAO(rtua, message, new Time(System.currentTimeMillis()), direction);
            commLogList.add(commLog);
//            if (commLogList.size() >= maxCacheSize) {
//                insertLogs();
//            }
        }
    }

    @Override
    public void run() {
        insertLogs();
    }
}
