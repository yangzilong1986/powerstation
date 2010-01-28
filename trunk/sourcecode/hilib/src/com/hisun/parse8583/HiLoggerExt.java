package com.hisun.parse8583;


import com.hisun.hilog4j.Logger;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Level;

import java.io.FileWriter;
import java.io.IOException;

public class HiLoggerExt extends Logger {
    public HiLoggerExt(String name) {

        super(name);
    }

    protected void println(FileWriter fw, Level level, Object[] msgs) throws IOException {

        for (int i = 0; i < msgs.length; ++i) {

            if (msgs[i] instanceof byte[]) {

                byte[] msg1 = (byte[]) (byte[]) msgs[i];

                for (int j = 0; j < msg1.length; ++j) {

                    fw.write(msg1[j]);
                }
            } else if (msgs[i] != null) {

                fw.write(msgs[i].toString());
            } else {

                fw.write("null");
            }

            if (i != msgs.length - 1) {

                fw.write(58);
            }
        }

        fw.write(SystemUtils.LINE_SEPARATOR);

        fw.flush();

        fw = null;
    }
}