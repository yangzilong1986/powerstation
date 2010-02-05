package com.hisun.component.web;

import com.hisun.atc.common.HiArgUtils;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownload {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        Logger log = HiLog.getLogger(msg);
        String fileDisplayName = args.get("DspNam");
        String fileDownloadName = HiArgUtils.getStringNotNull(args, "FilNam");
        if (fileDownloadName.indexOf(47) == -1) {
            fileDownloadName = "download/" + fileDownloadName;
        }

        HttpServletResponse response = (HttpServletResponse) msg.getObjectHeadItem("_WEB_RESPONSE");
        try {
            response.reset();
            fileDisplayName = new String(fileDisplayName.getBytes("gb2312"), "iso8859-1");
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileDisplayName);

            HttpServletRequest request = (HttpServletRequest) msg.getObjectHeadItem("_WEB_REQUEST");

            request.getRequestDispatcher(fileDownloadName).forward(request, response);

            response.flushBuffer();
        } catch (Exception e) {
            throw new HiException(e);
        }
        return 0;
    }
}