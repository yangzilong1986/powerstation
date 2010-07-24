package org.pssframework.dao.psmanage;

import java.io.Serializable;
import java.util.List;

import org.pssframework.dao.BaseIbatis3Dao;
import org.pssframework.model.psmanage.PSTreeNode;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Nick
 * 
 */
@Repository
public class PSTreeNodeDao extends BaseIbatis3Dao<PSTreeNode, Serializable> {

    @Override
    public String getPrefix() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveOrUpdate(final PSTreeNode entity) throws DataAccessException {
        // TODO Auto-generated method stub

    }

    /**
     * 
     * @param tgNo
     * @param tgName
     * @return
     */
    public List<PSTreeNode> findPSTreeByTg(String tgNo, String tgName) {

        return null;
    }
}
