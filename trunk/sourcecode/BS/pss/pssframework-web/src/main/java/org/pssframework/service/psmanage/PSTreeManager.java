package org.pssframework.service.psmanage;

import java.io.Serializable;

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

    @Override
    protected EntityDao getEntityDao() {
        // TODO Auto-generated method stub
        return null;
    }

}
