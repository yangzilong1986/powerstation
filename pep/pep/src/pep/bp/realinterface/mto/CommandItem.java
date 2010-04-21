/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.mto;

import java.util.*;

/**
 *
 * @author Thinkpad
 */
public class CommandItem {

    private String identifier;             //命令项标识
    private Map datacellParam;             //数据单元格式<String, String>
    private List<AssistParam> assistParams;        //辅助项，例如二类数据的数据时标

    public CommandItem() {
    }

    public CommandItem(String identifier, Map datacellParam,List<AssistParam> assistParams) {
        this.identifier = identifier;
        this.datacellParam = datacellParam;
        this.assistParams = assistParams;
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

    public List<AssistParam> getAssistParams(){
        return this.assistParams;
    }

    public void setAssistParams(List<AssistParam> assistParams){
        this.assistParams = assistParams;
    }
}
