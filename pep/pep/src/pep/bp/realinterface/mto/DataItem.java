/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.realinterface.mto;

/**
 *
 * @author Thinkpad
 */
public class DataItem {
    private String dataItemCode;
    private String dataItemValue;

    public DataItem(String dataItemCode,String dataItemValue){
        this.dataItemCode = dataItemCode;
        this.dataItemValue = dataItemValue;
    }
    /**
     * @return the dataItemCode
     */
    public String getDataItemCode() {
        return dataItemCode;
    }

    /**
     * @param dataItemCode the dataItemCode to set
     */
    public void setDataItemCode(String dataItemCode) {
        this.dataItemCode = dataItemCode;
    }

    /**
     * @return the dataItemValue
     */
    public String getDataItemValue() {
        return dataItemValue;
    }

    /**
     * @param dataItemValue the dataItemValue to set
     */
    public void setDataItemValue(String dataItemValue) {
        this.dataItemValue = dataItemValue;
    }
}
