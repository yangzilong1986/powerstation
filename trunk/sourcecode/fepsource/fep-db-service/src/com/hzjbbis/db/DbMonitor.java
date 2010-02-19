package com.hzjbbis.db;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

public class DbMonitor {
    private static final Logger log = Logger.getLogger(DbMonitor.class);
    private static final TraceLog tracer = TraceLog.getTracer();
    private static final ArrayList<DbMonitor> dbMonitors = new ArrayList();

    private String name = "defaultDbMonitor";
    private String serverIp;
    private int serverPort;
    private DataSource dataSource;
    private String testSql = "select * from dual";
    private int connectTimeout = 2;
    private int testInterval = 90;
    private FasSystem fasSystem = null;

    private boolean dbAvailable = false;

    private boolean initialized = false;

    private static final DbMonitorThread daemonThread = new DbMonitorThread();

    public static final DbMonitor createInstance() {
        DbMonitor monitor = new DbMonitor();
        dbMonitors.add(monitor);
        return monitor;
    }

    public static final DbMonitor getMonitor(String name) {
        if ((name == null) || (name.length() == 0)) name = "defaultDbMonitor";
        for (int i = 0; i < dbMonitors.size(); ++i)
            if (((DbMonitor) dbMonitors.get(i)).name.equals(name)) return ((DbMonitor) dbMonitors.get(i));
        return null;
    }

    public static final DbMonitor getMonitor(DataSource ds) {
        for (DbMonitor dm : dbMonitors) {
            if (dm.dataSource == ds) return dm;
        }
        return null;
    }

    public static final DbMonitor getMasterMonitor() {
        return getMonitor(null);
    }

    public void initialize() {
        testDbConnection();
        daemonThread.add(this);
    }

    public boolean testSocketConnectable() {
        return true;
    }

    public boolean testDbConnection() {
        this.initialized = true;
        Assert.notNull(this.dataSource, "dataSource must not be null");
        isAvailable();

        if ((this.testSql == null) || (this.testSql.length() < 5)) {
            return true;
        }
        Connection con = null;
        try {
            con = DataSourceUtils.getConnection(this.dataSource);
            con.createStatement().executeQuery(this.testSql);
            setAvailable(true);
        } catch (Exception e) {
            setAvailable(false);
        } finally {
            DataSourceUtils.releaseConnection(con, this.dataSource);
        }
        return isAvailable();
    }

    public final boolean isAvailable() {
        if (!(this.initialized)) testDbConnection();
        return this.dbAvailable;
    }

    public final void setAvailable(boolean available) {
        if (this.fasSystem == null) this.fasSystem = FasSystem.getFasSystem();
        if (this.fasSystem != null) this.fasSystem.setDbAvailable(available);
        if (this.dbAvailable != available) {
            tracer.trace(this.name + " detect DB available is:" + available);
        }
        this.dbAvailable = available;
    }

    public final void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public final void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public final void setTestSql(String testSql) {
        this.testSql = testSql;
    }

    public final void setJdbcUrl(String url) {
    }

    public final void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public final void setTestInterval(int testInterval) {
        this.testInterval = testInterval;
    }

    public final void setFasSystem(FasSystem fasSystem) {
        this.fasSystem = fasSystem;
    }

    static class DbMonitorThread extends Thread {
        private final ArrayList<DbMonitor> monitors = new ArrayList();

        public DbMonitorThread() {
            super("DbMonitorDaemonThread");
            setDaemon(true);
            start();
        }

        public void add(DbMonitor monitor) {
            this.monitors.add(monitor);
        }

        public void run() {
            try {
                DbMonitor m;
                while (this.monitors.size() == 0) {
                    Thread.sleep(3000L);
                }

                int interval = 3600;
                for (Iterator localIterator = this.monitors.iterator(); localIterator.hasNext();) {
                    m = (DbMonitor) localIterator.next();
                    if (m.testInterval > 60) {
                        if (m.testInterval < interval) interval = m.testInterval;
                    } else interval = 60;
                }
                Thread.sleep(interval * 1000);
                for (Iterator localIterator = this.monitors.iterator(); localIterator.hasNext();) {
                    m = (DbMonitor) localIterator.next();
                    m.testDbConnection();
                }
            } catch (Exception e) {
                DbMonitor.log.warn("dbMonitor test exception:" + e.getLocalizedMessage(), e);
            }
        }
    }
}