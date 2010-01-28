package com.hisun.hilog4j;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HiDirectLogCache implements ILogCache {
    private BufferedWriter bos = null;
    private int limitsSize;
    private String currentFileName;

    public HiDirectLogCache(int limitsSize) {

        this.limitsSize = limitsSize;
    }

    public void put(HiLogInfo info) throws IOException {

        log(info);
    }

    protected synchronized void log(HiLogInfo info) throws IOException {

        BufferedWriter bos = open(info);

        bos.write(info.getBuf().toString());

        bos.flush();
    }

    public BufferedWriter open(HiLogInfo info) throws IOException {

        String name = info.getName().get();

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

        if ((info.getName().isFixedSizeable()) && (l > this.limitsSize)) {

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

            this.bos = new BufferedWriter(new FileWriter(name, true));
        }

        return this.bos;
    }

    public void clear() {
    }

    public void flush() throws IOException {
    }

    public synchronized void close() throws IOException {

        if (this.bos == null) {

            return;
        }

        this.bos.close();

        this.bos = null;
    }
}