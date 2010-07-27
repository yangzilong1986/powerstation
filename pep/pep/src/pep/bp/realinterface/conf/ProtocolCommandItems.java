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

    private Map<String, ProtocolCommandItem> commandItemMap;
    private List<ProtocolCommandItem> commandItems;

    public ProtocolCommandItems() {
        this.commandItemMap = new HashMap<String, ProtocolCommandItem>();
        this.commandItems = new ArrayList<ProtocolCommandItem>();
    }

    public void AddCommandItem(ProtocolCommandItem commandItem) {
        this.commandItems.add(commandItem);
    }

    /**
     * @return the CommandItems
     */
    public List<ProtocolCommandItem> getCommandItems() {
        return commandItems;
    }

    /**
     * @param CommandItems the CommandItems to set
     */
    public void setCommandItems(List<ProtocolCommandItem> CommandItems) {
        this.commandItems = CommandItems;
    }

    public ProtocolCommandItem getCommandItem(String CommandItemCode){
        return commandItemMap.get(CommandItemCode);
    }

    public ProtocolDataItem getDataItem(String DataItemCode) {
        String CommandCode = DataItemCode.substring(0, 8);
        ProtocolCommandItem commandItem = commandItemMap.get(CommandCode);
        if (null != commandItem) {
            return commandItem.getDataItemMap().get(DataItemCode);
        } else {
            return null;
        }
    }

    public void FillMap() {
        if (this.commandItemMap.size() == 0) {
            for (ProtocolCommandItem commandItem : commandItems) {
                commandItem.FillMap();
                this.commandItemMap.put(commandItem.getCommandCode(), commandItem);
            }
        }
    }
}
