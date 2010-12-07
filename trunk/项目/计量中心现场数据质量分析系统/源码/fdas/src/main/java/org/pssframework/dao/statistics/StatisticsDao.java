/**
 * 
 */
package org.pssframework.dao.statistics;

import java.util.Map;

import org.pssframework.dao.BaseIbatis3Dao;
import org.pssframework.service.statistics.StatisticsType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class StatisticsDao extends BaseIbatis3Dao {
    private static String pre = "";

    @Override
    public void saveOrUpdate(Object entity) throws DataAccessException {

    }

    @Override
    public String getPrefix() {
        return pre;
    }

    public Page findByPageRequest(PageRequest<Map> pageRequest, StatisticsType statisticsType) {
        setPreFix(statisticsType);
        return pageQuery("pageSelect", pageRequest);
    }

    public Page findChartByPageRequest(PageRequest<Map> pageRequest, StatisticsType statisticsType) {
        setPreFix(statisticsType);
        pageRequest.setPageSize(Integer.MAX_VALUE);
        return pageQuery("chartSelect", pageRequest);
    }

    private void setPreFix(StatisticsType statisticsType) {
        pre = statisticsType.toString();
    }
}
