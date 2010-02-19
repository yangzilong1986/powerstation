package com.hzjbbis.fk.utils;

import sun.misc.Launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class ClassLoaderUtil {
    private static Field classes;
    private static Method addURL;
    private static URLClassLoader system;
    private static URLClassLoader ext;

    static {
        try {
            classes = ClassLoader.class.getDeclaredField("classes");
            addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        classes.setAccessible(true);
        addURL.setAccessible(true);

        system = (URLClassLoader) getSystemClassLoader();

        ext = (URLClassLoader) getExtClassLoader();
    }

    public static ClassLoader getSystemClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    public static ClassLoader getExtClassLoader() {
        return getSystemClassLoader().getParent();
    }

    public static List<Class<?>> getClassesLoadedBySystemClassLoader() {
        return getClassesLoadedByClassLoader(getSystemClassLoader());
    }

    public static List<Class<?>> getClassesLoadedByExtClassLoader() {
        return getClassesLoadedByClassLoader(getExtClassLoader());
    }

    public static List<Class<?>> getClassesLoadedByClassLoader(ClassLoader cl) {
        try {
            return ((List) classes.get(cl));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static URL[] getBootstrapURLs() {
        return Launcher.getBootstrapClassPath().getURLs();
    }

    public static URL[] getSystemURLs() {
        return system.getURLs();
    }

    public static URL[] getExtURLs() {
        return ext.getURLs();
    }

    private static void list(PrintStream ps, URL[] classPath) {
        for (int i = 0; i < classPath.length; ++i)
            ps.println(classPath[i]);
    }

    public static void listBootstrapClassPath() {
        listBootstrapClassPath(System.out);
    }

    public static void listBootstrapClassPath(PrintStream ps) {
        ps.println("BootstrapClassPath:");
        list(ps, getBootstrapClassPath());
    }

    public static void listSystemClassPath() {
        listSystemClassPath(System.out);
    }

    public static void listSystemClassPath(PrintStream ps) {
        ps.println("SystemClassPath:");
        list(ps, getSystemClassPath());
    }

    public static void listExtClassPath() {
        listExtClassPath(System.out);
    }

    public static void listExtClassPath(PrintStream ps) {
        ps.println("ExtClassPath:");
        list(ps, getExtClassPath());
    }

    public static URL[] getBootstrapClassPath() {
        return getBootstrapURLs();
    }

    public static URL[] getSystemClassPath() {
        return getSystemURLs();
    }

    public static URL[] getExtClassPath() {
        return getExtURLs();
    }

    public static void addURL2SystemClassLoader(URL url) {
        try {
            addURL.invoke(system, new Object[]{url});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addURL2ExtClassLoader(URL url) {
        try {
            addURL.invoke(ext, new Object[]{url});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addClassPath(String path) {
        addClassPath(new File(path));
    }

    public static void addExtClassPath(String path) {
        addExtClassPath(new File(path));
    }

    public static void addClassPath(File dirOrJar) {
        try {
            addURL2SystemClassLoader(dirOrJar.toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addExtClassPath(File dirOrJar) {
        try {
            addURL2ExtClassLoader(dirOrJar.toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initializeClassPath() {
        String workDir = System.getProperty("user.dir");
        String classRootPath = PathUtil.getRootPath(ClassLoaderUtil.class);

        int cnt = 1;
        if ((classRootPath != null) && (cnt-- > 0)) {
            try {
                File froot = new File(classRootPath);
                File fwork = new File(workDir);
                boolean same = froot.getCanonicalPath().equalsIgnoreCase(fwork.getCanonicalPath());
                if (!(same)) {
                    File fup = froot.getParentFile();
                    boolean upsame = fup.getCanonicalPath().equalsIgnoreCase(fwork.getCanonicalPath());
                    if (!(upsame)) {
                        boolean rootIsPlugins = froot.getName().equalsIgnoreCase("plugins");
                        if (rootIsPlugins) {
                            System.setProperty("user.dir", fup.getCanonicalPath());
                            workDir = fup.getCanonicalPath();
                        } else if ((froot.getName().equalsIgnoreCase("lib")) || (froot.getName().equalsIgnoreCase("libs"))) {
                            addDir2ClassPath(froot.getAbsolutePath(), true);
                            addClassPath(fup.getAbsolutePath());

                            System.setProperty("user.dir", fup.getCanonicalPath());
                            workDir = fup.getCanonicalPath();
                        } else if ((fup.getName().equalsIgnoreCase("lib")) || (fup.getName().equalsIgnoreCase("libs"))) {
                            File fupup = fup.getParentFile();

                            addDir2ClassPath(fup.getAbsolutePath(), true);
                            addClassPath(fupup.getAbsolutePath());

                            System.setProperty("user.dir", fupup.getCanonicalPath());
                            workDir = fupup.getCanonicalPath();
                        } else {
                            boolean upIsPlugins = fup.getName().equalsIgnoreCase("plugins");
                            if (upIsPlugins) {
                                System.setProperty("user.dir", fup.getParentFile().getCanonicalPath());
                            }
                        }
                    }
                }
            } catch (Exception exp) {
                exp.printStackTrace();
                return;
            }
        }

        File file = new File("log");
        if ((file != null) && (!(file.isDirectory()))) {
            file.mkdir();
        }

        addDir2ClassPath(".", false);
        addDir2ClassPath(workDir + File.separator + "classess", false);
        addDir2ClassPath(workDir + File.separator + "lib", true);
        addDir2ClassPath(workDir + File.separator + "libs", true);
        addDir2ClassPath(workDir + File.separator + "config", false);
        addDir2ClassPath(workDir + File.separator + "images", false);
        addDir2ClassPath(workDir + File.separator + "icons", false);
        addDir2ClassPath(workDir + File.separator + "configuration", false);
        addDir2ClassPath(workDir + File.separator + "cfg", false);
        listSystemClassPath();
        loadPropertieFile2SystemProperties();
    }

    private static void addFile2ClassPath(String filename) {
        File file = new File(filename);
        if (!(file.isFile())) return;
        if (filename.toLowerCase().endsWith(".jar")) {
            addClassPath(file);
        }
        if (filename.toLowerCase().endsWith(".zip")) addClassPath(file);
    }

    private static void addDir2ClassPath(String dir, boolean includeSub) {
        File file = new File(dir);
        if ((!(file.exists())) || (!(file.isDirectory()))) {
            return;
        }
        if (dir.equalsIgnoreCase(".")) {
            classPath = null;
            try {
                classPath = file.getCanonicalPath();
            } catch (Exception exp) {
                return;
            }
            addClassPath(classPath);
            return;
        }

        dir = file.getName();
        String classPath = null;
        try {
            classPath = file.getCanonicalPath();
        } catch (Exception exp) {
            return;
        }

        if ((dir.equalsIgnoreCase("com")) || (dir.equalsIgnoreCase("org"))) {
            addClassPath(file.getParent());
            return;
        }
        if ((dir.equalsIgnoreCase("config")) || (dir.equalsIgnoreCase("configuration")) || (dir.equalsIgnoreCase("classes")) || (dir.equalsIgnoreCase("cfg"))) {
            addClassPath(classPath);
            return;
        }

        if (!(includeSub)) {
            return;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; ++i)
            if (files[i].isFile()) {
                addFile2ClassPath(files[i].getPath());
            } else {
                if (!(files[i].isDirectory())) continue;
                addDir2ClassPath(files[i].getPath(), includeSub);
            }
    }

    public static final void loadPropertieFile2SystemProperties() {
        String curPath = System.getProperty("user.dir");
        String propFilePattern = ".*\\.properties";
        Pattern pattern = Pattern.compile(propFilePattern);
        FilenameFilter filter = new FilenameFilter(pattern) {
            public boolean accept(File dir, String name) {
                return ClassLoaderUtil.this.matcher(name).matches();
            }
        };
        searchPropertiesFile(curPath, filter);
        searchPropertiesFile(curPath + File.separator + "cfg", filter);
        searchPropertiesFile(curPath + File.separator + "config", filter);
        searchPropertiesFile(curPath + File.separator + "configuration", filter);
        searchPropertiesFile(curPath + File.separator + "bin", filter);
        searchPropertiesFile(curPath + File.separator + "classess", filter);
    }

    private static final void searchPropertiesFile(String dirPath, FilenameFilter filter) {
        try {
            File f = new File(dirPath);
            if ((!(f.exists())) || (!(f.isDirectory()))) {
                return;
            }
            File[] pfs = f.listFiles(filter);
            for (File pf : pfs) {
                Properties props = new Properties();
                props.load(new FileInputStream(pf));
                Enumeration pnames = props.propertyNames();
                while (pnames.hasMoreElements()) {
                    String propName = (String) pnames.nextElement();
                    String propValue = props.getProperty(propName);
                    System.setProperty("app." + propName, propValue);
                    System.out.println("add sys propertie:(app." + propName + "," + propValue + ")");
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}