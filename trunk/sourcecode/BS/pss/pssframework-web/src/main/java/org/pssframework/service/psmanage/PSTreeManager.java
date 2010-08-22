package org.pssframework.service.psmanage;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.model.psmanage.PSTreeNode;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Nick
 * 
 */
@Service
public class PSTreeManager extends BaseManager<PSTreeNode, Serializable> {

    @SuppressWarnings("unchecked")
    @Override
    protected EntityDao getEntityDao() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * @param tgId
     * @return
     */
    public List<PSTreeNode> findPSTreeByTgId(Long tgId) {
        List<PSTreeNode> list = new LinkedList<PSTreeNode>();

        if((new Long(1)).equals(tgId)) {
            // TG 1
            PSTreeNode psTreeNode1 = new PSTreeNode();
            psTreeNode1.setTreeNodeType("TG");
            psTreeNode1.setTreeNodeId("1");
            psTreeNode1.setTreeNodeName("测试台区0001");
            List<PSTreeNode> list1Children = new LinkedList<PSTreeNode>();
            // Terminal 11
            PSTreeNode psTreeNode11 = new PSTreeNode();
            psTreeNode11.setTreeNodeType("TERM");
            psTreeNode11.setTreeNodeId("1");
            psTreeNode11.setTreeNodeName("96123456");
            List<PSTreeNode> list11Children = new LinkedList<PSTreeNode>();
            // PS 111
            PSTreeNode psTreeNode111 = new PSTreeNode();
            psTreeNode111.setTreeNodeType("PS");
            psTreeNode111.setTreeNodeId("1");
            psTreeNode111.setTreeNodeName("测试漏保001");
            psTreeNode111.setChildren(null);

            list11Children.add(psTreeNode111);
            psTreeNode11.setChildren(list11Children);

            list1Children.add(psTreeNode11);
            psTreeNode1.setChildren(list1Children);

            list.add(psTreeNode1);
        }

        return list;
    }
}
