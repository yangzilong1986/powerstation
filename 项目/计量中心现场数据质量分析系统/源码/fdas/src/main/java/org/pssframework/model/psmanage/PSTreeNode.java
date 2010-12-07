package org.pssframework.model.psmanage;

import java.util.List;

import org.pssframework.base.BaseEntity;

public class PSTreeNode extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 296523343003940191L;

    private String treeNodeType;            // 树节点类型

    private String treeNodeId;              // 树节点编号

    private String treeNodeName;            // 树节点名称

    private PSTreeNode parent;              // 父节点

    private List<PSTreeNode> children;      // 子节点

    public String getTreeNodeType() {
        return treeNodeType;
    }

    public void setTreeNodeType(String treeNodeType) {
        this.treeNodeType = treeNodeType;
    }

    public String getTreeNodeId() {
        return treeNodeId;
    }

    public void setTreeNodeId(String treeNodeId) {
        this.treeNodeId = treeNodeId;
    }

    public String getTreeNodeName() {
        return treeNodeName;
    }

    public void setTreeNodeName(String treeNodeName) {
        this.treeNodeName = treeNodeName;
    }

    public PSTreeNode getParent() {
        return parent;
    }

    public void setParent(PSTreeNode parent) {
        this.parent = parent;
    }

    public List<PSTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<PSTreeNode> children) {
        this.children = children;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((treeNodeId == null) ? 0 : treeNodeId.hashCode());
        result = prime * result + ((treeNodeName == null) ? 0 : treeNodeName.hashCode());
        result = prime * result + ((treeNodeType == null) ? 0 : treeNodeType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        PSTreeNode other = (PSTreeNode) obj;
        if(children == null) {
            if(other.children != null)
                return false;
        }
        else if(!children.equals(other.children))
            return false;
        if(parent == null) {
            if(other.parent != null)
                return false;
        }
        else if(!parent.equals(other.parent))
            return false;
        if(treeNodeId == null) {
            if(other.treeNodeId != null)
                return false;
        }
        else if(!treeNodeId.equals(other.treeNodeId))
            return false;
        if(treeNodeName == null) {
            if(other.treeNodeName != null)
                return false;
        }
        else if(!treeNodeName.equals(other.treeNodeName))
            return false;
        if(treeNodeType == null) {
            if(other.treeNodeType != null)
                return false;
        }
        else if(!treeNodeType.equals(other.treeNodeType))
            return false;
        return true;
    }
}
