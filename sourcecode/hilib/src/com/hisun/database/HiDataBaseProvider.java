package com.hisun.database;


import com.hisun.exception.HiException;
import com.hisun.exception.HiSQLException;
import com.hisun.message.HiContext;
import com.hisun.util.HiDBSemaphore;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiSemaphore;
import com.hisun.util.HiServiceLocator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class HiDataBaseProvider extends HiJDBCProvider implements DataAccessProvider {
    private HiSemaphore dbSemaphore;
    private Connection _connection;
    private Stack _connections;


    public HiDataBaseProvider() {

        this.dbSemaphore = null;


        this._connection = null;


        this._connections = null;
    }


    public void close() {

        try {

            if (this._connection != null) {

                this._connection.commit();

                this._connection.close();

                if (this.dbSemaphore != null) {

                    this.dbSemaphore.release();

                }

                this._connection = null;

            }

        } catch (Exception e) {

        }

    }


    public void closeAll() {

        if ((this._connections == null) || (this._connections.isEmpty())) {

            close();

            return;

        }

        Iterator iter = this._connections.iterator();

        while (iter.hasNext()) {

            HiConnection con = (HiConnection) iter.next();

            try {

                con.con.commit();

                con.con.close();

                if (this.dbSemaphore != null) this.dbSemaphore.release();

            } catch (Exception e) {

            }

        }

    }


    public void commit() throws HiException {

        try {

            if (this._connection != null) this._connection.commit();

        } catch (Exception e) {

            throw new HiSQLException("215015", e, "commit 出错");

        }

    }


    public Connection getConnection() throws HiException {

        if (this._connection != null) return this._connection;

        if (HiICSProperty.isJUnitEnv()) {

            try {

                String url = System.getProperty("db_url");

                String userId = System.getProperty("userId");

                String password = System.getProperty("password");

                String driver = System.getProperty("db_driver");


                Class.forName(driver);


                this._connection = DriverManager.getConnection(url, userId, password);


                this._connection.setAutoCommit(false);

                return this._connection;

            } catch (Exception e) {

                throw HiException.makeException(e);

            }

        }


        if (this.dbSemaphore == null) {

            this.dbSemaphore = ((HiDBSemaphore) HiContext.getCurrentContext().getProperty("_SVR_DB_CONN_NUM_CTRL"));

        }


        try {

            if (this.dbSemaphore != null) {

                this.dbSemaphore.acquire();

            }

            DataSource ds = HiServiceLocator.getInstance().getDBDataSource(this._dsName);


            this._connection = ds.getConnection();

            this._connection.setAutoCommit(false);

            return this._connection;

        } catch (SQLException e) {

            if (this.dbSemaphore != null) this.dbSemaphore.release();

            throw new HiSQLException("215022", e, "");

        }

    }


    public Connection popConnection() {

        if ((this._connections == null) || (this._connections.isEmpty())) {

            return null;

        }

        HiConnection con = (HiConnection) this._connections.pop();

        this._connection = con.con;

        this._dsName = con.name;

        if (log.isInfoEnabled()) {

            log.info("pop db:[" + this._dsName + "]:[" + this._connection + "]");

        }

        return this._connection;

    }


    public void pushConnection() {

        if (this._connection == null) {

            return;

        }


        if (this._connections == null) {

            this._connections = new Stack();

        }

        if (log.isInfoEnabled()) {

            log.info("push db:[" + this._dsName + "]:[" + this._connection + "]");

        }

        this._connections.push(new HiConnection(this._dsName, this._connection));

        this._connection = null;

    }


    public void rollback() throws HiException {

        try {

            if (this._connection != null) this._connection.rollback();

        } catch (Exception e) {

            throw new HiException("220319", e.getMessage(), e);

        }

    }


    public int delete(Object o) throws HiException {

        return 0;

    }


    public int delete(List objs) throws HiException {

        return 0;

    }


    public int delete(Object[] objs) throws HiException {

        return 0;

    }


    public Object get(Class clazz, int id) throws HiException {

        return null;

    }


    public List list(Class clazz) throws HiException {

        return null;

    }


    public List list(Class clazz, String sql) throws HiException {

        return null;

    }


    public List list(Class clazz, String sql, List params) throws HiException {

        return null;

    }


    public int save(Object o) throws HiException {

        return 0;

    }


    public int save(List objs) throws HiException {

        return 0;

    }


    public int save(Object[] objs) throws HiException {

        return 0;

    }


    public List list(Class clazz, String sql, Object[] params) throws HiException {

        return null;

    }


    public int delete(String statementid) throws HiException {

        return 0;

    }


    public int delete(String statementid, Object paramObj) throws HiException {

        return 0;

    }


    public int delete(String statementid, Object[] paramObjs) throws HiException {

        return 0;

    }


    public int delete(String statementid, List paramObjs) throws HiException {

        return 0;

    }


    public Object get(String statementid) throws HiException {

        return null;

    }


    public Object get(String statementid, Object paramObj) throws HiException {

        return null;

    }


    public Object get(String statementid, Object paramObj, Object returnObj) throws HiException {

        return null;

    }


    public int insert(String statementid) throws HiException {

        return 0;

    }


    public int insert(String statementid, Object obj) throws HiException {

        return 0;

    }


    public int insert(String statementid, Object[] objs) throws HiException {

        return 0;

    }


    public int insert(String statementid, List objs) throws HiException {

        return 0;

    }


    public List list(String statementid) throws HiException {

        return null;

    }


    public List list(String statementid, Object paramObj) throws HiException {

        return null;

    }


    public List list(String statementid, int skip, int max) throws HiException {

        return null;

    }


    public List list(String statementid, Object paramObj, int skip, int max) throws HiException {

        return null;

    }


    public int update(String statementid) throws HiException {

        return 0;

    }


    public int update(String statementid, Object paramObj) throws HiException {

        return 0;

    }


    public int update(String statementid, Object[] paramObjs) throws HiException {

        return 0;

    }


    public int update(String statementid, List paramObjs) throws HiException {

        return 0;

    }

}