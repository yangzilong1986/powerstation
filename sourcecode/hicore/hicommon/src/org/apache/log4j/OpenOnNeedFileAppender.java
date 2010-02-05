package org.apache.log4j;

import com.hisun.util.HiICSProperty;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class OpenOnNeedFileAppender extends DailyRollingFileAppender implements ErrorHandler {
    private String scheduledFilename;

    protected boolean checkEntryConditions() {
        if (this.closed) {
            LogLog.warn("Not allowed to write to a closed appender.");
            return false;
        }

        if (this.layout == null) {
            this.errorHandler.error("No layout set for the appender named [" + this.name + "].");

            return false;
        }
        return true;
    }

    public OpenOnNeedFileAppender(Layout layout, String filename, boolean append) throws IOException {
        this.layout = layout;

        this.fileName = filename;
        this.fileAppend = append;
    }

    protected void subAppend(LoggingEvent event) {
        checkFileExist();
        if (this.qw == null) {
            activateOptions();

            File file = new File(this.fileName);
            this.scheduledFilename = this.fileName + this.sdf.format(new Date(file.lastModified()));

            this.qw.setErrorHandler(this);
        }

        super.subAppend(event);
        this.qw.flush();
    }

    private void checkFileExist() {
        File file = new File(this.fileName);
        if (file.exists()) return;
        try {
            if (this.qw != null) this.qw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.qw = null;
    }

    void rollOver() throws IOException {
        String datedFilename = this.fileName + this.sdf.format(this.now);

        if (this.scheduledFilename.equals(datedFilename)) {
            return;
        }

        closeFile();

        this.fileName = calculateFileName(this.fileName);
        try {
            setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            this.errorHandler.error("setFile(" + this.fileName + ", false) call failed.");
        }
        this.scheduledFilename = datedFilename;
    }

    private String calculateFileName(String fileName) {
        int idx = fileName.lastIndexOf(File.separator);
        String name = fileName.substring(idx + 1);
        idx = name.indexOf(46);
        if (idx == -1) {
            return HiICSProperty.getLogDir() + name;
        }
        String suffix = name.substring(idx + 1);
        if ("trc".equals(suffix)) return HiICSProperty.getTrcDir() + name;
        if ("log".equals(suffix)) {
            return HiICSProperty.getLogDir() + name;
        }
        return HiICSProperty.getLogDir() + name;
    }

    public void error(String message) {
    }

    public void error(String message, Exception e, int errorCode) {
        e.printStackTrace();
        try {
            this.qw.close();
            this.qw = null;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        activateOptions();
        this.qw.setErrorHandler(this);
    }

    public void error(String message, Exception e, int errorCode, LoggingEvent event) {
    }

    public void setAppender(Appender appender) {
    }

    public void setBackupAppender(Appender appender) {
    }

    public void setLogger(Logger logger) {
    }
}