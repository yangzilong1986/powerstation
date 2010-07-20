package org.pssframework.model.psmanage;

import java.util.List;

import org.pssframework.base.BaseEntity;

public class PSTreeNode extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 296523343003940191L;

    private String treeNodeType;

    private String treeNodeId;

    private String treeNodeName;

    private String treeNodeParentType;

    private String treeNodeParentId;

    private String treeNodeParentName;

    private List<PSTreeNode> children;

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

    public String getTreeNodeParentType() {
        return treeNodeParentType;
    }

    public void setTreeNodeParentType(String treeNodeParentType) {
        this.treeNodeParentType = treeNodeParentType;
    }

    public String getTreeNodeParentId() {
        return treeNodeParentId;
    }

    public void setTreeNodeParentId(String treeNodeParentId) {
        this.treeNodeParentId = treeNodeParentId;
    }

    public String getTreeNodeParentName() {
        return treeNodeParentName;
    }

    public void setTreeNodeParentName(String treeNodeParentName) {
        this.treeNodeParentName = treeNodeParentName;
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
        result = prime * result + ((treeNodeId == null) ? 0 : treeNodeId.hashCode());
        result = prime * result + ((treeNodeName == null) ? 0 : treeNodeName.hashCode());
        result = prime * result + ((treeNodeParentId == null) ? 0 : treeNodeParentId.hashCode());
        result = prime * result + ((treeNodeParentName == null) ? 0 : treeNodeParentName.hashCode());
        result = prime * result + ((treeNodeParentType == null) ? 0 : treeNodeParentType.hashCode());
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
        if(treeNodeParentId == null) {
            if(other.treeNodeParentId != null)
                return false;
        }
        else if(!treeNodeParentId.equals(other.treeNodeParentId))
            return false;
        if(treeNodeParentName == null) {
            if(other.treeNodeParentName != null)
                return false;
        }
        else if(!treeNodeParentName.equals(other.treeNodeParentName))
            return false;
        if(treeNodeParentType == null) {
            if(other.treeNodeParentType != null)
                return false;
        }
        else if(!treeNodeParentType.equals(other.treeNodeParentType))
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
