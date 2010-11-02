/**
 * 
 */
package org.pssframework.service.archive;

import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.SwitchValueInfoDao;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.SwitchValueInfo;
import org.pssframework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author djs-baocj
 *
 */
@Service
public class SwitchValueInfoManager extends BaseManager<SwitchValueInfo, Long> {

	@Autowired
	private SwitchValueInfoDao switchValueInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return switchValueInfoDao;
	}

	public boolean checkSwtichNo(SwitchValueInfo switchValueInfo) {
		if (compareOld(switchValueInfo))
			return checkSwtichNoRePeatByEnetity(switchValueInfo);
		else
			return false;

	}

	private boolean compareOld(SwitchValueInfo switchValueInfo) {
		if (switchValueInfo.getSwitchNo() == switchValueInfo.getSwitchNoOld()
				&& switchValueInfo.getTerminalInfo().getTermId().equals(switchValueInfo.getTermIdOld()))
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public String checkSwtichNoRePeat(SwitchValueInfo switchValueInfo) {

		boolean isSucc = false;

		String msg = "";

		if (!compareOld(switchValueInfo)) {

		} else {

			try {
				isSucc = checkSwtichNo(switchValueInfo);

				if (isSucc) {
					msg = "该终端下" + switchValueInfo.getSwitchNo() + "号开关量已存在，请重新输入或切换终端";
				}
			} catch (ServiceException e) {
				msg = e.getMessage();
			}
		}
		return msg;
	}

	/**
	 * 检测序号
	 * @param termId
	 * @param gpSn
	 * @return
	 */
	public boolean checkSwtichNoRePeatByEnetity(SwitchValueInfo switchValueInfo) {

		boolean ret = false;
		if (switchValueInfo.getTerminalInfo() == null)
			return ret;
		if (!GpInfoManger.termIdIsNull(switchValueInfo.getTerminalInfo().getTermId())) {
			List<SwitchValueInfo> switchValueInfos = switchValueInfoDao.findAllByProperty("terminalInfo",
					switchValueInfo.getTerminalInfo());
			for (SwitchValueInfo switchValueInfoDb : switchValueInfos) {
				if (switchValueInfoDb.getSwitchNo().equals(switchValueInfo.getSwitchNo())) {
					ret = true;
					break;
				}
			}
			return ret;
		} else
			return GpInfoManger.termIdIsNull(switchValueInfo.getTerminalInfo().getTermId());
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

	public List<SwitchValueInfo> findByPageRequest(Map<String, ?> mapRequest) {
		return switchValueInfoDao.findByPageRequest(mapRequest);
	}

}
