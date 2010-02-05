package com.hisun.loader;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.util.HiICSProperty;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public final class HiClassLoaderFactory {
    private static Logger log = HiLog.getLogger("loader.trc");

    protected static final Integer IS_DIR = new Integer(0);

    protected static final Integer IS_JAR = new Integer(1);

    protected static final Integer IS_GLOB = new Integer(2);

    protected static final Integer IS_URL = new Integer(3);

    public static ClassLoader createClassLoader(File[] unpacked, File[] packed, ClassLoader parent) throws Exception {
        return createClassLoader(unpacked, packed, null, parent);
    }

    public static ClassLoader createClassLoader(File[] unpacked, File[] packed, URL[] urls, ClassLoader parent) throws Exception {
        int i;
        ArrayList list = new ArrayList();

        if (unpacked != null) {
            for (i = 0; i < unpacked.length; ++i) {
                File file = unpacked[i];
                if (!(file.exists())) continue;
                if (!(file.canRead())) continue;
                file = new File(file.getCanonicalPath() + File.separator);
                URL url = file.toURL();
                log.debug("  Including directory " + url);
                list.add(url);
            }

        }

        if (packed != null) {
            for (i = 0; i < packed.length; ++i) {
                File directory = packed[i];
                if ((!(directory.isDirectory())) || (!(directory.exists()))) continue;
                if (!(directory.canRead())) {
                    continue;
                }
                String[] filenames = directory.list();
                for (int j = 0; j < filenames.length; ++j) {
                    String filename = filenames[j].toLowerCase();
                    if (!(filename.endsWith(".jar"))) continue;
                    File file = new File(directory, filenames[j]);
                    log.debug("  Including jar file " + file.getAbsolutePath());

                    URL url = file.toURL();
                    list.add(url);
                }
            }

        }

        URL[] array = (URL[]) (URL[]) list.toArray(new URL[list.size()]);
        ClassLoader classLoader = null;
        if (parent == null) classLoader = new HiStandardClassLoader(array);
        else classLoader = new HiStandardClassLoader(array, parent);
        return classLoader;
    }

    public static ClassLoader createClassLoader(String[] locations, Integer[] types, ClassLoader parent) throws Exception {
        ArrayList list = new ArrayList();

        if ((locations != null) && (types != null) && (locations.length == types.length)) {
            for (int i = 0; i < locations.length; ++i) {
                String location = locations[i];
                if (types[i] == IS_URL) {
                    URL url = new URL(location);
                    log.debug("  Including URL " + url);
                    list.add(url);
                } else {
                    File directory;
                    URL url;
                    if (types[i] == IS_DIR) {
                        directory = new File(location);
                        directory = new File(directory.getCanonicalPath());
                        if ((!(directory.exists())) || (!(directory.isDirectory()))) continue;
                        if (!(directory.canRead())) {
                            continue;
                        }
                        url = directory.toURL();
                        log.debug("  Including directory " + url);
                        list.add(url);
                    } else if (types[i] == IS_JAR) {
                        File file = new File(location);
                        file = new File(file.getCanonicalPath());
                        if (!(file.exists())) continue;
                        if (!(file.canRead())) continue;
                        url = file.toURL();
                        log.debug("  Including jar file " + url);
                        list.add(url);
                    } else if (types[i] == IS_GLOB) {
                        directory = new File(location);
                        if ((!(directory.exists())) || (!(directory.isDirectory()))) continue;
                        if (!(directory.canRead())) {
                            continue;
                        }
                        log.debug("  Including directory glob " + directory.getAbsolutePath());

                        String[] filenames = directory.list();
                        for (int j = 0; j < filenames.length; ++j) {
                            String filename = filenames[j].toLowerCase();
                            if (!(filename.endsWith(".jar"))) continue;
                            File file = new File(directory, filenames[j]);
                            file = new File(file.getCanonicalPath());
                            if (!(file.exists())) continue;
                            if (!(file.canRead())) continue;
                            log.debug("    Including glob jar file " + file.getAbsolutePath());

                            URL url = file.toURL();
                            list.add(url);
                        }
                    }
                }
            }
        }

        URL[] array = (URL[]) (URL[]) list.toArray(new URL[list.size()]);
        for (int i = 0; i < array.length; ++i) {
            log.debug("  location " + i + " is " + array[i]);
        }
        ClassLoader classLoader = null;
        if (parent == null) classLoader = new HiStandardClassLoader(array);
        else classLoader = new HiStandardClassLoader(array, parent);
        return classLoader;
    }

    public static ClassLoader createClassLoader(String name, String appName, ClassLoader parent) throws HiException {
        String value = HiICSProperty.getProperty(name + ".loader");
        log.debug("Creating new class loader:[" + name + "][" + value + "]");
        if ((value == null) || (value.equals(""))) {
            return parent;
        }
        ArrayList repositoryLocations = new ArrayList();
        ArrayList repositoryTypes = new ArrayList();

        StringTokenizer tokenizer = new StringTokenizer(value, ",");
        while (tokenizer.hasMoreElements()) {
            String repository = tokenizer.nextToken();
            try {
                URL url = new URL(repository);
                repositoryLocations.add(repository);
                repositoryTypes.add(IS_URL);
            } catch (MalformedURLException e) {
                if (repository.endsWith("*.jar")) {
                    repository = repository.substring(0, repository.length() - "*.jar".length());

                    repositoryLocations.add(repository);
                    repositoryTypes.add(IS_GLOB);
                } else if (repository.endsWith(".jar")) {
                    repositoryLocations.add(repository);
                    repositoryTypes.add(IS_JAR);
                } else {
                    repositoryLocations.add(repository);
                    repositoryTypes.add(IS_DIR);
                }
            }
        }
        repositoryLocations.add(HiICSProperty.getAppDir() + File.separator + appName);
        repositoryTypes.add(IS_DIR);
        repositoryLocations.add(HiICSProperty.getAppDir() + File.separator + appName + File.separator + "applib/");
        repositoryTypes.add(IS_GLOB);

        String[] locations = (String[]) (String[]) repositoryLocations.toArray(new String[0]);

        for (int j = 0; j < locations.length; ++j) {
            log.debug("locations:{" + locations[j] + "}");
        }
        Integer[] types = (Integer[]) (Integer[]) repositoryTypes.toArray(new Integer[0]);
        try {
            return createClassLoader(locations, types, parent);
        } catch (Exception e) {
            throw new HiException("", "crate classloader failure", e);
        }
    }

    public static ClassLoader createClassLoader(String name, ClassLoader parent) throws HiException {
        String value = HiICSProperty.getProperty(name + ".loader");

        log.debug("Creating new class loader:[" + name + "][" + value + "]");
        if ((value == null) || (value.equals(""))) {
            return parent;
        }
        ArrayList repositoryLocations = new ArrayList();
        ArrayList repositoryTypes = new ArrayList();

        StringTokenizer tokenizer = new StringTokenizer(value, ",");
        while (tokenizer.hasMoreElements()) {
            String repository = tokenizer.nextToken();
            try {
                URL url = new URL(repository);
                repositoryLocations.add(repository);
                repositoryTypes.add(IS_URL);
            } catch (MalformedURLException e) {
                if (repository.endsWith("*.jar")) {
                    repository = repository.substring(0, repository.length() - "*.jar".length());

                    repositoryLocations.add(repository);
                    repositoryTypes.add(IS_GLOB);
                } else if (repository.endsWith(".jar")) {
                    repositoryLocations.add(repository);
                    repositoryTypes.add(IS_JAR);
                } else {
                    repositoryLocations.add(repository);
                    repositoryTypes.add(IS_DIR);
                }
            }
        }
        String[] locations = (String[]) (String[]) repositoryLocations.toArray(new String[0]);

        Integer[] types = (Integer[]) (Integer[]) repositoryTypes.toArray(new Integer[0]);
        try {
            return createClassLoader(locations, types, parent);
        } catch (Exception e) {
            throw new HiException("", "crate classloader failure", e);
        }
    }
}