/**
 * 
 */
package org.pssframework.service.archive;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.archive.AnalogueInfoDao;
import org.pssframework.dao.archive.MpInfoDao;
import org.pssframework.dao.archive.PsInfoDao;
import org.pssframework.dao.archive.TerminalInfoDao;
import org.pssframework.dao.archive.TgInfoDao;
import org.pssframework.model.archive.AnalogueInfo;
import org.pssframework.model.archive.MpInfo;
import org.pssframework.model.archive.PsInfo;
import org.pssframework.model.archive.TerminalInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.archive.TranInfo;
import org.pssframework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

/**
 * @author Administrator
 * 
 */
@Service
public class TgInfoManager extends BaseManager<TgInfo, Long> {
    @Autowired
    private TgInfoDao tgInfoDao;

    @Autowired
    private TerminalInfoDao terminalInfoDao;

    @Autowired
    private PsInfoDao psInfoDao;

    @Autowired
    private MpInfoDao mpInfoDao;

    @Autowired
    private AnalogueInfoDao analogueInfoDao;

    @SuppressWarnings("rawtypes")
    @Override
    protected EntityDao getEntityDao() {
        return this.tgInfoDao;
    }

    @Override
    public TgInfo getById(Long id) {
        Assert.notNull(id, "Id can't null");
        return tgInfoDao.getById(id);
    }

    @SuppressWarnings("rawtypes")
    public List<TgInfo> findByPageRequest(Map mapRequest) {
        List<TgInfo> list = new LinkedList<TgInfo>();
        list = tgInfoDao.findByPageRequest(mapRequest);
        if(list == null || list.size() == 0) {
            list = new LinkedList<TgInfo>();
        }
        return list;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void removeById(Long id) throws DataAccessException {
        Assert.notNull(id, "台区id不能为空");
        TgInfo tg = this.getById(id);

        List<TranInfo> trans = tg.getTranInfos();
        Map mapIn = Maps.newHashMap();
        mapIn.put("tgid", id);
        List<TerminalInfo> terms = this.terminalInfoDao.findByPageRequest(mapIn);

        List<PsInfo> psInfos = this.psInfoDao.findByPageRequest(mapIn);

        List<MpInfo> mpInfos = this.mpInfoDao.findByPageRequest(mapIn);

        List<AnalogueInfo> analogueInfos = this.analogueInfoDao.findByPageRequest(mapIn);

        if(CollectionUtils.isNotEmpty(trans))
            throw new ServiceException("该台区下存在变压器，请先删除关联变压器");

        if(CollectionUtils.isNotEmpty(terms))
            throw new ServiceException("该台区下存在终端，请先删除关联终端");

        if(CollectionUtils.isNotEmpty(psInfos))
            throw new ServiceException("该台区下存在漏保开关，请先删除关联漏保开关");

        if(CollectionUtils.isNotEmpty(mpInfos))
            throw new ServiceException("该台区下存在电表，请先删除关联电表");

        if(CollectionUtils.isNotEmpty(analogueInfos))
            throw new ServiceException("该台区下存在模拟量，请先删除关联模拟量");

        this.tgInfoDao.deleteById(id);

    }
}
