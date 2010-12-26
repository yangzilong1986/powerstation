/**
 * 
 */
package org.pssframework.service.archive;

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
    @SuppressWarnings("unused")
    private static char[] initChecked = new char[] { '0', '0', '0', '0', '0', '0', '0', '0' };

    @Autowired
    private PsInfoDao psInfoDao;

    @Autowired
    private GpInfoManger gpInfoManger;

    @SuppressWarnings("rawtypes")
    @Override
    protected EntityDao getEntityDao() {
        return psInfoDao;
    }

    @SuppressWarnings("rawtypes")
    public List<PsInfo> findByPageRequest(Map mapRequest) {
        return psInfoDao.findByPageRequest(mapRequest);
    }

    @Override
    public void saveOrUpdate(PsInfo model) throws DataAccessException {
        setCheckboxAutoTest(model);
        GpInfo gpInfo = model.getGpInfo();
        gpInfo.setTerminalInfo(model.getTerminalInfo());
        super.saveOrUpdate(model);
    }

    @Override
    public void update(PsInfo model) throws DataAccessException {
        setCheckboxAutoTest(model);
        super.saveOrUpdate(model);
    }

    public boolean checkGpsn(PsInfo psInfo) {
        GpInfo gpInfoIn = psInfo.getGpInfo();
        return gpInfoManger.checkGpSnRePeat(psInfo.getTerminalInfo().getTermId(), gpInfoIn.getGpSn(), "1",
                                            gpInfoIn.getGpSnOld());
    }

    public boolean checkGpAddr(PsInfo psInfo) {
        GpInfo gpInfoIn = psInfo.getGpInfo();
        return gpInfoManger.checkGpAddrRePeat(psInfo.getTerminalInfo().getTermId(), gpInfoIn.getGpAddr(), "1",
                                              gpInfoIn.getGpAddrOld());
    }

    private void setCheckboxAutoTest(PsInfo model) {
        if(model.getAutoTest() == null || "".equals(model.getAutoTest())) {
            model.setAutoTest("0");
        }
    }
}