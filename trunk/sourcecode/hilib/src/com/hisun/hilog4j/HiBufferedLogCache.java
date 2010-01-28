package com.hisun.hilog4j;


import com.hisun.util.HiObjectPoolUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HiBufferedLogCache implements ILogCache {
    private StringBuilder logBuf = new StringBuilder(81920);
    private FileWriter bos = null;
    private int limitsSize;
    private String currentFileName;
    private IFileName fileName;


    public HiBufferedLogCache(int limitsSize, IFileName fileName) {

        this.limitsSize = limitsSize;

        this.fileName = fileName;

    }


    public synchronized void put(HiLogInfo info) throws IOException {

        open(info);

        StringBuilder buf = info.getBuf();

        if (this.logBuf.capacity() < buf.length()) {

            this.bos.write(this.logBuf.toString());

            this.logBuf.setLength(0);

            this.bos.write(buf.toString());

            HiObjectPoolUtils.getInstance().returnStringBuilder(info.getBuf());

            return;

        }

        if (this.logBuf.capacity() - this.logBuf.length() < buf.length()) {

            this.bos.write(this.logBuf.toString());

            this.logBuf.setLength(0);

            return;

        }

        this.logBuf.append(buf.toString());

        HiObjectPoolUtils.getInstance().returnStringBuilder(info.getBuf());

    }


    public synchronized FileWriter open(HiLogInfo info) throws IOException {

        String name = this.fileName.get();

        if (!(name.equals(this.currentFileName))) {

            if (this.bos != null) {

                this.bos.close();

                this.bos = null;

            }

            this.currentFileName = name;

        }


        File f1 = new File(name);

        if (f1.getParent() != null) {

            File f2 = new File(f1.getParent());

            if (!(f2.exists())) {

                f2.mkdirs();

            }

            f2 = null;

        }


        if ((!(f1.exists())) && (this.bos != null)) {

            this.bos.close();

            this.bos = null;

        }


        long l = f1.length();

        if (l > this.limitsSize) {

            if (this.bos != null) {

                this.bos.close();

                this.bos = null;

            }

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

            File f2 = new File(name + "." + df.format(new Date()));

            f1.renameTo(f2);

        }


        f1 = null;

        if (this.bos == null) {

            this.bos = new FileWriter(name, true);

        }

        return this.bos;

    }


    public void flush() throws IOException {

        if (this.bos != null) {

            this.bos.write(this.logBuf.toString());

        }

        this.logBuf.setLength(0);

    }


    public void clear() {

        File f = new File(this.fileName.get());

        if (f.exists()) {

            f.delete();

        }

        this.logBuf.setLength(0);

    }


    public void close() throws IOException {

        flush();

        if (this.bos == null) {

            return;

        }

        this.bos.close();

    }

}