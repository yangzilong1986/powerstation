package com.hisun.encrypt;


import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;


public final class HiJarUtils {

    public static void jar(OutputStream out, File src) throws IOException {

        jar(out, new File[]{src}, null, null, null);

    }


    public static void jar(OutputStream out, File[] src) throws IOException {

        jar(out, src, null, null, null);

    }


    public static void jar(OutputStream out, File[] src, FileFilter filter) throws IOException {

        jar(out, src, filter, null, null);

    }


    public static void jar(OutputStream out, File[] src, FileFilter filter, String prefix, Manifest man) throws IOException {

        JarOutputStream jout;

        for (int i = 0; i < src.length; ++i) {

            if (!(src[i].exists())) {

                throw new FileNotFoundException(src.toString());

            }


        }


        if (man == null) jout = new JarOutputStream(out);

        else {

            jout = new JarOutputStream(out, man);

        }

        if ((prefix != null) && (prefix.length() > 0) && (!(prefix.equals("/")))) {

            if (prefix.charAt(0) == '/') {

                prefix = prefix.substring(1);

            }


            if (prefix.charAt(prefix.length() - 1) != '/') prefix = prefix + "/";

        } else {

            prefix = "";

        }

        JarInfo info = new JarInfo(jout, filter);

        for (int i = 0; i < src.length; ++i) {

            jar(src[i], prefix, info);

        }

        jout.close();

    }


    private static void jar(File src, String prefix, JarInfo info) throws IOException {

        JarOutputStream jout = info.out;

        if (src.isDirectory()) {

            prefix = prefix + src.getName() + "/";

            ZipEntry entry = new ZipEntry(prefix);

            entry.setTime(src.lastModified());

            entry.setMethod(0);

            entry.setSize(0L);

            entry.setCrc(0L);

            jout.putNextEntry(entry);

            jout.closeEntry();


            File[] files = src.listFiles(info.filter);

            for (int i = 0; i < files.length; ++i)

                jar(files[i], prefix, info);

        } else {

            if (!(src.isFile())) return;

            byte[] buffer = info.buffer;


            ZipEntry entry = new ZipEntry(prefix + src.getName());

            entry.setTime(src.lastModified());

            jout.putNextEntry(entry);


            FileInputStream in = new FileInputStream(src);


            while ((len = in.read(buffer, 0, buffer.length)) != -1) {

                int len;

                jout.write(buffer, 0, len);

            }

            in.close();

            jout.closeEntry();

        }

    }


    public static void unjar(String jarFile, String destDir) throws IOException {

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(jarFile));


        File dest = new File(destDir);

        unjar(in, dest);

    }


    public static void unjar(InputStream in, File dest) throws IOException {

        File file;

        File parent;

        OutputStream out;

        if (!(dest.exists())) {

            dest.mkdirs();

        }

        if (!(dest.isDirectory())) {

            throw new IOException("Destination must be a directory.");

        }

        JarInputStream jin = new JarInputStream(in);

        byte[] buffer = new byte[1024];


        ZipEntry entry = jin.getNextEntry();

        while (entry != null) {

            String fileName = entry.getName();

            if (fileName.charAt(fileName.length() - 1) == '/') {

                fileName = fileName.substring(0, fileName.length() - 1);

            }

            if (fileName.charAt(0) == '/') {

                fileName = fileName.substring(1);

            }

            if (File.separatorChar != '/') {

                fileName = fileName.replace('/', File.separatorChar);

            }

            file = new File(dest, fileName);

            if (entry.isDirectory()) {

                file.mkdirs();

                jin.closeEntry();

            } else {

                parent = file.getParentFile();

                if ((parent != null) && (!(parent.exists()))) {

                    parent.mkdirs();

                }


                out = new FileOutputStream(file);

                int len = 0;

                while ((len = jin.read(buffer, 0, buffer.length)) != -1) {

                    out.write(buffer, 0, len);

                }

                out.flush();

                out.close();

                jin.closeEntry();

                file.setLastModified(entry.getTime());

            }

            entry = jin.getNextEntry();

        }


        Manifest mf = jin.getManifest();

        if (mf != null) {

            file = new File(dest, "META-INF/MANIFEST.MF");

            parent = file.getParentFile();

            if (!(parent.exists())) {

                parent.mkdirs();

            }

            out = new FileOutputStream(file);

            mf.write(out);

            out.flush();

            out.close();

        }

        jin.close();

    }


    public static URL extractNestedJar(URL jarURL, File dest) throws IOException {

        if (!(jarURL.getProtocol().equals("jar"))) {

            return jarURL;

        }

        String destPath = dest.getAbsolutePath();

        URLConnection urlConn = jarURL.openConnection();

        JarURLConnection jarConn = (JarURLConnection) urlConn;


        String parentArchiveName = jarConn.getJarFile().getName();


        int length = Math.min(destPath.length(), parentArchiveName.length());

        int n = 0;

        while (n < length) {

            char a = destPath.charAt(n);

            char b = parentArchiveName.charAt(n);

            if (a != b) break;

            ++n;

        }


        parentArchiveName = parentArchiveName.substring(n);


        File archiveDir = new File(dest, parentArchiveName + "-contents");

        if ((!(archiveDir.exists())) && (!(archiveDir.mkdirs()))) {

            throw new IOException("Failed to create contents directory for archive, path=" + archiveDir.getAbsolutePath());

        }


        String archiveName = jarConn.getEntryName();

        File archiveFile = new File(archiveDir, archiveName);

        File archiveParentDir = archiveFile.getParentFile();

        if ((!(archiveParentDir.exists())) && (!(archiveParentDir.mkdirs()))) {

            throw new IOException("Failed to create parent directory for archive, path=" + archiveParentDir.getAbsolutePath());

        }


        InputStream archiveIS = jarConn.getInputStream();

        FileOutputStream fos = new FileOutputStream(archiveFile);

        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] buffer = new byte[4096];


        while ((read = archiveIS.read(buffer)) > 0) {

            int read;

            bos.write(buffer, 0, read);

        }

        archiveIS.close();

        bos.close();


        return archiveFile.toURL();

    }


    public static void main(String[] args) throws Exception {

        args = new String[3];

        args[1] = "d:/project/eclipse/publibs/lib/hicommon.jar";

        args[2] = "d:/project/eclipse/tmp-extract/";


        BufferedInputStream in = new BufferedInputStream(new FileInputStream(args[1]));


        File dest = new File(args[2]);

        unjar(in, dest);


        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(args[1]));


        File[] src = new File[args.length - 2];

        for (int i = 0; i < src.length; ++i) {

            src[i] = new File(args[(2 + i)]);

        }

        jar(out, src);


        HiDirectoryUtil.deleteDirectory(args[1]);

    }


    private static class JarInfo {
        public JarOutputStream out;
        public FileFilter filter;
        public byte[] buffer;


        public JarInfo(JarOutputStream out, FileFilter filter) {

            this.out = out;

            this.filter = filter;

            this.buffer = new byte[1024];

        }

    }

}