package org.pssframework.model.eparam;

import org.pssframework.base.BaseEntity;

public class DataItemInfo extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3906456622159846597L;
    
    private String dataItemCode;        //数据项标识
    private String dataItemName;        //数据项名称
    private String dataItemDesc;        //数据项说明
    private String dataItemUnit;        //数据项单位
    private String dataItemFormat;      //数据项格式
    private String dataItemValue;       //数据项值
    private String showHtmlString;      //

    public String getDataItemCode() {
        return dataItemCode;
    }

    public void setDataItemCode(String dataItemCode) {
        this.dataItemCode = dataItemCode;
    }

    public String getDataItemName() {
        return dataItemName;
    }

    public void setDataItemName(String dataItemName) {
        this.dataItemName = dataItemName;
    }

    public String getDataItemDesc() {
        return dataItemDesc;
    }

    public void setDataItemDesc(String dataItemDesc) {
        this.dataItemDesc = dataItemDesc;
    }

    public String getDataItemUnit() {
        return dataItemUnit;
    }

    public void setDataItemUnit(String dataItemUnit) {
        this.dataItemUnit = dataItemUnit;
    }

    public String getDataItemFormat() {
        return dataItemFormat;
    }

    public void setDataItemFormat(String dataItemFormat) {
        this.dataItemFormat = dataItemFormat;
    }

    public String getDataItemValue() {
        return dataItemValue;
    }

    public void setDataItemValue(String dataItemValue) {
        this.dataItemValue = dataItemValue;
    }

    public String getShowHtmlString() {
        return showHtmlString;
    }

    public void setShowHtmlString(String showHtmlString) {
        this.showHtmlString = showHtmlString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataItemCode == null) ? 0 : dataItemCode.hashCode());
        result = prime * result + ((dataItemDesc == null) ? 0 : dataItemDesc.hashCode());
        result = prime * result + ((dataItemFormat == null) ? 0 : dataItemFormat.hashCode());
        result = prime * result + ((dataItemName == null) ? 0 : dataItemName.hashCode());
        result = prime * result + ((dataItemUnit == null) ? 0 : dataItemUnit.hashCode());
        result = prime * result + ((dataItemValue == null) ? 0 : dataItemValue.hashCode());
        result = prime * result + ((showHtmlString == null) ? 0 : showHtmlString.hashCode());
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
        DataItemInfo other = (DataItemInfo) obj;
        if(dataItemCode == null) {
            if(other.dataItemCode != null)
                return false;
        }
        else if(!dataItemCode.equals(other.dataItemCode))
            return false;
        if(dataItemDesc == null) {
            if(other.dataItemDesc != null)
                return false;
        }
        else if(!dataItemDesc.equals(other.dataItemDesc))
            return false;
        if(dataItemFormat == null) {
            if(other.dataItemFormat != null)
                return false;
        }
        else if(!dataItemFormat.equals(other.dataItemFormat))
            return false;
        if(dataItemName == null) {
            if(other.dataItemName != null)
                return false;
        }
        else if(!dataItemName.equals(other.dataItemName))
            return false;
        if(dataItemUnit == null) {
            if(other.dataItemUnit != null)
                return false;
        }
        else if(!dataItemUnit.equals(other.dataItemUnit))
            return false;
        if(dataItemValue == null) {
            if(other.dataItemValue != null)
                return false;
        }
        else if(!dataItemValue.equals(other.dataItemValue))
            return false;
        if(showHtmlString == null) {
            if(other.showHtmlString != null)
                return false;
        }
        else if(!showHtmlString.equals(other.showHtmlString))
            return false;
        return true;
    }
}
