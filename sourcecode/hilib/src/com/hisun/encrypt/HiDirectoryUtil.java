package com.hisun.encrypt;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class HiDirectoryUtil {
    public static void createDirectory(String dirname) {

        File dir = new File(dirname);

        if (!(dir.exists())) dir.mkdirs();
    }

    public static void deleteDirectory(String dirname) {

        File dir = new File(dirname);


        if (dir.isDirectory()) {

            File[] filelist = dir.listFiles();

            for (int i = 0; i < filelist.length; ++i) {

                File thisfile = filelist[i];

                if (thisfile.isDirectory()) deleteDirectory(thisfile.getAbsolutePath());
                else {

                    thisfile.delete();
                }
            }

            dir.delete();
        }
    }

    public static void copyFile(String srcFile, String destFile) {

        copyFile(new File(srcFile), new File(destFile));
    }

    public static void copyFile(File srcFile, File destFile) {
        try {

            byte[] b = new byte[(int) srcFile.length()];

            FileInputStream fis = new FileInputStream(srcFile);

            fis.read(b);

            fis.close();


            FileOutputStream fos = new FileOutputStream(destFile);

            fos.write(b);

            fos.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String loadFileContent(String filename) {

        String s = "";
        try {

            File file = new File(filename);

            if (file.exists()) {

                byte[] b = new byte[(int) file.length()];

                FileInputStream fis = new FileInputStream(file);

                fis.read(b);

                fis.close();

                s = new String(b);
            }
        } catch (Exception e) {

            e.printStackTrace();

            s = "";
        }

        return s;
    }

    public static void writeFileContent(String filename, String content) {
        try {

            File file = new File(filename);

            FileOutputStream fos = new FileOutputStream(file);

            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public String loadResourceFileContent(String filename) {

        String s = "";

        InputStream is = super.getClass().getResourceAsStream(filename);

        if (is != null) {
            try {

                byte[] b = new byte[is.available()];

                is.read(b);

                is.close();

                s = new String(b);
            } catch (Exception e) {

                e.printStackTrace();

                s = "";
            }
        }

        return s;
    }

    public static void main(String[] args) {

        deleteDirectory("d:/project/eclipse/tmp-extract");
    }
}