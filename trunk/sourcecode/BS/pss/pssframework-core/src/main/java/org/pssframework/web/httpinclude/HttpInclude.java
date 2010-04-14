/*******************************************************************************
 * Copyright (c) 2010 PSS Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PSS Corporation - initial API and implementation
 *******************************************************************************/
package org.pssframework.web.httpinclude;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * 
 * 用于include其它页面以用于布局,可以用于在freemarker,velocity的servlet环境应用中直接include其它http请求
 * 
 * 示例使用:
 * <pre>
 * $httpInclude.include("http://www.google.com");
 * $httpInclude.include("/servlet/head?p1=v1&p2=v2");
 * $httpInclude.include("/head.jsp");
 * $httpInclude.include("/head.do?p1=v1&p2=v2");
 * $httpInclude.include("/head.htm");
 * </pre>
 * 
 * @author PSS
 *
 */
public class HttpInclude {
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    public HttpInclude(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String include(String includePath) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream(8192);
            include(output,includePath);
            // TODO handle output encoding 
            String result = output.toString("UTF-8");
//          System.out.println("smart include content:"+result);
            return result;
        } catch (ServletException e) {
            throw new RuntimeException("include error,path:"+includePath+" cause:"+e,e);
        } catch (IOException e) {
            throw new RuntimeException("include error,path:"+includePath+" cause:"+e,e);
        }
    }
    
    private void include(final OutputStream outputStream,String includePath) throws ServletException, IOException {
        if(isRemoteHttpRequest(includePath)) {
            getHttpRemoteContent(outputStream, includePath);
        }else {
            getLocalContent(outputStream, includePath);
        }
    }

    private static boolean isRemoteHttpRequest(String includePath) {
        return  includePath != null && (
        			includePath.toLowerCase().startsWith("http://") ||
        			includePath.toLowerCase().startsWith("https://")
        		);
    }

    private void getLocalContent(final OutputStream outputStream,String includePath) throws ServletException, IOException {
        // TODO handle getLocalContent() encoding 
    	// TODO printWriter must be auto flush on write()
        final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        request.getRequestDispatcher(includePath).include(request, new HttpServletResponseWrapper(response) {
            public PrintWriter getWriter() throws IOException {
                return printWriter;
            }
            public ServletOutputStream getOutputStream() throws IOException {
                return new ServletOutputStream() {
                    @Override
                    public void write(int b) throws IOException {
                        outputStream.write(b);
                    }
                    @Override
                    public void write(byte[] b) throws IOException {
                        outputStream.write(b);
                    }
                    @Override
                    public void write(byte[] b, int off, int len)throws IOException {
                        outputStream.write(b, off, len);
                    }
                };
            }
        });
        printWriter.flush();
    }
    //TODO handle cookies and http query parameters encoding, cookie并且需要处理不可见的 session id cookie问题
    private void getHttpRemoteContent(final OutputStream outputStream,String url) throws MalformedURLException, IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
//        conn.setRequestProperty("Cookie", toCookieString());
        InputStream input = conn.getInputStream();
        try {
        	IOUtils.copy(input,outputStream);
        }finally {
        	if(input != null) input.close();
        }
    }
    
    static class IOUtils { 
        private static void copy(InputStream in, OutputStream out) throws IOException {
            byte[] buff = new byte[8192];
            while(in.read(buff) >= 0) {
                out.write(buff);
            }
        }
        
        private static void copy(Reader in, Writer out) throws IOException {
            char[] buff = new char[8192];
            while(in.read(buff) >= 0) {
                out.write(buff);
            }
        }
    }
}
