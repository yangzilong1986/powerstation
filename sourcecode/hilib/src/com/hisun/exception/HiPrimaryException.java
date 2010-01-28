package com.hisun.exception;

import java.sql.SQLException;

public class HiPrimaryException extends HiSQLException {
    private static final long serialVersionUID = 6251719605867346264L;

    public HiPrimaryException(SQLException ex, String strSql) {

        super("215024", ex, strSql);
    }
}