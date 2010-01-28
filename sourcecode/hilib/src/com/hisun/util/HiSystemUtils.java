package com.hisun.util;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class HiSystemUtils {
    public static int exec(String cmd, String param1, String param2, boolean wait) throws HiException {

        return exec(cmd, new String[]{param1, param2}, wait);
    }

    public static int exec(String cmd, String param1, String param2, String param3, boolean wait) throws HiException {

        return exec(cmd, new String[]{param1, param2, param3}, wait);
    }

    public static int exec(String cmd, String param1, boolean wait) throws HiException {

        return exec(cmd, new String[]{param1}, wait);
    }

    public static int exec(String cmd, boolean wait) throws HiException {

        return exec(cmd, new String[0], wait);
    }

    public static int exec(String cmd, String[] params, boolean wait) throws HiException {

        Process p = null;

        int ret = 0;

        StringBuffer cmdBuf = new StringBuffer();

        cmdBuf.append(cmd);

        for (int i = 0; (params != null) && (i < params.length); ++i) {

            cmdBuf.append(" ");

            cmdBuf.append(params[i]);
        }

        Logger log = HiLog.getLogger("SYS.trc");

        if (log.isInfoEnabled()) log.info("exec cmd:[" + cmdBuf.toString() + "]");
        try {

            p = Runtime.getRuntime().exec("/bin/sh");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));


            bw.write(cmdBuf.toString(), 0, cmdBuf.length());

            bw.newLine();

            bw.write("exit", 0, 4);

            bw.newLine();

            bw.flush();


            if (wait) {
                try {

                    ret = p.waitFor();
                } catch (InterruptedException e) {

                    throw new HiException("220309", cmdBuf.toString(), e);
                }
            }


            InputStream errIs = p.getErrorStream();

            InputStream stdIs = p.getInputStream();

            int len = errIs.available();

            byte[] buf = null;

            if (len != 0) {

                buf = new byte[len];

                log.info("============stderr msg============");

                errIs.read(buf);

                log.info(new String(buf, 0, len));
            }


            len = stdIs.available();

            if (len != 0) {

                buf = new byte[len];

                log.info("============stdout msg===========");

                stdIs.read(buf);

                log.info(new String(buf, 0, len));
            }
        } catch (IOException e) {
        } finally {

            if (p != null) {

                p.destroy();
            }
        }

        return ret;
    }
}