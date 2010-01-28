package com.hisun.hilog4j;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HiIndexLogCache implements ILogCache {
    private static final IFileName TXN_DATA_FILE = new HiTrcFileName("TXN.dat");
    private static final IFileName TXN_INDEX_FILE = new HiTrcFileName("TXN.idx");
    private RandomAccessFile dataBos = null;
    private RandomAccessFile idxBos = null;
    private int limitsSize;
    private String currentIndexFileName = TXN_DATA_FILE.get();
    private String currentDataFileName = TXN_INDEX_FILE.get();


    public HiIndexLogCache(int limitsSize) {

        this.limitsSize = limitsSize;

    }


    public void put(HiLogInfo info) throws IOException {

        open();

        int len = info.getBuf().length();

        long offset = this.dataBos.length();

        this.idxBos.writeBytes(info.getName().name() + "|" + offset + "|" + this.dataBos.length() + "\n");

        this.dataBos.write(info.getBuf().toString().getBytes());

    }


    public void flush() throws IOException {

        if (this.dataBos != null) {

            this.dataBos.close();

        }

        if (this.idxBos != null) this.idxBos.close();

    }


    public synchronized void open() throws IOException {

        openData();

        openIndex();

    }


    private void openData() throws IOException {

        String name = TXN_DATA_FILE.get();

        if (!(name.equals(this.currentDataFileName))) {

            if (this.dataBos != null) {

                this.dataBos.close();

                this.dataBos = null;

            }

            this.currentDataFileName = name;

        }

        File f1 = new File(name);

        if ((!(f1.exists())) && (this.dataBos != null)) {

            this.dataBos.close();

            this.dataBos = null;

        }


        if (f1.getParent() != null) {

            File f2 = new File(f1.getParent());

            if (!(f2.exists())) {

                f2.mkdirs();

            }

            f2 = null;

        }


        long l = f1.length();

        if (l > this.limitsSize) {

            if (this.dataBos != null) {

                this.dataBos.close();

                this.dataBos = null;

            }

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

            File f2 = new File(name + "." + df.format(new Date()));

            f1.renameTo(f2);

        }


        f1 = null;

        if (this.dataBos == null) this.dataBos = new RandomAccessFile(name, "rw");

    }


    private void openIndex() throws IOException {

        String name = TXN_INDEX_FILE.get();

        if (!(name.equals(this.currentIndexFileName))) {

            if (this.idxBos != null) {

                this.idxBos.close();

                this.idxBos = null;

            }

            this.currentIndexFileName = name;

        }


        File f1 = new File(name);

        if ((!(f1.exists())) && (this.idxBos != null)) {

            this.idxBos.close();

            this.idxBos = null;

        }


        if (f1.getParent() != null) {

            File f2 = new File(f1.getParent());

            if (!(f2.exists())) {

                f2.mkdirs();

            }

            f2 = null;

        }


        if (this.idxBos == null) this.idxBos = new RandomAccessFile(name, "rw");

    }


    public void clear() {

    }


    public void close() throws IOException {

    }

}