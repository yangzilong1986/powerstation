package org.pssframework.dao.psmanage;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
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
@SuppressWarnings("unchecked")
@Repository
public class PSTreeNodeDao extends BaseIbatis3Dao<PSTreeNode, Serializable> {

    @Override
    public String getPrefix() {
        return "PSTreeNode";
    }

    @Override
    public void saveOrUpdate(final PSTreeNode entity) throws DataAccessException {
    }

    /**
     * 
     * @param tgNo
     * @param tgName
     * @return
     */
    public List<PSTreeNode> findPSTreeByTg(Long tgId) {
        List<PSTreeNode> list = new LinkedList<PSTreeNode>();

        // 
        List listTg = getSqlSessionTemplate().selectList(getQuery("getTgNodesByTgId"), tgId, 0, Integer.MAX_VALUE);
        if(listTg != null) {
            Iterator itTg = listTg.iterator();
            while(itTg.hasNext()) {
                PSTreeNode pstnTg = (PSTreeNode) itTg.next();
                if(pstnTg.getTreeNodeId() != null) {
                    List listTerm = getSqlSessionTemplate().selectList(getQuery("getTerminalNodesByTgId"),
                                                                       Long.parseLong(pstnTg.getTreeNodeId()), 0,
                                                                       Integer.MAX_VALUE);
                    List<PSTreeNode> listTermTemp = new LinkedList<PSTreeNode>();
                    if(listTerm != null) {
                        Iterator itTerm = listTerm.iterator();
                        while(itTerm.hasNext()) {
                            PSTreeNode pstnTerm = (PSTreeNode) itTerm.next();
                            if(pstnTerm.getTreeNodeId() != null) {
                                List listPs = getSqlSessionTemplate().selectList(getQuery("getPsNodesByTermId"),
                                                                                 Long.parseLong(pstnTerm.getTreeNodeId()), 0,
                                                                                 Integer.MAX_VALUE);
                                pstnTerm.setChildren(listPs);
                                listTermTemp.add(pstnTerm);
                            }
                        }
                    }
                    pstnTg.setChildren(listTermTemp);
                }
                list.add(pstnTg);
            }
        }
        else
            return list;

        return list;
    }
}
