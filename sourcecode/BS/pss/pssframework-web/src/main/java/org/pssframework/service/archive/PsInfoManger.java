/**
 * 
 */
package org.pssframework.service.archive;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.PsInfoDao;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.PsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * 
 */
@Service
public class PsInfoManger extends BaseManager<PsInfo, Long> {

	@Autowired
	private PsInfoDao psInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return psInfoDao;
	}

	public List<PsInfo> findByPageRequest(Map mapRequest) {
		// TODO Auto-generated method stub
		return psInfoDao.findByPageRequest(mapRequest);
	}

	public boolean checkGpsn(PsInfo psInfo) {
		GpInfo gpInfoIn = psInfo.getGpInfo();

		boolean bolRep = false;

		List<GpInfo> gpInfos = new LinkedList<GpInfo>();
		try {
			gpInfos = psInfo.getTerminalInfo().getGpInfos();
		} catch (NullPointerException e) {
			logger.debug("该漏保没有关联终端");
			return bolRep;
		}

		for (GpInfo gpInfo : gpInfos) {

			if (gpInfo.getGpSn() == gpInfoIn.getGpSn()) {

				bolRep = true;

				logger.debug("终端{}测量点序号重复", gpInfo.getTerminalInfo().getTermId());

				break;
			}
		}

		return bolRep;
	}
}