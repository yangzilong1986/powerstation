package com.hisun.version;

public class HiFileVersionInfo {
    private String version;
    private String compileTm;
    private String file;

    public String getFile() {

        return this.file;
    }

    public void setFile(String file) {

        this.file = file;
    }

    public String getVersion() {

        return this.version;
    }

    public void setVersion(String version) {

        this.version = version;
    }

    public String getCompileTm() {

        return this.compileTm;
    }

    public void setCompileTm(String compileTm) {

        this.compileTm = compileTm;
    }

    public HiFileVersionInfo(String file, String version, String compileTm) {

        this.file = file;

        this.version = version;

        this.compileTm = compileTm;
    }
}