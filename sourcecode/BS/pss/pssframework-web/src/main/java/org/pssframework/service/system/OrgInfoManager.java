/**
 * 
 */
package org.pssframework.service.system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.system.OrgInfoDao;
import org.pssframework.model.archive.LineInfo;
import org.pssframework.model.archive.TgInfo;
import org.pssframework.model.system.CodeInfo;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 * 
 */
@Service
public class OrgInfoManager extends BaseManager<OrgInfo, Long> {
    @SuppressWarnings("rawtypes")
    @Override
    protected EntityDao getEntityDao() {
        return this.entityDao;
    }

    @Autowired
    private OrgInfoDao<?> entityDao;

    public List<OrgInfo> getOrgList(Long orgId) {
        List<OrgInfo> orgList = new ArrayList<OrgInfo>();
        // orgList = entityDao.findBy(orgId);
        return orgList;
    }

    @SuppressWarnings("rawtypes")
    public List<OrgInfo> findByPageRequest(Map mapRequest) {

        List<OrgInfo> list = new LinkedList<OrgInfo>();
        list = entityDao.findByPageRequest(mapRequest);
        if(list == null || list.size() == 0) {
            list = new LinkedList<OrgInfo>();
        }
        return list;

    }

    /**
     * 查询该部门下可用的orgNo
     * 
     * @param pOrgInfo
     * @return
     */
    public String findPurposeOrgNo(OrgInfo pOrgInfo) {
        pOrgInfo = this.getById(pOrgInfo.getOrgId());
        String orgNo = this.entityDao.findPurposeOrgNo(pOrgInfo);

        // 第一个创建部门的情况
        if(orgNo == null || "".equals(orgNo) || orgNo.length() == 1)
            return orgNo = pOrgInfo.getOrgNo() + "001";
        else {
            String sNo = StringUtils.substringAfter(orgNo, pOrgInfo.getOrgNo());
            Integer iNo = Integer.parseInt(sNo) + 1;

            return pOrgInfo.getOrgNo() + StringUtils.leftPad(String.valueOf(iNo), 3, '0');
        }

    }

    @SuppressWarnings("rawtypes")
    public List<CodeInfo> findOrgTypes(Map mapRequest) {
        return entityDao.findOrgTypes(mapRequest);
    }

    public String checkSortNoRePeat(OrgInfo orgInfo) {
        String msg = "";
        if(checkSortNoRePeat(orgInfo.getParentOrgInfo().getOrgId(), orgInfo.getSortNo())) {
            msg = "该部门的" + orgInfo.getSortNo() + "号排序地址已存在，请重新输入";
        }
        return msg;
    }

    public boolean checkSortNoRePeat(Long orgId, Long sortNo) {
        List<OrgInfo> listOrg = Lists.newArrayList();
        listOrg = this.entityDao.findSortNoRePeat(orgId, sortNo);
        if(listOrg == null || listOrg.size() == 0)
            return false;
        return true;
    }

    @Override
    public void removeById(Long orgId) throws DataAccessException {
        Assert.notNull(orgId, "orgId 不能为null");
        // 获取该部门
        OrgInfo org = this.getById(orgId);

        // 获取该子部门
        List<OrgInfo> subOrgs = Lists.newArrayList();
        subOrgs = this.entityDao.findAllByProperty("parentOrgInfo", org);

        List<TgInfo> tglst = org.getTgInfos();

        List<LineInfo> linList = org.getLineInfos();

        if(CollectionUtils.isNotEmpty(subOrgs))
            throw new ServiceException("该部门下存在子部门，请先删除关联部门");

        if(CollectionUtils.isNotEmpty(linList))
            throw new ServiceException("该部门下存在线路，请先删除关联线路");

        if(CollectionUtils.isNotEmpty(tglst))
            throw new ServiceException("该部门下存在台区，请先删除关联台区");

        this.entityDao.delete(org);

    }

    public String checkOrgNoRePeat(OrgInfo orgInfo) {
        String chk = "";
        if(orgInfo.getOldOrgNo() == null || orgInfo.getOrgNo().equals(orgInfo.getOldOrgNo()))
            return chk;

        if(findByOrgNo(orgInfo.getOrgNo()) == null)
            return chk;
        else {
            chk = "该部门号重复，请重新输入";
        }

        return chk;
    }

    public OrgInfo findByOrgNo(String orgNo) {
        return this.entityDao.findByProperty("orgNo", orgNo);
    }
}
