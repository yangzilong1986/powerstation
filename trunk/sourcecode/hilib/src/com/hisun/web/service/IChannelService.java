package com.hisun.web.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract interface IChannelService {
    public abstract void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException;

    public abstract void init(ServletConfig paramServletConfig) throws ServletException;
}