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
    private List<CollectObject> CollectObjects = new ArrayList<CollectObject>();
    private List<CollectObject_TransMit> CollectObjects_Transmit = new ArrayList<CollectObject_TransMit>();

    public List<CollectObject> getCollectObjects(){
       return this.CollectObjects;
    }

    public List<CollectObject_TransMit> getCollectObjects_Transmit(){
       return this.CollectObjects_Transmit;
    }

    public void addCollectObject(CollectObject collectObject){
        if (null == CollectObjects)
            CollectObjects = new ArrayList<CollectObject>();
        this.CollectObjects.add(collectObject);
    }

    public void addCollectObject_Transmit(CollectObject_TransMit collectObject){
        if (null == CollectObjects)
            CollectObjects_Transmit = new ArrayList<CollectObject_TransMit>();
        this.CollectObjects_Transmit.add(collectObject);
    }

    public String toJson(){
        Map map = new HashMap();
       // map.put("ID",this.getID());
        map.put("CollectObjects", this.CollectObjects);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    public  MTOType getType(){
        return MTOType.GW_376;
    }

}
