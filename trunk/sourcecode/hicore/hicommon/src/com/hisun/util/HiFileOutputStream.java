package com.hisun.util;

import com.hisun.exception.HiException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HiFileOutputStream {
    private String file = null;

    private FileOutputStream fos = null;

    public HiFileOutputStream(String file) throws HiException {
        try {
            this.fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new HiException("220079", file);
        }
    }

    public void write(byte[] buffer) throws HiException {
        try {
            this.fos.write(buffer);
        } catch (IOException e) {
            throw new HiException("220079", this.file);
        }
    }

    public void write(HiByteBuffer buffer) throws HiException {
        write(buffer.getBytes());
    }

    public void close() throws HiException {
        try {
            this.fos.close();
        } catch (IOException e) {
            throw new HiException("220079", this.file);
        }
    }

    public static void write(String file, HiByteBuffer buffer) throws HiException {
        HiFileOutputStream fos = null;
        try {
            fos = new HiFileOutputStream(file);
            fos.write(buffer);
        } finally {
            if (fos != null) fos.close();
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.fos != null) this.fos.close();
    }
}