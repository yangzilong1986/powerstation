/**
 * 
 */
package org.pssframework.service.statistics;

import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.statistics.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Service
public class StatisticsManager extends BaseManager {

	@Autowired
	private StatisticsDao statisticsDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return statisticsDao;
	}

	public Page findByPageRequest(PageRequest<Map> pageRequest, StatisticsType powercruv) {
		// TODO Auto-generated method stub
		return statisticsDao.findByPageRequest(pageRequest, powercruv);
	}

}
