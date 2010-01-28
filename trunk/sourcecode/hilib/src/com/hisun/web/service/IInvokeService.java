package com.hisun.web.service;

import com.hisun.exception.HiException;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;

public abstract interface IInvokeService {
    public abstract HiETF invoke(HiETF paramHiETF) throws HiException;

    public abstract HiMessage invoke(HiMessage paramHiMessage) throws HiException;
}