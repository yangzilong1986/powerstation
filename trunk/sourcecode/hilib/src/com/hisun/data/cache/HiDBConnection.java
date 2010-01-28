package com.hisun.data.cache;


import com.hisun.exception.HiException;
import com.hisun.util.HiServiceLocator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HiDBConnection {
    private DataSource dataSource;

    public HiDBConnection() {

        this.dataSource = null;
    }

    public Connection getConnection(String dsName) throws HiException {
        try {
            return getDBDataSource(dsName).getConnection();
        } catch (SQLException e) {

            throw new HiException(e);
        }
    }

    private DataSource getDBDataSource(String name) throws HiException {

        if (this.dataSource != null) return this.dataSource;
        try {

            this.dataSource = HiServiceLocator.getInstance().getDataSource(name);

            return this.dataSource;
        } catch (Exception e) {

            throw new HiException(e);
        }
    }
}