package com.hisun.web.servlet;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class FileUploadServlet extends HttpServlet {
    private String uploadPath;
    private String tempPath;
    private int maxFileSize;


    public void destroy() {

        super.destroy();

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setSizeThreshold(4096);

        ServletFileUpload upload = new ServletFileUpload(factory);

        upload.setFileSizeMax(this.maxFileSize * 1024 * 1024);

        upload.setHeaderEncoding("GBK");

        try {

            List fileItems = upload.parseRequest(request);

            Iterator iter = fileItems.iterator();


            while (iter.hasNext()) {

                FileItem item = (FileItem) iter.next();

                if (item.isFormField()) {

                    continue;

                }

                String orgName = item.getName();

                String fldName = item.getFieldName();


                if (StringUtils.isBlank(orgName)) {

                    continue;

                }


                int idx = orgName.lastIndexOf(92);

                if (idx != -1) {

                    orgName = orgName.substring(idx + 1);

                }


                File f1 = new File(request.getSession().getServletContext().getRealPath(this.uploadPath + orgName));


                item.write(f1);

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);

        }

    }


    public void init() throws ServletException {

        this.uploadPath = getServletConfig().getInitParameter("UpLoadPath");

        if (!(this.uploadPath.endsWith("/"))) {

            this.uploadPath += "/";

        }


        this.tempPath = getServletConfig().getInitParameter("TempPath");

        if (!(this.tempPath.endsWith("/"))) {

            this.tempPath += "/";

        }

        this.maxFileSize = NumberUtils.toInt(getServletConfig().getInitParameter("MaxFileSize"));


        if (!(new File(this.uploadPath).isDirectory())) {

            new File(this.uploadPath).mkdirs();

        }

        if (!(new File(this.tempPath).isDirectory())) new File(this.tempPath).mkdirs();

    }

}