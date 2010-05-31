/*
 * 用于描述某一规约的数据项规则
 */

package pep.bp.realinterface.conf;

import java.util.HashMap;
import java.util.Map;
import pep.bp.realinterface.mto.CommandItem;

/**
 *
 * @author Thinkpad
 */
public class ProtocolCommandItems {
    private Map<String,ProtocolCommandItem> CommandItems;

    public ProtocolCommandItems(){
        this.CommandItems = new HashMap<String,ProtocolCommandItem>();
    }

    public void AddCommandItem(ProtocolCommandItem commandItem){
        this.CommandItems.put(commandItem.getCommandCode(), commandItem);
    }
    /**
     * @return the CommandItems
     */
    public Map<String, ProtocolCommandItem> getCommandItems() {
        return CommandItems;
    }

    /**
     * @param CommandItems the CommandItems to set
     */
    public void setCommandItems(Map<String, ProtocolCommandItem> CommandItems) {
        this.CommandItems = CommandItems;
    }

    public ProtocolDataItem getDataItem(String DataItemCode){
        String CommandCode = DataItemCode.substring(1, 8);
        ProtocolCommandItem commandItem= CommandItems.get(CommandCode);
        if (null != commandItem){
            return commandItem.getDataItems().get(DataItemCode);
        }
        else
            return null;
    }
}
