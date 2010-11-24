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



public class CircleDataItems {

    /**
     * @return the dataItemGroups
     */
    public List<DataItemGroup> getDataItemGroups() {
        return dataItemGroups;
    }
   
    private List<DataItemGroup> dataItemGroups;

    public CircleDataItems(){
        this.dataItemGroups = new ArrayList<DataItemGroup>();
    }

    public void AddDataItemGroup(DataItemGroup group){
        this.getDataItemGroups().add(group);
    }

    public void setDataItemGroups(List<DataItemGroup> dataItemGroups) {
        this.dataItemGroups = dataItemGroups;
    }
}
