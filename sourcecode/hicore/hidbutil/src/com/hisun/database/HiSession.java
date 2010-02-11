package com.hisun.database;

import org.hibernate.Session;

class HiSession {
    public String dsname;
    public Session session;

    public HiSession(String daname, Session session) {
        this.dsname = daname;
        this.session = session;
    }
}