package org.pssframework.model.eparam;

import java.util.List;

import org.pssframework.base.BaseEntity;

public class TermParamInfo extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 9032576284693311344L;

    private Long termId;
    private String logialAddr;
    private String runStatus;
    private String curStatus;
    private String termType;
    private String commMode;
    private String channelType;
    private String protocolNo;
    private List<CommandItemInfo> commandItemList;

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getLogialAddr() {
        return logialAddr;
    }

    public void setLogialAddr(String logialAddr) {
        this.logialAddr = logialAddr;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getCommMode() {
        return commMode;
    }

    public void setCommMode(String commMode) {
        this.commMode = commMode;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public List<CommandItemInfo> getCommandItemList() {
        return commandItemList;
    }

    public void setCommandItemList(List<CommandItemInfo> commandItemList) {
        this.commandItemList = commandItemList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((channelType == null) ? 0 : channelType.hashCode());
        result = prime * result + ((commMode == null) ? 0 : commMode.hashCode());
        result = prime * result + ((commandItemList == null) ? 0 : commandItemList.hashCode());
        result = prime * result + ((curStatus == null) ? 0 : curStatus.hashCode());
        result = prime * result + ((logialAddr == null) ? 0 : logialAddr.hashCode());
        result = prime * result + ((protocolNo == null) ? 0 : protocolNo.hashCode());
        result = prime * result + ((runStatus == null) ? 0 : runStatus.hashCode());
        result = prime * result + ((termId == null) ? 0 : termId.hashCode());
        result = prime * result + ((termType == null) ? 0 : termType.hashCode());
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
        TermParamInfo other = (TermParamInfo) obj;
        if(channelType == null) {
            if(other.channelType != null)
                return false;
        }
        else if(!channelType.equals(other.channelType))
            return false;
        if(commMode == null) {
            if(other.commMode != null)
                return false;
        }
        else if(!commMode.equals(other.commMode))
            return false;
        if(commandItemList == null) {
            if(other.commandItemList != null)
                return false;
        }
        else if(!commandItemList.equals(other.commandItemList))
            return false;
        if(curStatus == null) {
            if(other.curStatus != null)
                return false;
        }
        else if(!curStatus.equals(other.curStatus))
            return false;
        if(logialAddr == null) {
            if(other.logialAddr != null)
                return false;
        }
        else if(!logialAddr.equals(other.logialAddr))
            return false;
        if(protocolNo == null) {
            if(other.protocolNo != null)
                return false;
        }
        else if(!protocolNo.equals(other.protocolNo))
            return false;
        if(runStatus == null) {
            if(other.runStatus != null)
                return false;
        }
        else if(!runStatus.equals(other.runStatus))
            return false;
        if(termId == null) {
            if(other.termId != null)
                return false;
        }
        else if(!termId.equals(other.termId))
            return false;
        if(termType == null) {
            if(other.termType != null)
                return false;
        }
        else if(!termType.equals(other.termType))
            return false;
        return true;
    }
}
