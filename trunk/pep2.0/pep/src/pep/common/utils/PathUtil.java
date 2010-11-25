/**
 * @Description:
 * @author lijun
 * @date 2010-11-25 11:22:08
 */
package pep.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtil {
    private final static Logger log = LoggerFactory.getLogger(PathUtil.class);
    /**
     * 获取一个类的class文件所在的绝对路径。 这个类可以是JDK自身的类，也可以是用户自定义的类，或者是第三方开发包里的类。
     * 只要是在本程序中可以被加载的类，都可以定位到它的class文件的绝对路径。
     * @param cls
     * @return 这个类的class文件位置的绝对路径。 如果没有这个类的定义，则返回null。
     * @throws IOException
     */
    public static String getPathFromClass(Class<?> cls) throws IOException {
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

    /**
     *获取类的class文件的根目录
     * @param cls
     * @return
     */
    public static String getRootPath(Class<?> cls) {
        try {
            if (null == cls) {
                cls = PathUtil.class;
            }
            String classPath = getPathFromClass(cls);
            if (null == classPath) {
                return null;
            }
            String lowClassPath = classPath.toLowerCase();
            if (lowClassPath.endsWith(".jar")
                    || lowClassPath.endsWith(".zip")) {
                File file = new File(classPath);
                return file.getParent();
            } else {
                String className = cls.getName().replace('.', File.separatorChar);
                int index = classPath.lastIndexOf(className);
                if (index < 0) {
                    return null;
                }
                return classPath.substring(0, index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param filename
     * @return
     */
    public static String getConfigFilePath(String filename) {
        try {
            //检测当前工作目录
            File file = new File(filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            //检测当前工作目录下子目录
            String curPath = System.getProperty("user.dir") + File.separator;
            file = new File(curPath + "config" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            file = new File(curPath + "configuration" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            file = new File(curPath + "cfg" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            //相对路进
            String rootPath = getRootPath(null);
            if (null == rootPath) {
                return null;
            }
            if (rootPath.charAt(rootPath.length() - 1) != File.separatorChar) {
                rootPath += File.separator;
            }
            String path = rootPath + filename;
            file = new File(path);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            //配置文件不在class文件根目录下。需要检测config、cfg、configuration目录
            file = new File(rootPath + "config" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            file = new File(rootPath + "configuration" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            file = new File(rootPath + "cfg" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            //检测当前类根目录的上一级目录
            file = new File(rootPath);
            rootPath = file.getParent() + File.separator;
            file = new File(rootPath + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }

            //检测当前类根目录的上一级目录的下级子目录（config cfg configuration)
            file = new File(rootPath + "config" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            file = new File(rootPath + "configuration" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
            file = new File(rootPath + "cfg" + File.separator + filename);
            if (file.exists()) {
                return file.getCanonicalPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个方法可以通过与某个类的class文件的相对路径来获取文件或目录的绝对路径。 通常在程序中很难定位某个相对路径，特别是在B/S应用中。
     * 通过这个方法，我们可以根据我们程序自身的类文件的位置来定位某个相对路径。
     * 比如：某个txt文件相对于程序的Test类文件的路径是../../resource/test.txt，
     * 那么使用本方法Path.getFullPathRelateClass("../../resource/test.txt",Test.class)
     * 得到的结果是txt文件的在系统中的绝对路径。
     * @param relatedPath
     * @param cls
     * @return
     * @throws IOException
     */
    public static String getFullPathRelateClass(String relatedPath, Class<?> cls)
			throws IOException {
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

    /**
     * 获取类的class文件位置的URL。这个方法是本类最基础的方法，供其它方法调用。
     * @param cls
     * @return
     */
    private static URL getClassLocationURL(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("null input: cls");
        }
        URL result = null;
        final String clsAsResource = cls.getName().replace('.', '/').concat(
                ".class");
        final ProtectionDomain pd = cls.getProtectionDomain();
        if (pd != null) {
            final CodeSource cs = pd.getCodeSource();

            if (cs != null) {
                result = cs.getLocation();
            }

            if (result != null) {

                if ("file".equals(result.getProtocol())) {
                    try {
                        if (result.toExternalForm().endsWith(".jar")
                                || result.toExternalForm().endsWith(".zip")) {
                            result = new URL("jar:".concat(
                                    result.toExternalForm()).concat("!/").concat(clsAsResource));
                        } else if (new File(result.getFile()).isDirectory()) {
                            result = new URL(result, clsAsResource);
                        }
                    } catch (MalformedURLException ignore) {
                    }
                }
            }
        }

        if (result == null) {
            final ClassLoader clsLoader = cls.getClassLoader();
            result = clsLoader != null ? clsLoader.getResource(clsAsResource)
                    : ClassLoader.getSystemResource(clsAsResource);
        }
        return result;
    }
}
