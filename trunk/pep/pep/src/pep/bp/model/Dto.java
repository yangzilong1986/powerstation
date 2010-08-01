/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Thinkpad
 */
public class Dto {

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
     * @return the afn
     */
    public byte getAfn() {
        return afn;
    }

    /**
     * @param afn the afn to set
     */
    public void setAfn(byte afn) {
        this.afn = afn;
    }

    /**
     * @return the dataItems
     */
    public List<DtoItem> getDataItems() {
        return dataItems;
    }

    /**
     * @param dataItems the dataItems to set
     */
    public void setDataItems(List<DtoItem> dataItems) {
        this.dataItems = dataItems;
    }
    
    public class DtoItem {
        public int gp; //测量点号
        public String meterAddress;
        public boolean isMeterAddress;  //使用地址表示测量点
        public String dataTime;
        public String commandItemCode;
        public Map<String,String> dataMap;
        
        protected DtoItem(int gp, String dataTime, String commandItemCode){
            super();
            this.gp = gp;
            this.isMeterAddress = false;
            this.dataTime = dataTime;
            this.commandItemCode = commandItemCode;
            this.dataMap = new TreeMap<String,String>();
        }
        
         protected DtoItem(String meterAddress, String dataTime, String commandItemCode){
            super();
            this.meterAddress = meterAddress;
            this.isMeterAddress = true;
            this.dataTime = dataTime;
            this.commandItemCode = commandItemCode;
            this.dataMap = new TreeMap<String,String>();
         }
    }
    
    private String logicAddress;
    private byte afn;
    private List<DtoItem> dataItems;

    public Dto(String rtua,byte afn){
        this.logicAddress = rtua;
        this.afn = afn;
        this.dataItems = new ArrayList<DtoItem>();
    }


    public DtoItem AddDataItem(String meterAddress, String dataTime, String commandItemCode){
        DtoItem dtoItem = new DtoItem(meterAddress,dataTime,commandItemCode);
        this.dataItems.add(dtoItem);
        return dtoItem;
    }

    public DtoItem AddDataItem(int gp, String dataTime, String commandItemCode){
        DtoItem dtoItem = new DtoItem(gp,dataTime,commandItemCode);
        this.dataItems.add(dtoItem);
        return dtoItem;
    }
}
