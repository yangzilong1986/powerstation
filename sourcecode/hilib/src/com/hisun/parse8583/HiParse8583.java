package com.hisun.parse8583;


import com.hisun.common.util.HiByteUtil;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.util.HiByteBuffer;
import org.apache.log4j.Level;

import java.io.BufferedReader;
import java.io.FileReader;


public class HiParse8583 {
    private HiParser8583Handler _rqHandler;
    private HiParser8583Handler _rpHandler;
    private BufferedReader _is;
    private Logger _log;
    boolean _is_rqpackage = true;
    private String curline;
    private String curMsgid;


    public static void main(String[] args) throws Exception {

        if (args.length < 5) {

            System.out.println("USAGE: parse8583 infile outfile cfgfile version [MSGID]");


            System.exit(-1);

        }

        try {

            HiParse8583 parse = new HiParse8583();

            Params param = new Params();

            param._inFile = args[0];

            param._outFile = args[1];

            param._cfgFile1 = args[2];

            param._cfgFile2 = args[3];

            param._version = args[4];


            String id = null;

            if (args.length == 6) {

                id = args[5];

            }

            parse.process(param, id);

        } catch (Exception e) {

            e.printStackTrace();

            System.exit(-1);

        }

        System.exit(0);

    }


    public void process(Params param, String id) throws Exception {

        setHandlerParam(param);

        byte[] buffer = null;

        do {
            do if ((buffer = readRecord()) == null) return; while ((id != null) && (!(id.equals(this.curMsgid))));


            if (this._log.isInfoEnabled()) {

                this._log.info(this.curline + "|");

            }

            if (buffer == null) return;

            try {

                if (this._is_rqpackage) processRqRecord(buffer);

                else processRpRecord(buffer);

            } catch (Throwable t) {

                System.out.println("解析出错:" + this.curMsgid + "," + t);

            }

        } while (id == null);

    }


    public byte[] readRecord() throws Exception {

        String line = this._is.readLine();

        if (line == null) return null;

        this.curline = line;

        int idx = line.lastIndexOf("|");

        if (idx == -1) {

            throw new Exception("非法行:" + line);

        }

        String[] parts = line.split("\\|");

        if (parts[2].equalsIgnoreCase("rq")) this._is_rqpackage = true;

        else {

            this._is_rqpackage = false;

        }

        this.curMsgid = parts[1];

        line = line.substring(idx + 1);

        return HiByteUtil.hexToByteArray(line);

    }


    public void setHandlerParam(Params param) throws Exception {

        HiContext ctx1 = HiContext.getRootContext();

        HiContext.setCurrentContext(ctx1);

        this._log = new HiLoggerExt(param._outFile);

        this._log.setLevel(Level.INFO);

        ctx1.setProperty("SVR.log", this._log);

        this._rqHandler = new HiParser8583Handler();

        this._rqHandler.setCFG(param._cfgFile1);

        this._rqHandler.setVersion(param._version);

        this._rqHandler.serverInit(null);


        this._rpHandler = new HiParser8583Handler();

        this._rpHandler.setCFG(param._cfgFile2);

        this._rpHandler.setVersion(param._version);

        this._rpHandler.serverInit(null);


        this._is = new BufferedReader(new FileReader(param._inFile));

    }


    public void processRqRecord(byte[] buffer) throws Exception {

        HiMessageContext msgCtx = new HiMessageContext();

        HiMessageContext.setCurrentContext(msgCtx);

        HiMessage msg = new HiMessage("Parse8583", "PLTIN0");

        msg.setBody(new HiByteBuffer(buffer));

        msgCtx.setCurrentMsg(msg);


        this._rqHandler.process(msgCtx);

    }


    public void processRpRecord(byte[] buffer) throws Exception {

        HiMessageContext msgCtx = new HiMessageContext();

        HiMessageContext.setCurrentContext(msgCtx);

        HiMessage msg = new HiMessage("Parse8583", "PLTIN0");

        msg.setBody(new HiByteBuffer(buffer));

        msgCtx.setCurrentMsg(msg);


        this._rpHandler.process(msgCtx);

    }

}