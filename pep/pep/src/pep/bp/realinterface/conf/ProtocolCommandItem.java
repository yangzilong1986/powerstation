/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Thinkpad
 */
public class ProtocolCommandItem {

    private String commandCode;
    private String diffFormat;//上下报文格式不一致
    private List<ProtocolDataItem> dataItems;
    private Map<String, ProtocolDataItem> dataItemMap;

    public ProtocolCommandItem() {
        dataItems = new ArrayList<ProtocolDataItem>();
        dataItemMap = new TreeMap<String, ProtocolDataItem>();
    }

    public ProtocolCommandItem(String CommandCode,ProtocolDataItem dataItem) {
        this();
        this.commandCode = CommandCode;
        dataItems.add(dataItem);
    }

    public void AddDataItem(ProtocolDataItem dataItem) {
        dataItems.add(dataItem);
    }

    /**
     * @return the CommandCode
     */
    public String getCommandCode() {
        return commandCode;
    }

    /**
     * @param CommandCode the CommandCode to set
     */
    public void setCommandCode(String CommandCode) {
        this.commandCode = CommandCode;
    }

    /**
     * @return the DataItems
     */
    public List<ProtocolDataItem> getDataItems() {
        return dataItems;
    }

    /**
     * @param DataItems the DataItems to set
     */
    public void setDataItems(List<ProtocolDataItem> DataItems) {
        this.dataItems = DataItems;
    }

    public Map<String, ProtocolDataItem> getDataItemMap() {
        return dataItemMap;
    }

    public void FillMap() {
        if (dataItemMap.size() == 0) {
            for (ProtocolDataItem dataItem : dataItems) {
                this.dataItemMap.put(dataItem.getDataItemCode(), dataItem);
            }
        }
    }

    /**
     * @return the DiffFormat
     */
    public String getDiffFormat() {
        return diffFormat;
    }

    /**
     * @param DiffFormat the DiffFormat to set
     */
    public void setDiffFormat(String DiffFormat) {
        this.diffFormat = DiffFormat;
    }

}
