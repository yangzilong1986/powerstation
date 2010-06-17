package org.pssframework.model.eparam;

import java.util.List;

import org.pssframework.base.BaseEntity;

public class CommandItemInfo extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8695882146811245762L;
    
    private String commandItemCode;                     //命令项标识
    private String commandItemName;                     //命令项名称
    private String commandItemDesc;                     //命令项说明
    private String paraType;                            //参数类型
    private String wrFlag;                              //读写标识
    private List<DataItemInfo> dataItemList;            //

    public String getCommandItemCode() {
        return commandItemCode;
    }

    public void setCommandItemCode(String commandItemCode) {
        this.commandItemCode = commandItemCode;
    }

    public String getCommandItemName() {
        return commandItemName;
    }

    public void setCommandItemName(String commandItemName) {
        this.commandItemName = commandItemName;
    }

    public String getCommandItemDesc() {
        return commandItemDesc;
    }

    public void setCommandItemDesc(String commandItemDesc) {
        this.commandItemDesc = commandItemDesc;
    }

    public String getParaType() {
        return paraType;
    }

    public void setParaType(String paraType) {
        this.paraType = paraType;
    }

    public String getWrFlag() {
        return wrFlag;
    }

    public void setWrFlag(String wrFlag) {
        this.wrFlag = wrFlag;
    }

    public List<DataItemInfo> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(List<DataItemInfo> dataItemList) {
        this.dataItemList = dataItemList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commandItemCode == null) ? 0 : commandItemCode.hashCode());
        result = prime * result + ((commandItemDesc == null) ? 0 : commandItemDesc.hashCode());
        result = prime * result + ((commandItemName == null) ? 0 : commandItemName.hashCode());
        result = prime * result + ((dataItemList == null) ? 0 : dataItemList.hashCode());
        result = prime * result + ((paraType == null) ? 0 : paraType.hashCode());
        result = prime * result + ((wrFlag == null) ? 0 : wrFlag.hashCode());
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
        CommandItemInfo other = (CommandItemInfo) obj;
        if(commandItemCode == null) {
            if(other.commandItemCode != null)
                return false;
        }
        else if(!commandItemCode.equals(other.commandItemCode))
            return false;
        if(commandItemDesc == null) {
            if(other.commandItemDesc != null)
                return false;
        }
        else if(!commandItemDesc.equals(other.commandItemDesc))
            return false;
        if(commandItemName == null) {
            if(other.commandItemName != null)
                return false;
        }
        else if(!commandItemName.equals(other.commandItemName))
            return false;
        if(dataItemList == null) {
            if(other.dataItemList != null)
                return false;
        }
        else if(!dataItemList.equals(other.dataItemList))
            return false;
        if(paraType == null) {
            if(other.paraType != null)
                return false;
        }
        else if(!paraType.equals(other.paraType))
            return false;
        if(wrFlag == null) {
            if(other.wrFlag != null)
                return false;
        }
        else if(!wrFlag.equals(other.wrFlag))
            return false;
        return true;
    }
}
