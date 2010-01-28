package com.hisun.database;


class HiProcedureParam {
    String name;
    boolean out;
    String type;


    public HiProcedureParam(String name, int columnType, String dataType) {

        this.name = name;

        this.out = HiJDBCProvider.isOutParam(columnType);


        this.type = dataType;

    }


    public boolean isCursor() {

        return "REF CURSOR".equals(this.type);

    }


    public boolean isString() {

        return (!(isCursor()));

    }

}