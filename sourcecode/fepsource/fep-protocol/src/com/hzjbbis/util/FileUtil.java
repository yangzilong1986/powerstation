package com.hzjbbis.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static File mkdirs(String path) {
        File dir = new File(path);
        if (dir.isFile()) {
            throw new IllegalArgumentException(path + " is not a directory");
        }

        if (!(dir.exists())) {
            dir.mkdirs();
        }

        return dir;
    }

    public static File openFile(String path, String fileName) {
        File dir = mkdirs(path);
        File file = new File(dir, fileName);
        if (!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException("Error to open file: " + fileName, ex);
            }
        }

        return file;
    }

    public static void deleteFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (file.exists()) file.delete();
    }

    public static String getAbsolutePath(String path) {
        File f = new File(path);
        return f.getAbsolutePath();
    }

    public static String getAbsolutePath(String path, String fileName) {
        File dir = mkdirs(getAbsolutePath(path));
        File file = new File(dir, fileName);
        return file.getAbsolutePath();
    }
}