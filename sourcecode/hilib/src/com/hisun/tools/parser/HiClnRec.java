package com.hisun.tools.parser;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.tools.HiClnParam;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiStringUtils;
import com.hisun.util.HiSystemUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HiClnRec {
    private String _tablename;
    private String _datefield;
    private String _datefmt;
    private int _holddays;
    private String _condition;
    private String _script;
    private int _dateType;
    private static final String DATE_PATTERN = "yyyyMMdd";
    private int _type;


    public HiClnRec() {

        this._dateType = 0;


        this._type = 0;
    }


    public int getDateType() {

        return this._dateType;

    }


    public void setDateType(int dateType) {

        this._dateType = dateType;

    }


    public String getTablename() {

        return this._tablename;

    }


    public void setTablename(String tablename) {

        this._tablename = tablename;

    }


    public String getDatefield() {

        return this._datefield;

    }


    public void setDatefield(String datefield) {

        this._datefield = datefield;

    }


    public String getDatefmt() {

        return this._datefmt;

    }


    public void setDatefmt(String datefmt) {

        if (StringUtils.equals("YYYYMMDD", datefmt)) this._datefmt = "yyyyMMdd";

        else this._datefmt = datefmt;

    }


    public int getHolddays() {

        return this._holddays;

    }


    public void setHolddays(int holddays) {

        this._holddays = holddays;

    }


    public String getCondition() {

        return this._condition;

    }


    public void setCondition(String condition) {

        this._condition = condition;

    }


    public void process(HiClnParam param) throws Exception {

        switch (this._type) {

            case 0:

                processNormal(param);

                break;

            case 1:

                processSqlScript(param);

                break;

            case 2:

                processShellScript(param);

        }

    }


    public void processNormal(HiClnParam param) throws Exception {

        StringBuffer sqlCmd = new StringBuffer();

        boolean flag = false;


        sqlCmd.append("DELETE FROM ");

        sqlCmd.append(this._tablename);

        sqlCmd.append(" WHERE ");

        if (StringUtils.isNotEmpty(this._datefield)) {

            if (this._dateType == 0) date = DateUtils.parseDate(param._sysDate, new String[]{this._datefmt});

            else if (this._dateType == 1) date = new Date();

            else {

                date = new Date();

            }


            Date date = DateUtils.addDays(date, -1 * this._holddays);

            sqlCmd.append(this._datefield);

            sqlCmd.append(" < ");

            sqlCmd.append("'");

            sqlCmd.append(DateFormatUtils.format(date, this._datefmt));

            sqlCmd.append("'");

            flag = true;

        }

        String[] args = null;

        if (StringUtils.isNotEmpty(this._condition)) {

            if (flag) {

                sqlCmd.append(" AND ");

                args = new String[param._args.length - 1];

                System.arraycopy(param._args, 1, args, 0, args.length);

            } else {

                args = param._args;

            }

            sqlCmd.append(HiStringUtils.format(this._condition, args));

        }

        if (param._log.isInfoEnabled()) param._log.info("sql:[" + sqlCmd + "]");

        try {

            param._dbUtil.execUpdate(sqlCmd.toString());

            param._dbUtil.commit();

        } catch (SQLException e) {

            param._log.error("execute sql:[" + sqlCmd + "] failure", e);

            param._dbUtil.rollback();

        }

    }


    public void processSqlScript(HiClnParam param) throws Exception {

        try {

            this._script = HiStringUtils.format(this._script, param._args);

            if (param._log.isInfoEnabled()) {

                param._log.info("sql:[" + this._script + "]");

            }


            param._dbUtil.execUpdate(this._script);

            param._dbUtil.commit();

        } catch (SQLException e) {

            param._log.error("excute script:[" + this._script + "] failure", e);

            param._dbUtil.rollback();

        }

    }


    public void processShellScript(HiClnParam param) throws Exception {

        String workdir = HiICSProperty.getWorkDir();

        try {

            this._script = HiStringUtils.format(this._script, param._args);

            if (param._log.isInfoEnabled()) {

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

                param._log.info(df.format(new Date()) + "; sql:[" + this._script + "]");

            }


            HiSystemUtils.exec("sh " + workdir + "/" + this._script, true);

        } catch (HiException e) {

            param._log.error("excute script:[" + this._script + "] failure", e);

        }

    }


    public String getScript() {

        return this._script;

    }


    public void setScript(String script) {

        this._script = script;

    }


    public int getType() {

        return this._type;

    }


    public void setType(int type) {

        this._type = type;

    }


    public static void main(String[] args) throws Exception {

        HiClnRec clnRec = new HiClnRec();

        clnRec._condition = "TM < 1000 AND TM1 < 10000";

        clnRec._datefield = "ChgDat";

        clnRec._datefmt = "yyyyMMdd";

        clnRec._tablename = "ATMTXNJNL";

        HiClnParam param = new HiClnParam();

        param._dbUtil = null;

        param._log = HiLog.getLogger("test.trc");

        param._sysDate = "20050601";

        clnRec.processNormal(param);

    }

}