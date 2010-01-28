package com.hisun.tools;


import com.hisun.message.HiContext;
import org.apache.commons.lang.StringUtils;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

public class HiDBUtil {
    private Connection _conn;

    public Connection getConnection() throws Exception {

        if (this._conn != null) return this._conn;

        InitialContext ic = getContext();

        DataSource ds = (DataSource) ic.lookup(HiContext.getRootContext().getStrProp("@PARA", "_DB_NAME"));


        this._conn = ds.getConnection();

        this._conn.setAutoCommit(false);

        return this._conn;
    }

    public InitialContext getContext() throws Exception {

        Hashtable env = new Hashtable();

        HiContext ctx = HiContext.getRootContext();


        String value = (String) ctx.getProperty("@PARA", "_INITIAL_CONTEXT_FACTORY");


        if (StringUtils.isEmpty(value)) {

            throw new Exception("_INITIAL_CONTEXT_FACTORY is empty");
        }

        env.put("java.naming.factory.initial", value);


        value = (String) ctx.getProperty("@PARA", "_PROVIDER_URL");


        if (StringUtils.isEmpty(value)) {

            throw new Exception("_PROVIDER_URL is empty");
        }

        env.put("java.naming.provider.url", value);

        InitialContext ic = new InitialContext(env);

        return ic;
    }

    public int execUpdate(String sql) throws Exception {

        this._conn = getConnection();

        PreparedStatement stmt = this._conn.prepareStatement(sql);

        return stmt.executeUpdate();
    }

    public void commit() throws SQLException {

        if (this._conn == null) return;

        this._conn.commit();
    }

    public void rollback() throws SQLException {

        if (this._conn == null) return;

        this._conn.rollback();
    }
}