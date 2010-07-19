/**
 * 
 */
package org.pssframework.service.atuorm;

import java.util.Collections;
import java.util.List;

import net.jcreate.e3.table.CreateDataModelException;
import net.jcreate.e3.table.DataModel;
import net.jcreate.e3.table.SortInfo;
import net.jcreate.e3.table.model.CollectionDataModel;
import net.jcreate.e3.table.support.DefaultPageInfo;
import net.jcreate.e3.table.support.DefaultSortInfo;
import net.jcreate.e3.table.support.EmptySortInfo;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.autorm.RealTimeReadingDao;
import org.pssframework.model.autorm.RealTimeReadingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Service
public class RealTimeReadingManager extends BaseManager<RealTimeReadingInfo, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private RealTimeReadingDao realTimeReadingDao;

	@Override
	public EntityDao getEntityDao() {
		return this.realTimeReadingDao;
	}

	public DataModel findByPageRequest(PageRequest pagePara) {

		Page page = realTimeReadingDao.findByPageRequest(pagePara);

		return this.create(pagePara, page);
	}

	public DataModel create(PageRequest pagePara, Page page) throws CreateDataModelException {

		SortInfo sortInfo = null;
		if (pagePara.getSortColumns() != null) {
			List list = pagePara.getSortInfos();
			sortInfo = new DefaultSortInfo(pagePara.getSortColumns(), String.valueOf(list.get(0)));
		} else {
			sortInfo = EmptySortInfo.me;
		}

		//开始位置,从0开始
		int start = page.getFirstResult();

		//每页记录数,
		int pageSize = page.getPageSize();

		//总记录数
		int totalSize = page.getTotalCount();

		if (totalSize < 0)
			throw new CreateDataModelException("记录总数应该大于或等于零!");
		DefaultPageInfo pageInfo = new DefaultPageInfo(start, totalSize, pageSize);
		//pageInfo.setExported(pNavRequest.isExported());
		//当start大于 totalSize -1 时,需要调整start的值.
		if (start < 0) {
			start = 0;
		}
		if (start > (totalSize - 1)) {
			//@todo: getStartOfLastPage的计算不会依赖start的值,否则这种算法有问题
			//,有空的时候把getStartOfLastPage代码挪出来
			start = pageInfo.getStartOfLastPage();
			pageInfo.setStart(start);
		}

		List<?> data = null;
		if (totalSize > 0) {
			data = page.getResult();
		} else {
			data = Collections.EMPTY_LIST;
		}

		DataModel result = new CollectionDataModel(data, sortInfo, pageInfo);
		logger.info("curpage:" + pageInfo.getCurrPage());
		logger.info("getTotalPages:" + pageInfo.getTotalPages());
		logger.info("getTotal:" + pageInfo.getTotal());
		return result;
	}
}
