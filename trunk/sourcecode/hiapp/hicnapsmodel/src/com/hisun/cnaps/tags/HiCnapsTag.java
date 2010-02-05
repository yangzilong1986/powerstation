package com.hisun.cnaps.tags;

import com.hisun.cnaps.CnapsTag;
import com.hisun.cnaps.common.HiRepeatTagManager;
import com.hisun.exception.HiException;
import com.hisun.message.HiETF;

public abstract interface HiCnapsTag extends CnapsTag {
    public abstract String getEtfName();

    public abstract void setEtfname(String paramString);

    public abstract void setRepeatName(String paramString);

    public abstract String getRepeatName();

    public abstract void setRepeatManager(HiRepeatTagManager paramHiRepeatTagManager);

    public abstract void parse(String paramString) throws HiException;

    public abstract void unpack2Etf(HiETF paramHiETF) throws HiException;

    public abstract String parse(HiETF paramHiETF) throws HiException;
}