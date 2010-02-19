package com.hzjbbis.util;

import java.io.*;
import java.util.Calendar;
import java.util.Properties;

public class FileBasedProperties extends Properties {
    private static final long serialVersionUID = 3788399848195322525L;
    private String filepath;
    private boolean readOnly;

    public FileBasedProperties(String file) {
        this.filepath = getAbsolutePath(file);
        if (null == this.filepath) throw new RuntimeException("属性文件不存在：" + file);
        loadFromFile();
    }

    private String getAbsolutePath(String name) {
        File file = new File(name);
        if (file.exists()) return file.getAbsolutePath();
        int index = name.lastIndexOf(File.separator);
        if (index >= 0) name = name.substring(index, name.length());
        else {
            name = File.separator + name;
        }

        String workDir = System.getProperty("user.dir");
        String cfgpath = workDir + File.separator + "config" + name;
        file = new File(cfgpath);
        if (file.exists()) return file.getAbsolutePath();
        cfgpath = workDir + File.separator + "configuration" + name;
        file = new File(cfgpath);
        if (file.exists()) return file.getAbsolutePath();
        cfgpath = workDir + File.separator + "cfg" + name;
        file = new File(cfgpath);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        String classRoot = PathUtil.getRootPath(FileBasedProperties.class);
        String path = classRoot + name;
        file = new File(path);
        if (file.exists()) return file.getAbsolutePath();
        file = new File(classRoot);

        classRoot = file.getParentFile().getAbsolutePath();
        path = classRoot + name;
        file = new File(path);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        cfgpath = classRoot + File.separator + "config" + name;
        file = new File(cfgpath);
        if (file.exists()) return file.getAbsolutePath();
        cfgpath = classRoot + File.separator + "configuration" + name;
        file = new File(cfgpath);
        if (file.exists()) return file.getAbsolutePath();
        cfgpath = classRoot + File.separator + "cfg" + name;
        file = new File(cfgpath);
        if (file.exists()) return file.getAbsolutePath();
        return null;
    }

    public FileBasedProperties(String file, Properties defaults) {
        super(defaults);
        this.filepath = getAbsolutePath(file);
        if (null == this.filepath) throw new RuntimeException("属性文件不存在：" + file);
        loadFromFile();
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        value = value.trim();
        if (value.length() == 0) {
            return defaultValue;
        }

        return Boolean.valueOf(value).booleanValue();
    }

    public int getInt(String key, int defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        value = value.trim();
        if (value.length() == 0) {
            return defaultValue;
        }

        return Integer.parseInt(value);
    }

    public Calendar getDate(String key) {
        String value = getProperty(key);
        return CalendarUtil.parse(value);
    }

    public Calendar getDate(String key, Calendar defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        return CalendarUtil.parse(value, defaultValue);
    }

    public long getSize(String key, long defaultValue) {
        int index;
        String value = getProperty(key);
        if ((value == null) || (value.trim().length() == 0)) {
            return defaultValue;
        }

        String s = value.trim().toUpperCase();
        if (s.length() == 0) {
            return defaultValue;
        }

        long multiplier = 1L;

        if ((index = s.indexOf("KB")) == -1) if ((index = s.indexOf("K")) == -1) break label94;
        multiplier = 1024L;
        s = s.substring(0, index);
        break label183:

        if ((index = s.indexOf("MB")) == -1) label94:if ((index = s.indexOf("M")) == -1) break label140;
        multiplier = 1048576L;
        s = s.substring(0, index);
        break label183:

        if ((index = s.indexOf("GB")) == -1) label140:if ((index = s.indexOf("G")) == -1) break label183;
        multiplier = 1073741824L;
        s = s.substring(0, index);

        label183:
        return (Long.parseLong(s) * multiplier);
    }

    public synchronized Object setProperty(String key, String value) {
        Object oldValue = super.setProperty(key, value);
        if (!(this.readOnly)) {
            OutputStream out = null;
            try {
                File f = new File(this.filepath);
                if (!(f.exists())) {
                    f.createNewFile();
                }
                out = new FileOutputStream(f);
                super.store(out, null);
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        return oldValue;
    }

    public void loadFromFile() {
        File f = new File(this.filepath);
        InputStream in = null;
        if (f.exists()) {
            this.readOnly = false;
            try {
                in = new FileInputStream(f);
            } catch (FileNotFoundException e) {
            }
        } else {
            in = FileBasedProperties.class.getResourceAsStream(this.filepath);
            if (in != null) {
                this.readOnly = true;
            }
        }

        if (in == null) return;
        try {
            super.load(in);
        } catch (IOException ex) {
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }
    }
}