package com.hisun.atmp.handler;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerDestroyListener;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiStringManager;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HiSaveFile implements IHandler, IServerInitListener, IServerDestroyListener {
    private String type;
    private String file;
    private Logger log;
    private static HiStringManager sm = HiStringManager.getManager();

    public HiSaveFile() {
        this.type = "bin";
        this.file = "TXN.trc";
        this.log = null;
    }

    public void process(HiMessageContext arg0) throws HiException {
        HiMessage msg = arg0.getCurrentMsg();
        HiByteBuffer buf = (HiByteBuffer) msg.getBody();
        byte[] Input = buf.getBytes();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        FileOutputStream out = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            bos.write(df.format(new Date()).getBytes());
            bos.write("|".getBytes());
            bos.write(msg.getRequestId().getBytes());
            bos.write("|".getBytes());
            bos.write(msg.getHeadItem("SCH").getBytes());
            bos.write("|".getBytes());
            bos.write(String.valueOf(Input.length).getBytes());
            bos.write("|".getBytes());
            if (this.type.equals("bin")) writeBinByte(bos, Input);
            else if (this.type.equals("hex")) {
                writeHexByte(bos, Input);
            }
            bos.write("\n".getBytes());
            out = new FileOutputStream(HiICSProperty.getTrcDir() + this.file, true);
            out.write(bos.toByteArray());
            out.flush();
            out.close();
        } catch (Throwable e) {
            if (out == null) return;
            try {
                out.close();
            } catch (IOException e1) {
            }
        }
    }

    private void writeHexByte(OutputStream w, byte[] b) throws IOException {
        for (int i = 0; i < b.length; ++i) {
            w.write(bin2char((b[i] & 0xF0) >> 4));
            w.write(bin2char(b[i] & 0xF));
        }
    }

    private void writeBinByte(OutputStream out, byte[] b) throws IOException {
        out.write(b);
    }

    private static char bin2char(int bin) {
        return (char) ((bin < 10) ? bin + 48 : bin - 10 + 65);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return this.file;
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        this.log = arg0.getLog();
    }

    public void serverDestroy(ServerEvent arg0) throws HiException {
    }
}