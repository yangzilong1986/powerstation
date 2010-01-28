package com.hisun.deploy;


import com.hisun.exception.HiException;
import com.hisun.util.HiResource;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class HiDeploymentHelper {
    private static String _type;
    private static HiDeploymentDescriptor descriptor = null;


    public static void init(String type, boolean reload) throws HiException {

        if ((!(reload)) && (_type == type) && (descriptor != null)) {

            return;

        }

        descriptor = null;

        _type = type;


        if (StringUtils.equalsIgnoreCase(type, "websphere")) {

            descriptor = new HiWSDescriptor();

        } else {

            descriptor = new HiWLDescriptor();

        }

    }


    public static void createDescriptor(String serviceName, String destPath) throws HiException {

        String fullPath = destPath + "/META-INF/";

        File path = new File(fullPath);

        if (!(path.exists())) {

            path.mkdirs();

        }


        if (descriptor == null) return;

        descriptor.createDescriptor(serviceName, fullPath);

    }


    public static void deployManage(String mngType, String serviceName) throws HiException {

        if ((!(StringUtils.equalsIgnoreCase(mngType, "install"))) && (!(StringUtils.equalsIgnoreCase(mngType, "uninstall")))) {

            throw new HiException("", "not valid deploy type");

        }


        URL fileUrl = HiResource.getResource("admin/ant.sh");

        if (fileUrl == null) {

            throw new HiException("", "file [admin/ant.sh] can't find");

        }

        String antSh = fileUrl.getFile();


        fileUrl = HiResource.getResource("admin/build.xml");

        if (fileUrl == null) {

            throw new HiException("", "file [admin/build.xml] can't find");

        }

        String buildFile = fileUrl.getFile();


        String antCmd = antSh + " " + buildFile + " " + serviceName + " " + mngType;


        System.out.println("AntCmd:[" + antCmd + "]");


        Process p = null;

        try {

            p = Runtime.getRuntime().exec(antCmd);

        } catch (IOException e) {

            e.printStackTrace();

        }


        try {

            if (p != null) {

                p.waitFor();


                if (p.exitValue() != 0) {

                    System.out.println("exit ret:" + p.exitValue());


                    byte[] bb = new byte[1024];

                    InputStream in = p.getErrorStream();

                    try {

                        in.read(bb);

                        System.out.println("error[" + new String(bb) + "]");

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            } else {

                System.out.println("Failure: get process is null.");

            }

        } catch (InterruptedException ie) {

            System.out.println(ie);

        }

    }


    public static Element loadTemplate(SAXReader saxReader, String file) throws HiException {

        URL fileUrl = HiResource.getResource(file);

        try {

            return saxReader.read(fileUrl).getRootElement();

        } catch (DocumentException e) {

            throw new HiException("", fileUrl.getFile(), e);

        }

    }

}