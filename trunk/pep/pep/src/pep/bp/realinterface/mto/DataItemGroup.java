/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.mto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thinkpad
 */
public class DataItemGroup {

    private List<DataItem> dataItemList;

    public DataItemGroup() {
        this.dataItemList = new ArrayList<DataItem>();
    }

    public void AddDataItem(DataItem dataItem) {
        this.getDataItemList().add(dataItem);
    }

    /**
     * @return the dataItemList
     */
    public List<DataItem> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(List<DataItem> dataItemList) {
        this.dataItemList = dataItemList;
    }
}
