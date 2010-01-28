package com.hisun.file;


import com.hisun.exception.HiException;
import com.hisun.util.HiICSProperty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


public class HiFileUtil {

    private static void saveFile(String parent, String name, String data, boolean append) throws HiException {

        FileWriter fw = null;

        try {

            File f1 = new File(parent);

            if (!(f1.exists())) {

                f1.mkdirs();

            }


            File f2 = new File(f1, name);

            f2.createNewFile();

            fw = new FileWriter(f2, append);

            fw.write(data);

        } catch (Exception e) {

        } finally {

            try {

                if (fw != null) fw.close();

            } catch (IOException e) {

            }

        }

    }


    public static void saveFile(String name, String data) throws HiException {

        saveFile(null, name, data, false);

    }


    public static void appendFile(String name, String data) throws HiException {

        saveFile(null, name, data, true);

    }


    public static void saveTempFile(String name, String data) throws HiException {

        saveFile(HiICSProperty.getTmpDir(), name, data, false);

    }


    public static void saveTempFile(URL url, String data) throws HiException {

        File f = new File(url.getFile());

        saveFile(HiICSProperty.getTmpDir(), f.getName(), data, false);

    }

}