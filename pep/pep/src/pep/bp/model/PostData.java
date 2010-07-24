/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Thinkpad
 */
public class PostData {
    private String logicAddress;
    private ArrayList gpArray;
    private String DataTime;
    private byte afn;
    private String commandItemCode;
    private TreeMap<String,String> dataMap;

    public PostData(){
        this.dataMap= new TreeMap<String,String>();
        gpArray =new ArrayList();
    }

    public void AddData(String dataItemCode,String dataValue){
        if(!this.dataMap.containsKey(dataItemCode))
            this.dataMap.put(DataTime, dataValue);
    }

    public void AddGP(int gpSn){
        this.gpArray.add(gpSn);
    }
    /**
     * @return the logicAddress
     */
    public String getLogicAddress() {
        return logicAddress;
    }

    /**
     * @param logicAddress the logicAddress to set
     */
    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }


    /**
     * @return the DataTime
     */
    public String getDataTime() {
        return DataTime;
    }

    /**
     * @param DataTime the DataTime to set
     */
    public void setDataTime(String DataTime) {
        this.DataTime = DataTime;
    }

    /**
     * @return the afn
     */
    public int getAfn() {
        return afn;
    }

    /**
     * @param afn the afn to set
     */
    public void setAfn(byte afn) {
        this.afn = afn;
    }

    /**
     * @return the commandItemCode
     */
    public String getCommandItemCode() {
        return commandItemCode;
    }

    /**
     * @param commandItemCode the commandItemCode to set
     */
    public void setCommandItemCode(String commandItemCode) {
        this.commandItemCode = commandItemCode;
    }
}
