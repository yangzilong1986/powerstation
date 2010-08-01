/**
 *
 */
package org.pssframework.service.archive;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.MpInfoDao;
import org.pssframework.dao.system.CodeInfoDao;
import org.pssframework.model.archive.GpInfo;
import org.pssframework.model.archive.MeterInfo;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.support.system.SystemConst;
import org.pssframework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class MpInfoManger extends BaseManager<MpInfo, Long> {

	@Autowired
	private MpInfoDao mpInfoDao;

	@Autowired
	private CodeInfoDao codeInfoDao;

	@Override
	protected EntityDao getEntityDao() {
		// TODO Auto-generated method stub
		return mpInfoDao;
	}

	public List<MpInfo> findByPageRequest(Map mapRequest) {
		// TODO Auto-generated method stub
		return mpInfoDao.findByPageRequest(mapRequest);
	}

	public boolean checkGpSn(MpInfo info) {
		boolean bolRep = false;
		GpInfo gpInfosIn = null;
		try {
			gpInfosIn = info.getGpInfos().get(0);

			formatGpAddr(gpInfosIn);

		} catch (IndexOutOfBoundsException e) {
			return bolRep;
		} finally {

		}

		// List<GpInfo> gpInfos = gpInfosIn.getTerminalInfo().getGpInfos();

		// for (GpInfo gpInfo : gpInfos) {
		//
		// if (gpInfo.getGpSn() == gpInfoIn.getGpSn()) {
		//
		// bolRep = true;
		//
		// break;
		// }
		// }
		// }
		return bolRep;
	}

	@Override
	public void saveOrUpdate(MpInfo entity) throws org.springframework.dao.DataAccessException {
		setTotalTimes(entity);
		setOrgInfo(entity);
		super.saveOrUpdate(entity);

	}

	private void setOrgInfo(MpInfo entity) {
		entity.setOrgId(entity.getTgInfo().getOrgInfo().getOrgId());
	}

	/**
	 * 设置总倍率
	 *
	 * @param entity
	 */
	@SuppressWarnings("rawtypes")
	private void setTotalTimes(MpInfo entity) {
		MeterInfo meterInfo = entity.getMeterInfo();
		List<GpInfo> lstGpInfos = entity.getGpInfos();
		Double ct = null;
		Double pt = null;
		CodeInfo codeInfoCt = null;
		CodeInfo codeInfoPt = null;
		Map ctMap = new HashMap();
		ctMap.put(CodeInfoDao.CODECATE, SystemConst.CODE_CT_RATIO);

		Map ptMap = new HashMap();
		ctMap.put(CodeInfoDao.CODECATE, SystemConst.CODE_PT_RATIO);

		for (GpInfo gpInfo : lstGpInfos) {
			//ct
			ctMap.put(CodeInfoDao.CODE, gpInfo.getCtTimes());
			//pt
			ptMap.put(CodeInfoDao.CODE, gpInfo.getPtTimes());

		}
		if (codeInfoDao.findAll(ctMap) != null && codeInfoDao.findAll(ctMap).size() > 0) {
			codeInfoCt = codeInfoDao.findAll(ctMap).get(0);
			ct = Double.parseDouble(codeInfoCt.getValue());
		}

		if (codeInfoDao.findAll(ctMap) != null && codeInfoDao.findAll(ctMap).size() > 0) {
			codeInfoPt = codeInfoDao.findAll(ctMap).get(0);
			pt = Double.parseDouble(codeInfoPt.getValue());
		}

		//(10,2)
		DecimalFormat df = new DecimalFormat("########.##");
		meterInfo.settFactor(Double.parseDouble(df.format(ct * pt)));
	}

	private void formatGpAddr(GpInfo gpInfo) {
		String gpAddr = gpInfo.getGpAddr();
		if (gpAddr != null && gpAddr.length() < 12) {
			gpAddr = StringUtil.lPad(gpAddr, "0", 12);
			gpInfo.setGpAddr(gpAddr);
		}

	}
}
