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

    public List<CollectObject> getCollectObjects(){
       return this.CollectObjects;
    }

    public void addCollectObject(CollectObject collectObject){
        if (null == CollectObjects)
            CollectObjects = new ArrayList<CollectObject>();
        this.CollectObjects.add(collectObject);
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
