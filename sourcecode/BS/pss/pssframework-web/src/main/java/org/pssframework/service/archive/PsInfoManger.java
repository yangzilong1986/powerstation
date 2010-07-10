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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * 
 */
@Service
public class PsInfoManger extends BaseManager<PsInfo, Long> {

	private static char[] initChecked = new char[] { '0', '0', '0', '0', '0', '0', '0', '0' };

	@Autowired
	private PsInfoDao psInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		return psInfoDao;
	}

	@SuppressWarnings("unchecked")
	public List<PsInfo> findByPageRequest(Map mapRequest) {
		return psInfoDao.findByPageRequest(mapRequest);
	}

	@Override
	public void saveOrUpdate(PsInfo model) throws DataAccessException {
		// 默认485
		model.getGpInfo().setGpChar("1");

		// 台区
		model.getGpInfo().setGpType("2");

		int[] functionsChecked = model.getFunctionsChecked();

		if (functionsChecked != null) {

			for (int checked : functionsChecked) {
				initChecked[checked] = '1';
			}

		}
		model.setFunctionCode(String.valueOf(initChecked));

		super.saveOrUpdate(model);
	}

	@Override
	public void update(PsInfo model) throws DataAccessException {

		int[] functionsChecked = model.getFunctionsChecked();

		logger.debug("update functionsChecked{}", functionsChecked);

		if (functionsChecked != null) {

			for (int checked : functionsChecked) {
				initChecked[checked] = '1';
			}

		}
		model.setFunctionCode(String.valueOf(initChecked));

		logger.debug("start to updating");

		super.update(model);
	}

	public boolean checkGpsn(PsInfo psInfo) {
		GpInfo gpInfoIn = psInfo.getGpInfo();

		boolean bolRep = false;

		List<GpInfo> gpInfos = new LinkedList<GpInfo>();

		try {
			gpInfos = psInfo.getTerminalInfo().getGpInfos();

		} catch (NullPointerException e) {

			logger.info("该漏保没有关联终端");

			return bolRep;
		}

		if (gpInfos != null && gpInfos.size() > 0) {

			for (GpInfo gpInfo : gpInfos) {

				if (gpInfo.getGpSn().equals(gpInfoIn.getGpSn())) {

					bolRep = true;

					logger.info("终端{}测量点序号重复", gpInfo.getTerminalInfo().getTermId());

					break;
				}
			}
		}
		return bolRep;
	}
}