/**
 * 
 */
package org.pssframework.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.CodeInfoDao;
import org.pssframework.model.system.CodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
@Service
public class CodeInfoManager extends BaseManager<CodeInfo, Long> {

	@Autowired(required = true)
	private CodeInfoDao codeInfoDao;

	public void setCodeInfoDao(CodeInfoDao codeInfoDao) {
		this.codeInfoDao = codeInfoDao;
	}

	@Override
	protected EntityDao getEntityDao() {
		return this.codeInfoDao;
	}

	public List<CodeInfo> findAll(PageRequest<Map> pageRequest) {
		return null;
	}

	public List<CodeInfo> findByPageRequest(Map<String, ?> mapRequest) {
		return codeInfoDao.findAll(mapRequest);
	}
	
    @SuppressWarnings("unchecked")
    public CodeInfo getCodeInfo(String codeCate, String code) {
        Map map = new HashMap();
        map.put("codecate", codeCate);
        map.put("code", code);
        List<CodeInfo> codeInfos = findByPageRequest(map);
        if(codeInfos.size() > 0) {
            return codeInfos.get(0);
        }
        else {
            return new CodeInfo();
        }
    }
}
