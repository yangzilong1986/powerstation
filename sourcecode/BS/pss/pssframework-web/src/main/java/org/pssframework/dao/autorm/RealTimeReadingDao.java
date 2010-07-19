/**
 * 
 */
package org.pssframework.dao.autorm;

import org.pssframework.dao.BaseIbatis3Dao;
import org.pssframework.model.autorm.RealTimeReadingInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Repository
public class RealTimeReadingDao extends BaseIbatis3Dao<RealTimeReadingInfo, Long> {

	private static final String sql = "";

	@Override
	public String getPrefix() {
		return "RealTimeReadingInfo";
	}

	@Override
	public void saveOrUpdate(RealTimeReadingInfo entity) throws DataAccessException {
		this.log.info("saveOrUpdate");

	}

	public Page findByPageRequest(PageRequest pageRequest) {
		this.log.info("findByPageRequest");
		return pageQuery("pageSelect", pageRequest);
	}

	//	public DataModel findByPageRequest(NavRequest pNavRequest) {
	//
	//		this.log.info("findByPageRequest");
	//		return pageQuery("pageSelect", pNavRequest);
	//	}
}
