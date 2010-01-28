package com.hisun.database;


import java.sql.Connection;


class HiConnection {
    String name;
    Connection con;


    HiConnection(String name, Connection con) {

        this.name = name;

        this.con = con;

    }

}