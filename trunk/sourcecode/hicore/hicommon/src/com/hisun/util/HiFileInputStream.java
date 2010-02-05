package com.hisun.util;

import com.hisun.exception.HiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HiFileInputStream {
    private FileInputStream fis = null;

    private String file = null;

    public HiFileInputStream(File f) throws HiException {
        this.file = f.getName();
        try {
            this.fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new HiException("212004", f.getName(), e);
        }
    }

    public HiFileInputStream(String file) throws HiException {
        this.file = file;
        try {
            this.fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new HiException("212004", file, e);
        }
    }

    public HiByteBuffer read(HiByteBuffer buffer) throws HiException {
        int len = 0;
        byte[] bs = new byte[1024];
        try {
            while ((len = this.fis.read(bs, 0, bs.length)) != -1) buffer.append(bs, 0, len);
        } catch (IOException e) {
            throw new HiException("220079", this.file, e);
        }
        return buffer;
    }

    public void close() throws HiException {
        try {
            if (this.fis != null) this.fis.close();
        } catch (IOException e) {
            throw new HiException("220079", this.file, e);
        }
    }

    public static HiByteBuffer read(String file, HiByteBuffer buffer) throws HiException {
        HiFileInputStream fis = null;
        try {
            fis = new HiFileInputStream(file);
            buffer = fis.read(buffer);
        } finally {
            if (fis != null) fis.close();
        }
        return buffer;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}