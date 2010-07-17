/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Thinkpad
 */
public class ProtocolCommandItem {

    private String CommandCode;
    private String DiffFormat;//上下报文格式不一致
    private List<ProtocolDataItem> DataItems;
    private Map<String, ProtocolDataItem> DataItemMap;

    public ProtocolCommandItem() {
        DataItems = new ArrayList<ProtocolDataItem>();
        DataItemMap = new TreeMap<String, ProtocolDataItem>();
    }

    public ProtocolCommandItem(String CommandCode,ProtocolDataItem dataItem) {
        this();
        this.CommandCode = CommandCode;
        DataItems.add(dataItem);
    }

    public void AddDataItem(ProtocolDataItem dataItem) {
        DataItems.add(dataItem);
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
    public List<ProtocolDataItem> getDataItems() {
        return DataItems;
    }

    /**
     * @param DataItems the DataItems to set
     */
    public void setDataItems(List<ProtocolDataItem> DataItems) {
        this.DataItems = DataItems;
    }

    public Map<String, ProtocolDataItem> getDataItemMap() {
        return DataItemMap;
    }

    public void FillMap() {
        if (DataItemMap.size() == 0) {
            for (ProtocolDataItem dataItem : DataItems) {
                this.DataItemMap.put(dataItem.getDataItemCode(), dataItem);
            }
        }
    }

    /**
     * @return the DiffFormat
     */
    public String getDiffFormat() {
        return DiffFormat;
    }

    /**
     * @param DiffFormat the DiffFormat to set
     */
    public void setDiffFormat(String DiffFormat) {
        this.DiffFormat = DiffFormat;
    }

}
