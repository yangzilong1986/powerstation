package com.hisun.database;


import com.hisun.hilib.HiATLParam;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class HiResultSet {
    private ArrayList records = new ArrayList(1);

    public HiResultSet(ResultSet rs) throws SQLException {

        ResultSetMetaData meta = rs.getMetaData();

        int cols = meta.getColumnCount();

        while (rs.next()) {

            HiATLParam param = new HiATLParam();

            for (int i = 0; i < cols; ++i) {

                String strColName = meta.getColumnName(i + 1);

                param.put(strColName, String.valueOf(rs.getObject(i + 1)));
            }

            this.records.add(param);
        }
    }

    public HiATLParam getFirstRecord() {

        return ((HiATLParam) this.records.get(0));
    }

    public HiATLParam getRecord(int idx) {

        return ((HiATLParam) this.records.get(idx));
    }

    public int size() {

        return this.records.size();
    }

    public int getInt(int row, int columnIndex) {

        HiATLParam params = (HiATLParam) this.records.get(row);

        return params.getInt(columnIndex);
    }

    public int getInt(int row, String name) {

        HiATLParam params = (HiATLParam) this.records.get(row);

        return params.getInt(name);
    }

    public String getValue(int row, int columnIndex) {

        HiATLParam params = (HiATLParam) this.records.get(row);

        return params.getValue(columnIndex);
    }

    public String getValue(int row, String name) {

        HiATLParam params = (HiATLParam) this.records.get(row);

        return params.get(name);
    }
}