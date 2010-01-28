package com.hisun.web.action.other;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.web.action.BaseAction;
import com.hisun.web.tag.Page;
import com.hisun.web.tag.PageData;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class QryAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Page page;

    public QryAction() {

        this.page = null;
    }

    protected HiETF beforeProcess(HttpServletRequest request, Logger _log) throws HiException {

        if (_log.isDebugEnabled()) _log.debug("QryAction constuctETF doing");

        HiETF etf = super.beforeProcess(request, _log);

        this.page = new Page();

        int i = 1;
        try {

            i = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {

            i = 1;
        }

        this.page.setPage(i);

        this.page.setPageSize(15);

        etf.setChildValue("PAG_INX", String.valueOf((i - 1) * this.page.getPageSize() + 1));

        etf.setChildValue("PAG_END", String.valueOf(i * 15));

        etf.setChildValue("PAG_SIZ", String.valueOf(this.page.getPageSize()));

        return etf;
    }

    protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log) {

        if (_log.isDebugEnabled()) _log.debug("QryAction processEnd doing");

        int count = NumberUtils.toInt(rspetf.getChildValue("TOT_NUM"));

        this.page.setRowCount(count);

        List list = new ArrayList();

        PageData pageData = null;

        if (count == 0) {

            pageData = new PageData(this.page, list);
        } else {

            list = rspetf.getChildNodes("GROUP_");

            pageData = new PageData(this.page, list);
        }

        request.setAttribute("pageData", pageData);

        return super.endProcess(request, rspetf, _log);
    }
}