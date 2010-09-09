package org.pssframework.service.psmanage;

import java.io.Serializable;
import java.util.List;

import org.pssframework.base.BaseManager;
import org.pssframework.base.EntityDao;
import org.pssframework.dao.psmanage.PSTreeNodeDao;
import org.pssframework.model.psmanage.PSTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Nick
 * 
 */
@Service
public class PSTreeManager extends BaseManager<PSTreeNode, Serializable> {
    @Autowired
    private PSTreeNodeDao psTreeNodeDao;

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
        List<PSTreeNode> list = psTreeNodeDao.findPSTreeByTg(tgId);
        
        /*if((new Long(1)).equals(tgId)) {
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
        if((new Long(2)).equals(tgId)) {
            // TG 1
            PSTreeNode psTreeNode1 = new PSTreeNode();
            psTreeNode1.setTreeNodeType("TG");
            psTreeNode1.setTreeNodeId("2");
            psTreeNode1.setTreeNodeName("乾龙4#（96123455）");
            List<PSTreeNode> list1Children = new LinkedList<PSTreeNode>();
            // Terminal 11
            PSTreeNode psTreeNode11 = new PSTreeNode();
            psTreeNode11.setTreeNodeType("TERM");
            psTreeNode11.setTreeNodeId("21");
            psTreeNode11.setTreeNodeName("96123455");
            List<PSTreeNode> list11Children = new LinkedList<PSTreeNode>();
            // PS 111
            PSTreeNode psTreeNode111 = new PSTreeNode();
            psTreeNode111.setTreeNodeType("PS");
            psTreeNode111.setTreeNodeId("61");
            psTreeNode111.setTreeNodeName("test");
            psTreeNode111.setChildren(null);
            // PS 112
            PSTreeNode psTreeNode112 = new PSTreeNode();
            psTreeNode112.setTreeNodeType("PS");
            psTreeNode112.setTreeNodeId("101");
            psTreeNode112.setTreeNodeName("JD6-6");
            psTreeNode112.setChildren(null);

            list11Children.add(psTreeNode111);
            list11Children.add(psTreeNode112);
            psTreeNode11.setChildren(list11Children);

            list1Children.add(psTreeNode11);
            psTreeNode1.setChildren(list1Children);

            list.add(psTreeNode1);
        }
        if((new Long(3)).equals(tgId)) {
            // TG 1
            PSTreeNode psTreeNode1 = new PSTreeNode();
            psTreeNode1.setTreeNodeType("TG");
            psTreeNode1.setTreeNodeId("3");
            psTreeNode1.setTreeNodeName("乾龙1#（96123456）");
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
            psTreeNode111.setTreeNodeName("test1");
            psTreeNode111.setChildren(null);
            // PS 112
            PSTreeNode psTreeNode112 = new PSTreeNode();
            psTreeNode112.setTreeNodeType("PS");
            psTreeNode112.setTreeNodeId("22");
            psTreeNode112.setTreeNodeName("test2");
            psTreeNode112.setChildren(null);
            // PS 113
            PSTreeNode psTreeNode113 = new PSTreeNode();
            psTreeNode113.setTreeNodeType("PS");
            psTreeNode113.setTreeNodeId("62");
            psTreeNode113.setTreeNodeName("3号开关");
            psTreeNode113.setChildren(null);

            list11Children.add(psTreeNode111);
            list11Children.add(psTreeNode112);
            list11Children.add(psTreeNode113);
            psTreeNode11.setChildren(list11Children);

            list1Children.add(psTreeNode11);
            psTreeNode1.setChildren(list1Children);

            list.add(psTreeNode1);
        }
        if((new Long(62)).equals(tgId)) {
            // TG 1
            PSTreeNode psTreeNode1 = new PSTreeNode();
            psTreeNode1.setTreeNodeType("TG");
            psTreeNode1.setTreeNodeId("3");
            psTreeNode1.setTreeNodeName("乾龙2#（96123457）");
            List<PSTreeNode> list1Children = new LinkedList<PSTreeNode>();
            // Terminal 11
            PSTreeNode psTreeNode11 = new PSTreeNode();
            psTreeNode11.setTreeNodeType("TERM");
            psTreeNode11.setTreeNodeId("61");
            psTreeNode11.setTreeNodeName("96123457");
            List<PSTreeNode> list11Children = new LinkedList<PSTreeNode>();
            // PS 111
            PSTreeNode psTreeNode111 = new PSTreeNode();
            psTreeNode111.setTreeNodeType("PS");
            psTreeNode111.setTreeNodeId("81");
            psTreeNode111.setTreeNodeName("JD6-6");
            psTreeNode111.setChildren(null);

            list11Children.add(psTreeNode111);
            psTreeNode11.setChildren(list11Children);

            list1Children.add(psTreeNode11);
            psTreeNode1.setChildren(list1Children);

            list.add(psTreeNode1);
        }*/

        return list;
    }
}
