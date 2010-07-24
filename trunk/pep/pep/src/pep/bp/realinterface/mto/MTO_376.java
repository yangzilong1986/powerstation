/*
 * 国网系规约----消息传输对象
 */

package pep.bp.realinterface.mto;
import java.util.*;
import net.sf.json.*;
/**
 *
 * @author Thinkpad
 */
public class MTO_376 extends MessageTranObject{
    private List<CollectObject> collectObjects = new ArrayList<CollectObject>();
    private List<CollectObject_TransMit> collectObjects_Transmit = new ArrayList<CollectObject_TransMit>();

    public List<CollectObject> getCollectObjects(){
       return this.collectObjects;
    }

    public List<CollectObject_TransMit> getCollectObjects_Transmit(){
       return this.collectObjects_Transmit;
    }

    public void addCollectObject(CollectObject collectObject){
        if (null == getCollectObjects())
            setCollectObjects(new ArrayList<CollectObject>());
        this.getCollectObjects().add(collectObject);
    }

    public void addCollectObject_Transmit(CollectObject_TransMit collectObject){
        if (null == getCollectObjects())
            setCollectObjects_Transmit(new ArrayList<CollectObject_TransMit>());
        this.getCollectObjects_Transmit().add(collectObject);
    }

    public String toJson(){
        Map map = new HashMap();
       // map.put("ID",this.getID());
        map.put("CollectObjects", this.getCollectObjects());
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    public  MTOType getType(){
        return MTOType.GW_376;
    }

    /**
     * @param collectObjects the collectObjects to set
     */
    public void setCollectObjects(List<CollectObject> collectObjects) {
        this.collectObjects = collectObjects;
    }

    /**
     * @param collectObjects_Transmit the collectObjects_Transmit to set
     */
    public void setCollectObjects_Transmit(List<CollectObject_TransMit> collectObjects_Transmit) {
        this.collectObjects_Transmit = collectObjects_Transmit;
    }

}
