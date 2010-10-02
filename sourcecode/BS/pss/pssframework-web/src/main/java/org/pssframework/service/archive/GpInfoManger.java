/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.GpInfoDao;
import org.pssframework.dao.archive.TerminalInfoDao;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 * 
 */
@Service
public class GpInfoManger extends BaseManager<GpInfo, Long> {

	protected static final Logger logger = LoggerFactory.getLogger(GpInfoManger.class);

	@Autowired
	private GpInfoDao gpInfoDao;

	@Autowired
	private TerminalInfoDao terminalInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return gpInfoDao;
	}

	public List<GpInfo> findByPageRequest(Map mapRequest) {
		// TODO Auto-generated method stub
		return gpInfoDao.findByPageRequest(mapRequest);
	}

	public Page findPage(PageRequest query) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkGpSnRePeat(List<GpInfo> dbGpInfos, Long gpSn) {
		boolean ret = false;

		for (GpInfo gpInfo : dbGpInfos) {
			if (gpInfo.getGpSn().equals(gpSn)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	/**
	 * 检测序号
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	public boolean checkGpSnRePeat(Long termId, Long gpSn) {

		if (!termIdIsNull(termId))
			return checkGpSnRePeat(gpInfoDao.findByTermId(termId), gpSn);
		else
			return termIdIsNull(termId);
	}

	public boolean checkGpAddrRePeat(List<GpInfo> dbGpInfos, String gpAddr) {
		boolean ret = false;

		for (GpInfo gpInfo : dbGpInfos) {
			if (gpAddr.equals(gpInfo.getGpAddr())) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	/**
	 * 检测表地址
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	public boolean checkGpAddrRePeat(Long termId, String gpAddr) {
		if (!termIdIsNull(termId))
			return checkGpAddrRePeat(gpInfoDao.findByTermId(termId), gpAddr);
		else
			return termIdIsNull(termId);

	}

	public static boolean termIdIsNull(Long termId) {
		if (termId == null) {
			logger.info("没有关联终端");
			throw new ServiceException("没有关联终端");
		} else
			return false;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public String checkGpAddrRePeat(GpInfo model) {

		String msg = "";
		boolean isSucc = false;

		if (model == null || model.getTerminalInfo() == null || model.getGpAddr() == null) {
			msg = "";
		} else {

			try {
				isSucc = checkGpAddrRePeat(model.getTerminalInfo().getTermId(), model.getGpAddr());

				if (isSucc) {
					msg = "该终端下" + model.getGpAddr() + "号测量地址已存在，请重新输入或切换终端";
				}
			} catch (ServiceException e) {
				msg = e.getMessage();
			}
		}
		return msg;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public String checkGpSnRePeat(GpInfo model) {

		boolean isSucc = false;

		String msg = "";

		if (model == null || model.getTerminalInfo() == null || model.getGpSn() == null) {
			msg = "";
		} else {

			try {
				isSucc = checkGpSnRePeat(model.getTerminalInfo().getTermId(), model.getGpSn());

				if (isSucc) {
					msg = "该终端下" + model.getGpSn() + "号测量点已存在，请重新输入或切换终端";
				}
			} catch (ServiceException e) {
				msg = e.getMessage();
			}
		}
		return msg;
	}

}
