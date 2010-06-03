/**
 * 
 */
package org.pssframework.service.system;

import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.CodeInfoDao;
import org.pssframework.model.system.CodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Service
public class CodeInfoManager extends BaseManager<CodeInfo, Long> {

	@Autowired
	private CodeInfoDao entitydao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return this.entitydao;
	}

	public Page findByPageRequest(PageRequest<Map> pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
