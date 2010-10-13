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
import org.pssframework.model.archive.SwitchValueInfoPK;
import org.pssframework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author djs-baocj
 *
 */
@Service
public class SwitchValueInfoManager extends BaseManager<SwitchValueInfo, SwitchValueInfoPK> {

	@Autowired
	private SwitchValueInfoDao switchValueInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return switchValueInfoDao;
	}

	public boolean checkSwtichNo(SwitchValueInfo switchValueInfo) {
		SwitchValueInfoPK switchValueInfoPK = switchValueInfo.getSwitchValueId();
		if (compareOld(switchValueInfo))
			return checkSwtichNoRePeat(switchValueInfoPK);
		else
			return false;

	}

	private boolean compareOld(SwitchValueInfo switchValueInfo) {
		SwitchValueInfoPK switchValueInfoPK = switchValueInfo.getSwitchValueId();
		if (switchValueInfoPK.getSwitchNo() == switchValueInfo.getSwitchNoOld()
				&& switchValueInfoPK.getTerminalInfo().getTermId() == switchValueInfo.getTermIdOld())
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
					msg = "该终端下" + switchValueInfo.getSwitchValueId().getSwitchNo() + "号开关量已存在，请重新输入或切换终端";
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
	public boolean checkSwtichNoRePeat(SwitchValueInfoPK switchValueInfoPK) {

		boolean ret = false;
		if (switchValueInfoPK.getTerminalInfo() == null)
			return ret;
		if (!GpInfoManger.termIdIsNull(switchValueInfoPK.getTerminalInfo().getTermId())) {
			List<SwitchValueInfo> switchValueInfos = switchValueInfoDao.findAllByPK(switchValueInfoPK);
			for (SwitchValueInfo switchValueInfo : switchValueInfos) {
				if (switchValueInfo.getSwitchValueId().getSwitchNo().equals(switchValueInfoPK.getSwitchNo())) {
					ret = true;
					break;
				}
			}
			return ret;
		} else
			return GpInfoManger.termIdIsNull(switchValueInfoPK.getTerminalInfo().getTermId());
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
