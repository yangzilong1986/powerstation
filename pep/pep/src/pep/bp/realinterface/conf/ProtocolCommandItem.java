/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.realinterface.conf;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thinkpad
 */
public class ProtocolCommandItem {
    private String CommandCode;
    private Map<String,ProtocolDataItem> DataItems;

    public ProtocolCommandItem(){
        DataItems = new HashMap<String,ProtocolDataItem>();
    }

    public void AddDataItem(ProtocolDataItem dataItem){
        this.DataItems.put(dataItem.getDataItemCode(), dataItem);
    }

    /**
     * @return the CommandCode
     */
    public String getCommandCode() {
        return CommandCode;
    }

    /**
     * @param CommandCode the CommandCode to set
     */
    public void setCommandCode(String CommandCode) {
        this.CommandCode = CommandCode;
    }

    /**
     * @return the DataItems
     */
    public Map<String, ProtocolDataItem> getDataItems() {
        return DataItems;
    }

    /**
     * @param DataItems the DataItems to set
     */
    public void setDataItems(Map<String, ProtocolDataItem> DataItems) {
        this.DataItems = DataItems;
    }
}
