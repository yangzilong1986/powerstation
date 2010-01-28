package com.hisun.web.service;

import com.hisun.exception.HiException;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;

import javax.servlet.ServletContext;

public abstract interface HiCallHostService {
    public abstract HiETF callhost(HiMessage paramHiMessage, ServletContext paramServletContext) throws HiException;

    public abstract HiETF callhost(String paramString, HiETF paramHiETF, ServletContext paramServletContext) throws HiException;
}