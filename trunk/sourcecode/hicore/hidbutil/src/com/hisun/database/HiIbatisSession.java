package com.hisun.database;

import com.ibatis.sqlmap.client.SqlMapSession;

class HiIbatisSession {
    public String dsname;
    public SqlMapSession session;

    public HiIbatisSession(String daname, SqlMapSession session) {
        this.dsname = daname;
        this.session = session;
    }
}