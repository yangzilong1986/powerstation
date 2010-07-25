/*
 * 用于描述某一规约的数据项规则
 */
package pep.bp.realinterface.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Thinkpad
 */
public class ProtocolCommandItems {

    private Map<String, ProtocolCommandItem> CommandItemMap;
    private List<ProtocolCommandItem> CommandItems;

    public ProtocolCommandItems() {
        this.CommandItemMap = new HashMap<String, ProtocolCommandItem>();
        this.CommandItems = new ArrayList<ProtocolCommandItem>();
    }

    public void AddCommandItem(ProtocolCommandItem commandItem) {
        this.CommandItems.add(commandItem);
    }

    /**
     * @return the CommandItems
     */
    public List<ProtocolCommandItem> getCommandItems() {
        return CommandItems;
    }

    /**
     * @param CommandItems the CommandItems to set
     */
    public void setCommandItems(List<ProtocolCommandItem> CommandItems) {
        this.CommandItems = CommandItems;
    }

    public ProtocolCommandItem getCommandItem(String CommandItemCode){
        return CommandItemMap.get(CommandItemCode);
    }

    public ProtocolDataItem getDataItem(String DataItemCode) {
        String CommandCode = DataItemCode.substring(0, 8);
        ProtocolCommandItem commandItem = CommandItemMap.get(CommandCode);
        if (null != commandItem) {
            return commandItem.getDataItemMap().get(DataItemCode);
        } else {
            return null;
        }
    }

    public void FillMap() {
        if (this.CommandItemMap.size() == 0) {
            for (ProtocolCommandItem commandItem : CommandItems) {
                commandItem.FillMap();
                this.CommandItemMap.put(commandItem.getCommandCode(), commandItem);
            }
        }
    }
}
