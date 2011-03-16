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
    private Map<String,String> datacellParam;             //普通数据单元格式<String, String>
    private CircleDataItems circleDataItems;//循环数据项容器
    private List<AssistParam> assistParams;        //辅助项，例如二类数据的数据时标
    private int circleLevel ;

    public CommandItem() {
    }

    public CommandItem(String identifier, Map<String,String> datacellParam,List<AssistParam> assistParams) {
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

    public Map<String,String> getDatacellParam() {
        return datacellParam;
    }

    public void setDatacellParam(Map<String,String> datacellParam) {
        this.datacellParam = datacellParam;
    }

    public List<AssistParam> getAssistParams(){
        return this.assistParams;
    }

    public void setAssistParams(List<AssistParam> assistParams){
        this.assistParams = assistParams;
    }

    /**
     * @return the circleLevel
     */
    public int getCircleLevel() {
        return circleLevel;
    }

    /**
     * @param circleLevel the circleLevel to set
     */
    public void setCircleLevel(int circleLevel) {
        this.circleLevel = circleLevel;
    }

    /**
     * @return the circleDataItems
     */
    public CircleDataItems getCircleDataItems() {
        return circleDataItems;
    }

    /**
     * @param circleDataItems the circleDataItems to set
     */
    public void setCircleDataItems(CircleDataItems circleDataItems) {
        this.circleDataItems = circleDataItems;
    }
}
