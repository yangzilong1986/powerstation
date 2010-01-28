package com.hisun.data.cache;


import com.hisun.exception.HiException;
import com.hisun.exception.HiSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class HiDBUtil {
    private HiDBConnection DBConnection;
    private Connection conn;
    private String dsName;


    public HiDBUtil() {

        this.DBConnection = new HiDBConnection();

        this.conn = null;

    }


    protected List execQuery(String strSql) throws HiException {

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            if (this.conn == null) {

                this.conn = this.DBConnection.getConnection(this.dsName);

            }

            stmt = this.conn.prepareStatement(strSql);

            ArrayList list = new ArrayList();

            rs = stmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            int cols = meta.getColumnCount();

            while (rs.next()) {

                values = new HashMap();

                for (int i = 0; i < cols; ++i) {

                    String strColName = meta.getColumnName(i + 1);

                    Object value = rs.getObject(i + 1);

                    if (value == null) {

                        value = "";

                    }

                    if (value instanceof Clob) {

                        Clob tmp = (Clob) value;

                        value = tmp.getSubString(1L, (int) tmp.length());

                        values.put(strColName.toUpperCase(), value);

                    } else {

                        values.put(strColName.toUpperCase(), String.valueOf(value).trim());

                    }


                }


                list.add(values);

            }


            HashMap values = Collections.unmodifiableList(list);


            return values;

        } catch (Exception e) {

            if (e instanceof HiException) ;

            throw new HiSQLException("215016", e, strSql);

        } finally {

            close(stmt, rs);

        }

    }


    public void close(Statement stmt, ResultSet rs) {

        try {

            if (rs != null) rs.close();

            if (stmt != null) stmt.close();

        } catch (Exception e) {

        }

    }


    public void close() {

        try {

            if (this.conn != null) {

                this.conn.commit();

                this.conn.close();

                this.conn = null;

            }

        } catch (Exception e) {

        }

    }


    public String getDsName() {

        return this.dsName;

    }


    public void setDsName(String dsName) {

        this.dsName = dsName;

    }

}