package peis.interfaces.hicollect;

import java.util.Map;

/**
 * 命令项
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20090625
 */
public class CommandItem {
    private String identifier;             //命令项标识
    private Map datacellParam;             //数据单元格式<String, String>
	
    public CommandItem() {
    }
	
	public CommandItem(String identifier, Map datacellParam) {
        this.identifier = identifier;
        this.datacellParam = datacellParam;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Map getDatacellParam() {
        return datacellParam;
    }

    public void setDatacellParam(Map datacellParam) {
        this.datacellParam = datacellParam;
    }
}
