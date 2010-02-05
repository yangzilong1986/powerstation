package com.hisun.component.no;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

public class GetUniqueNo {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HashMap map = ctx.getDataBaseUtil().readRecord("select PUBSIDN.NEXTVAL ID from dual");

        String name = args.get("name");
        if (StringUtils.isBlank(name)) {
            name = "ID";
        }
        HiETF root = ctx.getCurrentMsg().getETFBody();
        root.setChildValue(name, (String) map.get("ID"));
        return 0;
    }
}