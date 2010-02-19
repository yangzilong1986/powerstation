package com.hzjbbis.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class PathUtil {
    public static String getPathFromClass(Class cls) throws IOException {
        String path = null;
        if (cls == null) {
            throw new NullPointerException();
        }
        URL url = getClassLocationURL(cls);
        if (url != null) {
            path = url.getPath();
            if ("jar".equalsIgnoreCase(url.getProtocol())) {
                try {
                    path = new URL(path).getPath();
                } catch (MalformedURLException e) {
                }
                int location = path.indexOf("!/");
                if (location != -1) {
                    path = path.substring(0, location);
                }
            }
            File file = new File(path);
            path = file.getCanonicalPath();
        }
        return path;
    }

    public static String getFullPathRelateClass(String relatedPath, Class cls) throws IOException {
        String path = null;
        if (relatedPath == null) {
            throw new NullPointerException();
        }
        String clsPath = getPathFromClass(cls);
        File clsFile = new File(clsPath);
        String tempPath = clsFile.getParent() + File.separator + relatedPath;
        File file = new File(tempPath);
        path = file.getCanonicalPath();
        return path;
    }

    private static URL getClassLocationURL(Class cls) {
        if (cls == null) throw new IllegalArgumentException("null input: cls");
        URL result = null;
        String clsAsResource = cls.getName().replace('.', '/').concat(".class");

        ProtectionDomain pd = cls.getProtectionDomain();

        if (pd != null) {
            CodeSource cs = pd.getCodeSource();

            if (cs != null) {
                result = cs.getLocation();
            }
            if ((result != null) && ("file".equals(result.getProtocol()))) {
                try {
                    if ((result.toExternalForm().endsWith(".jar")) || (result.toExternalForm().endsWith(".zip"))) {
                        result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
                    } else if (new File(result.getFile()).isDirectory()) result = new URL(result, clsAsResource);
                } catch (MalformedURLException ignore) {
                }
            }
        }
        if (result == null) {
            ClassLoader clsLoader = cls.getClassLoader();
            result = (clsLoader != null) ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
        }

        return result;
    }

    public static String getRootPath(Class cls) {
        try {
            if (null == cls) cls = PathUtil.class;
            String classPath = getPathFromClass(cls);
            if (null == classPath) return null;
            String lowClassPath = classPath.toLowerCase();
            if ((lowClassPath.endsWith(".jar")) || (lowClassPath.endsWith(".zip"))) {
                File file = new File(classPath);
                return file.getParent();
            }

            String className = cls.getName().replace('.', File.separatorChar);
            int index = classPath.lastIndexOf(className);
            if (index < 0) return null;
            return classPath.substring(0, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getConfigFilePath(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            String curPath = System.getProperty("user.dir") + File.separator;
            file = new File(curPath + "config" + File.separator + filename);
            if (file.exists()) return file.getCanonicalPath();
            file = new File(curPath + "configuration" + File.separator + filename);
            if (file.exists()) return file.getCanonicalPath();
            file = new File(curPath + "cfg" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            String rootPath = getRootPath(null);
            if (null == rootPath) return null;
            if (rootPath.charAt(rootPath.length() - 1) != File.separatorChar) rootPath = rootPath + File.separator;
            String path = rootPath + filename;
            file = new File(path);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            file = new File(rootPath + "config" + File.separator + filename);
            if (file.exists()) return file.getCanonicalPath();
            file = new File(rootPath + "configuration" + File.separator + filename);
            if (file.exists()) return file.getCanonicalPath();
            file = new File(rootPath + "cfg" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            file = new File(rootPath);
            rootPath = file.getParent() + File.separator;
            file = new File(rootPath + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            file = new File(rootPath + "config" + File.separator + filename);
            if (file.exists()) return file.getCanonicalPath();
            file = new File(rootPath + "configuration" + File.separator + filename);
            if (file.exists()) return file.getCanonicalPath();
            file = new File(rootPath + "cfg" + File.separator + filename);
            if (file.exists()) return file.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            System.out.println("path from class 'PathUtil.class'=" + getPathFromClass(PathUtil.class));
            System.out.println("PathUtil's class root path=" + getRootPath(PathUtil.class));

            System.out.println(getConfigFilePath("fas.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}