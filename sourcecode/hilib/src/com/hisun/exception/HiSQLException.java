package com.hisun.exception;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;

import java.sql.SQLException;

public class HiSQLException extends HiException {
    private static final long serialVersionUID = 1L;
    private String strSql;
    private int retCode = -1;
    private String SQLState;
    private int errorCode;

    public HiSQLException(String code, Throwable ex, String strSql) {

        super(code, strSql, ex);

        if (ex instanceof SQLException) {

            this.SQLState = ((SQLException) ex).getSQLState();

            this.errorCode = ((SQLException) ex).getErrorCode();
        }

        this.strSql = strSql;

        writeLog();
    }

    public HiSQLException(String code, SQLException ex, String strSql) {

        super(code, strSql, ex);

        this.strSql = strSql;

        this.SQLState = ex.getSQLState();

        this.errorCode = ex.getErrorCode();

        writeLog();
    }

    public String getSQLState() {

        return this.SQLState;
    }

    public void setRetCode(int retCode) {

        this.retCode = retCode;
    }

    public int getRetCode() {

        return this.retCode;
    }

    public int getSqlErrorCode() {

        return this.errorCode;
    }

    public void writeLog() {

        Logger log = HiLog.getErrorLogger("database.log");


        log.error(this.strSql, this);
    }

    public String toString() {

        StringBuffer result = new StringBuffer();

        result.append(super.toString());

        result.append(", ErrorCode:[");

        result.append(this.errorCode);

        result.append("], SQLState:[");

        result.append(this.SQLState);

        result.append("]");

        return result.toString();
    }
}