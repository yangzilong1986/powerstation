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

	/**
	 * 485
	 * @param dbGpInfos
	 * @param gpSn
	 * @return
	 */
	public boolean checkGpSnRePeat(List<GpInfo> dbGpInfos, Long gpSn) {
		return checkGpSnRePeat(dbGpInfos, gpSn, "1");
	}

	public boolean checkGpSnRePeat(List<GpInfo> dbGpInfos, Long gpSn, String gpChar) {
		boolean ret = false;

		if (gpChar == null) {
			gpChar = "1";
		}
		for (GpInfo gpInfo : dbGpInfos) {
			if (gpInfo.getGpSn().equals(gpSn) && gpChar.equals(gpInfo.getGpChar())) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	/**
	 * 检测序号 485
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	public boolean checkGpSnRePeat(Long termId, Long gpSn) {

		return checkGpSnRePeat(termId, gpSn, "1");
	}

	/**
	 * 检测序号
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	public boolean checkGpSnRePeat(Long termId, Long gpSn, String gpChar) {

		return checkGpSnRePeat(termId, gpSn, gpChar, null);
	}

	/**
	 * 检测序号
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	public boolean checkGpSnRePeat(Long termId, Long gpSn, String gpChar, Long gpSnOld) {
		if (compareOldGpSn(gpSn, gpSnOld)) {
			if (!termIdIsNull(termId))
				return checkGpSnRePeat(gpInfoDao.findByTermId(termId), gpSn, gpChar);
			else
				return false;
		} else
			return false;
	}

	/**
	 * 检测表地址 485
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	protected boolean checkGpAddrRePeat(Long termId, String gpAddr) {

		return checkGpAddrRePeat(termId, gpAddr, "1");

	}

	/**
	 * 检测表地址
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	protected boolean checkGpAddrRePeat(Long termId, String gpAddr, String gpChar) {

		return checkGpAddrRePeat(termId, gpAddr, gpAddr, null);

	}

	/**
	 * 检测表地址
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	protected boolean checkGpAddrRePeat(Long termId, String gpAddr, String gpChar, String gpAddrOld) {

		if (compareOldGpAddr(gpAddr, gpAddrOld)) {
			if (!termIdIsNull(termId))
				return checkGpAddrRePeat(gpInfoDao.findByTermId(termId), gpAddr, gpChar);
			else
				return false;
		} else
			return false;

	}

	/**
	 * 485
	 * @param dbGpInfos
	 * @param gpAddr
	 * @return
	 */
	private boolean checkGpAddrRePeat(List<GpInfo> dbGpInfos, String gpAddr) {
		return checkGpAddrRePeat(dbGpInfos, gpAddr, "1");
	}

	private boolean checkGpAddrRePeat(List<GpInfo> dbGpInfos, String gpAddr, String gpChar) {
		boolean ret = false;

		if (gpChar == null) {
			gpChar = "1";
		}
		for (GpInfo gpInfo : dbGpInfos) {
			if (gpAddr.equals(gpInfo.getGpAddr()) && gpChar.equals(gpInfo.getGpChar())) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public static boolean termIdIsNull(Long termId) {
		if (termId == null) {
			logger.info("没有关联终端");
			throw new ServiceException("没有关联终端");
		} else
			return false;
	}

	private boolean compareOldGpSn(GpInfo gpInfo) {
		return compareOldGpSn(gpInfo.getGpSn(), gpInfo.getGpSnOld());
	}

	private boolean compareOldGpSn(Long gpSn, Long gpSnOld) {
		if (gpSn == gpSnOld)
			return false;
		else
			return true;
	}

	private boolean compareOldGpAddr(GpInfo gpInfo) {
		return compareOldGpAddr(gpInfo.getGpAddr(), gpInfo.getGpAddrOld());
	}

	private boolean compareOldGpAddr(String gpAddr, String gpAddrOld) {
		if (gpAddr.equals(gpAddrOld))
			return false;
		else
			return true;
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

				isSucc = checkGpAddrRePeat(model.getTerminalInfo().getTermId(), model.getGpAddr(), model.getGpChar(),
						model.getGpAddrOld());

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

				isSucc = checkGpSnRePeat(model.getTerminalInfo().getTermId(), model.getGpSn(), model.getGpChar(),
						model.getGpSnOld());

				if (isSucc) {
					msg = "该终端下" + model.getGpSn() + "号测量点已存在，请重新输入或切换终端";
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
	public String checkPortRePeat(GpInfo model) {
		boolean isSucc = false;

		String msg = "";

		if (model == null || model.getTerminalInfo() == null || model.getPort() == null) {
			msg = "";
		} else {

			try {

				isSucc = checkPortRePeat(model.getTerminalInfo().getTermId(), model.getPort(), model.getGpChar(),
						model.getOdlPort());

				if (isSucc) {
					msg = "该终端下" + model.getPort() + "号端口已存在，请重新输入或切换终端";
				}
			} catch (ServiceException e) {
				msg = e.getMessage();
			}
		}
		return msg;
	}

	private boolean checkPortRePeat(Long termId, String port, String gpChar, String odlPort) {

		if (compareOldPort(port, odlPort)) {
			if (!termIdIsNull(termId))
				return checkPortRePeat(gpInfoDao.findByTermId(termId), port, gpChar);
			else
				return false;
		} else
			return false;
	}

	private boolean checkPortRePeat(List<GpInfo> dbGpInfos, String port, String gpChar) {
		boolean ret = false;

		if (gpChar == null) {
			gpChar = "1";
		}
		for (GpInfo gpInfo : dbGpInfos) {
			if (port.equals(gpInfo.getPort()) && gpChar.equals(gpInfo.getGpChar())) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	private boolean compareOldPort(String port, String odlPort) {
		if (port.equals(odlPort))
			return false;
		else
			return true;
	}
}
