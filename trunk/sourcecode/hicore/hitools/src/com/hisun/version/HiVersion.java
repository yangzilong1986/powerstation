package com.hisun.version;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class HiVersion {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("hiversion [-d|-f] jarfile");
            System.exit(-1);
        }
        ArrayList versionFiles = new ArrayList();

        if ("-d".equals(args[0])) {
            dumpDirVersion(args[1], versionFiles);
        } else if ("-f".equals(args[0])) {
            dumpFileVersion(args[1], versionFiles);
        } else {
            System.out.println(args[0] + " invalid; hiversion [-d|-f] jarfile");
            System.exit(-1);
        }

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < versionFiles.size(); ++i) {
            buf.setLength(0);
            HiFileVersionInfo info = (HiFileVersionInfo) versionFiles.get(i);
            buf.append(info.getFile());
            for (int j = info.getFile().length(); j < 40; ++j) {
                buf.append(" ");
            }
            buf.append(" version:[" + info.getVersion() + "]\t compile time:[" + info.getCompileTm() + "]");
            System.out.println(buf);
        }
    }

    public static void dumpFileVersion(String file, ArrayList list) throws IOException {
        JarFile jf = new JarFile(file, true);
        Manifest mf = jf.getManifest();

        Attributes attrs = mf.getMainAttributes();
        String version = attrs.getValue("IBS-Integrator-Module-Version");
        String compileTm = attrs.getValue("IBS-Integrator-Module-Compile-Time");
        if ((version == null) || (compileTm == null)) {
            return;
        }
        list.add(new HiFileVersionInfo(file, version, compileTm));
    }

    public static void dumpDirVersion(String dir, ArrayList versionFiles) throws IOException {
        File f = new File(dir);
        String[] flist = f.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                int idx = name.indexOf(46);
                if (idx == -1) {
                    return false;
                }
                return "jar".equals(name.substring(idx + 1));
            }
        });
        for (int i = 0; i < flist.length; ++i)
            dumpFileVersion(dir + "/" + flist[i], versionFiles);
    }
}